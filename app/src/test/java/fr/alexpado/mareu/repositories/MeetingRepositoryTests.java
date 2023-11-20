package fr.alexpado.mareu.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Optional;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.interfaces.repositories.MeetingRepository;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;

public class MeetingRepositoryTests {

    private MeetingRepository repository = null;

    @Before // = @BeforeEach
    public void setup() {

        this.repository = new FakeMeetingRepository();
    }


    @Test
    public void findById_returnNone() {
        assertFalse(this.repository.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    public void save_isEffective() {
        Room room = InjectionStore.roomRepository().findAll().get(0);

        LocalTime now = LocalTime.now().withNano(0).withSecond(0);

        Meeting meeting = new Meeting();
        meeting.setSubject("Unit tests");
        meeting.setLocation(room);
        meeting.setTime(now);

        this.repository.save(meeting);

        assertNotNull(meeting.getId());
        assertTrue(this.repository.findById(meeting.getId()).isPresent());
    }

    @Test
    public void remove_isEffective() {

        Room room = InjectionStore.roomRepository().findAll().get(0);

        LocalTime now = LocalTime.now().withNano(0).withSecond(0);

        Meeting meeting = new Meeting();
        meeting.setSubject("Unit tests");
        meeting.setLocation(room);
        meeting.setTime(now);

        this.repository.save(meeting);

        Optional<Meeting> byId = this.repository.findById(meeting.getId());
        assertTrue(byId.isPresent());
        assertEquals(meeting.getId(), byId.get().getId());
    }

}
