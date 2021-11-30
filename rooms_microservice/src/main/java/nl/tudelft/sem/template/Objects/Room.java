package nl.tudelft.sem.template.Objects;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity(name = "Room")
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "ID", nullable = true)
    private int id;

    @Column(name = "NAME", nullable = true, length = 255)
    private String name;

    @Column(name = "CAPACITY", nullable = true)
    private int capacity;

    @Column(name = "EQUIPMENT", nullable = true)
    private String equipment;

    @Column(name = "AVAILABLE", nullable = true, length = 50)
    private String available;

    @Column(name = "BUILDING_NUMBER", nullable = true)
    private int buildingNumber;

    public Room(){

    }

    public Room(int id, String name, int capacity, String equipment, String available, int buildingNumber) {
        super();
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

    public void setId(int id) {
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && capacity == room.capacity && buildingNumber == room.buildingNumber && Objects.equals(name, room.name) && Objects.equals(equipment, room.equipment) && this.available.equals(room.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, equipment, available, buildingNumber);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", equipment=" + equipment +
                ", available='" + available + '\'' +
                ", buildingNumber=" + buildingNumber +
                '}';
    }
}
