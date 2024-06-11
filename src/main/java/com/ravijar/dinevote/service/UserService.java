package com.ravijar.dinevote.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final SimpMessagingTemplate messagingTemplate;

    public UserService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendUserData(String data, String[] useIds) {
        for (String useId : useIds) {
            messagingTemplate.convertAndSend("/subscribe/user/" + useId, data);
        }
    }
}
