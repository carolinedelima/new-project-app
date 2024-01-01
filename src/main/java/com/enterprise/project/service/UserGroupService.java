package com.enterprise.project.service;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    public List<UserGroup> getUserGroups() {
        return (List<UserGroup>) userGroupRepository.findAll();
    }

    public UserGroup getUserGroup(Map<String, String> params) {
        final String id = params.get("id");
        if (id != null) {
            Optional<UserGroup> userGroup = userGroupRepository.findById(Long.valueOf(id));
            if (userGroup.isPresent()) {
                return userGroup.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Group [id]" + id + " not found.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only allowed [id] param.");
        }
    }

    public void createUserGroup(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    public void deleteUserGroup(Long id) {
        if (userGroupRepository.findById(id).isPresent()) {
            userGroupRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Group [id]" + id + " not found.");
        }
    }

    public void updateUserGroup(Long id, UserGroup newUserGroup) {
        Optional<UserGroup> oldUserGroup = userGroupRepository.findById(id);

        if (oldUserGroup.isPresent()) {
            newUserGroup.setId(id);
            if (newUserGroup.getGroupName() == null) {
                newUserGroup.setGroupName(oldUserGroup.get().getGroupName());
            }
            if (newUserGroup.getAdminUser() == null) {
                newUserGroup.setAdminUser(oldUserGroup.get().getAdminUser());
            }
            if (newUserGroup.getUserIds() == null) {
                newUserGroup.setUserIds(oldUserGroup.get().getUserIds());
            }
            userGroupRepository.save(newUserGroup);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Group [id]" + id + " not found.");
        }
    }
}