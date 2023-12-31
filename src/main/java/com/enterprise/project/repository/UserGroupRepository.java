package com.enterprise.project.repository;

import com.enterprise.project.model.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {
    Optional<UserGroup> findByGroupName(String groupName);
}
