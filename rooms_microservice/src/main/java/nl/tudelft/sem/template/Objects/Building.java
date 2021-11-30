package nl.tudelft.sem.template.Objects;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Building")
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "OPENING_TIME")
    private String openingTime;

    @Column(name = "CLOSING_TIME")
    private String closingTime;

    @Column(name = "NAME", length = 255)
    private String name;


    public Building(){

    }

    public Building(int id, String openingTime, String closingTime, String name) {
        super();
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return id == building.id && this.openingTime.equals(building.openingTime) && this.closingTime.equals(building.closingTime) && Objects.equals(name, building.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, closingTime, name);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", name='" + name + '\'' +
                '}';
    }
}
