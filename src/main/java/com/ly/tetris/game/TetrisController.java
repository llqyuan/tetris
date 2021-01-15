package com.ly.tetris.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;
import com.ly.tetris.infostructs.GameStartMessage;
import com.ly.tetris.infostructs.KeyCommand;


/** 
Implements the Controller portion of the Model-View-Controller 
design pattern.
*/

@Controller
public class TetrisController {

    private Logger logger = LoggerFactory.getLogger(TetrisController.class);
    private TetrisGame game;
    
    /** 
    Receives a message from the browser to drop the piece by one square
    (automatic, due to gravity)
    @param event info communicated from browser
    */
    @MessageMapping("/timed-fall")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedFall(EventMessage event) 
    throws Exception {
        return game.gravityDrop(event);
    }

    /**
    Receives a message from the browser to lock the piece, assuming 
    it's on the ground (automatic, lock delay)
    @param event info communicated from browser
    */
    @MessageMapping("/timed-lock")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedLock(EventMessage event)
    throws Exception {
        return game.automaticLock(event);
    }

    /**
    Receives a message from the browser to start a new game.
    @param message info communicated from browser
    */
    @MessageMapping("/start-new-game")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage startNewGame(GameStartMessage message) 
    throws Exception {
        logger.info(
            "Started new game with level " + 
            Integer.toString(message.getLevel()) + 
            ".");
        game = new TetrisGame(message.getLevel());
        return game.startingPieceSpawn();
    }

    /** 
    Receives a message from the browser to hard-drop the piece 
    (player command).
    @param event info communicated from browser
    */
    @MessageMapping("/hard-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage hardDropCommand(EventMessage event)
    throws Exception {
        return game.hardDrop(event);
    }

    /** 
    Receives a message from the browser to sonic-drop the 
    current piece (player command).
    @param event info communicated from browser
    */
    @MessageMapping("/sonic-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage sonicDropCommand(EventMessage event)
    throws Exception {
        return game.sonicDrop(event);
    }

    /** 
    Receives a message from the browser to move the piece by 
    one square to the left or right (player command).
    @param event info communicated from browser
    */
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

    /** 
    Receives a message from the browser to rotate the piece
    (player command).
    @param event info communicated from browser
    */
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

    /** 
    Receives a message from the browser to hold the current 
    piece (player command).
    @param event info communicated from browser
    */
    @MessageMapping("/hold")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage holdCommand(EventMessage event)
    throws Exception {
        return game.hold(event);
    }
}