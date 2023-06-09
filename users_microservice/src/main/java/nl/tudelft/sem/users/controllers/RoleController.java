package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleController {

    @Autowired
    private transient RoleService roleService;

    @GetMapping("/sayHiToRole")
    @ResponseBody
    public String sayHi() {
        return "Hello from role!";
    }

    @GetMapping("employee/roles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("employee/getRole/{id}")
    @ResponseBody
    public Role getRole(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }

    @PostMapping("admin/roles")
    @ResponseBody
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @PutMapping("admin/roles/{id}")
    @ResponseBody
    public void updateRole(@RequestBody Role role, @PathVariable("id") Long id) {
        roleService.updateRole(id, role);
    }

    @DeleteMapping("admin/roles/{id}")
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }
}
