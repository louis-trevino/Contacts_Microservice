package life.trevino.contacts.repo;

import life.trevino.contacts.dom.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends MongoRepository<Group, String> {

    public Group findByName(String name);

}
