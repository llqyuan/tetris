package com.ly.tetris.infostructs;


/*
TimerUpdateMessage contains information on the timer for automatic piece-drops 
due to gravity, or automatic delayed locking when a piece reaches the ground.
*/

public class TimerUpdateMessage {

    // Set to true to tell the browser to send a message 
    // to drop the piece by one block later. Use when 
    // the falling piece is in the air.
    private boolean updateFallTimer;

    // Set to true to tell the browser to send a message 
    // to lock the piece later. Use when the falling piece 
    // is on the ground/stack.
    private boolean updateLockTimer;

    // If one of the above two is true, tells the browser to send 
    // the message after this many milliseconds. Irrelevant if 
    // both of the above are false.
    private int requestNewUpdateIn;

    public TimerUpdateMessage() {
        this.updateFallTimer = false;
        this.updateLockTimer = false;
        this.requestNewUpdateIn = -1;
    }

    public TimerUpdateMessage(
        boolean updateFallTimer, 
        boolean updateLockTimer, 
        int requestNewUpdateIn)
    {
        this.updateFallTimer = updateFallTimer;
        this.updateLockTimer = updateLockTimer;
        this.requestNewUpdateIn = requestNewUpdateIn;
    }

    public TimerUpdateMessage(TimerUpdateMessage other) {
        this.updateFallTimer = other.updateFallTimer;
        this.updateLockTimer = other.updateLockTimer;
        this.requestNewUpdateIn = other.requestNewUpdateIn;
    }

    public boolean getUpdateFallTimer() {
        return updateFallTimer;
    }

    public boolean getUpdateLockTimer() {
        return updateLockTimer;
    }

    public int getRequestNewUpdateIn() {
        return requestNewUpdateIn;
    }
}