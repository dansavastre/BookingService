package nl.tudelft.sem.users.objects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Role")
@Table(name = "role")
public class Role {

    @Id
    @SequenceGenerator(
        name = "role_sequence",
        sequenceName = "role_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = IDENTITY,
        generator = "role_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    /**
     * Parameterless constructor for role.
     */
    public Role() {
    }

    /**
     * Constructor for role with parameter.
     *
     * @param type the type of the role
     */
    public Role(String type) {
        this.type = type;
    }

    /**
     * Getter for role id.
     *
     * @return the id of the role
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for role type.
     *
     * @return the type of the role
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for setting the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter for setting the role type.
     *
     * @param type the new role type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(type, role.type);
    }

    /**
     * Hashcode method to hash a role.
     *
     * @return hashed value as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    /**
     * toString method, converts a role to a string representation.
     *
     * @return a string representation of the role
     */
    @Override
    public String toString() {
        return "Role{"
            + "id='"
            + id
            + '\''
            + ", type='"
            + type
            + '\''
            + '}';
    }
}
