package life.trevino.contacts.dom;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Group {

    @Id
    private String id;

    private String name;
    private List<String> contactIds = new ArrayList<>();

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<String> contactIds) {
        this.contactIds = contactIds;
    }
}
