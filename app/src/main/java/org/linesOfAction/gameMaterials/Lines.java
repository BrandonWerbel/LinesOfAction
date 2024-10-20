package org.linesOfAction.gameMaterials;

import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import java.util.ArrayList;

public class Lines {

    private Board board;

    public Lines(Board board) {
        this.board = board;
    }

    /** 
     * Returns the horizontal line which contains the given coordinate
     * @param coordinate
     * @return Line
     */
    public Line getHorizontalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            coordinates.add(new Coordinate(x, coordinate.getY()));
            pieces.add(board.getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    
    /** 
     * Returns the Vertical line which contains the given coordinate
     * @param coordinate
     * @return Line
     */
    public Line getVerticalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        for(int y = 0; y < Constants.BOARD_SIZE; y++) {
            coordinates.add(new Coordinate(coordinate.getX(), y));
            pieces.add(board.getPiece(coordinates.getLast()));
        }

        return new Line(pieces, coordinates);
    }

    /** 
     * Returns the forward diagonal (bottom left to top right) line which contains the given coordinate
     * @param coordinate
     * @return Line
     */
    public Line getForwardDiagonalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        int startingX = coordinate.getX();
        int startingY = coordinate.getY();

        int offsetLeft = Math.min(startingX, Constants.BOARD_SIZE - 1 - startingY);

        Coordinate newCoordinate = coordinate.moveForwardDiagonal(-offsetLeft);

        while(newCoordinate.isOnBoard()){
            coordinates.add(newCoordinate);
            pieces.add(board.getPiece(newCoordinate));

            newCoordinate = newCoordinate.moveForwardDiagonal(1);
        }

        return new Line(pieces, coordinates);
    }

    /** 
     * Returns the backward diagonal (top left to bottom right) line which contains the given coordinate
     * @param coordinate
     * @return Line
     */
    public Line getBackwardDiagonalLine(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();

        int startingX = coordinate.getX();
        int startingY = coordinate.getY();

        int offsetLeft = Math.min(startingX, startingY);

        Coordinate newCoordinate = coordinate.moveBackwardDiagonal(-offsetLeft);

        while(newCoordinate.isOnBoard()){
            coordinates.add(newCoordinate);
            pieces.add(board.getPiece(newCoordinate));

            newCoordinate = newCoordinate.moveBackwardDiagonal(1);
        }

        return new Line(pieces, coordinates);
    }
}
