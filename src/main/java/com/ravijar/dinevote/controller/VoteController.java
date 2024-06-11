package com.ravijar.dinevote.controller;

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
    private final UserService userService;

    VoteController(VoteService voteService, UserService userService) {
        this.voteService = voteService;
        this.userService = userService;
    }

    @PostMapping
    public void manageSession(@RequestBody SessionInput sessionInput) {
        SessionOutput sessionOutput = voteService.addVoteSession(sessionInput);
        userService.sendUserData(sessionOutput.getSessionId(), sessionOutput.getUserIds());
    }

    @MessageMapping("/vote/{sessionId}")
    @SendTo("/subscribe/vote/{sessionId}")
    public LocationVote getVoteData(VoteInput voteInput) {
        return voteService.updateVoteSession(voteInput.getSessionId(), voteInput.getUserVote());
    }

}
