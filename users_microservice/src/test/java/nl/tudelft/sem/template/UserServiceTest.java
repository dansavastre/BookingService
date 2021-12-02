package nl.tudelft.sem.template;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.repositories.UserRepository;
import nl.tudelft.sem.template.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureMockMvc
@SpringBootTest(classes = UserApplication.class)
public class UserServiceTest {

    @Mock
    private transient UserRepository userRepository;

    @InjectMocks
    private transient UserService userService;

    transient User user1;
    transient User user2;
    transient User user3;
    transient String s = "6363";

    @BeforeEach
    void setup() {
        user1 = new User(s, "123", "Bob", "Benson", "Student");
        user2 = new User("4832", "pwd", "Andy", "Joe", "Admin");
        user3 = new User("2839", "ok", "Joe", "Bob", "Secretary");
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
        when(userRepository.findById(s)).thenReturn(java.util.Optional.ofNullable(user1));
        Assertions.assertEquals(user1, userService.getUser(s));
    }

    @Test
    void addUser_test() {
        userService.addUser(user1);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void updateUser_test() {
        userService.updateUser(s, user1);
        verify(userRepository, times(1)).deleteById(s);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void deleteUser_test() {
        userService.deleteUser(s);
        verify(userRepository, times(1)).deleteById(s);
    }
}
