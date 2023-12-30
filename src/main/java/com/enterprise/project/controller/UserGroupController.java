package com.enterprise.project.controller;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;

    @GetMapping("/userGroups")
    public ResponseEntity<List<UserGroup>> getUserGroups() {
        return ResponseEntity.ok(userGroupService.getUserGroups());
    }
}
