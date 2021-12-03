package object.test;

import java.time.LocalTime;
import nl.tudelft.sem.template.objects.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    private transient Building building;

    @BeforeEach
    void setup() {
        building = new Building(1, LocalTime.of(8, 0), LocalTime.of(22, 0), "Ewi");
    }

    @Test
    void getId_test() {
        Assertions.assertThat(building.getId()).isEqualTo(1);
    }

    @Test
    void getOpeningTime_test() {
        Assertions.assertThat(building.getOpeningTime()).isEqualTo(LocalTime.of(8, 0));
    }

    @Test
    void getClosingTime_test() {
        Assertions.assertThat(building.getClosingTime()).isEqualTo(LocalTime.of(22, 0));
    }

    @Test
    void getName_test() {
        Assertions.assertThat(building.getName()).isEqualTo("Ewi");
    }

}
