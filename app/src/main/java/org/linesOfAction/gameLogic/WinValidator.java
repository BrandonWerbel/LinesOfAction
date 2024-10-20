package org.linesOfAction.gameLogic;

import org.linesOfAction.gameMaterials.Board;
import org.linesOfAction.gameMaterials.GamePiece;
import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

import java.util.Optional;
import java.util.HashSet;


public class WinValidator {

    
    /** 
     * Checks if a given player has won on the current configuration of the given board.
     * A player has won if all their pieces are connected in a single continuous formation
     * @param player
     * @param board
     * @return boolean
     */
    public static boolean hasWon(GamePiece player, Board board) {

        if(player == null) throw new IllegalArgumentException("player cannot be null");

        Optional<Coordinate> firstPiece = getSinglePlayerPiece(player, board);

        if(firstPiece.isEmpty()) {
            return true;
        }

        Board tempBoard = removeFormation(firstPiece.get(), new Board(board));

        if(tempBoard.getNumPieces(player) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Given a board, returns a new board in which all pieces
     * of the same color as the piece at the given coordinate
     * which are transitively touching the original piece are removed.
     * @param startingCoordinate
     * @param board
     * @return
     */
    private static Board removeFormation(Coordinate startingCoordinate, Board board) {
        Board newBoard = board;

        if(newBoard.getPiece(startingCoordinate) == null) 
            return newBoard;

        GamePiece player = newBoard.getPiece(startingCoordinate);
        newBoard.setPiece(startingCoordinate, null);

        HashSet<Coordinate> neighbors = startingCoordinate.getNeighbors();
        for(Coordinate neighbor : neighbors) {
            if(newBoard.getPiece(neighbor) == player) {
                removeFormation(neighbor, newBoard);
            }
        }

        return newBoard;
    }

    /**
     * Given a board and a player, finds the coordinate of a single piece of the given color
     * @param player
     * @param board
     * @return
     */
    private static Optional<Coordinate> getSinglePlayerPiece(GamePiece player, Board board) {
        
        for(int x = 0; x < Constants.BOARD_SIZE; x++) {
            for(int y = 0; y < Constants.BOARD_SIZE; y++) {
                if(board.getPiece(x, y) == player)
                    return Optional.of(new Coordinate(x, y));
            }
        }

        return Optional.empty();
    }
}
