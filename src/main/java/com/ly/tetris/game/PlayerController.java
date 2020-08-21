package com.ly.tetris.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;

@Controller
public class PlayerController {

    private TetrisGame game;

    public PlayerController() {
        game = new TetrisGame();
    }
    
    @MessageMapping("/timed-interval")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage timedInterval(EventMessage event) 
    throws Exception {
        return new BoardUpdateMessage();
    }

    @MessageMapping("/key-event")
    @SendTo("/topic/board-update")
    public BoardUpdateMessage keyEvent(EventMessage event)
    throws Exception {
        return new BoardUpdateMessage(event.getKeyCommand());
    }
}