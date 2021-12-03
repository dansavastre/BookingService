package object.test;

import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.objects.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private transient User user;

    @BeforeEach
    void setup() {
        user = new User("1234567", "password123", "Dan", "Savastre", "admin");
    }

    @Test
    void getId_test() {
        Assertions.assertThat(user.getId()).isEqualTo("1234567");
    }

    @Test
    void getPassword_test() {
        Assertions.assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void getFirstName_test() {
        Assertions.assertThat(user.getFirstName()).isEqualTo("Dan");
    }

    @Test
    void getLastName_test() {
        Assertions.assertThat(user.getLastName()).isEqualTo("Savastre");
    }

    @Test
    void getAvailable_test() {
        Assertions.assertThat(user.getUserType()).isEqualTo("admin");
    }

}
