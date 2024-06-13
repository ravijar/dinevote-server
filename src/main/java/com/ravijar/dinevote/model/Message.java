package com.ravijar.dinevote.model;

import com.ravijar.dinevote.enums.communication.MessageTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    private MessageTypes messageType;
    private Object data;
}
