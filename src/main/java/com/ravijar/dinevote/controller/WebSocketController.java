package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.model.*;
import com.ravijar.dinevote.service.VoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final VoteService voteService;

    WebSocketController(VoteService voteService) {
        this.voteService = voteService;
    }

    @MessageMapping("/vote")
    @SendTo("/subscribe/vote")
    public SessionOutput getSessionData(SessionInput sessionInput) {
        return voteService.addVoteSession(sessionInput);
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/subscribe/vote/{sessionId}")
    public LocationVote getVoteData(VoteInput voteInput) {
        return voteService.updateVoteSession(voteInput.getSessionId(), voteInput.getUserVote());
    }
}
