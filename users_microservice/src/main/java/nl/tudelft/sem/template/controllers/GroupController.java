package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.services.GroupService;
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
public class GroupController {

    @Autowired
    private transient GroupService groupService;

    @GetMapping("/sayHiToGroup")
    @ResponseBody
    public String sayHi() {
        return "Hello from Group!";
    }

    @GetMapping("admin/groups")
    @ResponseBody
    public List<Group> getAllUsers() {
        return groupService.getAllGroups();
    }

    @GetMapping("admin/getGroup/{id}")
    @ResponseBody
    public Group getUser(@PathVariable("id") String id) {
        return groupService.getGroup(id);
    }

    @PostMapping("admin/group")
    @ResponseBody
    public void addGroup(@RequestBody Group group) {
        groupService.addGroup(group);
    }

    @PutMapping("admin/groups/{id}")
    @ResponseBody
    public void updateGroup(@RequestBody Group group, @PathVariable("id") String id) {
        groupService.updateGroup(id, group);
    }

    @DeleteMapping("admin/groups/{id}")
    public void deleteGroup(@PathVariable("id") String id) {
        groupService.deleteGroup(id);
    }
}
