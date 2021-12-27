package nl.tudelft.sem.template.objects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroupTest {

    transient User user1, user2, user3, user4;
    transient Group group;

    @BeforeEach
    void setup() {
        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group = new Group("1", "TestGroup", List.of(user1, user2));
    }

    @Test
    void getIdTest() {
        Assertions.assertThat(group.getId()).isEqualTo("1");
    }

    @Test
    void getGroupNameTest() {
        Assertions.assertThat(group.getGoupName()).isEqualTo("TestGroup");
    }

    @Test
    void getMembersTest() {
        Assertions.assertThat(group.getMembers()).isEqualTo(List.of(user1, user2));
    }

    @Test
    void setIdTest() {
        group.setId("2");
        Assertions.assertThat(group.getId()).isEqualTo("2");
    }

    @Test
    void setGroupNameTest() {
        group.setGoupName("NewGroupName");
        Assertions.assertThat(group.getGoupName()).isEqualTo("NewGroupName");
    }

    @Test
    void setMembersTest() {
        group.setMembers(List.of(user1, user2, user3, user4));
        Assertions.assertThat(group.getMembers()).isEqualTo(List.of(user1, user2, user3, user4));
    }

    @Test
    void equalsTestTrue() {
        Assertions.assertThat(group.equals(new Group("1", "TestGroup", List.of(user1, user2)))).isTrue();
    }

    @Test
    void equalsTestFalse() {
        Assertions.assertThat(group.equals(new Group("2", "DifferentGroup", List.of(user1, user2)))).isFalse();

    }
}
