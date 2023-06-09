package nl.tudelft.sem.main.objects;

import java.util.Map;
import java.util.Objects;

public class Room {

    private transient String id;
    private transient int roomNumber;
    private transient String name;
    private transient int capacity;
    private transient Map<String, String> equipment;
    private transient String available;
    private transient Building building;

    public Room() {

    }

    /**
     * Parameterised constructor for the Room class.
     *
     * @param roomNumber             Id of the room
     * @param name           Name of the room
     * @param capacity       Capacity of the room
     * @param equipment      Equipment present in the room
     * @param available      Availability of the room (whether it is under maintenance or not)
     * @param building        The building this room is in
     */
    public Room(int roomNumber,
                String name,
                int capacity,
                Map<String, String> equipment,
                String available,
                Building building) {
        super();
        this.roomNumber = roomNumber;
        this.name = name;
        this.capacity = capacity;
        this.equipment = equipment;
        this.available = available;
        this.building = building;
        this.id = building.getId() + "-" + roomNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<String, String> getEquipment() {
        return equipment;
    }

    public void addEquipment(String equipment) {
        this.equipment.put(equipment, "True");
    }

    public void setEquipmentAsDefective(String equipment) {
        this.equipment.replace(equipment, "False");
    }

    public void setEquipmentAsWorking(String equipment) {
        this.equipment.replace(equipment, "True");
    }

    public String getAvailable() {
        return available;
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
                && Objects.equals(building, room.building)
                && Objects.equals(name, room.name)
                && Objects.equals(equipment, room.equipment)
                && this.available.equals(room.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, equipment, available, building);
    }

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", capacity=" + capacity
                + ", equipment=" + equipment
                + ", available='" + available + '\''
                + ", buildingNumber=" + building.toString()
                + '}';
    }
}
