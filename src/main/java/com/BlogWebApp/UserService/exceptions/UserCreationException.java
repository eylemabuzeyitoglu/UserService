package com.BlogWebApp.UserService.exceptions;

public class UserCreationException extends RuntimeException{
    public UserCreationException(String message) {
        super(message);
    }
}
