package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    // we willen de board tracken
    final Board board;
    // we willen de piece ook tracken
    final Piece movedPiece;
    // we willen weten waar die piece is gegaan
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move {
        // in die attack classen willen we ook letten op die stuk dat wordt aangevallen
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }


}
