package nl.tudelft.sem.template.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.services.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GroupControllerTest {

    @Mock
    private transient GroupService groupService;

    @InjectMocks
    private transient GroupController groupController;

    transient User user1;
    transient User user2;
    transient User user3;
    transient User user4;
    transient Group group1;
    transient Group group2;
    transient List<Group> groups;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group1 = new Group("1", "TestGroup", List.of(user1, user2));
        group2 = new Group("2", "Second Group", List.of(user2, user3, user4));
        user1.setGroups(List.of(group1));
        user2.setGroups(List.of(group1, group2));
        user3.setGroups(List.of(group2));
        user4.setGroups(List.of(group2));
        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
    }

    @Test
    void sayHiTest() {
        Assertions.assertThat(groupController.sayHi()).isEqualTo("Hello from Group!");
    }

    @Test
    void getAllGroupsTest() {
        when(groupService.getAllGroups()).thenReturn(groups);
        Assertions.assertThat(groupController.getAllGroups()).isEqualTo(groups);
    }

    @Test
    void getGroupTest() {
        when(groupService.getGroup("1")).thenReturn(group1);
        Assertions.assertThat(groupController.getGroup("1")).isEqualTo(group1);
    }

    @Test
    void getMyGroupTest() {
        when(groupService.getGroup("1")).thenReturn(group1);
        Assertions.assertThat(groupController.getGroup("1")).isEqualTo(group1);
    }

    @Test
    void addGroupTest() {
        groupController.addGroup(group2);
        verify(groupService, times(1)).addGroup(group2);
    }

    @Test
    void updateGroupTest() {
        Group newGroup1 = new Group("1", "First Group", List.of(user1, user2, user3));
        groupController.updateGroup(newGroup1, "1");
        verify(groupService, times(1)).updateGroup("1", newGroup1);
    }

    @Test
    void deleteGroupTest() {
        groupController.deleteGroup("1");
        verify(groupService, times(1)).deleteGroup("1");
    }
}
