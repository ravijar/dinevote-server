package com.ravijar.dinevote.model;

import com.ravijar.dinevote.enums.communication.MessageTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Message {
    private MessageTypes messageType;
    private Object data;
}
