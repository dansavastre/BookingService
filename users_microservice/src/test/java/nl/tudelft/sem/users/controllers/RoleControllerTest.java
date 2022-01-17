package nl.tudelft.sem.users.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RoleControllerTest {

    @Mock
    private transient RoleService roleService;

    @InjectMocks
    private transient RoleController roleController;

    transient Role r1;
    transient Role r2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        r1 = new Role("employee");
        r1.setId(1L);
        r2 = new Role("admin");
    }

    @Test
    void sayHi_test() {
        Assertions.assertEquals("Hello from role!",
            roleController.sayHi());
    }

    @Test
    void getAllRoles_test() {
        List<Role> roles = new ArrayList<>();
        roles.add(r1);
        roles.add(r2);
        when(roleService.getAllRoles()).thenReturn(roles);
        Assertions.assertEquals(roles, roleController.getAllRoles());
    }

    @Test
    void getRole_test() {
        when(roleService.getRole(1L)).thenReturn(r1);
        Assertions.assertEquals(r1, roleService.getRole(1L));
    }

    @Test
    void addRole_test() {
        roleController.addRole(r1);
        verify(roleService, times(1)).addRole(r1);
    }

    @Test
    void updateRole_Test() {
        roleController.updateRole(r1, 1L);
        verify(roleService, times(1)).updateRole(1L, r1);
    }

    @Test
    void deleteRole_test() {
        roleController.deleteRole(1L);
        verify(roleService, times(1)).deleteRole(1L);
    }
}
