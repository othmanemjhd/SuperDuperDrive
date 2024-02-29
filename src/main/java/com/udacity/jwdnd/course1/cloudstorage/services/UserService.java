package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }
    public boolean isUsernameAvailable(String username){
        return userMapper.getUser(username) == null;
    }
    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);
        return userMapper.insertUser(new User(null,user.getUsername(),encodedSalt, hashedPassword,
                user.getFirstname(), user.getLastname()));
    }

    public User getUser(String username){

        return this.userMapper.getUser(username);
    }
    public int getCurrentUserId(){
        return  this.userMapper.getUser(getCurrentUsername()).getuserId();
    }
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getName(); // Retrieves the username of the currently authenticated user
        }

        return null; // No user authenticated
    }
}
