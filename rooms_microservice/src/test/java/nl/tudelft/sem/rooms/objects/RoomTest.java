package nl.tudelft.sem.rooms.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTest {

    transient Room r0;
    transient Room r1;
    transient Room r2;
    transient Map<String, String> equipmentMap;
    private transient String projector = "projector";
    private transient String trueS = "True";
    transient Building building1;
    transient Building building2;

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("projector", trueS);
        equipmentMap.put("smartBoard", trueS);
        building1 = new Building(36, LocalTime.of(8, 30),
                LocalTime.of(18, 00), "name1");
        building2 = new Building(24, LocalTime.of(10, 30),
                LocalTime.of(17, 30), "name2");
        r0 = new Room(12, "Europe", 12, equipmentMap, "yes", building1);
        r1 = new Room(11, "Australia", 6, equipmentMap, "no", building1);
        r2 = new Room(11, "Australia", 6, equipmentMap, "no", building1);
    }

    @Test
    void addingEquipmentPass_test() {
        r1.addEquipment("chair");
        assertEquals(trueS, r1.getEquipment().get("chair"));
    }

    @Test
    void addingEquipmentFail_test() {
        r1.addEquipment("desk");
        assertEquals(null, r1.getEquipment().get("123"));
    }

    @Test
    void settingEquipmentAsDefectiveTrue_test() {
        r1.setEquipmentAsDefective(projector);
        assertEquals("False", equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsDefectiveFalse_test() {
        r1.setEquipmentAsDefective("123");
        assertNotEquals("False", equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingTrue_test() {
        r1.setEquipmentAsDefective(projector);
        r1.setEquipmentAsWorking(projector);
        assertEquals(trueS, equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingFalse_test() {
        r1.setEquipmentAsDefective(projector);
        r1.setEquipmentAsWorking("123");
        assertEquals("False", equipmentMap.get(projector));
    }

    @Test
    void equals_same_object_test() {
        assertTrue(r0.equals(r0));
    }

    @Test
    void equals_not_building_test() {
        assertFalse(r0.equals("definitely not a room object"));
    }

    @Test
    void equals_different_test() {
        assertFalse(r0.equals(r1));
    }

    @Test
    void equals_same_test() {
        assertTrue(r1.equals(r2));
    }

    @Test
    void toString_test() {
        String expected = "Room{id=36-11, name='Australia', capacity=6, "
                + "equipment={projector=True, smartBoard=True}, available='no',"
                + " building=Building{id=36, openingTime=08:30,"
                + " closingTime=18:00, name='name1'}}";
        assertEquals(expected, r1.toString());

    }

}
