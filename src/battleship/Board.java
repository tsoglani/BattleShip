/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Board extends JPanel {
    
    public static final int SIZE = 10;
    private Piece[][] pieces = new Piece[SIZE][SIZE];
    public static final Color shipColor = Color.BLACK;
    public static final Color shipErrorColor = Color.red;
    public static final Color fieldColor = Color.cyan;
    public static final Color canPutHereColor = Color.green;
    public static Ship currentShip;
    private ArrayList<Ship> ships = new ArrayList<Ship>();
    private SelectShipsPanel selectShipsPanel;
    private ArrayList<Point> pointWithShip = new ArrayList<Point>();
    private boolean isUserPlayer;
    public static boolean isUserTurn = true;
    private Fr frame;
    public int countScore;
    public static final int WINNING_SCORE = 15;
    private ArrayList<Piece> freePoints = new ArrayList<Piece>();

    /**
     * 
     */
    public Board(boolean isUserPlayer) {
        this.isUserPlayer = isUserPlayer;
        
        
        setLayout(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                pieces[i][j] = new Piece(fieldColor, i, j);
                add(pieces[i][j]);
            }
        }
        
        
        
        
    }

    /**
     * 
     * @param ship 
     */
    public void addShip(Ship newShip, Color color, boolean isEnteringPiece) {
        ships.remove(newShip);
        ships.add(newShip);
        validateShip(color, isEnteringPiece);
    }

    /**
     * 
     * @return 
     */
    public boolean checkIfCanPutShip() {
        Ship ship = Board.currentShip;
        
        if (ship != null) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    
                    if (pieces[i][j].getBackground() == shipColor) {
                        if (ship.containPiece(i, j)) {
                            //  System.out.println("containnnnnnnn");
                            pieces[ship.getStartingRow()][ship.getStartingColum()].setBackground(shipErrorColor);
                            
                            
                            return false;
                        }
                    }
                    
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param color
     * @param isEnteringPiece 
     */
    public void validateShip(Color color, boolean isEnteringPiece) {
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pieces[i][j].setBackground(fieldColor);
            }
        }
        
        
        for (Ship ship : ships) {
            boolean error = false;
            Color myColor = shipColor;
            if (isEnteringPiece) {
                if (!checkIfCanPutShip()) {
                    error = true;
                    // continue;
                }
            }
            //    continue;

//
            ship.setRow(ship.getStartingRow());
            ship.setColum(ship.getStartingColum());
            try {
                for (int i = 0; i < ship.getLength(); i++) {
                    
                    if (ship == Board.currentShip) { // an einai to ploio p thelw na valw 
                        myColor = color;
                        if (error) {
                            myColor = shipErrorColor;
                        }
                    }
                    if (ship.getIsHorizontal()) {
                        pieces[ship.getRow()][ship.getColum()].setBackground(myColor);
                        
                        ship.setColum(ship.getColum() + 1);
                        
                    } else {
                        pieces[ship.getRow()][ship.getColum()].setBackground(myColor);
                        ship.setRow(ship.getRow() + 1);
                        
                    }
                    
                    
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                pieces[ship.getStartingRow()][ship.getStartingColum()].setBackground(shipErrorColor);
            }
            
        }
        repaint();
        revalidate();
    }

    /**
     * 
     */
    public void readyForBattle() {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getBackground() == shipColor) {
                    pointWithShip.add(new Point(i, j));
                }
            }
        }
        
        
    }

    /**
     * clear all field backfground
     */
    public void clear() {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                pieces[i][j].setBackground(fieldColor);
            }
        }
    }

    /**
     * 
     * @return 
     */
    public SelectShipsPanel getSelectShipsPanel() {
        return selectShipsPanel;
    }
    
    public void setSelectShipsPanel(SelectShipsPanel selectShipsPanel) {
        this.selectShipsPanel = selectShipsPanel;
    }
    
    public ArrayList<Ship> getShips() {
        return ships;
    }
    
    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
    
    public ArrayList<Point> getPointWithShip() {
        return pointWithShip;
    }
    
    public boolean isUserPlayer() {
        return isUserPlayer;
    }
    
    public Fr getFrame() {
        return frame;
    }
    
    public void setFrame(Fr frame) {
        this.frame = frame;
    }
    
    public Piece[][] getPieces() {
        return pieces;
    }
    
    public ArrayList<Piece> getFreePoints() {
        return freePoints;
    }
    /**
     * 
     */
    public void checkForGameIsOver() {
        updateCounting();
        
        int allShipPartsCount = 0;
        for (Ship ship : ships) {
            allShipPartsCount += ship.getLength();
        }
        System.out.println("counter = " + allShipPartsCount);
        //   if (countScore >= WINNING_SCORE) {
        if (countScore >= allShipPartsCount) {
            frame.setGameIsOver(true);
            frame.remove(frame.getCentralPanel());
            
            if (!isUserPlayer) {
                System.out.println("you winnn");
                JOptionPane.showMessageDialog(this, " player win");
                frame.setPlayerWin(true);
            } else {
                System.out.println("you loose");
                JOptionPane.showMessageDialog(this, " player loose");
                frame.setPlayerLose(true);
            }
            new Thread() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
            
            frame.repaint();
            
        }
    }

    /**
     * 
     */
    private void updateCounting() {
        countScore = 0;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getBackground() == Board.canPutHereColor) {
                    countScore++;
                }
            }
        }
        
    }
}
