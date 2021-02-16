package com.ly.tetris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpMappingController {
    
    @GetMapping("/game")
    public String game(Model model) {
        return "game/game";
    }

    @GetMapping(value={"", "/", "/index"})
    public String home(Model model) {
        return "index";
    }
}
