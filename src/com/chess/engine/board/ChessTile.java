package com.chess.engine.board;
import java.util.Map;
import java.util.HashMap;
// dit moet je handmatig importeren
import com.google.common.collect.ImmutableMap;
// we hebbem die CHESSTILE class onveranderlijk gemaakt // immutable
// wat bereiken we hiermee met een immutable / onveranderlijke klass
// we kunnen als we willen alle lege tiles creeren die valide zijn upfront / vooraf en wanneer we eentje willen
// ophalen ( tegel ) inplaats van om een nieuwe te creeren dan kunnen we het gewoon zoeken of opkijken in de cache


// we gaan nu concentreren op de notie van immutability (effective java immutability)
// boek heet : Effective Java door Joshua Bloch
// immutable == onveranderlijk
// een immutable // onveranderlijk object is heel simpel, een immutable object kan in een staat zijn ,
// de staat in welke het werdt gecreerd als je ervoor zorgt dat alle constructors klasse invariante instellen
// dan is het gegarandeerd dat deze invarianten voor altijd waar zullen zijn

// mutable veranderlijke objecten kunnen willekeurige complexe toestandsruimten hebben
// Als de documentatie geen nauwkeurige beschrijving geeft van de statusovergangen die door veranderlijke
// methoden worden uitgevoerd, kan het moeilijk of onmogelijk zijn om een ​​betrouwbaarheidsklasse
// voor veranderlijke methoden te gebruiken.

// Onveranderlijke objecten zijn inherent dreigingsveilig, ze vereisen geen synchronisatie
// ze kunnen niet beschadigd raken door meerdere threads die er toegang toe hebben

//Dit is veruit de gemakkelijkste manier om threadveiligheid te benaderen. In feite kan geen
// enkele thread ooit het effect van een andere thread op een onveranderlijk object waarnemen.
// daarom kan een onveranderlijk object vrijelijk gedeeld worden

// Onveranderlijke klassen zouden hier hun voordeel mee moeten doen door cliënten aan te moedigen
// bestaande instanties te hergebruiken waar mogelijk. Een manier om dit te doen is door openbare
// statische eindconstanten te verstrekken voor vrijelijk gebruikte waarden.

// public static final Complex ZERO = new Complex(0,0);
// public static final Complex ONE = new Complex(1,0);
// public static final Complex I = new Complex(0,1);

import com.chess.engine.pieces.Piece;

// we gaan onze code organiseren in logische packages
// com.chess.engine.board hierin ga ik die tile zetten
// com.chess.engine.pieces hierin ga ik mijn stukken zetten


// we kunnen een abstracte class niet initialisern (new ChessTile kan niet je kan geen
// object maken van een abstracte class)
public abstract class ChessTile {
    // we hebben geen Setter methode gedefinieerd op die tileCoordinate  en we hebben die tile coordinaate niet
    // gedefiniierd tot die zienbaarheid van die coordinate is
    // dus je kan een instantie creeren // object  van een tile coordinaat maken en refereer die field member erop
    // en het veranderen // mutate it en we willen dat voorkomen voor klanten die onze API zullen gebruiken
    // met orotected kan je het alleen gebruiken in de package zelf en in die subsclassen en final zodat je het niet
    // meer kan vernaderen
    // als deze eigenschap wordt gestuurd in de constructor mag je het niet meer veranderen
    // je kan het allee een keer zetten in die constructor
    protected final int tileCoordinate;

    // dan gaan we een private static methode = createAllPossibleEmptyTiles():
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
//        return emptyTileMap;
        // java schreeuwt omdat die ImmutableMap class niet voorkomt in een JDK het is een onderdeerl van
        // de Guava library dat is een thrid party library gegeven door Google
        // wat we willlen is een ummitable of onveranderlijke map een map is net een container
        // als ik een immutable of onveranderlijke map wilt is het niet voldoende voor mij om die emptyTileMap
        // te retourneren want nadat ik het retourneer kan iemadn zeggen bijvoorbeeld CLEAR() die emptyTileMap
        // het doel is als je die emptyTileMap retourneert moet niemand anders het kunnen veraderne
        // dus google en hun guava library zullen ervoor zorgen dat het niet wordt veranderdt
        // wat we hier hebben gedaan elke mogelijke empty tile / tegel dat kan bestaan heb ik vooraf gecreeerd
        // als ik die laatste chess tile wou dan doe ik een get(63) en als ik de eerste wou doe ik een get(0)
//        Collections.unmodifiableMap(emptyTileMap);
        // dit zal je een unmodifiable map geven
        return ImmutableMap.copyOf(emptyTileMap);
    }

    // we hebben die constructor private gemaakt zodat als iemand een nieuwe tile probeert te maken zal het zijn via
    // een FACTORY METHODE die ik ga geven
    // als iemand een tile probeert te maken moeten ze het doen via deze methode
    // en als ze een lege tile willen kunnen ze een lege gecachte tile krijgen
    // anders gaan ze een nieuwe bezette tegel creeren
    public static ChessTile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    // die constructor gaat ervoor zorgen dat we een individuele tile kunnen maken of creeeren
    private ChessTile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    // deze methode zal handig zijn om te weten of een tile of tegel al bezet is
    // een abstracte methode kan je alleen maken in een abstracte class
    // die implementatie moet je zetten in die subclass die het zal implementeren
    public abstract boolean isTileOccupied();

    // om een stuk te halenn op een specieke tile
    // als het een tile is die vol is zal het dat retourneren
    // als het een lege tile is zal het null retournerne
    public abstract Piece getPiece();

    // class voor die Empty tile
    public static final class EmptyTile extends ChessTile {

        private EmptyTile(final int coordinate) {
            // dit zal die super class aanroepen met die tile coordinaat
            super(coordinate);
        }
        // de lichaam van een abstracte methode moet je maken in die subclasse
        @Override
        public boolean isTileOccupied() {
            // als die tile / tegel false is retourneer je false
            return false;
        }
        @Override
        public Piece getPiece() {
            // omdat er geen stuk is op een lege tile / tegel
            return null;
        }
    }

    // nu gaan we die Tegel / tile definieren die niet leeg is
    public static final class OccupiedTile extends ChessTile {
        // enigste verschill is dat er wel een stuk is gedefinieerd op een een bezette tiel of tegel
        // die eigenschap die je kan veranderen is Piece pieceOnTile
//        Piece pieceOnTile; // eigenschap
        // er is geen andere manier om deze eigenschap aan te roepen zonder die methode getPiece aan te roepen
        private final Piece pieceOnTile; // eigenschap


        // die constructor voor die Occupied tile gaat die coordinaaat plus die stuk nemen
        private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            // we gaan die superclass constructor aanroepen om die tile / tegel zal instantieerenn
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        @Override
        public boolean isTileOccupied() {
            // als het vol is retourneer true
            return true;
        }
        @Override
        public Piece getPiece() {
            // retourneert de huidige stuk op de tegel
            return this.pieceOnTile;
        }
    }

}

/* iedereen weet dat er in een schaakbord 64 hokjes / tiles zijn
* in jouw gedachte zou het lijken op een twee dimentionele array voorbeeld
* een 8 bij 8
* maar in deze voorbeeld gaan we een abstractie creeren voor een  eenmalige
* schaak tile / tegel en we gaan die chess tile / tegel nummeren van o tot 63
* als je telt vanaf 0 tot 63 heb je in totaal 64 tegels of tiles
* als je gaat doen van 0 tot 64 dan heb je in totaal 65 tegels en dat is fout
* vb je wilt 5 tegels dan kan je doen van 0 naar 4 (0, 1, 2, 3, 4) of van
* 1 naar 5 (1,2,3,4,5)  */

/* een tile is een tegel */
/* 28-04-2025 ik ben bij de eerste video en ik ben gestopt bij de tijd 2.10 */

/* we kunnen ook een ander IDEA gebruiken zoals VSCode maar we gebruiken intelliJ
* voor deze particuliere project en in deze IDEA kan je jouw code schrijven en je test doen
* en ook debugging kan je erin doen  */
/* we kunnen ook een */

/* we kunnen het op verschillende manieren doen 1. we kunne een class hebben ChessTile
* die alles representeert of we kunnen een Polymorphic behavior toepassen dan kunnen we een
* gemeenschappelijke abstracte class ChessTile gebruiken en twee subclassen een class dat een
* lege tile zou presenteren ( tile of tegel dat geen stukken erin heeft)  en de andere tweee representeren
* ChessTiles / tegels die bezet zijn  */

/* welke methodes moeten we eerst implemeteren */


/* nu gaan we een abstracte overeenkomstige class maken genaamd Piece */
