package life.trevino.contacts.repo;

import life.trevino.contacts.dom.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepo extends MongoRepository<Contact, String> {

    public Contact findByFirstName(String firstName);

    public Contact findByLastName(String lastName);

    public Contact findByAddress(String address);

    public Contact findByMobilePhone(String mobilePhone);

    public Contact findByHomePhone(String homePhone);

    public Contact findByWorkPhone(String workPhone);

    public List<Contact> findByFirstNameAndLastName(String firstName, String lastname);

}

