package org.linesOfAction.gameMaterials;

import org.linesOfAction.util.Coordinate;

import java.util.ArrayList;

public class Lines {

    private GamePiece[][] board;

    public Lines(GamePiece[][] board) {
        this.board = board;
    }

    public Line getHorizontalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int x = 0; x < 8; x++) {
            coordinates.add(new Coordinate(x, coordinate.getY()));
            pieces.add(getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    public Line getVerticalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int y = 0; y < 8; y++) {
            coordinates.add(new Coordinate(coordinate.getX(), y));
            pieces.add(getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    public Line getForwardDiagonalLine(Coordinate coordinate) {
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

    public Line getBackwardDiagonalLine(Coordinate coordinate) {
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

    private GamePiece getPiece(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }
}
