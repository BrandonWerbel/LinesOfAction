package org.linesOfAction.gameMaterials;

import java.util.ArrayList;
import java.util.HashSet;

import org.linesOfAction.util.Coordinate;

public class Line {

    private ArrayList<GamePiece> pieces;
    private ArrayList<Coordinate> coordinates;

    public Line(ArrayList<GamePiece> pieces, ArrayList<Coordinate> coordinates) {
        this.pieces = pieces;
        this.coordinates = coordinates;
    }

    public HashSet<Coordinate> getMoves(Coordinate startCoordinate) {

        HashSet<Coordinate> moves = new HashSet<Coordinate>();
        int range = getRange();
        int startIndex = coordinates.indexOf(startCoordinate);

        if(startIndex < 0) 
            throw new IllegalArgumentException(
                String.format("Given coordinate %s is not in line %s", 
                        startCoordinate.toString(),
                        coordinates.toString()));

        if(checkLegalMove(startIndex, startIndex - range))
            moves.add(coordinates.get(startIndex - range));

        if(checkLegalMove(startIndex, startIndex + range))
            moves.add(coordinates.get(startIndex + range));

        return moves;
    }

    private int getRange() {
        int range = 0;
        for(GamePiece piece : pieces) {
            if(piece != null)
                range++;
        }
        return range;
    }

    private boolean checkLegalMove(int startIndex, int targetIndex) {

        if(startIndex < 0 || startIndex >= pieces.size())
            return false;
        
        if(targetIndex < 0 || targetIndex >= pieces.size())
            return false;

        if(pieces.get(startIndex) == pieces.get(targetIndex))
            return false;
            

        int minIndex = Math.min(startIndex, targetIndex);
        int maxIndex = Math.max(startIndex, targetIndex);
        for(int i = minIndex + 1; i < maxIndex; i++) {
            if(pieces.get(startIndex).isOppositeColorTo(pieces.get(i)))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }
    
}
