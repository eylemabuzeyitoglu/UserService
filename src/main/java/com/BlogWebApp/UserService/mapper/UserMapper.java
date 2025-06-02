package com.BlogWebApp.UserService.mapper;


import com.BlogWebApp.Common.dto.UserResponse;
import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.UserService.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    public User toUserEntity(UserRequest userRequest){
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return user;
    }
}
