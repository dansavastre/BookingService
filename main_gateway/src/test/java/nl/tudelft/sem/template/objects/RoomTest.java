package nl.tudelft.sem.template.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RoomTest {

    private transient Room room;
    private transient Map<String, String> equipmentMap;
    private final transient String projector = "projector";
    private final transient String trueS = "True";

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("wifi", trueS);
        equipmentMap.put("projector", trueS);
        equipmentMap.put("smart board", trueS);
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
        assertEquals(trueS, room.getEquipment().get("chair"));
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
        assertEquals(trueS, equipmentMap.get(projector));
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
