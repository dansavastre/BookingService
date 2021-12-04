package nl.tudelft.sem.template.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTest {

    transient Room r0;
    transient Room r1;
    transient Room r2;
    transient Map<String, Boolean> equipmentMap;
    private transient String projector = "projector";

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("projector", true);
        equipmentMap.put("smartBoard", true);
        r0 = new Room(12, "Europe", 12, equipmentMap, "yes", 36);
        r1 = new Room(11, "Australia", 6, equipmentMap, "no", 36);
        r2 = new Room(11, "Australia", 6, equipmentMap, "no", 36);
    }

    @Test
    void addingEquipmentPass_test() {
        r1.addEquipment("chair");
        assertEquals(true, r1.getEquipment().get("chair"));
    }

    @Test
    void addingEquipmentFail_test() {
        r1.addEquipment("desk");
        assertEquals(null, r1.getEquipment().get("123"));
    }

    @Test
    void settingEquipmentAsDefectiveTrue_test() {
        r1.setEquipmentAsDefective(projector);
        assertEquals(false, equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsDefectiveFalse_test() {
        r1.setEquipmentAsDefective("123");
        assertNotEquals(false, equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingTrue_test() {
        r1.setEquipmentAsDefective(projector);
        r1.setEquipmentAsWorking(projector);
        assertEquals(true, equipmentMap.get(projector));
    }

    @Test
    void settingEquipmentAsWorkingFalse_test() {
        r1.setEquipmentAsDefective(projector);
        r1.setEquipmentAsWorking("123");
        assertEquals(false, equipmentMap.get(projector));
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
        String expected = "Room{id=11, name='Australia', capacity=6, "
                + "equipment={projector=true, smartBoard=true}, available='no', buildingNumber=36}";
        assertEquals(expected, r1.toString());

    }

}
