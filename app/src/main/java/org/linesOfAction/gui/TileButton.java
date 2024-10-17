package org.linesOfAction.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.linesOfAction.gameMaterials.GamePiece;
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
        
        setMinimumSize(new Dimension(100, 100));
        setMaximumSize(new Dimension(100, 100));
        setOpaque(true);

        backgroundColor = (x + y) % 2 == 0 ? new Color(173, 123, 106) : new Color(102, 51, 0);

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
        
        int pieceWidth = (int) (this.getWidth() * 0.8);
        int pieceHeight = (int) (this.getHeight() * 0.8);
        int pieceX = (this.getWidth() - pieceWidth) / 2;
        int pieceY = (this.getHeight() - pieceHeight) / 2;

        g.fillOval(pieceX, pieceY, pieceWidth, pieceHeight);
    }

    private void paintSelection(Graphics2D g) {
        g.setColor(Color.BLUE);
        
        int ovalWidth = (int) (this.getWidth() * 0.3);
        int ovalHeight = (int) (this.getHeight() * 0.3);
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
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
    }
    
}
