package fr.alexpado.mareu.services;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.MeetingRepository;

/**
 * Service allowing an easy interaction with {@link MeetingRepository}.
 */
public class MeetingService {

    private final MeetingRepository repository;

    public MeetingService(MeetingRepository repository) {

        this.repository = repository;

        this.randomize(
                InjectionStore.roomService(),
                InjectionStore.userService(),
                3
        );
    }

    /**
     * Remove all registered {@link Meeting}
     */
    public void reset() {

        this.repository.findAll().clear();
    }

    /**
     * Create a new {@link Meeting} using the provided data.
     *
     * @param room
     *         The {@link Room} in which the {@link Meeting} will take place.
     * @param time
     *         The {@link LocalTime} at which the {@link Meeting} will take place.
     * @param subject
     *         The main subject for the {@link Meeting}.
     *
     * @return The saved {@link Meeting}.
     */
    public Meeting book(Room room, LocalTime time, String subject) {

        Meeting meeting = new Meeting();
        meeting.setLocation(room);
        meeting.setTime(time);
        meeting.setSubject(subject);
        return this.repository.save(meeting);
    }

    /**
     * Remove the provided {@link Meeting}.
     *
     * @param meeting
     *         The {@link Meeting} to remove.
     */
    public void remove(Meeting meeting) {

        this.repository.remove(meeting);
    }

    /**
     * Retrieve all {@link Meeting} currently saved.
     *
     * @return A list of {@link Meeting}
     */
    public List<Meeting> getAll() {

        return this.repository.findAll();
    }

    public void randomize(RoomService roomService, UserService userService, int count) {

        int amountGenerated = 0;

        while (amountGenerated < count) {

            List<Room> rooms = roomService.getRooms();
            List<User> users = userService.getUsers();

            Random rng         = new Random();
            int    userPerRoom = 3;

            Room    room    = rooms.get(rng.nextInt(rooms.size()));
            Meeting meeting = this.book(room, LocalTime.now(), "Lorem Ipsum");
            meeting.setSubject("Lorem Ipsum (ID " + meeting.getId() + ")");

            for (int i = 0; i < userPerRoom; i++) {
                meeting.getParticipants().add(users.get(rng.nextInt(users.size())));
            }

            amountGenerated++;
        }
    }

}
