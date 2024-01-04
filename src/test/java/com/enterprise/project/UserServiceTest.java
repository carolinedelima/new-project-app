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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    User user = new User();
    static final Long USER_ID = 1L;
    static final String USER_NAME = "Caroline";
    static final String USER_EMAIL = "caroline@lima.com";
    static final String USER_PASSWORD = "12345";

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

    @Test
    public void getUserByEmail() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.ofNullable(user));
        User userByEmail = userService.getUser(Map.of("email", USER_EMAIL));
        Assertions.assertNotNull(userByEmail);
        Assertions.assertEquals(userByEmail, user);
    }

    @Test
    public void createUser() {
        userService.createUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void deleteUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        userService.deleteUser(USER_ID);
        verify(userRepository, times(1)).deleteById(USER_ID);
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        User userUpdate = user;
        userUpdate.setName("Beatriz");
        userService.updateUser(USER_ID, userUpdate);
        verify(userRepository, times(1)).save(userUpdate);
    }

    @Test(expected = ResponseStatusException.class)
    public void createUserSameId() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        userService.createUser(user);
    }

    @Test(expected = ResponseStatusException.class)
    public void createUserSameEmail() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.ofNullable(user));
        userService.createUser(user);
    }

    @Test(expected = ResponseStatusException.class)
    public void getUserByIdNotFound() {
        userService.getUser(Map.of("id", "0"));
    }

    @Test(expected = ResponseStatusException.class)
    public void getUserByEmailNotFound() {
        userService.getUser(Map.of("email", "new@email.com"));
    }

    @Test(expected = ResponseStatusException.class)
    public void getUserByWrongParam() {
        userService.getUser(Map.of("name", USER_NAME));
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteUserByIdNotFound() {
        userService.deleteUser(USER_ID);
    }

    @Test(expected = ResponseStatusException.class)
    public void updateUserByIdNotFound() {
        userService.updateUser(USER_ID, user);
    }

    @Test(expected = ResponseStatusException.class)
    public void updateUserEmailAlreadyExists() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.ofNullable(user));
        userService.updateUser(USER_ID, user);
    }
}