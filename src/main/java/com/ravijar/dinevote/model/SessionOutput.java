package com.ravijar.dinevote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SessionOutput {
    private String sessionId;
    private String[] userIds;
}
