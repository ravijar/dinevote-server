package com.ravijar.dinevote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravijar.dinevote.enums.communication.MessageTypes;
import com.ravijar.dinevote.model.*;
import com.ravijar.dinevote.service.VoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/initiate")
    public void initiateVoteSession(@RequestBody SessionInit sessionInit) {
        voteService.addVoteSession(sessionInit);
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/subscribe/vote/{sessionId}")
    public Message messageVoteSession(Message message) {
        Message response = null;

        switch (message.getMessageType()) {
            case SESSION_LOGIN:
                SessionLogin sessionLogin = objectMapper.convertValue(message.getData(), SessionLogin.class);
                response = new Message(MessageTypes.SESSION_LOGIN, voteService.addUserToSession(sessionLogin.getSessionId(), sessionLogin.getUserId()));
                break;

            case VOTE:
                VoteInput voteInput = objectMapper.convertValue(message.getData(), VoteInput.class);
                response = new Message(MessageTypes.VOTE, voteService.updateVoteSession(voteInput.getSessionId(), voteInput.getUserVote()));
                break;
        }

        return response;
    }

}
