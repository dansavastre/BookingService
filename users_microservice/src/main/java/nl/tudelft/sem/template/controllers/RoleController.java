package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import nl.tudelft.sem.template.services.RoleService;
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

    @GetMapping("/roles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/getRole/{id}")
    @ResponseBody
    public Role getRole(@PathVariable("id") String id) {
        return roleService.getRole(id);
    }

    @PostMapping("/roles")
    @ResponseBody
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @PutMapping("/roles/{id}")
    @ResponseBody
    public void updateRole(@RequestBody Role role, @PathVariable("id") String id) {
        roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
    }
}
