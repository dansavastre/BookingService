package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private transient GroupRepository groupRepository;

    /**
     * Returns a list of all groups in the database.
     *
     * @return  - List of type group
     */
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    /**
     * Returns the group with specific id.
     *
     * @param id    - The id of the group
     * @return      - Group object
     */
    public Group getGroup(Long id) {
        return groupRepository.findById(id).get();
    }

    /**
     * Adds a new group to the database.
     *
     * @param group - Group to be added to database
     */
    public void addGroup(Group group) {
        groupRepository.save(group);
    }

    /**
     * Updates a group to have new attributes.
     *
     * @param id    - The id of the group to be updated
     * @param group - The new attributes of the group
     */
    public void updateGroup(Long id, Group group) {
        groupRepository.save(group);
    }

    /**
     * Deletes a group from the database.
     *
     * @param id    - The id of the group to be deleted
     */
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
