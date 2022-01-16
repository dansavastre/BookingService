package nl.tudelft.sem.main.objects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {

    private transient Role role;

    @BeforeEach
    void setup() {
        role = new Role("employee");
        role.setId(1L);
    }

    @Test
    void getId_test() {
        Assertions.assertThat(role.getId()).isEqualTo(1L);
    }

    @Test
    void getType_test() {
        Assertions.assertThat(role.getType()).isEqualTo("employee");
    }

    @Test
    void setType_test() {
        role.setType("admin");
        Assertions.assertThat(role.getType()).isEqualTo("admin");
    }

    @Test
    void setId_test() {
        role.setId(3l);
        Assertions.assertThat(role.getId()).isEqualTo(3L);
    }

    @Test
    void equalsTrue_test() {
        Role role2 = new Role();
        role2.setType("employee");
        role2.setId(1L);
        assertTrue(role.equals(role2));
        assertTrue(role.hashCode() == role2.hashCode());
    }

    @Test
    void equalsSame_test() {
        assertTrue(role.equals(role));
        assertTrue(role.hashCode() == role.hashCode());
    }

    @Test
    void equalsFalse_test() {
        Role role2 = new Role("employee");
        role2.setId(2L);
        role2.setType("admin");
        assertFalse(role.equals(role2));
        assertFalse(role.hashCode() == role2.hashCode());
    }

    @Test
    void equalsNull_test() {
        assertFalse(role.equals(null));
    }
    @Test
    void equalsWrongClass_test() {
        assertFalse(role.equals("1"));
    }

    @Test
    void toString_test() {
        Assertions.assertThat(role.toString()).isEqualTo("Role{id='1', type='employee'}");
    }

}
