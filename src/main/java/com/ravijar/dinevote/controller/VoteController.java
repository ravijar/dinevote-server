package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.model.*;
import com.ravijar.dinevote.service.VoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;
    private final SimpMessagingTemplate messagingTemplate;

    VoteController(VoteService voteService , SimpMessagingTemplate messagingTemplate) {
        this.voteService = voteService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public void manageSession(@RequestBody SessionInput sessionInput) {
        SessionOutput sessionOutput = voteService.addVoteSession(sessionInput);
        sendUserData(sessionOutput.getSessionId(), sessionOutput.getUserIds());
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/subscribe/vote/{sessionId}")
    public LocationVote getVoteData(VoteInput voteInput) {
        return voteService.updateVoteSession(voteInput.getSessionId(), voteInput.getUserVote());
    }

    @MessageMapping("user/{userId}")
    @SendTo("subscribe/user/{userId}")
    public String getUserData(String message) {
        return message;
    }

    public void sendUserData(String data, String[] useIds) {
        for (String useId : useIds) {
            messagingTemplate.convertAndSend("/subscribe/user/" + useId, data);
        }
    }
}
