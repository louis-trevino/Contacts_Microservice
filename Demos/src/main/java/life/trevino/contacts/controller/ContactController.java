package life.trevino.contacts.controller;

import life.trevino.contacts.dao.ContactDao;
import life.trevino.contacts.dao.GroupDao;
import life.trevino.contacts.dom.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    ContactDao contactDao;
    GroupDao groupDao;

    @Autowired
    public ContactController(ContactDao contactDao, GroupDao groupDao) {
        this.contactDao = contactDao;
        this.groupDao = groupDao;
    }


    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Contact>> findAllContacts() {
        try {
            List<Contact> contacts = contactDao.findAll();
            ResponseEntity<List<Contact>> re = new ResponseEntity<>(contacts, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<List<Contact>> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Contact> findById(
            @PathVariable(name="id") String id
    ) {
        try {
            Contact contact = contactDao.findById(id);
            ResponseEntity<Contact> re = new ResponseEntity<>(contact, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Contact> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/find/{firstNameLastname}", method= RequestMethod.GET)
    public ResponseEntity<List<Contact>> findByFirstNameAndLastName(
            @PathVariable(name="firstNameLastname") String firstNameLastname
    ) {
        try {
            String[] nameParts = firstNameLastname.split("[-]");
            String firstName = nameParts[0];
            String lastName = nameParts[1];
            List<Contact> contacts = contactDao.findByFirstNameAndLastName(firstName, lastName);
            ResponseEntity<List<Contact>> re = new ResponseEntity<>(contacts, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<List<Contact>> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Contact> addContact(
            HttpServletRequest request,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("address") String address,
            @RequestParam("homePhone") String homePhone,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "workPhone", required = false) String workPhone

    ) {
        try {
            Contact contact = new Contact(firstName, lastName, address, homePhone, mobilePhone, workPhone);
            contact = contactDao.save(contact);
            ResponseEntity<Contact> re = new ResponseEntity<>(contact, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Contact> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Contact> updateContact(
            HttpServletRequest request,
            @PathVariable(name="id") String id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("address") String address,
            @RequestParam("homePhone") String homePhone,
            @RequestParam("mobilePhone") String mobilePhone,
            @RequestParam("workPhone") String workPhone

    ) {
        try {
            Contact contact = contactDao.findById(id);
            // only update passed fields
            if (StringUtils.isEmpty(firstName)) {contact.setFirstName(firstName);}
            if (StringUtils.isEmpty(lastName)) {contact.setLastName(lastName);}
            if (StringUtils.isEmpty(address)) {contact.setAddress(address);}
            if (StringUtils.isEmpty(homePhone)) {contact.setHomePhone(homePhone);}
            if (StringUtils.isEmpty(mobilePhone)) {contact.setMobilePhone(mobilePhone);}
            if (StringUtils.isEmpty(workPhone)) {contact.setWorkPhone(workPhone);}

            contact = contactDao.save(contact);
            ResponseEntity<Contact> re = new ResponseEntity<>(contact, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Contact> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteById(
            @PathVariable(name="id") String id
    ) {
        try {
            Contact contact = contactDao.findById(id);
            if (contact!=null) {
                contactDao.deleteById(id);
            }
            ResponseEntity<Contact> re = new ResponseEntity(contact, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Contact> re = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


}
