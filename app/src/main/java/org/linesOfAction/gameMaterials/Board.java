package org.linesOfAction.gameMaterials;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

public class Board {

    private GamePiece[][] board;

    private GamePiece currentPlayer;

    public Board() {
        board = new GamePiece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        currentPlayer = GamePiece.BLACK;

        setUpNewGame();
    }

    private void setUpNewGame() {
        for(int y = 1; y < Constants.BOARD_SIZE - 1; y++) {
            board[0][y] = GamePiece.WHITE;
            board[Constants.BOARD_SIZE - 1][y] = GamePiece.WHITE;
        }

        for(int x = 1; x < Constants.BOARD_SIZE - 1; x++) {
            board[x][0] = GamePiece.BLACK;
            board[x][Constants.BOARD_SIZE - 1] = GamePiece.BLACK;
        }
    }

    public GamePiece getPiece(int x, int y) {
        return board[x][y];
    }

    public GamePiece getPiece(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    public void move(Coordinate start, Coordinate target) {
        if(isValidMove(start, target)){
            setPiece(target, getPiece(start));
            setPiece(start, null);
            switchPlayer();
        }
    }

    private void setPiece(Coordinate coordinate, GamePiece piece) {
        board[coordinate.getX()][coordinate.getY()] = piece;
    }

    private void switchPlayer() {
        switch(currentPlayer) {
            case BLACK -> currentPlayer = GamePiece.WHITE;
            case WHITE -> currentPlayer = GamePiece.BLACK;
            default -> throw new IllegalStateException("current player is neither black nor white");
            
        }
    }

    private boolean isValidMove(Coordinate start, Coordinate target) {
        return getPossibleMovement(start).contains(target);
    }

    public HashSet<Coordinate> getPossibleMovement(Coordinate coordinate) {
        HashSet<Coordinate> moves = new HashSet<Coordinate>();
        GamePiece piece = getPiece(coordinate);

        if(piece != currentPlayer)
            return moves;

        Lines lines = new Lines(board);
        ArrayList<Line> linesWithCoordiante = new ArrayList<Line>();
        linesWithCoordiante.add(lines.getHorizontalLine(coordinate));
        linesWithCoordiante.add(lines.getVerticalLine(coordinate));
        linesWithCoordiante.add(lines.getForwardDiagonalLine(coordinate));
        linesWithCoordiante.add(lines.getBackwardDiagonalLine(coordinate));
        
        for(Line line : linesWithCoordiante) {
            moves.addAll(line.getMoves(coordinate));
        }

        return moves;   
    }

    /**
     * @return string representation of current board, with O's representing whites and X's representing blacks
     */
    @Override
    public String toString() {
        GamePiece[][] transposedBoard = new GamePiece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            for(int y = 0; y < Constants.BOARD_SIZE; y++) {
                transposedBoard[x][y] = board[y][x];
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
