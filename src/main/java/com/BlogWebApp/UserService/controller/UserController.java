package com.BlogWebApp.UserService.controller;

import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.UserService.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void CreateUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    @PatchMapping("/{id}")
    public void updateUserInfo(@PathVariable long id,
                           @RequestBody UserRequest userRequest){
        userService.updateUserInfo(id, userRequest);
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
