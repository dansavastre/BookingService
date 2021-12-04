package controllers.test;

import nl.tudelft.sem.template.controllers.UserController;
import nl.tudelft.sem.template.objects.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient UserController userController;

    private transient User u1;
    private transient User u2;
    private transient List<User> users = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        u1 = new User("1234567", "password", "Dan", "Savastre", "admin");
        u2 = new User("9876543", "psw", "Mike", "Jones", "employee");
        users.add(u1);
        users.add(u2);
    }

    @Test
    void getUsers_test() {
        String uri = "http://localhost:8081/users";
        when(restTemplate.getForObject(uri, List.class))
                .thenReturn(users);

        Assertions.assertThat(userController.getUsers()).isEqualTo(users);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
    }

    @Test
    void getUser_test() {
        String uri = "http://localhost:8081/getUser/".concat("1234567");
        when(restTemplate.getForObject(uri, User.class))
                .thenReturn(u1);
        Assertions.assertThat(userController.getUser("1234567")).isEqualTo(u1);
        verify(restTemplate, times(1)).getForObject(uri, User.class);
    }

    @Test
    void postUser_test() {
        String uri = "http://localhost:8081/users";
        Assertions.assertThat(userController.postUser(u1)).isTrue();
        verify(restTemplate, times(1)).postForObject(uri, u1, void.class);
    }

    @Test
    void updateUser_test() {
        String uri = "http://localhost:8081/users/".concat("1234567");
        Assertions.assertThat(userController.updateUser(u2, "1234567")).isTrue();
        verify(restTemplate, times(1)).put(uri, u2);
    }

    @Test
    void deleteUser_test() {
        String uri = "http://localhost:8081/users/".concat("1234567");
        Assertions.assertThat(userController.deleteUser("1234567")).isTrue();
        verify(restTemplate, times(1)).delete(uri);
    }

}
