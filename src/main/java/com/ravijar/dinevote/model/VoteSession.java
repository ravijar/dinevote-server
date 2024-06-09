package com.ravijar.dinevote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class VoteSession {
    private List<LocationVote> locationVotes;
    private List<UserVote> userVotes;
}
