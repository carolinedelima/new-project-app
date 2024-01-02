package com.enterprise.project;

import com.enterprise.project.model.UserGroup;
import com.enterprise.project.repository.UserGroupRepository;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupServiceTest {

    UserGroup userGroup = new UserGroup();
    private static final Long USER_GROUP_ID = 1L;
    private static final String USER_GROUP_NAME = "Group1";
    private static final Long USER_GROUP_ADMIN = 1L;
    private static final Set<Long> USER_GROUP_USER_IDS = Set.of(2L);

    @Mock
    UserGroupRepository userGroupRepository;

    @InjectMocks
    UserGroupService userGroupService;

    @Before
    public void setUp() {
        userGroup.setId(USER_GROUP_ID);
        userGroup.setGroupName(USER_GROUP_NAME);
        userGroup.setAdminUserId(USER_GROUP_ADMIN);
        userGroup.setUserIds(USER_GROUP_USER_IDS);
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
}
