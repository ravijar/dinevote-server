package com.ravijar.dinevote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SessionInput {
    private String[] fsqIds;
    private String[] userIds;
}
