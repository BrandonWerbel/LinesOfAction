package org.linesOfAction.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.linesOfAction.gameMaterials.GamePiece;
import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

public class TileButton extends JButton {

    private Coordinate coordinate;
    private Color backgroundColor;
    private GamePiece piece;
    boolean selected;

    private Border defaultBorder;

    public TileButton(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        piece = null;
        selected = false;
        this.defaultBorder = this.getBorder();
        
        setMinimumSize(new Dimension(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
        setMaximumSize(new Dimension(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
        setOpaque(true);

        backgroundColor = (x + y) % 2 == 0 ? Constants.LIGHT_TILE_COLOR : Constants.DARK_TILE_COLOR;

        setBackground(backgroundColor);
    }

    public void addPiece(GamePiece piece) {
        if(piece == null) {
            removePiece();
            return;
        }
        
        this.piece = piece;
        repaint();
    }

    public void removePiece() {
        this.piece = null;
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D graphics2d = (Graphics2D) g;
        super.paintComponent(graphics2d);

        if(piece != null) {
            paintNewPiece(graphics2d);
        }

        if(selected) {
            paintSelection(graphics2d);
        }
    }

    private void paintNewPiece(Graphics2D g) {
        if(this.piece == null) {
            throw new IllegalStateException("Must have piece in order to paint it");
        }

        g.setColor(this.piece.getValue());
        
        int pieceWidth = (int) (this.getWidth() * Constants.GAMEPIECE_RATIO);
        int pieceHeight = (int) (this.getHeight() * Constants.GAMEPIECE_RATIO);
        int pieceX = (this.getWidth() - pieceWidth) / 2;
        int pieceY = (this.getHeight() - pieceHeight) / 2;

        g.fillOval(pieceX, pieceY, pieceWidth, pieceHeight);
    }

    private void paintSelection(Graphics2D g) {
        g.setColor(Constants.SELECTION_MARKER_COLOR);
        
        int ovalWidth = (int) (this.getWidth() * Constants.TILE_SELECTION_MARKER_RATIO);
        int ovalHeight = (int) (this.getHeight() * Constants.TILE_SELECTION_MARKER_RATIO);
        int ovalX = (this.getWidth() - ovalWidth) / 2;
        int ovalY = (this.getHeight() - ovalHeight) / 2;

        g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
    }

    public void unselect() {
        selected = false;
        repaint();
    }

    public void select() {
        selected = true;
        repaint();
    }

    public Coordinate getCoordinate() { return coordinate; }
    public boolean isOccupied() { return this.piece != null; }

    public void removePossibleMove() {
        this.setBorder(defaultBorder);
    }

    public void addPossibleMove() {
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN, Constants.POSSIBLE_MOVE_BORDER_THICKNESS));
    }
    
}
