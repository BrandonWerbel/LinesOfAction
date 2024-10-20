package org.linesOfAction.gui;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import org.linesOfAction.gameLogic.Game;
import org.linesOfAction.gameMaterials.GamePiece;
import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import java.util.HashSet;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel implements ActionListener {

    Game game;
    TileButton[][] tiles;

    TileButton selectedTile;

    HashSet<TileButton> currentPossibleMoves;

    public BoardPanel() {
        setLayout(new GridLayout(Constants.BOARD_SIZE, Constants.BOARD_SIZE));
        setSize(Constants.BOARD_SIZE * Constants.BUTTON_SIZE,Constants.BOARD_SIZE * Constants.BUTTON_SIZE);

        game = new Game();
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

    
    /** 
     * Returns the tile at the given coordinate
     * @param coordinate
     * @return TileButton
     */
    private TileButton getTile(Coordinate coordinate) {
        return tiles[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Iteratively goes through the game state and updates tiles accordingly
     */
    private void updateTiles() {
        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            for(int y = 0; y < Constants.BOARD_SIZE; y++) {
                tiles[x][y].addPiece(game.getPiece(x, y));
            }
        }
    }

    /**
     * First unselects the current tile. Then, 
     * if there are some possible moves from 
     * the given tile, selects the tile and 
     * shows possible moves from that tile
     * @param tile
     */
    private void selectTile(TileButton tile) {
        unselectTile();

        if(!game.getPossibleMovement(tile.getCoordinate()).isEmpty()) {
            this.selectedTile = tile;
            tile.select();
            showPossibleMoves(tile.getCoordinate());
        }
    }

    /**
     * Unselects the current tile
     */
    private void unselectTile() {
        unshowPossibleMoves();
        if(this.selectedTile != null) {
            selectedTile.unselect();
            selectedTile = null;
        }
    }

    /**
     * Finds valid moves from the given coordinate, and 
     * visually updates the respective tiles accordingly
     * @param coordinate
     */
    private void showPossibleMoves(Coordinate coordinate) {
        unshowPossibleMoves();
        HashSet<Coordinate> moves = game.getPossibleMovement(coordinate);
        for(Coordinate move : moves) {
            TileButton tile = getTile(move);
            currentPossibleMoves.add(tile);
            tile.addPossibleMove();
        }
    }

    /**
     * Removes the possible move markers from the current possible moves
     */
    private void unshowPossibleMoves() {
        for(TileButton tile : currentPossibleMoves) {
            tile.removePossibleMove();
        } 
    }

    /**
     * If there is no selected tile, select the tile
     * that has been clicked. Otherwise, attempt to 
     * move from selected tile to clicked tile
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!(e.getSource() instanceof TileButton)) {
            return;
        }
        
        TileButton tile = (TileButton) e.getSource();

        if(this.selectedTile == null) {
            selectTile(tile);
        } else {
            game.move(selectedTile.getCoordinate(), tile.getCoordinate());
            unselectTile();
        }

        updateTiles();

        if(!game.isGameOver().isEmpty()) {
            endGame(game.isGameOver().get());
        }
    }

    /**
     * Disables all tiles, and shows a message announcing the winner
     * @param winner
     */
    private void endGame(GamePiece winner) {

        for(TileButton[] column : tiles) {
            for(TileButton tile : column) {
                tile.setEnabled(false);
            }
        }

        JOptionPane.showMessageDialog(null, winner.toString() + " wins!");
    }
}
