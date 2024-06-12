package com.ravijar.dinevote.service;

import com.ravijar.dinevote.enums.user.Status;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, Status> liveUsers = new HashMap<>();
    private final Map<String, String> requestedUsers = new HashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public UserService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendSessionId(String SessionId, String[] useIds) {
        for (String useId : useIds) {
            messagingTemplate.convertAndSend("/subscribe/user/" + useId, SessionId);
        }
    }

    public void addLiveUser(String userId) {
        if (requestedUsers.containsKey(userId)) {
            liveUsers.put(userId, Status.AVAILABLE);
            requestedUsers.remove(userId);
        }
    }

    public void removeLiveUser(String userId) {
        liveUsers.remove(userId);
    }

    public void changeUserStatus(String userId, Status status) {
        if (liveUsers.containsKey(userId)) {
            liveUsers.replace(userId, status);
        }
    }

    public void requestUser(String userId, String sessionId) {
        if (!requestedUsers.containsKey(userId)) {
            requestedUsers.put(userId, sessionId);
        }
    }
}
