package nl.tudelft.sem.main.objects;

import java.util.Objects;

public class Role {
    private transient Long id;
    private transient String type;

    /**
     * Constructor for role with parameter.
     *
     * @param type the type of the role
     */
    public Role(String type) {
        this.type = type;
    }

    public Role() {
        super();
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
