package com.chess.engine;

public enum Alliance {
    WHITE,
    BLACK
}
/* voor java 5 als je constanten wou declaren zou je het zo doen
* public static final int getal = 0; maar dit is niet type safe Omdat een seizoen slechts een int is, kunt u elke
*  andere int-waarde doorgeven waarvoor een seizoen vereist is
 */

/* maar je kan constanten creeren met een enum en je kan ook een gedrag zetten op een enum */