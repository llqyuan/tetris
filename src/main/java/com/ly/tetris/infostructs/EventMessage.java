package com.ly.tetris.infostructs;

public class EventMessage {
    private KeyCommand keyCommand;
    
    public EventMessage() {
        this.keyCommand = KeyCommand.NOTHING;
    }

    public EventMessage(KeyCommand keyCommand) {
        this.keyCommand = keyCommand;
    }

    public KeyCommand getKeyCommand() {
        return keyCommand;
    }

    public void setKeyCommand(KeyCommand keyCommand) {
        this.keyCommand = keyCommand;
    }
}