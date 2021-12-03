package object.test;

import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class RoomTest {

    private transient Room room;

    @BeforeEach
    void setup() {
        room = new Room(1, "Steve Jobs Room", 8, "wifi, desk, whiteboard", "available", 36);
    }

    @Test
    void getId_test() {
        Assertions.assertThat(room.getId()).isEqualTo(1);
    }

    @Test
    void getName_test() {
        Assertions.assertThat(room.getName()).isEqualTo("Steve Jobs Room");
    }

    @Test
    void getCapacity_test() {
        Assertions.assertThat(room.getCapacity()).isEqualTo(8);
    }

    @Test
    void getEquipment_test() {
        Assertions.assertThat(room.getEquipment()).isEqualTo("wifi, desk, whiteboard");
    }

    @Test
    void getAvailable_test() {
        Assertions.assertThat(room.getAvailable()).isEqualTo("available");
    }

    @Test
    void getBuildingNumber_test() {
        Assertions.assertThat(room.getBuildingNumber()).isEqualTo(36);
    }
    
}
