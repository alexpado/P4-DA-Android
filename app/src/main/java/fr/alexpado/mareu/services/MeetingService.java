package fr.alexpado.mareu.services;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.alexpado.mareu.data.BookingFragmentData;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.MeetingRepository;

/**
 * Service allowing an easy interaction with {@link MeetingRepository}.
 */
public class MeetingService {

    private final MeetingRepository               repository;
    private final Map<String, Predicate<Meeting>> filters = new HashMap<>();

    public MeetingService(MeetingRepository repository) {

        this.repository = repository;
    }

    /**
     * Add a new filter to change the displayable list of {@link Meeting}.
     *
     * @param filterName
     *         The name of the filter, for future references.
     * @param predicate
     *         The filter to apply to the {@link Meeting} list.
     */
    public void applyFilter(String filterName, Predicate<Meeting> predicate) {

        this.filters.put(filterName, predicate);
    }

    /**
     * Remove a filter added through {@link #applyFilter(String, Predicate)}.
     *
     * @param filterName
     *         The name of the filter.
     */
    public void removeFilter(String filterName) {

        this.filters.remove(filterName);
    }

    /**
     * Check if a filter name has been registered.
     *
     * @param filterName
     *         The name of the filter.
     *
     * @return True if a filter of this name exists, false otherwise.
     */
    public boolean hasFilter(String filterName) {

        return this.filters.containsKey(filterName);
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
    public Meeting book(Room room, LocalTime time, String subject, Collection<User> participants) {

        Meeting meeting = new Meeting();
        meeting.setLocation(room);
        meeting.setTime(time);
        meeting.setSubject(subject);
        meeting.getParticipants().addAll(participants);
        return this.repository.save(meeting);
    }

    /**
     * reate a new {@link Meeting} using the provided data.
     *
     * @param data
     *         A {@link BookingFragmentData} containing user inputs for the {@link Meeting}
     *
     * @return The saved {@link Meeting}
     */
    public Meeting book(BookingFragmentData data) {

        return this.book(data.getRoom(), data.getTime(), data.getSubject(), data.getParticipants());
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

        if (this.filters.isEmpty()) {
            return this.repository.findAll();
        }

        Stream<Meeting> stream = this.repository.findAll().stream();

        for (Predicate<Meeting> predicate : this.filters.values()) {
            stream = stream.filter(predicate);
        }

        return stream.collect(Collectors.toList());
    }

}
