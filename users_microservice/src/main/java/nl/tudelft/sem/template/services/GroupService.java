package nl.tudelft.sem.template.services;

import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private transient GroupRepository groupRepository;


    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    public Group getGroup(String id) {
        return groupRepository.findById(id).get();
    }

    public void addGroup(Group group) {
        groupRepository.save(group);
    }

    public void updateGroup(String id, Group group) {
        groupRepository.deleteById(id);
        groupRepository.save(group);
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }
}
