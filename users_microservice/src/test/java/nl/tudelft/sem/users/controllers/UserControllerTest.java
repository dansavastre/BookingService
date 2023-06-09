package nl.tudelft.sem.users.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.objects.User;
import nl.tudelft.sem.users.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private transient UserService userService;

    @InjectMocks
    private transient UserController userController;

    transient User user1;

    transient User user2;

    transient User user3;

    transient String string = "6363";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = new User(string, "123", "Bob", "Benson");
        user2 = new User("4832", "pwd", "Andy", "Joe");
        user3 = new User("2839", "ok", "Joe", "Bob");
    }

    @Test
    void sayHi_test() {
        Assertions.assertEquals("Hello from the user microservice!", userController.sayHi());
    }

    @Test
    void getAllRooms_test() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userService.getAllUsers()).thenReturn(users);
        Assertions.assertEquals(users, userController.getAllUsers());
    }

    @Test
    void addUser_test() {
        userController.addUser(user1);
        verify(userService, times(1)).addUser(user1);
    }

    @Test
    void updateUser_Test() {
        userController.updateUser(user1, string);
        verify(userService, times(1)).updateUser(string, user1);
    }

    @Test
    void deleteUser_test() {
        userController.deleteUser(string);
        verify(userService, times(1)).deleteUser(string);
    }
}
