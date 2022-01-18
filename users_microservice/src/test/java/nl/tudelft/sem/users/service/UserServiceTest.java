package nl.tudelft.sem.users.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.objects.User;
import nl.tudelft.sem.users.repositories.UserRepository;
import nl.tudelft.sem.users.services.RoleService;
import nl.tudelft.sem.users.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    private transient UserRepository userRepository;
    @Mock
    private transient RoleService roleService;
    @Mock
    private transient BCryptPasswordEncoder encoder;

    @InjectMocks
    private transient UserService userService;

    transient User user1;
    transient User user2;
    transient User user3;
    transient String string1 = "6363";
    transient String string2 = "123";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = new User(string1, string2, "Bob", "Benson");
        user2 = new User("4832", "pwd", "Andy", "Joe");
        user3 = new User("2839", "ok", "Joe", "Bob");

        user1.addRole(new Role("employee"));
    }

    @Test
    void loadUserByUsername_empty_test() {
        when(userRepository.findById(string2)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(string2);
        });
    }

    @Test
    void loadUserByUsername_test() {
        when(userRepository.findById(string1)).thenReturn(Optional.of(user1));
        UserDetails userDetails = userService.loadUserByUsername(string1);
        Assertions.assertEquals(string1, userDetails.getUsername());
        Assertions.assertEquals(string2, userDetails.getPassword());
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
        when(userRepository.findById(string1)).thenReturn(java.util.Optional.ofNullable(user1));
        Assertions.assertEquals(user1, userService.getUser(string1));
    }

    @Test
    void getUser_null_test() {
        when(userRepository.findById(string1)).thenReturn(java.util.Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getUser(string1);
        });
    }

    @Test
    void addUser_test() {
        String pw = user1.getPassword();
        when(encoder.encode(pw)).thenReturn(pw);
        when(encoder.matches(pw, pw)).thenReturn(true);
        userService.addUser(user1);
        verify(userRepository, times(1)).save(user1);
        verify(roleService, times(1)).addRole(any());
    }

    @Test
    void updateUser_test() {
        userService.updateUser(string1, user1);
        verify(userRepository, times(1)).deleteById(string1);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void deleteUser_test() {
        userService.deleteUser(string1);
        verify(userRepository, times(1)).deleteById(string1);
    }
}
