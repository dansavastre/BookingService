package nl.tudelft.sem.template.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.UserApplication;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.repositories.UserRepository;
import nl.tudelft.sem.template.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    private transient UserRepository userRepository;
    @Mock
    private transient BCryptPasswordEncoder encoder;

    @InjectMocks
    private transient UserService userService;

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
    void getAllUsers_test() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);
        Assertions.assertEquals(users, userService.getAllUsers());
    }

    @Test
    void getUser_test() {
        when(userRepository.findById(string)).thenReturn(java.util.Optional.ofNullable(user1));
        Assertions.assertEquals(user1, userService.getUser(string));
    }

    @Test
    void addUser_test() {
        String pw = user1.getPassword();
        when(encoder.encode(pw)).thenReturn(pw);
        when(encoder.matches(pw, pw)).thenReturn(true);
        userService.addUser(user1);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void updateUser_test() {
        userService.updateUser(string, user1);
        verify(userRepository, times(1)).deleteById(string);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void deleteUser_test() {
        userService.deleteUser(string);
        verify(userRepository, times(1)).deleteById(string);
    }
}
