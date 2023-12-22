package com.enterprise.project.service;

import com.enterprise.project.model.User;
import com.enterprise.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
}
