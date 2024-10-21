package org.linesOfAction.gameLogic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.linesOfAction.gameMaterials.Board;
import org.linesOfAction.gameMaterials.GamePiece;
import org.linesOfAction.gameMaterials.Line;
import org.linesOfAction.gameMaterials.Lines;
import org.linesOfAction.util.Constants;
import org.linesOfAction.util.Coordinate;

public class Game {

    private Board board;

    private GamePiece currentPlayer;

    public Game() {
        board = new Board();
        currentPlayer = GamePiece.BLACK;

        setUpNewGame();
    }

    /**
     * Sets board with 12 blacks and 12 whites, so that 
     * black pieces are at top and bottom, and white pieces 
     * are on right and left sides
     */
    private void setUpNewGame() {
        for(int y = 1; y < Constants.BOARD_SIZE - 1; y++) {
            board.setPiece(0, y, GamePiece.WHITE);
            board.setPiece(Constants.BOARD_SIZE - 1, y, GamePiece.WHITE);
        }

        for(int x = 1; x < Constants.BOARD_SIZE - 1; x++) {
            board.setPiece(x, 0, GamePiece.BLACK);
            board.setPiece(x, Constants.BOARD_SIZE - 1, GamePiece.BLACK);
        }
    }

    
    /** 
     * If a move is valid, moves the gamePiece at the given 
     * start coordinate to the given target coordinate, 
     * and then switches the current player. If move is 
     * not valid, does nothing
     * @param start
     * @param target
     */
    public void move(Coordinate start, Coordinate target) {
        if(isValidMove(start, target)){
            board.setPiece(target, board.getPiece(start));
            board.setPiece(start, null);
            switchPlayer();
        }
    }

    public void move(int[] move) {
        if(move.length != 4) throw new IllegalArgumentException("move array must have length 2");

        Coordinate startCoordinate = new Coordinate(move[0],move[1]);
        Coordinate endCoordinate = new Coordinate(move[2],move[3]);
        move(startCoordinate, endCoordinate);
    }

    /**
     * Switches current player to whichever player is not current
     */
    private void switchPlayer() {
        switch(currentPlayer) {
            case BLACK -> currentPlayer = GamePiece.WHITE;
            case WHITE -> currentPlayer = GamePiece.BLACK;
            default -> throw new IllegalStateException("current player is neither black nor white");
            
        }
    }

    /**
     * Checks if a piece is allowed to move from the given start coordinate to the given target coordinate
     * @param start
     * @param target
     * @return true if valid, false otherwise
     */
    private boolean isValidMove(Coordinate start, Coordinate target) {
        return getPossibleMovement(start).contains(target);
    }

    /**
     * Finds all valid moves for a piece at a given coordinate
     * @param coordinate
     * @return HashSet containing coordinates of all valid moves
     */
    public HashSet<Coordinate> getPossibleMovement(Coordinate coordinate) {
        HashSet<Coordinate> moves = new HashSet<Coordinate>();
        GamePiece piece = board.getPiece(coordinate);

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
        return board.toString();
    }

    
    /** Determines whether or not the game has ended
     * @return Optional GamePiece Will return a white
     *  gamePiece if white has won, black gamePiece is 
     * black has won, or an empty Optional if nobody has won
     */
    public Optional<GamePiece> isGameOver() {
        if(WinValidator.hasWon(GamePiece.WHITE, board)) {
            return Optional.of(GamePiece.WHITE);
        }
        if(WinValidator.hasWon(GamePiece.BLACK, board)) {
            return Optional.of(GamePiece.BLACK);
        }
        return Optional.empty();
    }

    public int getNumPieces(GamePiece player) { return board.getNumPieces(player); }

    public GamePiece getPiece(int x, int y) { return board.getPiece(x, y); }

    /**
     * Returns a 2d array representing the current board, 
     * with 1s representing white tiles, -1s representing 
     * black tiles, and 0s representing empty tiles
     * @return 2d integer array
     */
    public int[][] getBoard2dIntArray() {
        return board.get2dIntArray(); 
    }
}
