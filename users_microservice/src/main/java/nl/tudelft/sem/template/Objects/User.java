package nl.tudelft.sem.template.Objects;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "firstName", nullable = true, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = true, length = 50)
    private String lastName;

    @Column(name = "userType", nullable = false, length = 50)
    private String userType;


    public User() {

    }

    public User(String id, String password, String firstName, String lastName, String userType) {
        super();
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userType, user.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, firstName, lastName, userType);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
