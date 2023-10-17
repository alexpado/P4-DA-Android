package fr.alexpado.mareu.entities;

import java.util.Objects;

import fr.alexpado.mareu.interfaces.Entity;

/**
 * Class representing data for a meeting room.
 */
public class Room implements Entity {

    private Long   id;
    private String name;

    public Room(String name) {

        this.name = name;
    }

    /**
     * Retrieve this {@link Room} ID.
     *
     * @return A possibly null {@link Long} representing this {@link Room} ID.
     */
    @Override
    public Long getId() {

        return this.id;
    }

    /**
     * Define this {@link Room} ID.
     *
     * @param id
     *         The new {@link Room} id.
     */
    @Override
    public void setId(long id) {

        this.id = id;
    }

    /**
     * Retrieve this {@link Room} name.
     *
     * @return The {@link Room} name.
     */
    public String getName() {

        return this.name;
    }

    /**
     * Define this {@link Room} name.
     *
     * @param name
     *         The {@link Room} name.
     */
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(this.id, room.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);
    }

    @Override
    public String toString() {

        return "Room{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }

}
