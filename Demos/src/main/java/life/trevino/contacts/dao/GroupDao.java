package life.trevino.contacts.dao;

import life.trevino.contacts.dom.Group;
import life.trevino.contacts.repo.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDao {

    private GroupRepo groupRepo;

    @Autowired
    public GroupDao(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Group findById(String id) {
        Group group = groupRepo.findById(id).get();
        return group;
    }

    public List<Group> findAll() {
        List<Group> groups = groupRepo.findAll();
        return groups;
    }

    public Group findByName(String name) {
        Group group = groupRepo.findByName(name);
        return group;
    }

    public Group save(Group group) {
        group = groupRepo.save(group);
        return group;
    }

    public void deleteById(String id) {
        groupRepo.deleteById(id);
    }

}
