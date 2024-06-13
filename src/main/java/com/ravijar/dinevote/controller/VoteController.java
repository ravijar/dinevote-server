package com.ravijar.dinevote.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravijar.dinevote.enums.communication.MessageTypes;
import com.ravijar.dinevote.model.*;
import com.ravijar.dinevote.service.UserService;
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

    @PostMapping
    public void initiateVoteSession(@RequestBody SessionInput sessionInput) {
        voteService.addVoteSession(sessionInput);
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/subscribe/vote/{sessionId}")
    public Message messageVoteSession(Message message) {
        VoteInput voteInput = objectMapper.convertValue(message.getData(), VoteInput.class);
        return new Message(MessageTypes.VOTE, voteService.updateVoteSession(voteInput.getSessionId(), voteInput.getUserVote()));
    }

}
