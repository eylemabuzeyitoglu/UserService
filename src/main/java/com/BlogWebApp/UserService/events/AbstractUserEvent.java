package com.BlogWebApp.UserService.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractUserEvent implements Serializable {
    private final Long userId;
    private final String userName;
}
