package nl.tudelft.sem.users.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.repositories.RoleRepository;
import nl.tudelft.sem.users.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RoleServiceTest {

    @Mock
    private transient RoleRepository roleRepository;

    @InjectMocks
    private transient RoleService roleService;

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
    void getAllRoles_test() {
        List<Role> roles = new ArrayList<>();
        roles.add(r1);
        roles.add(r2);
        when(roleRepository.findAll()).thenReturn(roles);
        Assertions.assertEquals(roles, roleService.getAllRoles());
    }

    @Test
    void getRole_test() {
        when(roleRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(r1));
        Assertions.assertEquals(r1, roleService.getRole(1L));
    }

    @Test
    void addRole_test() {
        roleService.addRole(r1);
        verify(roleRepository, times(1)).save(r1);
    }

    @Test
    void updateRole_test() {
        roleService.updateRole(1L, r1);
        verify(roleRepository, times(1)).save(r1);
    }

    @Test
    void deleteRole_test() {
        roleService.deleteRole(1L);
        verify(roleRepository, times(1)).deleteById(1L);
    }
}
