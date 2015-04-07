/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Piece extends JPanel {

    private int size;
    private int row, colum;
    public static Ship ship5, ship4, ship3, ship1, ship2;
    public static boolean theBattleHasStarted = false;
    private static Thread thread;

    public Piece(Color color, int row, int colum) {
        setBackground(color);
        this.row = row;
        this.colum = colum;
        if (Board.fieldColor != Color.blue&&Board.fieldColor != Color.cyan) {
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        }
        else{
         setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white)));
        }
        addMouseListener(mouseListener);
    }

    /**
     * 
     * @param size 
     */
    public Piece(int size) {
        this.size = size;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black)));
        for (int i = 0; i < size; i++) {

            JPanel panel = new JPanel();

            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
            panel.setBackground(Color.darkGray);
            add(panel);
        }
    }

    /**
     * 
     * @return 
     */
    public int getlength() {

        return size;
    }

    /**
     * 
     * @param board 
     */
    private void playerSearchingShips(Board board) {


        ArrayList<Point> points = board.getPointWithShip();
        boolean findTHeShip = false;


        for (Point point : points) {
            if (point.x == row && point.y == colum) {
                findTHeShip = true;
            }
        }
        if (findTHeShip) {
            setBackground(Color.green);
        } else {
            setBackground(Color.red);
        }

    }

    /**
     * 
     * @param board 
     */
    private void checkForFreePoint(Board board) {
        board.getFreePoints().removeAll(board.getFreePoints());
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getBackground() == Board.fieldColor || pieces[i][j].getBackground() == Board.shipColor) {
                    board.getFreePoints().add(pieces[i][j]);
                }
            }
        }


        randomSelectPiece(board);
    }

    /**
     * 
     * @param board 
     */
    private void randomSelectPiece(Board board) {
        Random random = new Random();
        int randomNumber = random.nextInt(board.getFreePoints().size());
        Color color = Board.shipErrorColor;
        for (Point point : board.getPointWithShip()) {

            if (board.getPieces()[point.x][point.y] == board.getFreePoints().get(randomNumber)) {


                color = Board.canPutHereColor;
            }
        }
        board.getFreePoints().get(randomNumber).setBackground(color);
        board.getFreePoints().remove(randomNumber);
    }
    /**
     * 
     */
    private MouseListener mouseListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent me) {

            Board board = (Board) Piece.this.getParent();

            if (theBattleHasStarted) {
                if (Fr.gameIsOver) {
                    return;
                }
                if (getBackground() == Board.canPutHereColor || getBackground() == Board.shipErrorColor) {
                    System.out.println("is already pressed");
                    return;
                }
                if (board.isUserPlayer()) {
                    return;
                }
                if (Board.isUserTurn) {
                    playerSearchingShips(board);
                    Board.isUserTurn = !Board.isUserTurn;
                }


                if (!Board.isUserTurn) {

                    final Board userBoard = board.getFrame().getUserBoard();


                    if (thread == null || !thread.isAlive()) {
                        thread = new Thread() {

                            @Override
                            public void run() {
                                try {

                                    Thread.sleep(Fr.moveDelay);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                checkForFreePoint(userBoard);
                                Board.isUserTurn = !Board.isUserTurn;


                            }
                        };
                        thread.start();

                    }


                }




                board.checkForGameIsOver();

                return;
            }
            Ship ship = checkWitchShipPressed();

            if (ship != null && Board.currentShip == null) {
                Board.currentShip = ship;
                board.getShips().remove(ship);
                return;

            }


            if (Board.currentShip == null) {
                SelectShipsPanel.curentSelectedPieceToRemove = null;
                return;
            }
            if (getBackground() == Board.shipErrorColor) {
                return;
            }

            Board.currentShip.setRow(row);
            Board.currentShip.setColum(colum);
            Board.currentShip.setStartingRow(row);
            Board.currentShip.setStartingColum(colum);

            board.addShip(Board.currentShip, Board.shipColor, false);
            //  System.out.println(Board.currentShip.getLength());
            if (Board.currentShip.getLength() == 5) {
                ship5 = Board.currentShip;
            }
            if (Board.currentShip.getLength() == 4) {
                ship4 = Board.currentShip;
            }
            if (Board.currentShip.getLength() == 3) {
                ship3 = Board.currentShip;
            }
            if (Board.currentShip.getLength() == 2) {
                ship2 = Board.currentShip;
            }
            if (Board.currentShip.getLength() == 1) {
                ship1 = Board.currentShip;
            }
            board.getSelectShipsPanel().removeShipSelection(SelectShipsPanel.curentSelectedPieceToRemove);
            Board.currentShip = null;
            SelectShipsPanel.curentSelectedPieceToRemove = null;
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            if (Board.currentShip == null || theBattleHasStarted) {
                return;
            }

            if (getBackground() == Board.shipColor) {
                //  System.out.println("ship here");
                setBackground(Board.shipErrorColor);

            }
            Board board = (Board) Piece.this.getParent();
            Board.currentShip.setRow(row);
            Board.currentShip.setColum(colum);
            Board.currentShip.setStartingRow(row);
            Board.currentShip.setStartingColum(colum);

            if (getBackground() != Board.shipErrorColor) {
                board.addShip(Board.currentShip, Board.canPutHereColor, true);
            } else {
                board.addShip(Board.currentShip, Board.shipErrorColor, true);
            }
            board.repaint();
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    };

    /*
     * 
     */
    private Ship checkWitchShipPressed() {
        Ship pressedShip = null;
        if (ship5 != null) {
            if (ship5.containPiece(row, colum)) {
                pressedShip = ship5;

            }
        }
        if (ship4 != null) {
            if (ship4.containPiece(row, colum)) {
                pressedShip = ship4;

            }
        }

        if (ship3 != null) {
            if (ship3.containPiece(row, colum)) {
                pressedShip = ship3;

            }
        }
        if (ship2 != null) {
            if (ship2.containPiece(row, colum)) {
                pressedShip = ship2;

            }
        }
        if (ship1 != null) {
            if (ship1.containPiece(row, colum)) {
                pressedShip = ship1;

            }
        }
        if (pressedShip != null) {
            return pressedShip;
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public Ship getShip1() {
        return ship1;
    }

    /**
     * 
     * @param ship1 
     */
    public void setShip1(Ship ship1) {
        this.ship1 = ship1;
    }

    /**
     * 
     * @return 
     */
    public Ship getShip3() {
        return ship3;
    }

    /**
     * 
     * @param ship3 
     */
    public void setShip3(Ship ship3) {
        this.ship3 = ship3;
    }

    /**
     * 
     * @return 
     */
    public Ship getShip4() {
        return ship4;
    }

    /**
     * 
     * @param ship4 
     */
    public void setShip4(Ship ship4) {
        this.ship4 = ship4;
    }

    /**
     * 
     * @return 
     */
    public Ship getShip5() {
        return ship5;
    }

    /**
     * 
     * @param ship5 
     */
    public void setShip5(Ship ship5) {
        this.ship5 = ship5;
    }

    public int getColum() {
        return colum;
    }

    public int getRow() {
        return row;
    }
}
