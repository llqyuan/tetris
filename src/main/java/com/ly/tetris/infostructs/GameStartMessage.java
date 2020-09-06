package com.ly.tetris.infostructs;


/*
GameStartMessage contains information on the settings that 
the user set for the new game.
*/

public class GameStartMessage {
    private int level;

    public GameStartMessage() {
        this.level = 1;
    }

    public GameStartMessage(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
