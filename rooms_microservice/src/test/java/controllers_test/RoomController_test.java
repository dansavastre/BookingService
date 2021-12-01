package controllers_test;

import nl.tudelft.sem.template.controllers.RoomController;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomController_test {

    @Mock
    private RoomService roomService;

    private RoomController roomController;

    Room r0;
    Room r1;
    Room r2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        r0 = new Room(12, "Europe", 12, "projector, whiteboard", "yes", 36);
        r1 = new Room(11, "Australia", 6, "projector", "no", 36);
        r2 = new Room(11, "Australia", 6, "projector", "yes", 36);
        roomController = new RoomController(roomService);
    }

    @Test
    void sayHi_test(){
        assertEquals("Hello from the room!", roomController.sayHi());
    }
    @Test
    void connectionStatus_test(){
        assertEquals("Rooms is connected!", roomController.connectionStatus());
    }

    @Test
    void getAllRooms_test() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(r0);
        rooms.add(r1);
        when(roomService.getAllRooms()).thenReturn(rooms);
        assertEquals(rooms, roomController.getAllRooms());
    }

    @Test
    void getRoom_test() {
        when(roomService.getRoom(12)).thenReturn(r0);
        assertEquals(r0, roomController.getRoom(12));
    }

    @Test
    void addRoom_test() {
        roomController.addRoom(r1);
        verify(roomService, times(1)).addRoom(r1);
    }

    @Test
    void updateRoom_test() {
        roomController.updateRoom(r2, 11);
        verify(roomService, times(1)).updateRoom(11, r2);
    }

    @Test
    void deleteRoom_test() {
        roomController.deleteRoom(11);
        verify(roomService, times(1)).deleteRoom(11);
    }
}
