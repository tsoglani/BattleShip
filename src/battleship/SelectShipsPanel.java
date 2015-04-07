/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class SelectShipsPanel extends JPanel {

    private Piece[] piecesToSelect = new Piece[5];
    private Ship[] ships = new Ship[5];
    private JPanel centralPanel = new JPanel();
    static Piece curentSelectedPieceToRemove;

    public SelectShipsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        centralPanel.setLayout(new GridLayout(5, 1));
        for (int i = piecesToSelect.length - 1; i >= 0; i--) {
            piecesToSelect[i] = new Piece(i + 1);
            piecesToSelect[i].addMouseListener(mouseListener);
            centralPanel.add(piecesToSelect[i]);
        }

        for (int i = 0; i < ships.length; i++) {
            ships[i] = new Ship(i + 1);
        }


        centralPanel.setSize(400, 500);
        add(centralPanel);
    }

    public void removeShipSelection(Piece piece) {
        if (piece == null) {
            return;
        }
      //  centralPanel.remove(piece);
        piece.setVisible(false);
        centralPanel.repaint();
        centralPanel.revalidate();
    }
    private MouseListener mouseListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent me) {
            if (Piece.theBattleHasStarted) {
                return;
            }
            Piece piece = (Piece) me.getSource();
            // System.out.println(piece.getlength());

            if (piece.getlength() == 1) {
                Board.currentShip = ships[0];
            }
            if (piece.getlength() == 2) {
                Board.currentShip = ships[1];
            }
            if (piece.getlength() == 3) {
                Board.currentShip = ships[2];
            }
            if (piece.getlength() == 4) {
                Board.currentShip = ships[3];
            }
            if (piece.getlength() == 5) {
                Board.currentShip = ships[4];
            }

            //  Board.currentShip = new Ship(piece.getlength());
            curentSelectedPieceToRemove = piece;

        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    };

    public boolean isEmpty() {
        if (centralPanel.getComponentCount() == 0) {
            return true;
        }
        boolean isNotVisible=true;
        for (int i = 0; i < centralPanel.getComponentCount(); i++) {
            if(centralPanel.getComponent(i).isVisible()){
            isNotVisible=false;
            }
        }
        
        return isNotVisible;
    }
}
