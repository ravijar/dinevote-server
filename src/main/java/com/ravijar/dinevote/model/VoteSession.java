package com.ravijar.dinevote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoteSession {
    private List<LocationVote> locationVotes;
    private List<UserVote> userVotes;
    private List<UserStatus> userStatuses;
    private int userCount;
    private int voteCount;
}
