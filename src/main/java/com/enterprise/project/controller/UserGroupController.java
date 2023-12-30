package com.enterprise.project.controller;

import com.enterprise.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserGroupController {

    @Autowired
    UserService userService;

    @GetMapping("/userGroups")
    public ResponseEntity<?> getUserGroups() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
