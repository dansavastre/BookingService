package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.RoomApplication;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.repositories.RoomRepository;
import nl.tudelft.sem.template.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;




@AutoConfigureMockMvc
@SpringBootTest(classes = RoomApplication.class)
public class RoomServiceTest {

    @Mock
    private transient RoomRepository roomRepository;

    @InjectMocks
    private transient RoomService roomService;

    transient Room r0;
    transient Room r1;
    transient Room r2;

    @BeforeEach
    void setup() {
        r0 = new Room(12, "Europe", 12, "projector, whiteboard", "yes", 36);
        r1 = new Room(11, "Australia", 6, "projector", "no", 36);
        r2 = new Room(11, "Australia", 6, "projector", "yes", 36);
    }

    @Test
    void getAllRooms_test() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(r0);
        rooms.add(r1);
        when(roomRepository.findAll()).thenReturn(rooms);
        assertEquals(rooms, roomService.getAllRooms());
    }

    @Test
    void getRoom_test() {
        when(roomRepository.findById(12)).thenReturn(java.util.Optional.ofNullable(r0));
        assertEquals(r0, roomService.getRoom(12));
    }

    @Test
    void addRoom_test() {
        roomService.addRoom(r1);
        verify(roomRepository, times(1)).save(r1);
    }

    @Test
    void updateRoom_test() {
        roomService.updateRoom(11, r2);
        verify(roomRepository, times(1)).deleteById(11);
        verify(roomRepository, times(1)).save(r2);
    }

    @Test
    void deleteRoom_test() {
        roomService.deleteRoom(11);
        verify(roomRepository, times(1)).deleteById(11);
    }
}
