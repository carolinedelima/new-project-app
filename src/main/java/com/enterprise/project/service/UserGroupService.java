package com.enterprise.project.service;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
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

    public List<UserGroup> getUserGroups() {
        return (List<UserGroup>) userGroupRepository.findAll();
    }

    public UserGroup getUserGroup(Map<String, String> params) {
        final String id = params.get("id");
        if (id != null) {
            return findById(Long.valueOf(id));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only allowed [id] param.");
        }
    }

    public void createUserGroup(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    public void deleteUserGroup(Long id) {
        findById(id);
        userGroupRepository.deleteById(id);
    }

    public void updateUserGroup(Long id, UserGroup newUserGroup) {
        final UserGroup oldUserGroup = findById(id);
        newUserGroup.setId(id);

        if (newUserGroup.getGroupName() == null) {
            newUserGroup.setGroupName(oldUserGroup.getGroupName());
        }
        if (newUserGroup.getAdminUser() == null) {
            newUserGroup.setAdminUser(oldUserGroup.getAdminUser());
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
}