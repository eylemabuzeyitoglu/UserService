package com.BlogWebApp.UserService.exceptions;

public class UserUpdateException extends RuntimeException{
    public UserUpdateException(String message) {
        super(message);
    }
}
