package com.enterprise.project.service;

import com.enterprise.project.model.User;
import com.enterprise.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> userIdNotFound(id.toString()));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        checkIfExistUserId(id);
        userRepository.deleteById(id);
    }

    private void checkIfExistUserId(Long id) {
        userRepository.findById(id).orElseThrow(() -> userIdNotFound(id.toString()));
    }

    private ResponseStatusException userIdNotFound(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User [Id] " + id + " not found.");
    }
}
