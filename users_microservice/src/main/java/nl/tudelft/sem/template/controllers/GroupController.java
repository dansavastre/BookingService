package nl.tudelft.sem.template.controllers;

import java.util.List;
import java.util.stream.Collectors;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("admin/getGroup/{id}")
    @ResponseBody
    public Group getGroup(@PathVariable("id") Long id) {
        return groupService.getGroup(id);
    }

    /**
     * Get the group for a certain secretary.
     *
     * @param id          ID of the group
     * @param secretaryId ID of the secretary who made the request
     * @param token       Security token for authentication purposes
     * @return The requested group, if it exists and is being requested by its secretary
     */
    @GetMapping("secretary/getMyGroup/{id}/{secretaryId}")
    @ResponseBody
    public Group getMyGroup(@PathVariable("id") Long id,
                            @PathVariable("secretaryId") String secretaryId,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (groupService.getGroup(id).getSecretary().equals(secretaryId)) {
            return groupService.getGroup(id);
        }
        return null;
    }

    @PostMapping("admin/group")
    @ResponseBody
    public void addGroup(@RequestBody Group group) {
        groupService.addGroup(group);
    }

    @PutMapping("admin/groups/{id}")
    @ResponseBody
    public void updateGroup(@RequestBody Group group,
                            @PathVariable("id") Long id) {
        groupService.updateGroup(id, group);
    }

    @DeleteMapping("admin/groups/{id}")
    public void deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
    }

    /**
     * Checks if a booking owner belongs to a certain group.
     *
     * @param groupId        The ID of the group to be checked
     * @param secretaryId    The secretaryID of the secretary to be checked for verification
     * @param bookingOwnerId The ID of the booking owner to be checked
     * @return A boolean showing if the booking is of a group member or not
     */
    @GetMapping("secretary/checkGroup/{groupId}/{secretaryId}/{bookingOwnerId}")
    @ResponseBody
    public boolean checkGroup(@PathVariable("groupId") Long groupId,
                              @PathVariable("secretaryId") String secretaryId,
                              @PathVariable("bookingOwnerId") String bookingOwnerId) {
        if (!groupService.getGroup(groupId).getSecretary().equals(secretaryId)) {
            return false;
        }
        List<String> membersIds = groupService.getGroup(groupId).getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return membersIds.contains(bookingOwnerId);
    }

}
