package com.brokeragefirmchallenge.bfchallenge.service;

import com.brokeragefirmchallenge.bfchallenge.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserRoleByUserId(Long userId) {
        return userRepository.findRoleByUserId(userId);  // Get role by userId
    }
}
