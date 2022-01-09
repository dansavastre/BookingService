package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.RoomApplication;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureMockMvc
@SpringBootTest(classes = RoomApplication.class)
public class RoomControllerTest {

    @Mock
    private transient RoomService roomService;
    @Mock
    private transient Authorization auth;

    @InjectMocks
    private transient RoomController roomController;

    transient Room r0;
    transient Room r1;
    transient Room r2;
    transient Map<String, String> equipmentMap;
    transient String token;
    transient Building building1;
    transient Building building2;

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("projector", "True");
        equipmentMap.put("smartBoard", "True");
        building1 = new Building(36, LocalTime.of(8, 30),
                LocalTime.of(18, 00), "name1");
        building2 = new Building(24, LocalTime.of(10, 30),
                LocalTime.of(17, 30), "name2");
        r0 = new Room(12, "Europe", 12, equipmentMap, "yes", building1);
        r1 = new Room(11, "Australia", 6, equipmentMap, "no", building2);
        r2 = new Room(11, "Australia", 6, equipmentMap, "yes", building1);
        token = "token";
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
        assertEquals(rooms, roomController.getAllRooms(token));
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);
    }

    @Test
    void getRoom_test() {
        when(roomService.getRoom("12")).thenReturn(r0);
        assertEquals(r0, roomController.getRoom("12", token));
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);

    }

    @Test
    void addRoom_test() {
        roomController.addRoom(r1, token);
        verify(roomService, times(1)).addRoom(r1);
        verify(auth, times(1)).authorize(Authorization.ADMIN, token);
    }

    @Test
    void updateRoom_test() {
        when(roomService.getRoom("11"))
                .thenReturn(r2);
        roomController.updateRoom(r2, "11", token);
        verify(roomService, times(1)).updateRoom("11", r2);
        verify(auth, times(1)).authorize(Authorization.ADMIN, token);
    }

    @Test
    void deleteRoom_test() {
        roomController.deleteRoom("11", token);
        verify(roomService, times(1)).deleteRoom("11");
        verify(auth, times(1)).authorize(Authorization.ADMIN, token);
    }
}
