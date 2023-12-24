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
        checkIfEmailAlreadyExist(user.getEmail());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        checkIfExistUserId(id);
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User newUser) {
        final User oldUser = checkIfExistUserId(id);
        newUser.setId(id);

        if (newUser.getEmail() != null) {
            checkIfEmailAlreadyExist(newUser.getEmail());
        } else {
            newUser.setEmail(oldUser.getEmail());
        }
        if (newUser.getName() == null) newUser.setName(oldUser.getName());
        if (newUser.getPassword() == null) newUser.setPassword(oldUser.getPassword());

        userRepository.save(newUser);
    }

    private User checkIfExistUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> userIdNotFound(id.toString()));
    }

    private void checkIfEmailAlreadyExist(String email) {
        userRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User [email] " + email + " not found."));
    }

    private ResponseStatusException userIdNotFound(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User [Id] " + id + " not found.");
    }
}
