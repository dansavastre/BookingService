package nl.tudelft.sem.users.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleTest {

    transient Role r1;
    transient Role r2;
    transient Role r3;

    @BeforeEach
    void setup() {

        r1 = new Role("employee");
        r2 = new Role("admin");
        r3 = new Role("admin");
    }

    @Test
    void equals_same_object_test() {
        assertTrue(r1.equals(r1));
    }

    @Test
    void equals_not_role_test() {
        assertFalse(r1.equals("r1"));
    }

    @Test
    void equals_different_test() {
        assertFalse(r1.equals(r2));
    }

    @Test
    void equals_same_test() {
        assertTrue(r2.equals(r3));
    }

    @Test
    void toString_test() {
        r1.setId(1L);
        String expected = "Role{id='1', type='employee'}";
        assertEquals(expected, r1.toString());
    }
}
