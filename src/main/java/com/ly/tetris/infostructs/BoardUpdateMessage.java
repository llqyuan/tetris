package com.ly.tetris.infostructs;

public class BoardUpdateMessage {
    private PieceName piece;

    public BoardUpdateMessage() {
        this.piece = PieceName.NOTHING;
    }

    public BoardUpdateMessage(PieceName piece) {
        this.piece = piece;
    }

    public PieceName getPieceName() {
        return piece;
    }
}