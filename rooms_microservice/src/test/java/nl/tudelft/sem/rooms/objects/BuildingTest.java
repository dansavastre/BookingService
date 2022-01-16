package nl.tudelft.sem.rooms.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BuildingTest {

    transient Building b0;
    transient Building b1;
    transient Building b2;

    @BeforeEach
    void setUp() {
        b0 = new Building(36, LocalTime.of(8, 0), LocalTime.of(20, 0), "EEMCS");
        b1 = new Building(25, LocalTime.of(8, 0), LocalTime.of(20, 0), "Library");
        b2 = new Building(36, LocalTime.of(8, 0), LocalTime.of(20, 0), "EEMCS");
    }

    @Test
    void equals_same_object_test() {
        assertTrue(b0.equals(b0));
    }

    @Test
    void equals_not_building_test() {
        assertFalse(b0.equals("definitely not a building object"));
    }

    @Test
    void equals_different_test() {
        assertFalse(b0.equals(b1));
    }

    @Test
    void equals_same_test() {
        assertTrue(b0.equals(b2));
    }

    @Test
    void toString_test() {
        String expected = "Building{id=36, openingTime=08:00, closingTime=20:00, name='EEMCS'}";
        assertEquals(expected, b0.toString());
    }
}
