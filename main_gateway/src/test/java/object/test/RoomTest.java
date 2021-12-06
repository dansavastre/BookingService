package object.test;

import java.util.HashMap;
import java.util.Map;
import nl.tudelft.sem.template.objects.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private transient Room room;
    private transient Map<String, String> equipmentMap;
    private final transient String projector = "projector";
    private final transient String t = "True";

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("wifi", t);
        equipmentMap.put("projector", t);
        equipmentMap.put("smart board", t);
        room = new Room(1, "Steve Jobs Room", 8, equipmentMap, "available", 36);
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
        Assertions.assertThat(room.getEquipment()).isEqualTo(equipmentMap);
    }

    @Test
    void addingEquipmentPass_test() {
        room.addEquipment("chair");
        assertEquals(t, room.getEquipment().get("chair"));
    }

    @Test
    void addingEquipmentFail_test() {
        room.addEquipment("desk");
        assertNull(room.getEquipment().get("123"));
    }

    @Test
    void settingEquipmentAsDefectiveTrue_test() {
        room.setEquipmentAsDefective(projector);
        assertEquals("False", equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsDefectiveFalse_test() {
        room.setEquipmentAsDefective("123");
        assertNotEquals("False", equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingTrue_test() {
        room.setEquipmentAsDefective(projector);
        room.setEquipmentAsWorking(projector);
        assertEquals(t, equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingFalse_test() {
        room.setEquipmentAsDefective(projector);
        room.setEquipmentAsWorking("123");
        assertEquals("False", equipmentMap.get(projector));
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