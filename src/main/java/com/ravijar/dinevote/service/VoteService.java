package com.ravijar.dinevote.service;

import com.ravijar.dinevote.enums.communication.MessageTypes;
import com.ravijar.dinevote.enums.communication.ResponseTypes;
import com.ravijar.dinevote.enums.user.Status;
import com.ravijar.dinevote.model.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VoteService {

    private final Map<String, VoteSession> voteSessions = new HashMap<>();
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public VoteService(UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public void addVoteSession(SessionInit sessionInit) {
        String sessionId = generateSessionId();
        List<LocationVote> locationVotes = new ArrayList<>();
        List<UserVote> userVotes = new ArrayList<>();
        List<UserStatus> userStatuses = new ArrayList<>();

        for (String fsqId : sessionInit.getFsqIds()) {
            locationVotes.add(new LocationVote(fsqId, 0));
        }

        for (String userId : sessionInit.getUserIds()) {
            userVotes.add(new UserVote(userId, null));
            userStatuses.add(new UserStatus(userId, Status.REQUESTED));
            userService.requestUser(userId, sessionId);
        }

        voteSessions.put(sessionId, new VoteSession(locationVotes, userVotes, userStatuses, 0, 0));

    }

    public void removeVoteSession(String sessionId) {
        voteSessions.remove(sessionId);
    }

    public LocationVote addVoteToVoteSession(String sessionId, UserVote newUserVote) {
        LocationVote updatedLocationVote = null;

        if (voteSessions.containsKey(sessionId)) {
            VoteSession voteSession = voteSessions.get(sessionId);

            for (LocationVote locationVote : voteSession.getLocationVotes()) {
                if (locationVote.getFsqId().equals(newUserVote.getFsqId())) {
                    locationVote.setVoteCount(locationVote.getVoteCount() + 1);
                    updatedLocationVote = locationVote;
                }
            }

            for (UserVote userVote : voteSession.getUserVotes()) {
                if (userVote.getUserId().equals(newUserVote.getUserId())) {
                    userVote.setFsqId(newUserVote.getFsqId());
                }
            }

            if (voteSession.getVoteCount() == voteSession.getUserVotes().size()) {
                sendSessionFinishMessage(sessionId);
                removeVoteSession(sessionId);
            }
        }

        return updatedLocationVote;
    }

    public VoteSession getVoteSessionData(String sessionId) {
        return voteSessions.get(sessionId);
    }

    public ResponseTypes addUserToVoteSession(String sessionId, String userId) {
        if (voteSessions.containsKey(sessionId)) {
            VoteSession voteSession = voteSessions.get(sessionId);
            for (UserStatus userStatus : voteSession.getUserStatuses()) {
                if (userStatus.getUserId().equals(userId)) {
                    userStatus.setStatus(Status.JOINED);
                    userService.changeUserStatus(userId, Status.JOINED);
                    userService.removeRequestedUser(userId);
                    voteSession.setUserCount(voteSession.getUserCount() + 1);
                }
            }

            if (voteSession.getUserCount() == voteSession.getUserStatuses().size()) {
                sendSessionStartMessage(sessionId);
            }
        } else {
            return ResponseTypes.FAILED;
        }
        return ResponseTypes.SUCCESSFUL;
    }

    public void sendSessionStartMessage(String sessionId) {
        Message message = new Message(MessageTypes.SESSION_STARTED, getVoteSessionData(sessionId));
        messagingTemplate.convertAndSend("/subscribe/vote/" + sessionId, message);
    }

    public void sendSessionFinishMessage(String sessionId) {
        Message message = new Message(MessageTypes.SESSION_FINISHED, getVoteSessionData(sessionId));
        messagingTemplate.convertAndSend("/subscribe/vote/" + sessionId, message);
    }
}
