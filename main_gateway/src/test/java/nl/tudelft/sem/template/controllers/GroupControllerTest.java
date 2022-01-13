package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jayway.jsonpath.internal.filter.ValueNode;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GroupControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @InjectMocks
    private transient GroupController groupController;

    transient User user1;
    transient User user2;
    transient User user3;
    transient User user4;
    transient Group group1;
    transient Group group2;
    transient Group group3;
    transient List<Group> groups;
    transient String token;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group1 = new Group(1L, "1", "TestGroup", List.of(user1, user2));
        group2 = new Group(2L, "2", "Second Group", List.of(user2, user3, user4));
        group3 = new Group(3L, "3", "Third Group", List.of(user1, user2, user3, user4));
        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        token = "token";
    }

    @Test
    void getAllGroupsTest() {
        String uri = "http://localhost:8081/admin/groups";
        ResponseEntity<List> res = new ResponseEntity<>(groups, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);

        Assertions.assertThat(groupController.getGroups(token)).isEqualTo(groups);
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getGroupTest() {
        String uri = "http://localhost:8081/admin/getGroup/".concat(String.valueOf(1L));
        ResponseEntity<Group> res = new ResponseEntity<>(group1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(Group.class)))
                .thenReturn(res);
        Assertions.assertThat(groupController.getGroup(1L, token)).isEqualTo(group1);
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(Group.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getMyGroupTest() {
        String uri = "http://localhost:8081/secretary/getMyGroup/1/1";
        ResponseEntity<Group> res = new ResponseEntity<>(group1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(Group.class)))
                .thenReturn(res);
        Assertions.assertThat(groupController.getMyGroup(1L, "1", token)).isEqualTo(group1);
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(Group.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void addGroupTest() {
        String uri = "http://localhost:8081/admin/group";
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.POST), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(groupController.postGroup(group3, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.POST), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(group3, entity.getValue().getBody());
    }

    @Test
    void updateGroupTest() {
        String uri = "http://localhost:8081/admin/groups/".concat(String.valueOf(1L));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.PUT), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(groupController
            .updateGroup(group3, String.valueOf(1L), token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.PUT), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(group3, entity.getValue().getBody());
    }

    @Test
    void deleteGroupTest() {
        String uri = "http://localhost:8081/admin/groups/".concat(String.valueOf(1L));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.DELETE), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(groupController.deleteGroup(String.valueOf(1L), token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.DELETE), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

}
