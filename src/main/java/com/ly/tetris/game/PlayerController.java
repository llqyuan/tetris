package com.ly.tetris.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;
import com.ly.tetris.infostructs.KeyCommand;

@Controller
public class PlayerController {

    private TetrisGame game;
    
    // Receives a message from the browser to drop the piece by one square
    // (automatic, due to gravity)
    @MessageMapping("/timed-fall")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedFall(EventMessage event) 
    throws Exception {
        return game.gravityDrop(event);
    }

    // Receives a message from the browser to lock the piece, assuming 
    // it's on the ground (automatic, lock delay)
    @MessageMapping("/timed-lock")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedLock(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }

    // Receives a message from the browser to start a new game.
    @MessageMapping("/start-new-game")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage startNewGame(EventMessage event) 
    throws Exception {
        game = new TetrisGame();
        return game.startingPieceSpawn(event);
    }

    // Receives a message from the browser to hard-drop the piece 
    // (player command).
    @MessageMapping("/hard-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage hardDropCommand(EventMessage event)
    throws Exception {
        return game.hardDrop(event);
    }

    // Receives a message from the browser to soft-drop the piece by 
    // one square (player command).
    @MessageMapping("/soft-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage softDropCommand(EventMessage event)
    throws Exception {
        return game.softDrop(event);
    }

    // Receives a message from the browser to move the piece by 
    // one square to the left or right (player command).
    @MessageMapping("/move")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage moveCommand(EventMessage event)
    throws Exception {
        if (event.getKeyCommand() == KeyCommand.LEFT) {
            return game.moveLeft(event);
        } else {
            return game.moveRight(event);
        }
    }

    // Receives a message from the browser to rotate the piece
    // (player command).
    @MessageMapping("/rotate")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage rotateCommand(EventMessage event)
    throws Exception {
        if (event.getKeyCommand() == KeyCommand.CLOCKWISE) {
            return game.rotateClockwise(event);
        } else {
            return game.rotateCounterClockwise(event);
        }
    }

    // Receives a message from the browser to hold the current 
    // piece (player command).
    @MessageMapping("/hold")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage holdCommand(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }
}