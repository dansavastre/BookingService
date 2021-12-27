package nl.tudelft.sem.template.objects;

import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name = "Room")
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "ID", nullable = true)
    private String id;

    @Column(name = "ROOMNUMBER")
    private int roomNumber;

    @Column(name = "NAME", nullable = true, length = 255)
    private String name;

    @Column(name = "CAPACITY", nullable = true)
    private int capacity;

    @Column(name = "EQUIPMENT", nullable = true)
    @ElementCollection(targetClass = String.class)
    private Map<String, String> equipment;

    @Column(name = "AVAILABLE", nullable = true, length = 50)
    private String available;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Building building;

    public Room() {

    }

    /**
     * Parameterised constructor for the Room class.
     *
     * @param roomNumber     ID of the room
     * @param name           Name of the room
     * @param capacity       Capacity of the room
     * @param equipment      Equipment present in the room
     * @param available      Availability of the room (whether it is under maintenance or not)
     * @param building      The building object that the room is in
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
        this.id = Integer.toString(roomNumber) + Integer.toString(building.getId());
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<String, String> getEquipment() {
        return equipment;
    }

    public void setEquipment(Map<String, String> equipment) {
        this.equipment = equipment;
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

    public void setAvailable(String available) {
        this.available = available;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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
        return id.equals(room.id)
                && capacity == room.capacity
                && building == room.building
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
                + ", building=" + building.toString()
                + '}';
    }
}
