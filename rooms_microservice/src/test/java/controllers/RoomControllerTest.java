package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.Application;
import nl.tudelft.sem.template.controllers.RoomController;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    Room r0;
    Room r1;
    Room r2;

    @BeforeEach
    void setup() {
        r0 = new Room(12, "Europe", 12, "projector, whiteboard", "yes", 36);
        r1 = new Room(11, "Australia", 6, "projector", "no", 36);
        r2 = new Room(11, "Australia", 6, "projector", "yes", 36);
    }

    @Test
    void sayHi_test() {
        assertEquals("Hello from the room!", roomController.sayHi());
    }

    @Test
    void connectionStatus_test() {
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
