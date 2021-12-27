package nl.tudelft.sem.template.objects;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Group")
@Table(name = "group")
public class Group {

    @Id
    @Column(name = "ID", nullable = true, length = 50)
    private String id;

    @Column(name = "GROUP_NAME", nullable = true, length = 255)
    private String goupName;

    @Column(name = "USERS", nullable = true, length = 50)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    /**
     * Generic Group constructor with no parameters.
     *
     */
    public Group() {
    }

    /**
     * Group constructor with all group attributes as parameters.
     *
     * @param id        - ID attribute used to identify groups as a String
     * @param goupName  - The name of the group as a String
     * @param users     - A list of User objects that are part of the group
     */
    public Group(String id, String goupName, List<User> users) {
        this.id = id;
        this.goupName = goupName;
        this.users = users;
    }

    /**
     * Getter for the group ID.
     *
     * @return group ID as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the group name.
     *
     * @return group name as a String
     */
    public String getGoupName() {
        return goupName;
    }

    /**
     * Getter for the list of users that are part of the group.
     *
     * @return list of Users that are part of the group
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Setter for the group ID.
     *
     * @param id    - String to set as new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for thr group name.
     *
     * @param goupName  - String to be set as new group name
     */
    public void setGoupName(String goupName) {
        this.goupName = goupName;
    }

    /**
     * Setter for the list of users.
     *
     * @param users - List of User objects to be set as the new users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * Equals method that checks whether 2 groups are equivalent based on their attributes.
     *
     * @param o - The group to be compared with
     * @return  - True if the users are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(this.id, group.id)
                && Objects.equals(this.goupName, group.goupName)
                && this.users.equals(group.users);
    }

    /**
     * HashCode method to hash group.
     *
     * @return  - hased value of group as integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, goupName, users);
    }

    @Override
    public String toString() {
        return "Group{"
                + "id='"
                + id + '\''
                + ", name='"
                + goupName
                + '\''
                + ", users="
                + users
                + '}';
    }
}
