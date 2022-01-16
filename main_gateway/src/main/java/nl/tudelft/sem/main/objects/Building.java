package nl.tudelft.sem.main.objects;

import java.time.LocalTime;
import java.util.Objects;

public class Building {

    private transient int id;
    private transient LocalTime openingTime;
    private transient LocalTime closingTime;
    private transient String name;

    /** Empty constructor.
     *
     */
    public Building() {
        super();
    }

    /** Constructor for Building.
     *
     * @param id the id of the building.
     * @param openingTime the opening time of the building.
     * @param closingTime the closing time of the building.
     * @param name the name of the building.
     */
    public Building(int id, LocalTime openingTime, LocalTime closingTime, String name) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.name = name;
    }

    /** Get the id of the building.
     *
     * @return the id of the building.
     */
    public int getId() {
        return id;
    }

    /** Get the opening time of the building.
     *
     * @return the opening time of the building.
     */
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    /** Get the closing time of the building.
     *
     * @return the closing time of the building.
     */
    public LocalTime getClosingTime() {
        return closingTime;
    }

    /** Get the name of the building.
     *
     * @return name of the building.
     */
    public String getName() {
        return name;
    }

    /** Checks if the object is equal to this building.
     *
     * @param o the object to compare to.
     * @return true if they are the same, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return id == building.id && Objects.equals(openingTime, building.openingTime)
            && Objects.equals(closingTime, building.closingTime)
            && Objects.equals(name, building.name);
    }

    /** Generates a hash for the building.
     *
     * @return the hash code for the building.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, closingTime, name);
    }

    /** A String version of the building.
     *
     * @return the booking in form of a building.
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Building{").append("id=").append(id)
            .append(", openingTime='").append(openingTime).append('\'').append(", closingTime='")
            .append(closingTime).append('\'').append(", name='").append(name).append('\'')
            .append('}').toString();
    }
}
