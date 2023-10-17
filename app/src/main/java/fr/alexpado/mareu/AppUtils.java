package fr.alexpado.mareu;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.services.RoomService;

public final class AppUtils {

    private AppUtils() {}

    public static ArrayAdapter<String> createRoomAdapter(Context context, int layout) {

        RoomService service = InjectionStore.roomService();

        List<String> items = service.getRooms()
                                    .stream()
                                    .map(Room::getName)
                                    .collect(Collectors.toList());

        return new ArrayAdapter<>(context, layout, items);
    }

    public static String extractText(TextView text) {

        return Optional.ofNullable(text.getText()).orElse("").toString();
    }

    /**
     * Get a random color for the provided seed.
     *
     * @param seed
     *         The {@link Random} seed.
     *
     * @return A color in the ARGB format.
     */
    public static int randomColor(long seed) {

        // Ensure it's always the same color when seed (id) is provided
        Random rnd = new Random(seed);
        int    r   = rnd.nextInt(255) + 1;
        int    g   = rnd.nextInt(255) + 1;
        int    b   = rnd.nextInt(255) + 1;

        return Color.valueOf(r, g, b).toArgb();
    }

}
