package org.linesOfAction.gameMaterials;

import java.awt.Color;

public enum GamePiece {
    BLACK(Color.black),
    WHITE(Color.white);

    Color color;
    GamePiece(Color color) {
        this.color = color;
    }
    
    public Color getValue() {
       return color;
    }

    public static boolean areOppositeColor(GamePiece piece1, GamePiece piece2) {
        if(piece1 == null || piece2 == null) return false;
        return (piece1 == GamePiece.WHITE ^ piece2 == GamePiece.WHITE);
    }
}
