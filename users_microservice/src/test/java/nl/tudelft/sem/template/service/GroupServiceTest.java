package nl.tudelft.sem.template.service;

import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.repositories.GroupRepository;
import nl.tudelft.sem.template.services.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private transient GroupRepository groupRepository;

    @InjectMocks
    private transient GroupService groupService;

    transient User user1, user2, user3, user4;
    transient Group group1, group2, group3;
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
        group3 = new Group("2", "Second Group", List.of(user1, user2, user3, user4));
        user1.setGroups(List.of(group1));
        user2.setGroups(List.of(group1, group2));
        user3.setGroups(List.of(group2));
        user4.setGroups(List.of(group2));
        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
    }

    @Test
    void getAllGroupsTest() {
        when(groupRepository.findAll()).thenReturn(groups);
        Assertions.assertThat(groupService.getAllGroups()).isEqualTo(groups);
    }

    @Test
    void getGroupTest() {
        when(groupRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(group1));
        Assertions.assertThat(groupService.getGroup("1")).isEqualTo(group1);
    }

    @Test
    void addGroupTest() {
        groupService.addGroup(group3);
        verify(groupRepository, times(1)).save(group3);
    }

    @Test
    void updateGroupTest() {
        groupService.updateGroup("1", group3);
        verify(groupRepository, times(1)).deleteById("1");
        verify(groupRepository, times(1)).save(group3);
    }

    @Test
    void deleteGroupTest() {
        groupService.deleteGroup("1");
        verify(groupRepository, times(1)).deleteById("1");
    }

}
