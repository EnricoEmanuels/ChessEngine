package com.chess.engine.board;

public class BoardUtils {

    // en alle values zijn false met uitzondering // coloumn is vertikaal
    // ik kan een unieke methode creeren voor elk van die boolen Arrays
    public static final boolean[] FIRST_COLUMN = initColumn(0) ;
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6) ;
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;


//    public static final boolean FIRST_COLUMN = null ; // null wilt lastig doen dan ga ik het gewoon true geven

    private BoardUtils() {
        throw new RuntimeException("You can't instantiate Me.");
    }

    // en dit neem een columnnummer en het creer 64 mogelijkheiden
    // voor elke column dat je het geeft vb column nummer 1
    // het gaat eerst zetten column 1 = true en dan zet het 8 vakken in die column
    // deze methode zal die boolean arrays initialiseren en ze uieindelijk vullen in een particuliere kolom
    private static boolean[] initColumn(int columnNumber) {
        // declareer die boolean array
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);

        return column;
    }

    // toen het bij die knigh was was het private
    public static boolean isValidTileCoordinate( final int coordinate) {
        // dit gaat zoeken of die tile in de chessboard is of er buitenn
        // dit gat niet alleen toegankeijk zijn tot die knight maar ook tot andere stukken
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
