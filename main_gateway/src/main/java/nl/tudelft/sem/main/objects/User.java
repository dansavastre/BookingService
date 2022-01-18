package nl.tudelft.sem.main.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class User {

    private transient String id;
    private transient String password;
    private transient String firstName;
    private transient String lastName;
    private transient List<Role> roles;

    /**
     * Parameterised constructor for the User class.
     *
     * @param id        NetID of the user
     * @param password  Password of the user
     * @param firstName First name of the user
     * @param lastName  Last name of the user
     */
    public User(String id, String password, String firstName, String lastName) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = new ArrayList<>();
    }

    /**
     * Parameterless constructor with placeholder values.
     */
    public User() {
        super();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id)
                && password.equals(user.password)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, firstName, lastName);
    }

    @Override
    public String toString() {
        String string = "";
        for (Role role : roles) {
            string = string + role.toString() + ", ";
        }
        if (!roles.isEmpty()) {
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
}
