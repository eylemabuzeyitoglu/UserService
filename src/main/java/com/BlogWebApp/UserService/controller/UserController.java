package com.BlogWebApp.UserService.controller;

import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void CreateUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    @PatchMapping
    public void updateUserInfo(@RequestParam Long userId,
                           @RequestBody UserRequest userRequest){
        userService.updateUserInfo(userId, userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PatchMapping("/{id}")
    public void resetPassword(@PathVariable Long userId,
                              @RequestParam String password){
        userService.resetPassword(userId, password);
    }

}
