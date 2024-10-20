package org.linesOfAction.gameMaterials;

import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import com.google.common.collect.Streams;

import java.util.stream.Stream;
import java.util.Arrays;

public class Board {

    private GamePiece[][] board;

    public Board() {
        this.board = new GamePiece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    }

    public Board(Board otherBoard) {
        this.board = Stream.of(otherBoard.board)
                        .map((column) -> Arrays.copyOf(column, column.length))
                        .toArray(GamePiece[][]::new);
    }

    
    /** 
     * Gets piece at given coordinate
     * @param x
     * @param y
     * @return GamePiece
     */
    public GamePiece getPiece(int x, int y) {
        return board[x][y];
    }

    /**
     * Gets piece at given coordinate
     * @param coordinate
     * @return
     */
    public GamePiece getPiece(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Sets piece at given coordinate to the given piece
     * @param x
     * @param y
     * @param piece
     */
    public void setPiece(int x, int y, GamePiece piece) {
        board[x][y] = piece;
    }

    /**
     * Sets piece at given coordinate to the given piece
     * @param coordinate
     * @param piece
     */
    public void setPiece(Coordinate coordinate, GamePiece piece) {
        board[coordinate.getX()][coordinate.getY()] = piece;
    }

    /**
     * Gets the number of pieces a given player has
     * @param player the color of the player at question
     * @return
     */
    public int getNumPieces(GamePiece player) {
        return Stream.of(board)
            .mapToInt((column) -> Stream.of(column)
                .mapToInt((piece) -> piece == player ? 1 : 0)
                .sum())
            .sum();
    }

    /**
     * @return String representation of current board, with O's representing whites and X's representing blacks
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

    public int[][] get2dIntArray() {
        return (int[][])Stream.of(board)
                .map((row) -> Stream.of(row))
                .map((row) -> 
                    row.mapToInt((piece) -> {
                        if (piece == GamePiece.BLACK) return 1;
                        else if (piece == GamePiece.WHITE) return -1;
                        else return 0;})
                    .toArray())
                    .toArray();
    }
    
}
