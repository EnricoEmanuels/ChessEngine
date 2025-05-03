package com.chess.engine;

public enum Alliance {
    // ik wist niet dat je een methode kon zetten op een constante
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
// we willen een methode zetten in de alliance classe die ervoor zal zorgen dat ze zwart of wit presenteren

/* voor java 5 als je constanten wou declaren zou je het zo doen
* public static final int getal = 0; maar dit is niet type safe Omdat een seizoen slechts een int is, kunt u elke
*  andere int-waarde doorgeven waarvoor een seizoen vereist is
 */

/* maar je kan constanten creeren met een enum en je kan ook een gedrag zetten op een enum */