/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Ship {

    private int length;
    private int row, colum;
    private int startingRow, startingColum;
    private boolean isHorizontal = true;
    private int leftPieces = 0;

    /**
     * \
     * @param length 
     */
    public Ship(int length) {
        this.length = length;
    }

    public Ship(int length, int row, int colum) {
        this.length = length;
        this.row = row;
        this.colum = colum;
        startingRow = row;
        startingColum = colum;
    }

    /**
     * 
     */
    public Ship() {
    }

    /**
     * 
     * @param length 
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 
     * @return 
     */
    public int getLength() {
        return length;
    }

    public boolean containPiece(int x, int y) {
        if (isHorizontal) {
            //  System.out.println(startingColum <= y && startingColum + length > y);
            //  System.out.println("startingColum = " + startingColum);
            // System.out.println("finish colum = " + (startingColum + length));
            // System.out.println("y= " + y);
            if (startingColum <= y && startingColum + length > y && startingRow == x) {
                // System.out.println("contain");
                return true;
            }

        } else {

            if (startingRow <= x && startingRow + length > x && startingColum == y) {
                return true;
            }
        }

        return false;
    }

    public void witchPiecesHasFound(Board board) {
        leftPieces = 0;
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                Piece piece = board.getPieces()[i][j];
                if (containPiece(piece.getRow(), piece.getColum())) {
                    if (piece.getBackground() == Board.canPutHereColor) {
                        leftPieces++;
                    }
                }

            }
        }

    }

    /**
     * 
     * @return 
     */
    public int getColum() {
        return colum;
    }

    /**\
     * 
     * @param colum 
     */
    public void setColum(int colum) {
        this.colum = colum;
    }

    /**
     * 
     * @return 
     */
    public int getRow() {
        return row;
    }

    /**
     * 
     * @param row 
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * 
     * @param isHorizontal 
     */
    public void setIsHorizontal(boolean isHorizontal) {

        this.isHorizontal = isHorizontal;
    }

    /**
     * 
     * @return 
     */
    public boolean getIsHorizontal() {

        return isHorizontal;
    }

    public int getStartingColum() {
        return startingColum;
    }

    public void setStartingColum(int startingColum) {
        this.startingColum = startingColum;
    }

    public int getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(int startingRow) {
        this.startingRow = startingRow;
    }
}
