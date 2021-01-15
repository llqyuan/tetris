package com.ly.tetris.infostructs;


/**
LineClearMessage contains information on the changes to the board when a 
line is cleared.
*/

public class LineClearMessage {
    private boolean tspin;
    private int lines;
    private int consecTetrisOrTSpin;
    private int combo;
    private boolean perfectClear;
    private boolean levelUp;

    public LineClearMessage() {
        tspin = false;
        lines = 0;
        consecTetrisOrTSpin = 0;
        combo = 0;
        perfectClear = false;
        levelUp = false;
    }

    public LineClearMessage(
        boolean tspin, 
        int lines, 
        int consecTetrisOrTSpin,
        int combo,
        boolean perfectClear,
        boolean levelUp) 
    {
        this.tspin = tspin;
        this.lines = lines;
        this.consecTetrisOrTSpin = consecTetrisOrTSpin;
        this.combo = combo;
        this.perfectClear = perfectClear;
        this.levelUp = levelUp;
    }

    public boolean getTSpin() {
        return tspin;
    }

    public int getLinesCleared() {
        return lines;
    }

    public int getConsecTetrisOrTSpin() {
        return consecTetrisOrTSpin;
    }

    public int getCombo() {
        return combo;
    }

    public boolean getPerfectClear() {
        return perfectClear;
    }

    public boolean getLevelUp() {
        return levelUp;
    }
}