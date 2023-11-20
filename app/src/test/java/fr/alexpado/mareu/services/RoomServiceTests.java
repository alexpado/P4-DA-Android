package fr.alexpado.mareu.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;
import fr.alexpado.mareu.repositories.FakeRoomRepository;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RoomServiceTests {

    private RoomService service = null;

    @Before // = @BeforeEach
    public void setup() {

        this.service = new RoomService(new FakeRoomRepository());
    }


    @Test
    public void getRooms_returnSomething() {

        assertFalse(this.service.getRooms().isEmpty());
    }

    @Test
    public void getRoomByName_returnOne() {
        final String roomName = "Mario";

        Optional<Room> roomByName = this.service.getRoomByName(roomName);

        assertTrue(roomByName.isPresent());
        assertEquals(roomName, roomByName.get().getName());
    }

    @Test
    public void getRoomByName_returnNone() {
        final String roomName = "unit-test";

        Optional<Room> roomByName = this.service.getRoomByName(roomName);
        assertFalse(roomByName.isPresent());
    }
}