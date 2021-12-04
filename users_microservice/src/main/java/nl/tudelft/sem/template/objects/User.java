package nl.tudelft.sem.template.objects;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    @Column(name = "USER_TYPE", nullable = true, length = 50)
    private String userType;


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
     * @param userType  - user type (e.g. Admin) as a String
     */
    public User(String id, String password, String firstName, String lastName, String userType) {
        super();
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
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
     * Setter to set/change the users id.
     *
     * @param id - String to set as new id
     */
    public void setId(String id) {
        this.id = id;
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
     * Getter for the users type.
     *
     * @return users type as a String
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Setter to set/change the users type.
     *
     * @param userType - new String for users first name
     */
    public void setUserType(String userType) {
        this.userType = userType;
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
                && Objects.equals(userType, user.userType);
    }

    /**
     * Hashcode method to hash a user.
     *
     * @return hashed value as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, password, firstName, lastName, userType);
    }

    /**
     * toString method, converts a User into a String representing its attributes.
     *
     * @return user as a String
     */
    @Override
    public String toString() {
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
                + ", userType='"
                + userType
                + '\''
                + '}';
    }
}
