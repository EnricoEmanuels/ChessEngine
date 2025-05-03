package com.chess.engine.pieces;
import com.chess.engine.Alliance;

import java.util.Collection;

import com.chess.engine.board.Move;
import com.chess.engine.board.Board;


public abstract class Piece {
    /* we gaan beginnen elke piece heeft een positie een tile of tegel coordinaat waarop het bezet is */
    protected final int piecePosition;
    // die stukje zal zwart of wit zijn
    protected final Alliance pieceAlliance;
    /* die alliance zal niet alleeb voordelig zijn voor die stukken maar ook voor die spelers */
    protected final boolean isFirstMove;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO more work here !!
        this.isFirstMove = false;
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

//    public final int getPiecePosition() {
//        return piecePosition;
//    }
    // ik ben gestopt bij video 3 tijd 6.00

    // methode maken die calculeert die legale moves
    // en dit zal een collectie retoureren van moves / schijnen we kunnen een set retourneren omdat het
    // ongeordend is
    // elke stuk gaan zijn eigen implemetatie hebben over calculateLegalMoves
    // dsu kight , bischop zal zijn eigen implementatie hebben erover
    // deze methode kijk naar de gegeven schaakbord en het zal die legale bewegingen tellen van die stuk zelf
    // dus voor elke concrete stuk KNIGHT, BISCHOP gaat het zijn eigen bewegingen hebben
    // voor elke aparte stuk zullen we die methdode overriden @Override
    public abstract Collection<Move> calculateLegalMoves(final Board board);
    // we zullen verder gaan met video 4 vanaf het begin
}
