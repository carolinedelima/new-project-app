package com.enterprise.project.controller;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;

    @GetMapping("/userGroups")
    public ResponseEntity<List<UserGroup>> getUserGroups() {
        return ResponseEntity.ok(userGroupService.getUserGroups());
    }

    @GetMapping("/userGroup")
    public ResponseEntity<UserGroup> getUserGroup(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(userGroupService.getUserGroup(params));
    }

    @PostMapping("/userGroup")
    public ResponseEntity<UserGroup> createUserGroup(@RequestBody UserGroup userGroup) {
        userGroupService.createUserGroup(userGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGroup);
    }

    @DeleteMapping("/userGroup")
    public ResponseEntity<Void> deleteUserGroup(@RequestParam Long id) {
        userGroupService.deleteUserGroup(id);
        return ResponseEntity.noContent().build();
    }
}
