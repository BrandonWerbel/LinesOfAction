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

    public boolean isOppositeColorTo(GamePiece piece2) {
        if(this == null || piece2 == null) return false;
        return (this == GamePiece.WHITE ^ piece2 == GamePiece.WHITE);
    }
}
