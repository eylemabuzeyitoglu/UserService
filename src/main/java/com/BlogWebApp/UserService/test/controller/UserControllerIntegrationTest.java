package com.BlogWebApp.UserService.test.controller;

import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.UserService.repository.UserRepository;
import com.BlogWebApp.UserService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void Setup(){

    }
    @Test
    void shouldCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Integration Test User");
        userRequest.setEmail("integration@example.com");
        userRequest.setPassword("securepassword123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUserInfo() throws Exception {

        UserRequest initialUser = new UserRequest();
        initialUser.setUserName("Initial User");
        initialUser.setEmail("initial@example.com");
        initialUser.setPassword("initialpass");
        userService.createUser(initialUser);

        Long userId = userRepository.findByEmail("initial@example.com").get().getUserId();

        UserRequest updateRequest = new UserRequest();
        updateRequest.setUserName("Updated User Name");
        updateRequest.setEmail("updated@example.com");


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());


    }

    @Test
    void shouldDeleteUser() throws Exception {
        UserRequest userToDelete = new UserRequest();
        userToDelete.setUserName("User To Delete");
        userToDelete.setEmail("delete@example.com");
        userToDelete.setPassword("deletepass");
        userService.createUser(userToDelete);

        Long userId = userRepository.findByEmail("delete@example.com").get().getUserId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", userId))
                .andExpect(status().isOk());


        assertFalse(userRepository.findById(userId).isPresent());
    }

    @Test
    void shouldResetUserPassword() throws Exception {
        UserRequest userToReset = new UserRequest();
        userToReset.setUserName("User To Reset");
        userToReset.setEmail("reset@example.com");
        userToReset.setPassword("oldpassword");
        userService.createUser(userToReset);

        Long userId = userRepository.findByEmail("reset@example.com").get().getUserId();
        String newPassword = "newsecurepassword";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user/{id}", userId)
                        .param("password", newPassword)) // RequestParam olduğu için param kullanıyoruz
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingUser() throws Exception {
        UserRequest updateRequest = new UserRequest();
        updateRequest.setUserName("Non Existent Update");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                        .param("userId", "99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", "99999"))
                .andExpect(status().isNotFound());
    }
}
