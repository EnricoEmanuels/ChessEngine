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

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

    // dit zijn al die mogelijke schijnen die die knight kan doen als je goed telt kom je op 8 schijnen
    // via onze huidige positie van die knight
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17 };
    // we gingen kijk naar een wikipedia entry voor een knight

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    // we kunnen zeggen dat onze huidige positie van de knight een nummer is en als we de chessmap zullen  nummereren
    // van 0 naar 64 vb 0 1 2 3 4 5 6 7
    //                  9 10 11 12 13 14 15
    // laten we zeggen die knight is op ChessTile 34 dan kunnen we een van de schijnen berekenenen dan doen we chesstle 34 - 6
    // dan is dit alvast 1 schijn en dat is chesstile 28 en dit is een legale schijn dus je chesstile - 6 is
    // alvast 1 legale schijn
    // maar je moet ook in gedachte houden dan als die - 6 eindig op een pion van jou is het geen legale schijn
    // van een je kan je eigen pion niet eten of als je knight is op B6 kan je na - 6 belanden
    // op een chestile die nieteens bestaat dsu dat moet je ook in gedachte houden

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        // het is gedeclareert buiten die for loop maar het wordt maar 1 keer gebruikt in die for loop daarna niet meer
        // dus we gaan het binnen die for loop declarenen omdat we het maar een keer declareren
//        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        // we gaan uberhaupt loopem door alle 8 die mogelijke schijnen
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            // er zullen ook excepties zijn maar we ons nog niet druk maken daarover
            // deze varaibel candidateDestinationCoordinate representeert de huidige uitkomsten
            // dit is de huidige cordinate currentCandidate
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            // als die candidateDestinationCoordinate een legale move of uitkomst is van de dan moet het true zijn in
            // die if clause
            if ( BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset) ) {
                    continue;
                }
                // als het niet buiten balans gaat moeten we zeggen
                final ChessTile candidateDestinationChessTile = board.getTile(candidateDestinationCoordinate);
                // als die uitkomst van die candidateDestinationCoordinate niet bezet is dan gaan we dit toevoegen
                // aan onze legale bewwegingen // als het niet leeg is // non attacking move
                if (!candidateDestinationChessTile.isTileOccupied()) {
                    // we zetteen voor nu dit omdat we momenteel nogniets hebben in onze classse move
                    // we moeten ook parameters aangeven
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    // als het leeg is
                    // dan moeten we de stuk zoeken op die destinatie
                    final Piece pieceAtDestination = candidateDestinationChessTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    // als die huidige piece alliance knight als het niet gelijk is aan die peice aliance is van
                    // pieceAlliance die is op die huidige uitkomst
                    // dan weten we dat dit een vijandige stuk is
                    if (this.pieceAlliance != pieceAlliance) {
                        // dit gaat een attacking move zijn
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }

                }
            }


        }
        // hier gaan we alle legale bewegingen retournern
        return ImmutableList.copyOf(legalMoves);
//        return List.of();
    }
    // als onze knight was op a4 hoe zouden we die -10 proberen dan zie je dat het te ver is
    // als onze knight op die eerste kolom is van die schaakboard
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        // een manier zoeken op alle tiles te pakken
        // we introducerne een array van booleans van boardutils en we noemen het firstcolumn
        // als mijn eerste positie valt in de huidige eerste kolom
        // als die knight op de eerste kolom is wil je niet dat het de zelfde stappen neemt zoals die methode boven
        // van calculateLegalMoves
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10  || candidateOffset == 6 );
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17 );
    }

}

/* verder met vid  7 bischop */
