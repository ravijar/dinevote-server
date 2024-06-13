package com.ravijar.dinevote.service;

import com.ravijar.dinevote.enums.communication.MessageTypes;
import com.ravijar.dinevote.enums.communication.ResponseTypes;
import com.ravijar.dinevote.enums.user.Status;
import com.ravijar.dinevote.model.Message;
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

    public ResponseTypes addLiveUser(String userId) {
        liveUsers.put(userId, Status.AVAILABLE);
        return ResponseTypes.SUCCESSFUL;
    }

    public ResponseTypes removeLiveUser(String userId) {
        liveUsers.remove(userId);
        return ResponseTypes.SUCCESSFUL;
    }

    public void changeUserStatus(String userId, Status status) {
        if (liveUsers.containsKey(userId)) {
            liveUsers.replace(userId, status);
        }
    }

    public void requestUser(String userId, String sessionId) {
        if (!requestedUsers.containsKey(userId)) {
            requestedUsers.put(userId, sessionId);
            sendRequestMessage(sessionId, userId);
        }
    }

    public boolean userRequested(String userId) {
        return requestedUsers.containsKey(userId);
    }

    public String getRequestedUserSessionId(String userId) {
        return requestedUsers.get(userId);
    }

    public void sendRequestMessage(String sessionId, String useId) {
        Message message = new Message(MessageTypes.USER_REQUESTED, sessionId);
        messagingTemplate.convertAndSend("/subscribe/user/" + useId, message);
    }
}
