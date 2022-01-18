package nl.tudelft.sem.main.objects;

import java.util.List;
import java.util.Objects;

public class Group {

    private transient Long id;
    private transient String groupName;
    private transient String secretary;
    private transient List<User> members;

    /**
     * Group constructor with all group attributes as parameters.
     *
     * @param secretary   - NetID of the Secretary in charge of this group
     * @param groupName   - The name of the group as a String
     * @param members     - A list of User objects that are part of the group
     */
    public Group(String secretary, String groupName, List<User> members) {
        this.secretary = secretary;
        this.groupName = groupName;
        this.members = members;
    }

    /**
     * Generic Group constructor with no parameters.
     *
     */
    public Group() {
    }

    /**
     * Group constructor with all parameters for testing purposes.
     *
     * @param id          - ID of the group
     * @param secretary   - NetID of the Secretary in charge of this group
     * @param groupName   - The name of the group as a String
     * @param members     - A list of User objects that are part of the group
     */
    public Group(Long id, String secretary, String groupName, List<User> members) {
        this.id = id;
        this.secretary = secretary;
        this.groupName = groupName;
        this.members = members;
    }

    /**
     * Getter for the group ID.
     *
     * @return group ID as a String
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the group name.
     *
     * @return group name as a String
     */
    public String getGroupName() {
        return groupName;
    }

    public String getSecretary() {
        return secretary;
    }

    public boolean addMember(User user) {
        return members.add(user);
    }

    public boolean removeMember(User user) {
        return members.remove(user);
    }

    /**
     * Getter for the list of users that are part of the group.
     *
     * @return list of Users that are part of the group
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Setter for the group name.
     *
     * @param groupName  - String to be set as new group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        return Objects.equals(id, group.id)
                && Objects.equals(groupName, group.groupName)
                && Objects.equals(secretary, group.secretary)
                && Objects.equals(members, group.members);
    }

    /**
     * HashCode method to hash group.
     *
     * @return  - hashed value of group as integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, secretary, members);
    }

    @Override
    public String toString() {
        return "Group{"
                + "id=" + id
                + ", groupName='" + groupName + '\''
                + ", secretary='" + secretary + '\''
                + ", members=" + members
                + '}';
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    private void setMembers(List<User> members) {
        this.members = members;
    }
}
