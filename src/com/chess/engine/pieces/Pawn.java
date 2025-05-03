package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

// zo die pawn heeft veel verschillende dingen
// die pawn kan in het begin twee tegels springen( dit kan alleen vanaf het begin) of het kan springen met 1 tegel
// als een pawn beweg is het naar voren maar als het eet is het schuin
// en je kan ook AN PASSANT doen met een pawn
// als een pawn promveert kan het elke stuk worden behalve een king
// plsu die witte pawn gaan naar de zwarte kant en die zwarte kawn kunnen alleen gaan naar de witte kan ze kunnen
// naar de tegenovergestelde richting gssn
public class Pawn extends Piece {

    // optellen met 8 om naar voren te bewegen
    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    Pawn(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            // dit zal die offset optellen met die piece positiion
            // maar deze stukje code zal alleen werken voor die black pawns want
            // die zwarte pawn bewegen in de positiefe 8 directie
            // voor wit aouden we -8 toeoegen
            // voor black gaan we gewoon 8 toepassen
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
            // we moeten ook kijken of die tegel vrij is en als het niet vrij is gaan we het skippen
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            // in de toekomst hebben we 16, 7, 9
            // we zeggen als het niet bezet is
            // als die tegel waarnaar je wilt gaan niet leeg is gaan we verder
            // deze move kijk naar de non attacking move
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //TODO more work to do here (deal with promotions)
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                // die else if let op die pawn jump
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                      (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                      (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                // wanner we wit zijn moeten we zijn op de specifieke lijn
                // als die e2 pawn na voren wil gaan moeten we ervoor zorgen dat die e3 pawn niet bezet is
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied() ) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                // wat we in die komende else if zetten is dat als je wit bent wilt schuin eten kan je niet doen -7 maar het moet zijn -9
                // met zwart als je op de eerste kolom ben kan je niet schuin eten naar rechts dan kan je niet schuin eten met 7 maar met 9 kan je wel chuin eten is een exceptie
            } else if (currentCandidateOffset == 7 &&
                        !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()  ||
                         (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack() )  ))  ) {
                // als ik een witte pawn ben en ik ben op de eerste kolom dan kan ik niet links attacken maar een zwarte pawn kan dat wel
                // black of white op de eerste kolom 1 of 8 ga je een paar excepties zijn

                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more to do here (atackig to a promotion)
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

             // dit gaat kijken naar die excepties op de 8 de column met die witte en zwarte pawn
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()  ||
                            (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack() )  )) ) {

                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more to do here (atackig to a promotion)
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
                
            }
            // wat als we op D7 waren en die pawn gaat promoten
        }

        return ImmutableList.copyOf(legalMoves);
    }
}

/*we hebben die pawn gedaan die queen die rook die bischop de volgende keer gaan we die king en verder gaan met die pawn 13==*/