/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
public class Fr extends JFrame {

    private final String HORIZONTAL = "Horizontal";
    private final String VERTICAL = "Vertical";
    private JPanel afterLinePanel = new JPanel();
    private JButton rotationButton = new JButton(VERTICAL);
    private JButton startGame = new JButton("start game");
    private SelectShipsPanel selectShipsPanel;
    private JPanel centralPanel = new JPanel();
    private Board userBoard;
    private Board opponentBoard;
    static boolean gameIsOver = false;
    private boolean playerWin, playerLose;
    public static int moveDelay = 1000;
private String userName;
    public Fr() {
        
         userName= JOptionPane.showInputDialog( "enter your name");
         System.out.println("welcome "+ userName);
        userBoard = new Board(true);
        opponentBoard = new Board(false);
        selectShipsPanel = new SelectShipsPanel();
        afterLinePanel.add(rotationButton);
        afterLinePanel.add(startGame);
        rotationButton.addActionListener(rotationListener);
        startGame.addActionListener(startGameListener);
        centralPanel.add(userBoard);
        centralPanel.setLayout(new GridLayout(1, 2, 20, 10));
        add(centralPanel);
        add(afterLinePanel, BorderLayout.AFTER_LAST_LINE);
        add(selectShipsPanel, BorderLayout.BEFORE_LINE_BEGINS);
        // add(new selectShipsPanel());
        userBoard.setFrame(this);
        opponentBoard.setFrame(this);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        userBoard.setSelectShipsPanel(selectShipsPanel);
    }
    ActionListener rotationListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (Board.currentShip == null || Piece.theBattleHasStarted) {
                return;
            }
            JButton button = (JButton) ae.getSource();
            if (button.getText().equalsIgnoreCase(VERTICAL)) {
                button.setText(HORIZONTAL);
            }
            if (button.getText().equalsIgnoreCase(HORIZONTAL)) {
                button.setText(VERTICAL);
            }
            Board.currentShip.setIsHorizontal(!Board.currentShip.getIsHorizontal());

        }
    };
    ActionListener startGameListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!userBoard.getSelectShipsPanel().isEmpty() || Piece.theBattleHasStarted) {
                return;
            }
            int rise =100;
            setSize(getSize().width+3*rise,getSize().height+rise);
            remove(getAfterLinePanel());
            remove(selectShipsPanel);
            userBoard.readyForBattle();
          //  userBoard.clear();

// oponent details
            ArrayList<Integer> sizes = new ArrayList<Integer>();
            sizes.add(1);
            sizes.add(2);
            sizes.add(3);
            sizes.add(4);
            sizes.add(5);
            Piece.ship1 = null;
            Piece.ship2 = null;
            Piece.ship3 = null;
            Piece.ship4 = null;
            Piece.ship5 = null;
            opponentShipDetails(sizes, 1);
            opponentShipDetails(sizes, 2);
            opponentShipDetails(sizes, 3);
            opponentShipDetails(sizes, 4);
            opponentShipDetails(sizes, 5);


            centralPanel.add(opponentBoard);
            opponentBoard.readyForBattle();
            opponentBoard.clear();

            validate();
            Piece.theBattleHasStarted = true;
            // System.out.println(opponentBoard.getPointWithShip());
        }
    };

    public void opponentShipDetails(ArrayList<Integer> sizes, int lengthOfShip) {

        Random random = new Random();
        int randomSize = random.nextInt(sizes.size());


        int row = random.nextInt(Board.SIZE - sizes.get(randomSize));

        int colum = random.nextInt(Board.SIZE - sizes.get(randomSize));

        Ship ship = new Ship(sizes.get(randomSize), row, colum);
        ship.setIsHorizontal(random.nextBoolean());

        while (true) {
            if (!isAnyShipHere(ship)) {
                break;
            } else {

                row = random.nextInt(Board.SIZE - sizes.get(randomSize));
                colum = random.nextInt(Board.SIZE - sizes.get(randomSize));
                ship.setStartingRow(row);
                ship.setStartingColum(colum);
            }
        }


        opponentBoard.addShip(ship, Board.shipColor, false);
        sizes.remove(randomSize);
        if (lengthOfShip == 1) {
            Piece.ship1 = ship;
        }
        if (lengthOfShip == 2) {
            Piece.ship2 = ship;
        }
        if (lengthOfShip == 3) {
            Piece.ship3 = ship;
        }
        if (lengthOfShip == 4) {
            Piece.ship4 = ship;
        }
        if (lengthOfShip == 5) {
            Piece.ship5 = ship;
        }


    }

    public boolean isAnyShipHere(Ship ship) {
        int row = ship.getStartingRow();
        int colum = ship.getStartingColum();
        int length = ship.getLength();
        // System.out.println(Piece.ship1==null);

        for (int i = 0; i < length; i++) {
            if (!ship.getIsHorizontal()) {
                if (Piece.ship1 != null && Piece.ship1 != ship && Piece.ship1.containPiece(row + i, colum)) {
                    return true;
                }
                if (Piece.ship2 != null && Piece.ship2 != ship && Piece.ship2.containPiece(row + i, colum)) {
                    return true;
                }
                if (Piece.ship3 != null && Piece.ship3 != ship && Piece.ship3.containPiece(row + i, colum)) {
                    return true;
                }
                if (Piece.ship4 != null && Piece.ship4 != ship && Piece.ship4.containPiece(row + i, colum)) {
                    return true;
                }
                if (Piece.ship5 != null && Piece.ship5 != ship && Piece.ship5.containPiece(row + i, colum)) {
                    return true;
                }

            } else {

                if (Piece.ship1 != null && Piece.ship1 != ship && Piece.ship1.containPiece(row, colum + i)) {
                    return true;
                }
                if (Piece.ship2 != null && Piece.ship2 != ship && Piece.ship2.containPiece(row, colum + i)) {
                    return true;
                }
                if (Piece.ship3 != null && Piece.ship3 != ship && Piece.ship3.containPiece(row, colum + i)) {
                    return true;
                }
                if (Piece.ship4 != null && Piece.ship4 != ship && Piece.ship4.containPiece(row, colum + i)) {
                    return true;
                }
                if (Piece.ship5 != null && Piece.ship5 != ship && Piece.ship5.containPiece(row, colum + i)) {
                    return true;
                }

            }
        }
        return false;
    }

    public Board getOpponentBoard() {
        return opponentBoard;

    }

    public Board getUserBoard() {
        return userBoard;
    }

    public boolean isGameIsOver() {
        return gameIsOver;
    }

    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }

    public JPanel getCentralPanel() {
        return centralPanel;
    }

    public boolean isPlayerLose() {
        return playerLose;
    }

    public void setPlayerLose(boolean playerLose) {
        this.playerLose = playerLose;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    public JPanel getAfterLinePanel() {
        return afterLinePanel;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gameIsOver) {
            g.setColor(Color.blue);

            g.setFont(new Font(Font.SERIF, Font.ITALIC, 60));
            g.drawString("game is over ", 90, 150);
            if (playerWin) {
                g.setColor(Color.green);
                g.drawString("you win ", 170, 250);

            }
            if (playerLose) {
                g.setColor(Color.red);
                g.drawString("you loose , next time  ", 170, 250);
            }
        }
    }
}
