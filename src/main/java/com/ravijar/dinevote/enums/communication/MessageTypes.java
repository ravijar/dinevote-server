package com.ravijar.dinevote.enums.communication;

import lombok.Getter;

@Getter
public enum MessageTypes {
    USER_LOGIN,
    USER_REQUESTED,
    USER_LOGOUT,
    SESSION_LOGIN,
    SESSION_STARTED,
    SESSION_FINISHED,
    VOTE

}
