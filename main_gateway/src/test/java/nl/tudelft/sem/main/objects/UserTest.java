package nl.tudelft.sem.main.objects;

import java.util.ArrayList;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private transient User user1;
    private transient User user2;
    private transient User user3;
    private transient Role role;

    @BeforeEach
    void setup() {
        user1 = new User("1234567", "password123", "Dan", "Savastre");
        user2 = new User("1234568", "password", "John", "Smith");
        user3 = new User("1234567", "password123", "Dan", "Savastre");
        role = new Role("employee");
        user1.addRole(role);
        user3.addRole(role);
    }

    @Test
    void getId_test() {
        Assertions.assertThat(user1.getId()).isEqualTo("1234567");
    }

    @Test
    void getPassword_test() {
        Assertions.assertThat(user1.getPassword()).isEqualTo("password123");
    }

    @Test
    void getFirstName_test() {
        Assertions.assertThat(user1.getFirstName()).isEqualTo("Dan");
    }

    @Test
    void getLastName_test() {
        Assertions.assertThat(user1.getLastName()).isEqualTo("Savastre");
    }

    @Test
    void getRoles_test() {
        Assertions.assertThat(user1.getRoles()).isEqualTo(new ArrayList<>(Arrays.asList(role)));
    }

    @Test
    void addRoles_test() {
        user1.addRole(new Role("secretary"));
        Assertions.assertThat(user1.getRoles()).isEqualTo(new ArrayList<>(Arrays
            .asList(role, new Role("secretary"))));
    }

    @Test
    public void equalsDifferentTest() {
        Assertions.assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void equalsSameValuesTest() {
        Assertions.assertThat(user1).isEqualTo(user1);
    }

    @Test
    public void equalsSameObjectTest() {
        Assertions.assertThat(user1).isEqualTo(user3);
    }

    @Test
    public void equalsDifferentClassTest() {
        Assertions.assertThat(user1).isNotEqualTo("user1");
    }

    @Test
    public void hashCodeTest() {
        Assertions.assertThat(user1.hashCode()).isEqualTo(598214880);
    }

    @Test
    public void toStringTest() {
        Assertions.assertThat(user1.toString())
            .isEqualTo("User{id='1234567', password='password123', "
                + "firstName='Dan', lastName='Savastre'"
                + ", roles='Role{id='null', type='employee'}'}");

    }

    @Test
    void parameterlessConstructor_test() {
        User user = new User();
        Assertions.assertThat(user).isNotNull();
    }

}
