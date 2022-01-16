package nl.tudelft.sem.main.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.sem.main.objects.User;
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


public class UserControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @InjectMocks
    private transient UserController userController;

    private transient User u1;
    private transient User u2;
    private transient List<User> users;
    private transient String id1;
    private transient String id2;
    private transient String token;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        u1 = new User("1234567", "password", "Dan", "Savastre");
        u2 = new User("9876543", "psw", "Mike", "Jones");
        id1 = u1.getId();
        id2 = u2.getId();
        users = new ArrayList<>(List.of(u1, u2));
        token = "token";
    }

    @Test
    void getUsers_test() {
        String uri = "http://localhost:8081/admin/users";
        ResponseEntity<List> res = new ResponseEntity<>(users, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);

        Assertions.assertThat(userController.getUsers(token)).isEqualTo(users);
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getUser_test() {
        String uri = "http://localhost:8081/admin/getUser/".concat(id1);
        ResponseEntity<User> res = new ResponseEntity<>(u1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(User.class)))
                .thenReturn(res);
        Assertions.assertThat(userController.getUser(id1, token)).isEqualTo(u1);
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.GET), entity.capture(), eq(User.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postUser_test() {
        String uri = "http://localhost:8081/admin/users";
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.POST), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(userController.postUser(u1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.POST), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(u1, entity.getValue().getBody());
    }

    @Test
    void updateUser_test() {
        String uri = "http://localhost:8081/admin/users/".concat(id1);
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.PUT), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(userController.updateUser(u2, id1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.PUT), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(u2, entity.getValue().getBody());
    }

    @Test
    void deleteUser_test() {
        String uri = "http://localhost:8081/admin/users/".concat(id1);
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.DELETE), entity.capture(), eq(void.class)))
                .thenReturn(res);
        Assertions.assertThat(userController.deleteUser(id1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.DELETE), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

}
