package fr.alexpado.mareu.data;

import java.time.LocalTime;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.Room;

public class BookingFragmentData {

    private String    subject;
    private Room      room;
    private LocalTime time;

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

    public boolean isValid() {

        return this.getSubject() != null && !this.getSubject().trim().isEmpty() &&
                this.getRoom() != null && this.getTime() != null;
    }

}
