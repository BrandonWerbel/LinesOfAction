package org.linesOfAction.gameMaterials;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.*;

import org.linesOfAction.util.Coordinate;

public class Board {

    private GamePiece[][] board;

    public Board() {
        board = new GamePiece[8][8];

        setUpNewGame();
    }

    private void setUpNewGame() {
        for(int y = 1; y < 7; y++) {
            board[0][y] = GamePiece.WHITE;
            board[7][y] = GamePiece.WHITE;
        }

        for(int x = 1; x < 7; x++) {
            board[x][0] = GamePiece.BLACK;
            board[x][7] = GamePiece.BLACK;
        }
    }

    public GamePiece getPiece(int x, int y) {
        return board[x][y];
    }

    public GamePiece getPiece(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    private void setPiece(Coordinate coordinate, GamePiece piece) {
        board[coordinate.getX()][coordinate.getY()] = piece;
    }

    public void move(Coordinate start, Coordinate target) {
        if(isValidMove(start, target)){
            setPiece(target, getPiece(start));
            setPiece(start, null);
        }
    } 

    private boolean isValidMove(Coordinate start, Coordinate target) {
        return getPossibleMovement(start).contains(target);
    }

    public HashSet<Coordinate> getPossibleMovement(Coordinate coordinate) {
        HashSet<Coordinate> moves = new HashSet<Coordinate>();
        GamePiece piece = getPiece(coordinate);

        if(piece == null)
            return moves;

        ArrayList<Line> lines = new ArrayList<Line>();
        lines.add(getHorizontalLine(coordinate));
        lines.add(getVerticalLine(coordinate));
        lines.add(getForwardDiagonalLine(coordinate));
        lines.add(getBackwardDiagonalLine(coordinate));
        
        for(Line line : lines) {
            moves.addAll(line.getMoves(coordinate));
        }

        return moves;   
    }

    private Line getHorizontalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int x = 0; x < 8; x++) {
            coordinates.add(new Coordinate(x, coordinate.getY()));
            pieces.add(getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    private Line getVerticalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int y = 0; y < 8; y++) {
            coordinates.add(new Coordinate(coordinate.getX(), y));
            pieces.add(getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    private Line getForwardDiagonalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        int startingX = coordinate.getX();
        int startingY = coordinate.getY();

        int offsetLeft = Math.min(startingX, 7 - startingY);

        Coordinate newCoordinate = coordinate.moveForwardDiagonal(-offsetLeft);

        while(newCoordinate.isOnBoard()){
            coordinates.add(newCoordinate);
            pieces.add(getPiece(newCoordinate));

            newCoordinate = newCoordinate.moveForwardDiagonal(1);
        }

        return new Line(pieces, coordinates);
    }

    private Line getBackwardDiagonalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        int startingX = coordinate.getX();
        int startingY = coordinate.getY();

        int offsetLeft = Math.min(startingX, startingY);

        Coordinate newCoordinate = coordinate.moveBackwardDiagonal(-offsetLeft);

        while(newCoordinate.isOnBoard()){
            coordinates.add(newCoordinate);
            pieces.add(getPiece(newCoordinate));

            newCoordinate = newCoordinate.moveBackwardDiagonal(1);
        }

        return new Line(pieces, coordinates);
    }

    /**
     * @return string representation of current board, with O's representing whites and X's representing blacks
     */
    @Override
    public String toString() {
        GamePiece[][] transposedBoard = new GamePiece[8][8];
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
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
