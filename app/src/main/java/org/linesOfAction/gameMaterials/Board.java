package org.linesOfAction.gameMaterials;

import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import java.util.stream.Stream;

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

    public int getNumPieces(GamePiece player) {
        return Stream.of(board)
            .mapToInt((column) -> Stream.of(column)
                .mapToInt((piece) -> piece == player ? 1 : 0)
                .sum())
            .sum();
    }

    /**
     * @return string representation of current board, with O's representing whites and X's representing blacks
     */
    @Override
    public String toString() {
        GamePiece[][] transposedBoard = new GamePiece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            for(int y = 0; y < Constants.BOARD_SIZE; y++) {
                transposedBoard[x][y] = getPiece(y, x);
            }
        }

        return Stream.of(transposedBoard)
            .map((row) -> Stream.of(row))
            .map((row) ->
                row.map((piece) -> {
                    if (piece == GamePiece.BLACK) return "X";
                    else if (piece == GamePiece.WHITE) return "O";
                    else return "-";})
                .reduce("", (result, element) -> result + " " + element))
            .reduce("", (result, element) -> result + "\n" + element);
    }
    
}
