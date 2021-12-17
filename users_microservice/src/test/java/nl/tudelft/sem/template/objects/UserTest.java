package nl.tudelft.sem.template.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    transient String string = "Random";

    @Test
    public void firstNameTest() {
        User user = new User();
        user.setFirstName(string);
        Assertions.assertEquals(string, user.getFirstName());
    }

    @Test
    public void lastNameTest() {
        User user = new User();
        user.setLastName(string);
        Assertions.assertEquals(string, user.getLastName());
    }

    @Test
    public void idTest() {
        User user = new User();
        user.setId("1234");
        Assertions.assertEquals("1234", user.getId());
    }

    @Test
    public void passwordTest() {
        User user = new User();
        user.setPassword("password");
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    public void equalsTest() {
        User user1 = new User("123", "pwd", "Joe", "B");
        User user2 = new User("123", "pwd", "Joe", "B");
        Assertions.assertTrue(user1.equals(user2));
    }

    @Test
    public void toStringTest() {
        User user = new User("123", "pwd", "Joe", "B");
        Assertions.assertEquals("User{id='123', password='pwd', "
                + "firstName='Joe', lastName='B', roles=''}", user.toString());

    }
}
