package org.linesOfAction.gameMaterials;

import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

public class Board {

    GamePiece[][] board;

    public Board() {
        this.board = new GamePiece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    }

    public GamePiece getPiece(int x, int y) {
        return board[x][y];
    }

    public GamePiece getPiece(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    public void setPiece(int x, int y, GamePiece piece) {
        board[x][y] = piece;
    }

    public void setPiece(Coordinate coordinate, GamePiece piece) {
        board[coordinate.getX()][coordinate.getY()] = piece;
    }
    
}
