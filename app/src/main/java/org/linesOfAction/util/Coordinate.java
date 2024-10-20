package org.linesOfAction.util;

import java.util.HashSet;

public class Coordinate {

    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x; }
    public int getY() {return y; }

    
    /** 
     * Returns a new coordinate, shifted up and to the right by the given offset
     * @param offset
     * @return Coordinate
     */
    public Coordinate moveForwardDiagonal(int offset) {
        return new Coordinate(x + offset, y - offset);
    }
    
    /** 
     * Returns a new coordinate, shifted down and to the right by the given offset
     * @param offset
     * @return Coordinate
     */
    public Coordinate moveBackwardDiagonal(int offset) {
        return new Coordinate(x + offset, y + offset);
    }

    /** 
     * Returns a new coordinate, shifted down by the given offset
     * @param offset
     * @return Coordinate
     */
    public Coordinate moveVertical(int offset) {
        return new Coordinate(x, y + offset);
    }

    /** 
     * Returns a new coordinate, shifted right by the given offset
     * @param offset
     * @return Coordinate
     */
    public Coordinate moveHorizontal(int offset) {
        return new Coordinate(x + offset, y);
    }

    /**
     * Returns all coordinates (contained on the board) immediately adjacent to original coordinate
     * @return
     */
    public HashSet<Coordinate> getNeighbors() {
        HashSet<Coordinate> neighbors = new HashSet<Coordinate>();

        neighbors.add(moveVertical(1));
        neighbors.add(moveVertical(-1));
        neighbors.add(moveHorizontal(1));
        neighbors.add(moveHorizontal(-1));
        neighbors.add(moveForwardDiagonal(1));
        neighbors.add(moveForwardDiagonal(-1));
        neighbors.add(moveBackwardDiagonal(1));
        neighbors.add(moveBackwardDiagonal(-1));

        neighbors.removeIf((element) -> !element.isOnBoard());

        return neighbors;

    }

    /**
     * Returns whether or not the coordiante is contained on the board
     * @return
     */
    public boolean isOnBoard() {
        return x < Constants.BOARD_SIZE && y < Constants.BOARD_SIZE && x >= 0 && y >= 0;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)",x, y);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Coordinate))
            return false;
        Coordinate other = (Coordinate) o;

        return other.getX() == this.getX() && other.getY() == this.getY();
    }

    @Override
    public int hashCode() {
        return this.getX() * Constants.BOARD_SIZE + this.getY();
    }

}
