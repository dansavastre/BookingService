package nl.tudelft.sem.template;

import org.junit.jupiter.api.Assertions;
import nl.tudelft.sem.template.controllers.UserController;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    User user1;

    User user2;

    User user3;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = new User("6363", "123", "Bob", "Benson", "Student");
        user2 = new User("4832", "pwd", "Andy", "Joe", "Admin");
        user3 = new User("2839", "ok", "Joe", "Bob", "Secretary");
        userController = new UserController(userService);
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
    void getUserById_test() {
        when(userService.getUser("123")).thenReturn(user1);
        Assertions.assertEquals(user1,userService.getUser("123"));
    }

    @Test
    void addUser_test() {
        userController.addUser(user1);
        verify(userService,times(1)).addUser(user1);
    }

    @Test
    void updateUser_Test() {
        userController.updateUser(user1,"123");
        verify(userService,times(1)).updateUser("123",user1);
    }

    @Test
    void deleteUser_test() {
        userController.deleteUser("123");
        verify(userService,times(1)).deleteUser("123");
    }
}
