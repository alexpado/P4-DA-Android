package fr.alexpado.mareu.repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

import fr.alexpado.mareu.interfaces.Entity;
import fr.alexpado.mareu.interfaces.Repository;

/**
 * Class implementing the {@link Repository} allowing to interact with an entity type. This class
 * should be extended to define the generic 'E' type.
 *
 * @param <E>
 *         The entity type.
 */
public abstract class FakeRepository<E extends Entity> implements Repository<E> {

    private final List<E> entities;

    public FakeRepository(List<E> entities) {

        this.entities = entities;
    }

    /**
     * Retrieve all saved entities.
     *
     * @return A {@link List} of entities.
     */
    @Override
    public List<E> findAll() {

        return this.entities;
    }

    /**
     * Find an entity by its ID.
     *
     * @param id
     *         The entity's ID.
     *
     * @return An optional entity. Will be empty if the id provided did not match any known entity.
     */
    @Override
    public Optional<E> findById(long id) {

        return this.entities.stream()
                            .filter(entity -> Objects.equals(entity.getId(), id))
                            .findFirst();
    }

    /**
     * Save the provided entity.
     *
     * @param entity
     *         The entity to save
     */
    @Override
    public E save(E entity) {

        OptionalLong max = this.entities.stream()
                                        .filter(item -> item.getId() != null)
                                        .mapToLong(Entity::getId)
                                        .max();

        entity.setId(max.orElse(0) + 1);
        this.entities.add(entity);
        return entity;
    }

    /**
     * Remove the provided entity.
     *
     * @param entity
     *         The entity to remove.
     */
    @Override
    public void remove(E entity) {

        this.entities.remove(entity);
    }

}
