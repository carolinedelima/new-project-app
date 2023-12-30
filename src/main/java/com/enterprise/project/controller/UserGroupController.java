package com.enterprise.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserGroupController {

    @GetMapping("/userGroups")
    public ResponseEntity<?> getUserGroups() {
        return ResponseEntity.ok().build();
    }
}
