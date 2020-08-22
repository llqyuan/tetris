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

    public PlayerController() {
        game = new TetrisGame();
    }
    
    @MessageMapping("/timed-fall")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedFall(EventMessage event) 
    throws Exception {
        return new BoardUpdateMessage();
    }

    @MessageMapping("/hard-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage hardDropCommand(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }

    @MessageMapping("/soft-drop")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage softDropCommand(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }

    @MessageMapping("/move")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage moveCommand(EventMessage event)
    throws Exception {
        if (event.getKeyCommand() == KeyCommand.LEFT) {
            // tell game to move left
        } else {
            // tell game to move right
        }
        return new BoardUpdateMessage(event.getKeyCommand());
    }

    @MessageMapping("/rotate")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage rotationCommand(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }

    @MessageMapping("/hold")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage holdCommand(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }
}