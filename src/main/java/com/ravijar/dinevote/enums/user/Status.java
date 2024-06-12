package com.ravijar.dinevote.enums.user;

import lombok.Getter;

@Getter
public enum Status {
    AVAILABLE("available"),
    JOINED("joined"),
    REQUESTED("requested");

    private String status;

    Status(String status){
        this.status = status;
    }
}
