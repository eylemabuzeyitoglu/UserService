package com.BlogWebApp.UserService.test.service;

import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.Common.dto.UserResponse;
import com.BlogWebApp.UserService.events.UserDeletedEvent;
import com.BlogWebApp.UserService.exceptions.UserNotFoundException;
import com.BlogWebApp.UserService.mapper.UserMapper;
import com.BlogWebApp.UserService.model.User;
import com.BlogWebApp.UserService.repository.UserRepository;
import com.BlogWebApp.UserService.service.RabbitMQMessageProducer;
import com.BlogWebApp.UserService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private RabbitMQMessageProducer messageProducer;

    @InjectMocks private UserService userService;

    private User user;

    @BeforeEach
    void Setup(){
        user = new User();
        user.setUserId(1L);
        user.setUserName("Test name");
        user.setEmail("Test email");
        user.setPassword("123456");
    }

    @Test
    void shouldCreateUserCorrect(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Test Name");
        userRequest.setEmail("test@mail.com");
        userRequest.setPassword("123456");

        User saveUser = new User();
        saveUser.setUserName("Test Name");
        saveUser.setEmail("test@mail.com");

        when(userMapper.toUserEntity(userRequest)).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("hashed123456");
        when(userRepository.save(user)).thenReturn(saveUser);
        when(userMapper.toUserResponse(saveUser)).thenReturn(new UserResponse());

        UserResponse userResponse = userService.createUser(userRequest);

        assertNotNull(userResponse);
        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateUSerCorrectly(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Update Test name");
        userRequest.setEmail("UpdateTest@mail.com");
        userRequest.setPassword("Update-12345");

        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUserName("Old name");
        existingUser.setEmail("Omlmail@mail.com");
        existingUser.setPassword("Old Password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("Update-12345")).thenReturn("hashedPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.toUserResponse(existingUser)).thenReturn(new UserResponse());

        UserResponse response = userService.updateUserInfo(1L, userRequest);

        assertNotNull(response);
        assertEquals("Update Test name", existingUser.getUserName());
        assertEquals("UpdateTest@mail.com", existingUser.getEmail());
        assertEquals("hashedPassword", existingUser.getPassword());

        verify(userRepository).save(existingUser);

    }

    @Test
    void shouldDeleteUserSuccessfully(){
        Long userIdToDelete = 3L;
        when(userRepository.findById(userIdToDelete)).thenReturn(Optional.of(user));

        userService.deleteUser(userIdToDelete);

        verify(userRepository).deleteById(userIdToDelete);
        verify(messageProducer).sendUserDeletedNotification(any(UserDeletedEvent.class));
    }

    @Test
    void shouldResetPasswordSuccessfully(){
        Long userIdToReset = 1L;
        String newPassword = "Update-12345";

        User existingUser = new User();
        existingUser.setUserId(userIdToReset);
        existingUser.setPassword("Old Password");

        when(userRepository.findById(userIdToReset)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(newPassword)).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        userService.resetPassword(userIdToReset, newPassword);

        assertEquals("hashedPassword", existingUser.getPassword());
        verify(userRepository).save(existingUser); //
    }

    @Test
    void updateUserInfo_userNotFound_throwsUserNotFoundException() {
        Long nonExistentUserId = 99L;
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Non Existent User");

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserInfo(nonExistentUserId, userRequest));

        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void deleteUser_userNotFound_throwsUserNotFoundException() {
        Long nonExistentUserId = 99L;
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(nonExistentUserId));

        verify(userRepository, never()).deleteById(anyLong());
        verify(messageProducer, never()).sendUserDeletedNotification(any(UserDeletedEvent.class));
    }

    @Test
    void resetPassword_userNotFound_throwsUserNotFoundException() {
        Long nonExistentUserId = 99L;
        String newPassword = "newPassword123";

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.resetPassword(nonExistentUserId, newPassword));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_userNotFound_throwsUserNotFoundException() {
        Long nonExistentUserId = 99L;
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(nonExistentUserId));
    }

    @Test
    void updateUserInfo_withEmptyFields_doesNotChangeExistingFields() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName(""); // Boş string
        userRequest.setEmail(null); // Null

        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUserName("Existing Name");
        existingUser.setEmail("existing@mail.com");
        existingUser.setPassword("existingPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.toUserResponse(existingUser)).thenReturn(new UserResponse());

        UserResponse response = userService.updateUserInfo(1L, userRequest);

        assertNotNull(response);
        assertEquals("Existing Name", existingUser.getUserName());
        assertEquals("existing@mail.com", existingUser.getEmail());
        assertEquals("existingPassword", existingUser.getPassword());

        verify(userRepository).save(existingUser);
    }
}

