package services_test;

import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.repositories.RoomRepository;
import nl.tudelft.sem.template.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomService_test {

    @Mock
    private RoomRepository roomRepository;

    private RoomService roomService;

    Room r0;
    Room r1;
    Room r2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        r0 = new Room(12, "Europe", 12, "projector, whiteboard", "yes", 36);
        r1 = new Room(11, "Australia", 6, "projector", "no", 36);
        r2 = new Room(11, "Australia", 6, "projector", "yes", 36);
        roomService = new RoomService(roomRepository);
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
