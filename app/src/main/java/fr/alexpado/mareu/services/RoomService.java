package fr.alexpado.mareu.services;

import java.util.List;
import java.util.Optional;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;

public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {

        this.repository = repository;
    }

    /**
     * Retrieve all registered {@link Room}.
     *
     * @return A {@link List} of {@link Room}.
     */
    public List<Room> getRooms() {

        return this.repository.findAll();
    }

    /**
     * Retrieve the first {@link Room} matching the provided name.
     *
     * @param name
     *         The {@link Room} name to find.
     *
     * @return An optional {@link Room}.
     */
    public Optional<Room> getRoomByName(String name) {

        return this.repository.findAll()
                              .stream()
                              .filter(room -> room.getName().equalsIgnoreCase(name))
                              .findFirst();
    }

}
