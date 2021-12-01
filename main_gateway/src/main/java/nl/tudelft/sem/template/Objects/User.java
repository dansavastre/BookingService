package nl.tudelft.sem.template.objects;

import java.util.Objects;


public class User {

    private final String id;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String userType;

    public User(String id, String password, String firstName, String lastName, String userType) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public User(){
        super();
        this.id = "NOTINSTANTIATED";
        this.password = "NOTINSTANTIATED";
        this.firstName = "NOTINSTANTIATED";
        this.lastName = "NOTINSTANTIATED";
        this.userType = "NOTINSTANTIATED";
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

    public String getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && password.equals(user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && userType.equals(user.userType);
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
