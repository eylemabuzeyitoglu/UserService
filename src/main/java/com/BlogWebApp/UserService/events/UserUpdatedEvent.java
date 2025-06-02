package com.BlogWebApp.UserService.events;

import com.BlogWebApp.UserService.model.User;

public class UserUpdatedEvent extends AbstractUserEvent{
    public UserUpdatedEvent(User user){
        super(user.getUserId(), user.getUserName());
    }
}
