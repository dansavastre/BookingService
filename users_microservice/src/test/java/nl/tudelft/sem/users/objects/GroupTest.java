package nl.tudelft.sem.users.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupTest {

    transient User user1;
    transient User user2;
    transient User user3;
    transient User user4;
    transient Group group;

    @BeforeEach
    void setup() {
        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group = new Group(1L, "1", "TestGroup", new ArrayList<>(Arrays.asList(user1, user2)));
    }

    @Test
    void getIdTest() {
        Assertions.assertThat(group.getId()).isEqualTo(1L);
    }

    @Test
    void getGroupNameTest() {
        Assertions.assertThat(group.getGroupName()).isEqualTo("TestGroup");
    }

    @Test
    void getMembersTest() {
        Assertions.assertThat(group.getMembers()).isEqualTo(List.of(user1, user2));
    }

    @Test
    void setGroupNameTest() {
        group.setGroupName("NewGroupName");
        Assertions.assertThat(group.getGroupName()).isEqualTo("NewGroupName");
    }

    @Test
    void getSecretaryTest() {
        Assertions.assertThat(group.getSecretary()).isEqualTo("1");
    }

    @Test
    void addMemberTest() {
        Assertions.assertThat(group.getMembers())
            .isEqualTo(new ArrayList<>(Arrays.asList(user1, user2)));
        Assertions.assertThat(group.addMember(user3)).isTrue();
        Assertions.assertThat(group.getMembers())
            .isEqualTo(new ArrayList<>(Arrays.asList(user1, user2, user3)));
    }

    @Test
    void removeMemberTest() {
        Assertions.assertThat(group.getMembers())
            .isEqualTo(new ArrayList<>(Arrays.asList(user1, user2)));
        Assertions.assertThat(group.removeMember(user2)).isTrue();
        Assertions.assertThat(group.getMembers())
                .isEqualTo(new ArrayList<>(Arrays.asList(user1)));
    }

    @Test
    void equalsTestTrue() {
        Group g = new Group(1L, "1", "TestGroup", List.of(user1, user2));
        Assertions.assertThat(group.equals(g)).isTrue();
    }

    @Test
    void equalsTestFalse() {
        Group g = new Group(1L, "2", "DifferentGroup", List.of(user1, user2));
        Assertions.assertThat(group.equals(g)).isFalse();

    }

}
