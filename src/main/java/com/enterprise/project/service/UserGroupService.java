package com.enterprise.project.service;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    public List<UserGroup> getUserGroups() {
        return (List<UserGroup>) userGroupRepository.findAll();
    }
}