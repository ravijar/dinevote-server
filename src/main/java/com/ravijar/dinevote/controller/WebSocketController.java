package com.ravijar.dinevote.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebSocketController {

    @MessageMapping("/vote")
    @SendTo("/session/vote")
    public String getSessionData(String message) {
        return message;
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/session/vote/{sessionId}")
    public String getVoteData(@Payload String message, @PathVariable String sessionId) {
        return message + "Joined the session";
    }
}
