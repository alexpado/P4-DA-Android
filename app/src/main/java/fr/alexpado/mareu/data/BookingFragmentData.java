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

    public String getSubject() {

        return this.subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
    }

    public Room getRoom() {

        return this.room;
    }

    public void setRoom(String roomName) {

        this.room = InjectionStore.roomService().getRoomByName(roomName).orElse(null);
    }

    public LocalTime getTime() {

        return this.time;
    }

    public void setTime(int hours, int minutes) {

        this.time = LocalTime.of(hours, minutes);
    }

    public Set<User> getParticipants() {

        return this.participants;
    }

    public void setParticipants(Set<User> participants) {

        this.participants = participants;
    }

    public boolean isValid() {

        return this.getSubject() != null && !this.getSubject().trim().isEmpty() &&
                this.getRoom() != null && this.getTime() != null && this.getParticipants()
                                                                        .size() > 1;
    }

}
