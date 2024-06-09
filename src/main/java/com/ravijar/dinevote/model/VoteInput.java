package com.ravijar.dinevote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VoteInput {
    private String sessionId;
    private UserVote userVote;
}
