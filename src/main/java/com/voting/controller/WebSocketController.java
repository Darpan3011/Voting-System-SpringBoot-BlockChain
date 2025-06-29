package com.voting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/vote-updates")
    @SendTo("/topic/vote-counts")
    public String broadcastVoteUpdate(String update) {
        return update;
    }
} 