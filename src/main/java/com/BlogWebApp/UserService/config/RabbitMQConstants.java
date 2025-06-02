package com.BlogWebApp.UserService.config;

public class RabbitMQConstants {
    public static final String USER_CREATED_QUEUE = "user-created-queue";
    public static final String USER_UPDATED_QUEUE = "user-updated-queue";
    public static final String USER_RESET_PASSWORD_QUEUE = "user-reset-password-queue";
    public static final String USER_DELETED_QUEUE = "user-deleted-queue";
    public static final String USER_EXCHANGE = "user-exchange";
    public static final String USER_CREATED_ROUTING_KEY = "user.created";
    public static final String USER_UPDATED_ROUTING_KEY = "user.updated";
    public static final String USER_RESET_PASSWORD_ROUTING_KEY = "user.resetPassword";
    public static final String USER_DELETED_ROUTING_KEY = "user.deleted";
}
