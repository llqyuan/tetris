package com.ly.tetris.infostructs;

public class BoardUpdateMessage {
    private PieceName piece;
    // expect other stuff later:
    // * location and type of piece in play
    // * piece was locked and added to tetris stack
    // * update hold
    // * update next queue
    // * new spawn was unsuccessful
    // * start or update a timer (fall timer, lock timer)

    // temp, remove
    private KeyCommand acknowledge = KeyCommand.NOTHING;

    public BoardUpdateMessage() {
        this.piece = PieceName.NOTHING;
    }

    public BoardUpdateMessage(PieceName piece) {
        this.piece = piece;
    }

    // temp, remove
    public BoardUpdateMessage(KeyCommand key) {
        this.piece = PieceName.NOTHING;
        this.acknowledge = key;
    }

    public PieceName getPieceName() {
        return piece;
    }

    // temp, remove
    public KeyCommand getAcknowledge() {
        return acknowledge;
    }
}