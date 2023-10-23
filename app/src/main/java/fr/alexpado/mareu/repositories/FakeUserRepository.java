package fr.alexpado.mareu.repositories;

import java.util.ArrayList;
import java.util.Optional;

import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;

/**
 * Specific implementation of {@link FakeRepository} for {@link User} entities.
 */
public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    public FakeUserRepository() {

        super(new ArrayList<>());

        // Fake users
        this.save(new User("j.dassin@lamzone.fr"));
        this.save(new User("s.gainsbourg@lamzone.fr"));
        this.save(new User("r.gotainer@lamzone.fr"));
        this.save(new User("p.obispo@lamzone.fr"));
        this.save(new User("e.moire@lamzone.fr"));
        this.save(new User("c.dion@lamzone.fr"));
        this.save(new User("d.balavoine@lamzone.fr"));
        this.save(new User("p.bruel@lamzone.fr"));
    }

    /**
     * Tries to find a {@link User} by the provided mail.
     *
     * @param mail
     *         The {@link User}'s mail.
     *
     * @return A {@link User}, if found.
     */
    @Override
    public Optional<User> findByMail(String mail) {

        return this.findAll()
                   .stream()
                   .filter(user -> user.getMail().equalsIgnoreCase(mail))
                   .findFirst();
    }

}
