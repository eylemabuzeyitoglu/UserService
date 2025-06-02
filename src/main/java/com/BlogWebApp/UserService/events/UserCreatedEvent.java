package com.BlogWebApp.UserService.events;

import com.BlogWebApp.UserService.model.User;

public class UserCreatedEvent extends AbstractUserEvent{

    public UserCreatedEvent(User user) {
        super(user.getUserId(),user.getUserName());
    }
}
