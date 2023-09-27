package fr.alexpado.mareu.repositories;

import java.util.ArrayList;

import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;

/**
 * Specific implementation of {@link FakeRepository} for {@link User} entities.
 */
public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    public FakeUserRepository() {

        super(new ArrayList<>());

        this.save(new User("j.dassin@lamzone.fr"));
        this.save(new User("s.gainsbourg@lamzone.fr"));
        this.save(new User("r.gotainer@lamzone.fr"));
        this.save(new User("p.obispo@lamzone.fr"));
        this.save(new User("e.moire@lamzone.fr"));
        this.save(new User("c.dion@lamzone.fr"));
        this.save(new User("d.balavoine@lamzone.fr"));
        this.save(new User("p.bruel@lamzone.fr"));
    }

}
