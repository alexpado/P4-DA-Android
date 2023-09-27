package fr.alexpado.mareu.entities;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.alexpado.mareu.interfaces.Entity;

/**
 * Class representing data for a meeting.
 */
public class Meeting implements Entity {

    private       Long      id;
    private       Room      location;
    private       String    subject;
    private       LocalTime time;
    private final Set<User> participants = new HashSet<>();

    /**
     * Retrieve this {@link Meeting} ID.
     *
     * @return A possibly null {@link Long} representing this {@link Meeting} ID.
     */
    @Override
    public Long getId() {

        return this.id;
    }

    /**
     * Define this {@link Meeting} ID.
     *
     * @param id
     *         The new {@link Meeting} ID.
     */
    @Override
    public void setId(long id) {

        this.id = id;
    }

    /**
     * Retrieve the {@link Room} in which this {@link Meeting} will take place.
     *
     * @return The {@link Meeting} {@link Room}
     */
    public Room getLocation() {

        return this.location;
    }

    /**
     * Define the {@link Room} in which this {@link Meeting} will take place.
     *
     * @param location
     *         The {@link Meeting} {@link Room}
     */
    public void setLocation(Room location) {

        this.location = location;
    }

    /**
     * Retrieve this {@link Meeting} subject.
     *
     * @return The {@link Meeting} subject.
     */
    public String getSubject() {

        return this.subject;
    }

    /**
     * Define this {@link Meeting} subject.
     *
     * @param subject
     *         The {@link Meeting} subject
     */
    public void setSubject(String subject) {

        this.subject = subject;
    }

    /**
     * Retrieve the {@link LocalTime} at which this {@link Meeting} will take place.
     *
     * @return The {@link Meeting} {@link LocalTime}.
     */
    public LocalTime getTime() {

        return this.time;
    }

    /**
     * Define the {@link LocalTime} at which this {@link Meeting} will take place.
     *
     * @param time
     *         The {@link Meeting} {@link LocalTime}.
     */
    public void setTime(LocalTime time) {

        this.time = time;
    }

    /**
     * Retrieve the list of {@link User} which will take part in this {@link Meeting}.
     *
     * @return A list of {@link User}.
     */
    public Set<User> getParticipants() {

        return this.participants;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Meeting meeting = (Meeting) o;
        return Objects.equals(this.id, meeting.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);
    }

    @NonNull
    @Override
    public String toString() {

        return "Meeting{" +
                "id=" + this.id +
                ", location=" + this.location +
                ", subject='" + this.subject + '\'' +
                ", time=" + this.time +
                ", participants=" + this.participants +
                '}';
    }

}
