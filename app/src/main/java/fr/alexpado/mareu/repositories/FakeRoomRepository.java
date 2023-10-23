package fr.alexpado.mareu.repositories;

import java.util.ArrayList;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;

/**
 * Specific implementation of {@link FakeRepository} for {@link Room} entities.
 */
public class FakeRoomRepository extends FakeRepository<Room> implements RoomRepository {

    public FakeRoomRepository() {

        super(new ArrayList<>());

        // Fake rooms
        this.save(new Room("Mario"));
        this.save(new Room("Luigi"));
        this.save(new Room("Yoshi"));
        this.save(new Room("Peach"));
        this.save(new Room("Wario"));
        this.save(new Room("Waluigi"));
        this.save(new Room("Bowser"));
        this.save(new Room("Boo"));
        this.save(new Room("D.K."));
        this.save(new Room("Toad"));
    }

}
