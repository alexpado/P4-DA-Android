package fr.alexpado.mareu.data;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.views.BookingFragment;

/**
 * Class used to store data from {@link BookingFragment}'s interface. This will be used to create a
 * brand new {@link Meeting} instance.
 */
public class BookingFragmentData {

    private String    subject;
    private Room      room;
    private LocalTime time;
    private Set<User> participants = new HashSet<>();

    /**
     * Retrieve the current subject
     *
     * @return The subject
     */
    public String getSubject() {

        return this.subject;
    }

    /**
     * Define the current subject
     *
     * @param subject
     *         The subject
     */
    public void setSubject(String subject) {

        this.subject = subject;
    }

    /**
     * Retrieve the current {@link Room}
     *
     * @return The {@link Room}
     */
    public Room getRoom() {

        return this.room;
    }

    /**
     * Define the current {@link Room}
     *
     * @param roomName
     *         The {@link Room}'s name
     */
    public void setRoom(String roomName) {

        this.room = InjectionStore.roomService().getRoomByName(roomName).orElse(null);
    }

    /**
     * Retrieve the current {@link LocalTime}
     *
     * @return The {@link LocalTime}
     */
    public LocalTime getTime() {

        return this.time;
    }

    /**
     * Define the current time based on the provided hours and minutes values.
     *
     * @param hours
     *         The hour value for the {@link LocalTime}
     * @param minutes
     *         The minute value for the {@link LocalTime}
     */
    public void setTime(int hours, int minutes) {

        this.time = LocalTime.of(hours, minutes);
    }

    /**
     * Retrieve the current set of {@link User} participating.
     *
     * @return The {@link User} set.
     */
    public Set<User> getParticipants() {

        return this.participants;
    }

    /**
     * Define the current set of {@link User} participating.
     *
     * @param participants
     *         The {@link User} set.
     */
    public void setParticipants(Set<User> participants) {

        this.participants = participants;
    }

    /**
     * Check if all inputs are valid for saving.
     *
     * @return True if valid, false otherwise.
     */
    public boolean isValid() {

        return this.getSubject() != null &&
                !this.getSubject().trim().isEmpty() &&
                this.getRoom() != null &&
                this.getTime() != null &&
                this.getParticipants().size() > 1;
    }

}
