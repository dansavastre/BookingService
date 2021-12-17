package nl.tudelft.sem.template.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class RoleControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient RoleController roleController;

    private transient Role r1;
    private transient Role r2;
    private transient List<Role> roles;
    private transient Long id1;
    private transient Long id2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        roles = new ArrayList<>();
        r1 = new Role("employee");
        r2 = new Role("admin");
        r1.setId(1L);
        r2.setId(2L);
        id1 = r1.getId();
        id2 = r2.getId();
        roles.add(r1);
        roles.add(r2);
    }

    @Test
    void getRoles_test() {
        String uri = "http://localhost:8081/employee/roles";
        when(restTemplate.getForObject(uri, List.class))
            .thenReturn(roles);

        Assertions.assertThat(roleController.getRoles()).isEqualTo(roles);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
    }

    @Test
    void getRole_test() {
        String uri = "http://localhost:8081/employee/getRole/".concat(String.valueOf(id1));
        when(restTemplate.getForObject(uri, Role.class))
            .thenReturn(r1);
        Assertions.assertThat(roleController.getRole(id1)).isEqualTo(r1);
        verify(restTemplate, times(1)).getForObject(uri, Role.class);
    }

    @Test
    void postRole_test() {
        String uri = "http://localhost:8081/admin/roles";
        Assertions.assertThat(roleController.postRole(r1)).isTrue();
        verify(restTemplate, times(1)).postForObject(uri, r1, void.class);
    }

    @Test
    void updateRole_test() {
        String uri = "http://localhost:8081/admin/roles/".concat(String.valueOf(id1));
        Assertions.assertThat(roleController.updateRole(r2, id1)).isTrue();
        verify(restTemplate, times(1)).put(uri, r2);
    }

    @Test
    void deleteRole_test() {
        String uri = "http://localhost:8081/admin/roles/".concat(String.valueOf(id1));
        Assertions.assertThat(roleController.deleteRole(id1)).isTrue();
        verify(restTemplate, times(1)).delete(uri);
    }
}
