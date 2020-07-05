package life.trevino.contacts.controller;

import life.trevino.contacts.dao.ContactDao;
import life.trevino.contacts.dao.GroupDao;
import life.trevino.contacts.dom.Contact;
import life.trevino.contacts.dom.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {


    ContactDao contactDao;
    GroupDao groupDao;

    @Autowired
    public GroupController(ContactDao contactDao, GroupDao groupDao) {
        this.contactDao = contactDao;
        this.groupDao = groupDao;
    }


    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Group>> findAllGroups() {
        try {
            List<Group> groups = groupDao.findAll();
            ResponseEntity<List<Group>> re = new ResponseEntity<>(groups, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<List<Group>> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Group> findById(
            @PathVariable(name="id") String id
    ) {
        try {
            Group group = groupDao.findById(id);
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/find/{name}", method= RequestMethod.GET)
    public ResponseEntity<Group> findByName(
            @PathVariable(name="name") String name
    ) {
        try {
            Group group = groupDao.findByName(name);
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }



    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Group> addGroup(
            HttpServletRequest request,
            @RequestParam("name") String name
    ) {
        try {
            Group group = new Group(name);
            group = groupDao.save(group);
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


    @RequestMapping(value="/find/{name}/addContact/{firstNameLastname}", method= RequestMethod.GET)
    public ResponseEntity<Group> addContactByFirstNameAndLastName(
            @PathVariable(name="name") String name,
            @PathVariable(name="firstNameLastname") String firstNameLastname
    ) {
        try {
            Group group = groupDao.findByName(name);
            String[] nameParts = firstNameLastname.split("[-]");
            String firstName = nameParts[0];
            String lastName = nameParts[1];
            List<Contact> contacts = contactDao.findByFirstNameAndLastName(firstName, lastName);
            for (Contact newContact : contacts) {
                if (!group.getContactIds().contains(newContact.getId())) {
                    group.getContactIds().add(newContact.getId());
                }
            }
            groupDao.save(group);
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }

    @RequestMapping(value="/find/{name}/removeContact/{firstNameLastname}", method= RequestMethod.GET)
    public ResponseEntity<Group> removeContactByFirstNameAndLastName(
            @PathVariable(name="name") String name,
            @PathVariable(name="firstNameLastname") String firstNameLastname
    ) {
        try {
            Group group = groupDao.findByName(name);
            String[] nameParts = firstNameLastname.split("[-]");
            String firstName = nameParts[0];
            String lastName = nameParts[1];
            List<Contact> contacts = contactDao.findByFirstNameAndLastName(firstName, lastName);
            for (Contact newContact : contacts) {
                int idx = group.getContactIds().indexOf(newContact.getId());
                if (idx >= 0) {
                    group.getContactIds().remove(idx);
                }
            }
            groupDao.save(group);
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Group> removeById(
            @PathVariable(name="id") String id
    ) {
        try {
            Group group = groupDao.findById(id);
            if (group!=null) {
                groupDao.deleteById(id);
            }
            ResponseEntity<Group> re = new ResponseEntity<>(group, HttpStatus.OK);
            return re;
        } catch (Exception ex) {
            // in real world application, if validation fails ==> BAD_REQUEST; if app error ==> INTERNAL_SERVER_ERROR
            ResponseEntity<Group> re = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return re;
        }
    }


}
