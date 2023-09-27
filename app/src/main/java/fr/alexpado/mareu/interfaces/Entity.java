package fr.alexpado.mareu.interfaces;

/**
 * Interface representing an entity holding an ID.
 */
public interface Entity {

    /**
     * Retrieve this entity id.
     *
     * @return The possibly null entity id.
     */
    Long getId();

    /**
     * Define this entity id.
     *
     * @param id
     *         The new entity id.
     */
    void setId(long id);

}
