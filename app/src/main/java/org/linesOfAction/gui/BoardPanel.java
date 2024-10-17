package org.linesOfAction.gui;

import javax.swing.JPanel;

import org.linesOfAction.gameMaterials.Board;
import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import java.util.HashSet;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel implements ActionListener {

    Board board;
    TileButton[][] tiles;

    TileButton selectedTile;

    HashSet<TileButton> currentPossibleMoves;

    public BoardPanel() {
        setLayout(new GridLayout(Constants.BOARD_SIZE, Constants.BOARD_SIZE));
        setSize(Constants.BOARD_SIZE * Constants.BUTTON_SIZE,Constants.BOARD_SIZE * Constants.BUTTON_SIZE);

        board = new Board();
        tiles = new TileButton[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        selectedTile = null;
        currentPossibleMoves = new HashSet<TileButton>();

        // instantiate tiles
        for(int y = 0; y < Constants.BOARD_SIZE; y++) {
            for(int x = 0; x < Constants.BOARD_SIZE; x++) {
                tiles[x][y] = new TileButton(x, y);
                tiles[x][y].addActionListener(this);
                add(tiles[x][y]);
            }
        }

        updateTiles();
    }

    private TileButton getTile(Coordinate coordinate) {
        return tiles[coordinate.getX()][coordinate.getY()];
    }

    private void updateTiles() {
        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            for(int y = 0; y < Constants.BOARD_SIZE; y++) {
                tiles[x][y].addPiece(board.getPiece(x, y));
            }
        }
    }

    private void selectTile(TileButton tile) {
        unselectTile();

        if(!board.getPossibleMovement(tile.getCoordinate()).isEmpty()) {
            this.selectedTile = tile;
            tile.select();
            showPossibleMoves(tile.getCoordinate());
        }
    }

    private void unselectTile() {
        unshowPossibleMoves();
        if(this.selectedTile != null) {
            selectedTile.unselect();
            selectedTile = null;
        }
    }

    private void showPossibleMoves(Coordinate coordinate) {
        unshowPossibleMoves();
        HashSet<Coordinate> moves = board.getPossibleMovement(coordinate);
        for(Coordinate move : moves) {
            TileButton tile = getTile(move);
            currentPossibleMoves.add(tile);
            tile.addPossibleMove();
        }
    }

    private void unshowPossibleMoves() {
        for(TileButton tile : currentPossibleMoves) {
            tile.removePossibleMove();
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!(e.getSource() instanceof TileButton)) {
            return;
        }
        
        TileButton tile = (TileButton) e.getSource();

        if(this.selectedTile == null) {
            selectTile(tile);
        } else {
            board.move(selectedTile.getCoordinate(), tile.getCoordinate());
            unselectTile();
        }

        updateTiles();
    }
}
