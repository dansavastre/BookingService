package object.test;

import nl.tudelft.sem.template.objects.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


}
