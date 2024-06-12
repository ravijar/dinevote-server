package com.ravijar.dinevote.service;

import com.ravijar.dinevote.enums.user.Status;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, String> users = new HashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public UserService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendSessionId(String SessionId, String[] useIds) {
        for (String useId : useIds) {
            messagingTemplate.convertAndSend("/subscribe/user/" + useId, SessionId);
        }
    }

    public void addUser(String userId) {
        users.put(userId, Status.AVAILABLE.getStatus());
    }

    public void removeUser(String userId) {
        users.remove(userId);
    }

    public void changeUserStatus(String userId) {
        if (users.containsKey(userId)) {

            if (users.get(userId).equals(Status.AVAILABLE.getStatus())) {
                users.replace(userId, Status.BUSY.getStatus());
            }
        }
    }
}
