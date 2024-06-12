package com.ravijar.dinevote.service;

import com.ravijar.dinevote.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VoteService {

    private final Map<String, VoteSession> voteSessions = new HashMap<>();
    private final UserService userService;

    public VoteService(UserService userService) {
        this.userService = userService;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public SessionOutput addVoteSession(SessionInput sessionInput) {
        String sessionId = generateSessionId();
        List<LocationVote> locationVotes = new ArrayList<>();
        List<UserVote> userVotes = new ArrayList<>();

        for (String fsqId : sessionInput.getFsqIds()) {
            locationVotes.add(new LocationVote(fsqId, 0));
        }

        for (String userId : sessionInput.getUserIds()) {
            userVotes.add(new UserVote(userId, null));
            userService.changeUserStatus(userId);
        }

        voteSessions.put(sessionId, new VoteSession(locationVotes, userVotes));

        return new SessionOutput(sessionId, sessionInput.getUserIds());
    }

    public LocationVote updateVoteSession(String sessionId, UserVote newUserVote) {
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
        }

        return updatedLocationVote;
    }
}
