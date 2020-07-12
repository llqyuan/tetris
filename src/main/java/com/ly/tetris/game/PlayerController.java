package com.ly.tetris.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;

@Controller
public class PlayerController {
    
    @MessageMapping("/timed-interval")
    @SendTo("/topic/drop-piece")
    public BoardUpdateMessage timedInterval(EventMessage event) 
    throws Exception {
        return new BoardUpdateMessage();
    }
}