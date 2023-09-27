package fr.alexpado.mareu.services;

import java.util.List;

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

}
