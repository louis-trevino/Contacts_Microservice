package life.trevino.contacts.dao;

import life.trevino.contacts.dom.Contact;
import life.trevino.contacts.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactDao {

    private ContactRepo contactRepo;

    @Autowired
    public ContactDao(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Contact findById(String id) {
        Contact contact = this.contactRepo.findById(id).get();
        return contact;
    }

    public List<Contact> findAll() {
        List<Contact> contacts = contactRepo.findAll();
        return contacts;
    }

    public List<Contact> findByName() {
        List<Contact> contacts = contactRepo.findAll();
        return contacts;
    }

    public Contact findByFirstName(String firstName) {
        Contact contact = contactRepo.findByFirstName(firstName);
        return contact;
    }

    public Contact findByLastName(String lastName) {
        Contact contact = contactRepo.findByLastName(lastName);
        return contact;
    }

    public List<Contact> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Contact> contacts = contactRepo.findByFirstNameAndLastName(firstName, lastName);
        return contacts;
    }

    public Contact findByMobilePhone(String mobilePhone) {
        Contact contact = contactRepo.findByMobilePhone(mobilePhone);
        return contact;
    }

    public Contact findByHomePhone(String homePhone) {
        Contact contact = contactRepo.findByHomePhone(homePhone);
        return contact;
    }

    public Contact save(Contact contact) {
        contact = contactRepo.save(contact);
        return contact;
    }

    public void deleteById(String id) {
        contactRepo.deleteById(id);
    }

}
