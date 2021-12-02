package objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.objects.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class RoomTest {

    transient Room r0;
    transient Room r1;
    transient Room r2;

    @BeforeEach
    void setup() {
        r0 = new Room(12, "Europe", 12, "projector, whiteboard", "yes", 36);
        r1 = new Room(11, "Australia", 6, "projector", "no", 36);
        r2 = new Room(11, "Australia", 6, "projector", "no", 36);
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
                + "equipment=projector, available='no', buildingNumber=36}";
        assertEquals(expected, r1.toString());

    }



}
