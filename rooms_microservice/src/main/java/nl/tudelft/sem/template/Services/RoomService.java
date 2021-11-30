package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.Room;
import nl.tudelft.sem.template.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        List<Room> r = new ArrayList<>();
        roomRepository.findAll().forEach(r::add);
        return r;
    }

    public Room getRoom(int id){
        return roomRepository.findById(id).get();
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public void updateRoom(int id, Room room) {
        roomRepository.deleteById(id);
        roomRepository.save(room);
    }

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }
}
