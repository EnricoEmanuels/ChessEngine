package com.chess.engine.pieces;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.ChessTile;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*  als die bishop is op D4 kan het alleen schuin bewegen, schuin of scheeft in twee richtingen
* legale schijnen zijn in 4 richtingen
* bischop op D4 als je doet -7 dan kan je alvast 1 legale move krijgen en als je weer -7 doet
* dan krijg je weer die een ander legale beweging die rechts schuin is er is een pattroon
* rechts boven of rechts beneden +- 7 dan krijg je die legale schijnene
* en als je die andere kant wilt dan kan je doen -+ 9 dan krijg je die ander kant van je bischop   */
public class Bischop extends Piece {
    // die bischop kan schuin gaan van links naar rechts met deze optellingen en aftrekkingen
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    Bischop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // loopen door die legale bewegingen of loopen via die vectors
        // eerste vector is -9
        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            // de destinatie uitkomst die je zal hebben voor je bischop is de som van
            // de huidige stuk en de
            // als je bishop is op D4 dan ben je op tegel of chesstile 34
            int candidateDestinationCoordinate = this.piecePosition;
            // eerst kijken op die positie valide is je bent toch nog op je een tegel van je bord
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }

                // als het valide is kan je het optellen met een vector
                // dus dit zegt gewoon je nieuwe candidateDestinationCoordinate = (huidige ) candidateDestinationCoordinate + candidateCoordinateOffset
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    // als het niet buiten balans gaat moeten we zeggen
                    final ChessTile candidateDestinationChessTile = board.getTile(candidateDestinationCoordinate);
                    // als die uitkomst van die candidateDestinationCoordinate niet bezet is dan gaan we dit toevoegen
                    // aan onze legale bewwegingen // als het niet leeg is // non attacking move
                    // als die legale beweging niet leeg is dan kunnen we het gebruiken als legale beweging
                    if (!candidateDestinationChessTile.isTileOccupied()) {
                        // we zetteen voor nu dit omdat we momenteel nogniets hebben in onze classse move
                        // we moeten ook parameters aangeven
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        // en als het bezet is is het bezet door de vijandige stukken
                        // als het leeg is
                        // dan moeten we de stuk zoeken op die destinatie
                        final Piece pieceAtDestination = candidateDestinationChessTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        // als die huidige piece alliance knight als het niet gelijk is aan die peice aliance is van
                        // pieceAlliance die is op die huidige uitkomst
                        // dan weten we dat dit een vijandige stuk is
                        if (this.pieceAlliance != pieceAlliance) {
                            // dit gaat een attacking move zijn
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        // als het bezet is door een vijandige of vrijdelijke stuk na dat willen we toch breken uit die loop
                        // van dan wil je kijken na die andere vectors je moet die andere vectors ook verifieren

                        break;


                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    /* als die bischop is op de eerste kolom dan het alleen rechts schuin of rechts links gaan
    * als je bischop was op A4 en je wou + 7 gebruiken kijk
    * van af links boven is 0 dan tel je 0 -> 1 en zo ga je verder dan als he + 7 doe dan ga je plotseling zien dat je een
    * bent op een illegale beweging en die -9 gaat ook lastig doen   */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset ) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);

    }
    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset ) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);

    }
    /* als we meer hebben kunnen we UNIT test doen pas als we nog meer dingen hebben in onze code  */
    /* we moeten beginnen met video 8 the Rookkk  */

}
