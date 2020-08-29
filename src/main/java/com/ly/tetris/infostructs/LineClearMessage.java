package com.ly.tetris.infostructs;

public class LineClearMessage {
    private boolean tspin;
    private int lines;
    private int consecTetrisOrTSpin;
    private int combo;
    private boolean perfectClear;

    public LineClearMessage() {
        tspin = false;
        lines = 0;
        consecTetrisOrTSpin = 0;
        combo = 0;
        perfectClear = false;
    }

    public LineClearMessage(
        boolean tspin, 
        int lines, 
        int consecTetrisOrTSpin,
        int combo,
        boolean perfectClear) 
    {
        this.tspin = tspin;
        this.lines = lines;
        this.consecTetrisOrTSpin = consecTetrisOrTSpin;
        this.combo = combo;
        this.perfectClear = perfectClear;
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
}