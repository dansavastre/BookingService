package nl.tudelft.sem.users.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @Column(name = "ID", nullable = true, length = 50)
    private String id;

    @Column(name = "PASSWORD", nullable = true, length = 255)
    private String password;

    @Column(name = "FIRST_NAME", nullable = true, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = true, length = 50)
    private String lastName;

    @Column(name = "ROLE", nullable = true, length = 50)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;



    /**
     * Generic User constructor with no parameters.
     */
    public User() {
    }

    /**
     * User constructor with all user attributes as parameters.
     *
     * @param id        - ID attribute used to identify users as a String
     * @param password  - password belonging to users account as a String
     * @param firstName - first name of user as a String
     * @param lastName  - last name of user as a String
     */
    public User(String id, String password, String firstName, String lastName) {
        super();
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = new ArrayList<>();
    }



    /**
     * Getter to get users id.
     *
     * @return users id as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Getter to get a users password.
     *
     * @return users password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter to set/change the users password.
     *
     * @param password - new String to set as password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the users first name.
     *
     * @return first name of a user as a String
     */
    public String getFirstName() {
        return firstName;
    }



    /**
     * Setter to set/change the users first name.
     *
     * @param firstName - new String for users first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the users last name.
     *
     * @return last name of user as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter to set/change the users last name.
     *
     * @param lastName - new String for users last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Equals method to check whether 2 users are equivalent based on the attributes.
     *
     * @param o = 2nd User object to compare to
     * @return true whether users are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && roles.equals(user.roles);
    }

    /**
     * Hashcode method to hash a user.
     *
     * @return hashed value as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, password, firstName, lastName);
    }

    /**
     * toString method, converts a User into a String representing its attributes.
     *
     * @return user as a String
     */
    @Override
    public String toString() {
        String string = "";
        for (Role role : roles) {
            string = string + role.toString() + ", ";
        }
        if (string.length() > 0) {
            string = string.substring(0, string.length() - 2);
        }
        return "User{"
                + "id='"
                + id
                + '\''
                + ", password='"
                + password
                + '\''
                + ", firstName='"
                + firstName
                + '\''
                + ", lastName='"
                + lastName
                + '\''
                + ", roles='"
                + string
                + '\''
                + '}';
    }

    /**
     * Getter for user roles.
     *
     * @return a list of the user's roles
     */
    public List<Role> getRoles() {
        return this.roles;
    }

    /**
     * Add a role to the roles of this user.
     *
     * @param role the role to add to the user
     */
    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
