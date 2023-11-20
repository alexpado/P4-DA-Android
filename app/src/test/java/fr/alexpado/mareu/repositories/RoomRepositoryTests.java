package fr.alexpado.mareu.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;

public class RoomRepositoryTests {

    private RoomRepository repository = null;

    @Before // = @BeforeEach
    public void setup() {

        this.repository = new FakeRoomRepository();
    }

    @Test
    public void findAll_returnSomething() {

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void findById_returnOne() {
        assertTrue(this.repository.findById(1L).isPresent());
    }

    @Test
    public void findById_returnNone() {
        assertFalse(this.repository.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    public void save_isEffective() {

        Room room = new Room("unit-test");
        this.repository.save(room);

        assertNotNull(room.getId());
        assertTrue(this.repository.findById(room.getId()).isPresent());
    }

    @Test
    public void remove_isEffective() {
        Optional<Room> byId = this.repository.findById(1L);
        assertTrue(byId.isPresent());
        assertEquals(Long.valueOf(1L), byId.get().getId());
    }

}
