package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import nl.tudelft.sem.template.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private transient RoleRepository roleRepository;


    /**
     * Method for getting a list of all roles in the database.
     *
     * @return list of roles found in the database
     */
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(Long id, Role role) {
        roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
