package fr.alexpado.mareu.entities;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import fr.alexpado.mareu.interfaces.Entity;

/**
 * Class representing data for a user.
 */
public class User implements Entity {

    private Long   id;
    private String mail;

    public User(String mail) {

        this.mail = mail;
    }

    /**
     * Retrieve this {@link User} ID.
     *
     * @return A possibly null {@link Long} representing this {@link User} ID.
     */
    @Override
    public Long getId() {

        return this.id;
    }

    /**
     * Define this {@link User} ID.
     *
     * @param id
     *         The new {@link User} ID.
     */
    @Override
    public void setId(long id) {

        this.id = id;
    }

    /**
     * Retrieve this {@link User} mail.
     *
     * @return The {@link User} mail.
     */
    @NotNull
    public String getMail() {

        return this.mail;
    }

    /**
     * Define this {@link User} mail.
     *
     * @param mail
     *         The new {@link User} mail.
     */
    public void setMail(@NotNull String mail) {

        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);
    }

    @NonNull
    @Override
    public String toString() {

        return "User{" +
                "id=" + this.id +
                ", mail='" + this.mail + '\'' +
                '}';
    }

}
