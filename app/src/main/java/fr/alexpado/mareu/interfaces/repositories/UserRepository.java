package fr.alexpado.mareu.interfaces.repositories;

import java.util.Optional;

import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.Repository;

/**
 * Specific interface of {@link Repository} for {@link User} entities.
 */
public interface UserRepository extends Repository<User> {

    /**
     * Tries to find a {@link User} by the provided mail.
     *
     * @param mail
     *         The {@link User}'s mail.
     *
     * @return A {@link User}, if found.
     */
    Optional<User> findByMail(String mail);

}
