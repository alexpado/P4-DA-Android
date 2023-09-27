package fr.alexpado.mareu.events;

import fr.alexpado.mareu.entities.Meeting;

/**
 * Interface representing an event when a "delete" button is clicked on a meeting item.
 */
public interface MeetingDeleteClicked {

    /**
     * Called when a user requested a {@link Meeting} to be deleted.
     *
     * @param meeting
     *         The {@link Meeting} to delete.
     */
    void onMeetingDeleted(Meeting meeting);

}
