package com.ly.tetris.infostructs;

public class LineClearMessage {
    private boolean tspin;
    private int lines;
    private int consecBackToBacks;
    private boolean perfectClear;

    public LineClearMessage() {
        tspin = false;
        lines = 0;
        consecBackToBacks = 0;
        perfectClear = false;
    }

    public LineClearMessage(
        boolean tspin, 
        int lines, 
        int consecBackToBacks,
        boolean perfectClear) 
    {
        this.tspin = tspin;
        this.lines = lines;
        this.consecBackToBacks = consecBackToBacks;
        this.perfectClear = perfectClear;
    }

    public boolean getTSpin() {
        return tspin;
    }

    public int getLinesCleared() {
        return lines;
    }

    public int getConsecBackToBacks() {
        return consecBackToBacks;
    }

    public boolean getPerfectClear() {
        return perfectClear;
    }
}