package com.enterprise.project;

import com.enterprise.project.model.User;
import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
import com.enterprise.project.repository.UserRepository;
import com.enterprise.project.service.UserGroupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.enterprise.project.UserServiceTest.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupServiceTest {

    User user = new User();
    UserGroup userGroup = new UserGroup();
    private static final Long USER_GROUP_ID = 1L;
    private static final String USER_GROUP_NAME = "Group1";
    private static final Long USER_GROUP_ADMIN = 1L;
    private static final Set<Long> USER_GROUP_USER_IDS = Set.of(2L);

    @Mock
    UserGroupRepository userGroupRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserGroupService userGroupService;

    @Before
    public void setUp() {
        userGroup.setId(USER_GROUP_ID);
        userGroup.setGroupName(USER_GROUP_NAME);
        userGroup.setAdminUserId(USER_GROUP_ADMIN);
        userGroup.setUserIds(USER_GROUP_USER_IDS);

        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
    }

    @Test
    public void getUserGroups() {
        when(userGroupRepository.findAll()).thenReturn(List.of(userGroup));
        List<UserGroup> userGroups = userGroupService.getUserGroups();
        Assertions.assertEquals(userGroups.size(), 1);
        Assertions.assertEquals(userGroups.get(0), this.userGroup);
    }

    @Test
    public void getUserGroupById() {
        when(userGroupRepository.findById(USER_GROUP_ID)).thenReturn(Optional.ofNullable(userGroup));
        UserGroup userGroup = userGroupService.getUserGroup(Map.of("id", USER_GROUP_ID.toString()));
        Assertions.assertNotNull(userGroup);
        Assertions.assertEquals(userGroup, this.userGroup);
    }

    @Test
    public void createUserGroup() {
        when(userRepository.findById(USER_GROUP_ID)).thenReturn(Optional.ofNullable(user));
        when(userGroupRepository.findByGroupName(USER_GROUP_NAME)).thenReturn(Optional.empty());
        userGroupService.createUserGroup(userGroup);
        verify(userGroupRepository, times(1)).save(userGroup);
    }

    @Test
    public void deleteUserGroup() {
        when(userGroupRepository.findById(USER_GROUP_ID)).thenReturn(Optional.ofNullable(userGroup));
        userGroupService.deleteUserGroup(USER_GROUP_ID);
        verify(userGroupRepository, times(1)).deleteById(USER_GROUP_ID);
    }

    @Test
    public void updateUserGroupName() {
        final String newUserGroupName = "New User Group Name";
        when(userGroupRepository.findById(USER_GROUP_ID)).thenReturn(Optional.ofNullable(userGroup));
        when(userGroupRepository.findByGroupName(newUserGroupName)).thenReturn(Optional.empty());

        UserGroup updateUserGroup = new UserGroup();
        updateUserGroup.setGroupName(newUserGroupName);
        UserGroup saveUserGroup = userGroup;
        saveUserGroup.setGroupName(newUserGroupName);

        userGroupService.updateUserGroup(USER_GROUP_ID, updateUserGroup);
        verify(userGroupRepository, times(1)).save(saveUserGroup);
    }
}
