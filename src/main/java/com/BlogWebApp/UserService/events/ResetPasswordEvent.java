package com.BlogWebApp.UserService.events;

import com.BlogWebApp.UserService.model.User;

public class ResetPasswordEvent extends AbstractUserEvent{
    public ResetPasswordEvent(User user) {
        super(user.getUserId(), user.getUserName());
    }
}
