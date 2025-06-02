package com.BlogWebApp.UserService.service;

import com.BlogWebApp.Common.dto.UserRequest;
import com.BlogWebApp.Common.dto.UserResponse;
import com.BlogWebApp.Common.security.JwtUtil;
import com.BlogWebApp.UserService.exceptions.UserNotFoundException;
import com.BlogWebApp.UserService.mapper.UserMapper;
import com.BlogWebApp.UserService.model.User;
import com.BlogWebApp.UserService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RabbitMQMessageProducer messageProducer;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        User user = userMapper.toUserEntity(userRequest);

        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(hashedPassword);

        User created = userRepository.save(user);
        return userMapper.toUserResponse(created);
    }

    @Transactional
    public UserResponse updateUserInfo(Long userId, UserRequest userRequest){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        if(userRequest.getUserName() != null && !userRequest.getUserName().isEmpty()){
            user.setUserName(userRequest.getUserName());
        }
        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        if(userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()){
            user.setEmail(userRequest.getEmail());
        }

        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
            userRepository.deleteById(userId);
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

         return userMapper.toUserResponse(user);
    }


    public void resetPassword(Long userId, String password){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


}






























