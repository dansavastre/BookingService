package nl.tudelft.sem.template.Objects;

import java.util.Objects;

public class Building {

    private final int id;
    private final String openingTime;
    private final String closingTime;
    private final String name;

    public Building(){
        super();
        this.id = -1;
        this.openingTime = "PLACEHOLDER";
        this.closingTime = "PLACEHOLDER";
        this.name = "PLACEHOLDER";
    }

    public Building(int id, String openingTime, String closingTime, String name) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return id == building.id && Objects.equals(openingTime, building.openingTime) && Objects.equals(closingTime, building.closingTime) && Objects.equals(name, building.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, closingTime, name);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
