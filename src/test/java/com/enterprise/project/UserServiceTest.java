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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    User user = new User();
    final Long id = 1L;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        user.setId(id);
        user.setName("Caroline");
        user.setEmail("caroline@lima.com");
        user.setPassword("12345");
    }

    @Test
    public void getUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> questions = userService.getUsers();
        Assertions.assertEquals(questions.size(), 1);
        Assertions.assertEquals(questions.get(0), user);
    }
}