package com.ravijar.dinevote.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @MessageMapping("user/{userId}")
    @SendTo("subscribe/user/{userId}")
    public String getUserData(String message) {
        return message;
    }
}
