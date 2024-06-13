package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.enums.communication.MessageTypes;
import com.ravijar.dinevote.enums.communication.ResponseTypes;
import com.ravijar.dinevote.model.Message;
import com.ravijar.dinevote.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("user/{userId}")
    @SendTo("subscribe/user/{userId}")
    public Message messageUserSession(Message message) {
        Message response = null;
        String userId = (String) message.getData();

        switch (message.getMessageType()){
            case USER_LOGIN:
                response = new Message(MessageTypes.USER_LOGIN, userService.addLiveUser(userId));
                break;

            case USER_REQUESTED:
                if (userService.userRequested(userId)) {
                    response = new Message(MessageTypes.USER_REQUESTED, userService.getRequestedUserSessionId(userId));
                } else {
                    response = new Message(MessageTypes.USER_REQUESTED, ResponseTypes.NOT_REQUESTED);
                }
                break;

            case USER_LOGOUT:
                response = new Message(MessageTypes.USER_LOGOUT, userService.removeLiveUser(userId));
                break;
        }

        return response;
    }
}
