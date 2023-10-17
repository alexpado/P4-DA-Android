package fr.alexpado.mareu.services;

import java.util.List;
import java.util.Optional;

import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    /**
     * Retrieve all registered {@link User}.
     *
     * @return A {@link List} of {@link User}.
     */
    public List<User> getUsers() {

        return this.repository.findAll();
    }

    /**
     * Tries to find a {@link User} by the provided mail.
     *
     * @param mail
     *         The {@link User}'s mail.
     *
     * @return A {@link User}, if found.
     */
    public Optional<User> findUser(String mail) {

        return this.repository.findByMail(mail);
    }

    /**
     * Create a new {@link User} with the provided mail.
     *
     * @param mail
     *         The {@link User}'s mail.
     *
     * @return The newly created {@link User}
     */
    public User createUser(String mail) {

        User user = new User(mail);
        return this.repository.save(user);
    }

}
