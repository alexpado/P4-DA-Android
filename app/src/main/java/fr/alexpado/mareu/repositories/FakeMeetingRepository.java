package fr.alexpado.mareu.repositories;

import java.util.ArrayList;

import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.interfaces.repositories.MeetingRepository;

/**
 * Specific implementation of {@link FakeRepository} for {@link Meeting} entities.
 */
public class FakeMeetingRepository extends FakeRepository<Meeting> implements MeetingRepository {

    public FakeMeetingRepository() {

        super(new ArrayList<>());
    }

}
