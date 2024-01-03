package com.enterprise.project.service;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
import com.enterprise.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    public List<UserGroup> getUserGroups() {
        return (List<UserGroup>) userGroupRepository.findAll();
    }

    public UserGroup getUserGroup(Map<String, String> params) {
        final String id = params.get("id");
        if (id != null) {
            return findById(Long.valueOf(id));
        }

        final String groupName = params.get("groupName");
        if (groupName != null) {
            return findByGroupName(groupName);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only allowed [id] and [groupName] params.");
        }
    }

    public void createUserGroup(UserGroup userGroup) {
        validateUserById(userGroup.getAdminUserId());
        validateGroupName(userGroup.getGroupName());
        userGroupRepository.save(userGroup);
    }

    public void deleteUserGroup(Long id) {
        findById(id);
        userGroupRepository.deleteById(id);
    }

    public void updateUserGroup(Long id, UserGroup newUserGroup) {
        final UserGroup oldUserGroup = findById(id);
        newUserGroup.setId(id);

        if (newUserGroup.getGroupName() != null) {
            validateGroupName(newUserGroup.getGroupName());
        } else {
            newUserGroup.setGroupName(oldUserGroup.getGroupName());
        }

        if (newUserGroup.getAdminUserId() != null) {
            validateUserById(newUserGroup.getAdminUserId());
        } else {
            newUserGroup.setAdminUserId(oldUserGroup.getAdminUserId());
        }

        if (newUserGroup.getUserIds() != null) {
            Set<Long> newUserIds = oldUserGroup.getUserIds();
            newUserIds.addAll(newUserGroup.getUserIds());
            newUserGroup.setUserIds(newUserIds);
        } else {
            newUserGroup.setUserIds(oldUserGroup.getUserIds());
        }
        userGroupRepository.save(newUserGroup);
    }

    private UserGroup findById(Long id) {
        return userGroupRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User Group [id]" + id + " not found."));
    }

    private UserGroup findByGroupName(String groupName) {
        return userGroupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User Group [groupName] " + groupName + " not found."));
    }

    private void validateUserById(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User [id] " + id + " not found."));
    }

    private void validateGroupName(String groupName) {
        userGroupRepository.findByGroupName(groupName).ifPresent(userGroup -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group Name " + groupName + " already taken.");
        });
    }
}