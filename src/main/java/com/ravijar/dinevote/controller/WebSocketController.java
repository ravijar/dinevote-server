package com.ravijar.dinevote.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/init")
    @SendTo("/session/data")
    public String initialMessage(String message) {
        System.out.println("initial message: " + message);
        return message;
    }
}
