package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.model.LocationVote;
import com.ravijar.dinevote.model.SessionInput;
import com.ravijar.dinevote.model.SessionOutput;
import com.ravijar.dinevote.model.UserVote;
import com.ravijar.dinevote.service.VoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebSocketController {

    private final VoteService voteService;

    WebSocketController(VoteService voteService) {
        this.voteService = voteService;
    }

    @MessageMapping("/vote")
    @SendTo("/session/vote")
    public SessionOutput getSessionData(SessionInput sessionInput) {
        return voteService.addVoteSession(sessionInput);
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/session/vote/{sessionId}")
    public LocationVote getVoteData(@Payload UserVote userVote, @PathVariable String sessionId) {
        return voteService.updateVoteSession(sessionId, userVote);
    }
}
