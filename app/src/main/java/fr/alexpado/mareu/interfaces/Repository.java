package fr.alexpado.mareu.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a repository for a given entity type.
 *
 * @param <E>
 *         The entity type.
 */
public interface Repository<E extends Entity> {

    /**
     * Retrieve all saved entities.
     *
     * @return A {@link List} of entities.
     */
    List<E> findAll();

    /**
     * Find an entity by its ID.
     *
     * @param id
     *         The entity's ID.
     *
     * @return An optional entity. Will be empty if the id provided did not match any known entity.
     */
    Optional<E> findById(long id);

    /**
     * Save the provided entity.
     *
     * @param entity
     *         The entity to save
     *
     * @return The saved entity
     */
    E save(E entity);

    /**
     * Remove the provided entity.
     *
     * @param entity
     *         The entity to remove.
     */
    void remove(E entity);

}
