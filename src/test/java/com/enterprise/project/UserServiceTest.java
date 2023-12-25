package com.enterprise.project;

import com.enterprise.project.model.User;
import com.enterprise.project.repository.UserRepository;
import com.enterprise.project.service.UserService;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    User user = new User();
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "Caroline";
    private static final String USER_EMAIL = "caroline@lima.com";
    private static final String USER_PASSWORD = "12345";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
    }

    @Test
    public void getUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.getUsers();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0), this.user);
    }

    @Test
    public void getUserById() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        User userById = userService.getUser(Map.of("id", USER_ID.toString()));
        Assertions.assertNotNull(userById);
        Assertions.assertEquals(userById, user);
    }
}