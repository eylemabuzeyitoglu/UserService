package com.BlogWebApp.UserService.events;

import com.BlogWebApp.UserService.model.User;

public class UserDeletedEvent extends AbstractUserEvent{
    public UserDeletedEvent(User user) {
        super(user.getUserId(), user.getUserName());
    }
}
