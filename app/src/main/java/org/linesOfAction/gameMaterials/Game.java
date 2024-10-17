package org.linesOfAction.gameMaterials;

import java.util.ArrayList;
import java.util.HashSet;

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

    public void move(Coordinate start, Coordinate target) {
        if(isValidMove(start, target)){
            board.setPiece(target, board.getPiece(start));
            board.setPiece(start, null);
            switchPlayer();
        }
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

    public GamePiece getPiece(int x, int y) { return board.getPiece(x, y); }
}
