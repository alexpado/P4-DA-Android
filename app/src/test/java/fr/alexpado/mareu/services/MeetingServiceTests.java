package fr.alexpado.mareu.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.repositories.FakeMeetingRepository;

public class MeetingServiceTests {

    private MeetingService service = null;

    @Before // = @BeforeEach
    public void setup() {
        this.service = new MeetingService(new FakeMeetingRepository());
    }

    @Test
    public void getAll_returnNone() {

        assertTrue(this.service.getAll().isEmpty());
    }


    @Test
    public void book_saveOne() {

        RoomService roomService = InjectionStore.roomService();
        UserService userService = InjectionStore.userService();

        Room room = roomService.getRooms().get(0);

        Meeting unitTest = this.service.book(room, LocalTime.now(), "Unit Test", userService.getUsers());

        assertNotNull(unitTest.getId());
    }

    @Test
    public void applyFilter_actuallyDoesSomething() {

        RoomService roomService = InjectionStore.roomService();
        UserService userService = InjectionStore.userService();

        Room firstRoom = roomService.getRooms().get(0);
        Room secondRoom = roomService.getRooms().get(1);
        Room thirdRoom = roomService.getRooms().get(2);

        LocalTime firstTime = LocalTime.of(12, 0);
        LocalTime secondTime = LocalTime.of(16, 0);
        LocalTime thirdTime = LocalTime.of(20, 0);

        Meeting firstMeeting = this.service.book(firstRoom, firstTime, "First Meeting", userService.getUsers());
        this.service.book(secondRoom, secondTime, "Second Meeting", userService.getUsers());
        this.service.book(thirdRoom, thirdTime, "Third Meeting", userService.getUsers());

        int totalAmount = 3;

        // Sanity Checks
        assertEquals(totalAmount, this.service.getAll().size());

        this.service.applyFilter("unitTest", meeting -> meeting.getLocation().equals(firstRoom));

        assertEquals(1, this.service.getAll().size());
        assertEquals(firstMeeting.getId(), this.service.getAll().get(0).getId());

        assertTrue(this.service.hasFilter("unitTest"));

        this.service.removeFilter("unitTest");
        assertEquals(totalAmount, this.service.getAll().size());
        assertFalse(this.service.hasFilter("unitTest"));
    }

    @Test
    public void remove_isRemoved() {
        RoomService roomService = InjectionStore.roomService();
        UserService userService = InjectionStore.userService();

        Room firstRoom = roomService.getRooms().get(0);
        LocalTime firstTime = LocalTime.of(12, 0);
        Meeting firstMeeting = this.service.book(firstRoom, firstTime, "First Meeting", userService.getUsers());

        assertEquals(1, this.service.getAll().size());
        this.service.remove(firstMeeting);
        assertTrue(this.service.getAll().isEmpty());
    }


}
