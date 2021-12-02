package nl.tudelft.sem.template.objects;

import java.util.Objects;

public class Room {

    private transient int id;
    private transient String name;
    private transient int capacity;
    private transient String equipment;
    private transient String available;
    private transient int buildingNumber;

    /**
     * Parameterless constructor with placeholder values.
     */
    public Room() {
        super();
    }

    /**
     * Parametrised constructor for the Room class.
     *
     * @param id             Id number of the room
     * @param name           Name of the room
     * @param capacity       Capacity of the room
     * @param equipment      Equipment available in the room
     * @param available      Availability of the room
     * @param buildingNumber Id of the building that the room is in
     */
    public Room(int id, String name, int capacity, String equipment,
                String available, int buildingNumber) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.equipment = equipment;
        this.available = available;
        this.buildingNumber = buildingNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getAvailable() {
        return available;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id
                && capacity == room.capacity
                && buildingNumber == room.buildingNumber
                && Objects.equals(name, room.name)
                && Objects.equals(equipment, room.equipment)
                && Objects.equals(available, room.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, equipment, available, buildingNumber);
    }

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", capacity=" + capacity
                + ", equipment='" + equipment + '\''
                + ", available='" + available + '\''
                + ", buildingNumber=" + buildingNumber
                + '}';
    }
}
