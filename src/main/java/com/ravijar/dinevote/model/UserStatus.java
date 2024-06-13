package com.ravijar.dinevote.model;

import com.ravijar.dinevote.enums.user.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserStatus {
    private String userId;
    private Status status;
}
