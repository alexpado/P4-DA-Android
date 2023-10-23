package fr.alexpado.mareu;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.services.RoomService;

/**
 * Class containing utility methods.
 */
public final class AppUtils {

    private AppUtils() {}

    /**
     * Create an {@link ArrayAdapter} from all {@link Room} using {@link RoomService#getRooms()}.
     *
     * @param context
     *         The current view context.
     * @param layout
     *         The layout resource id to use to inflate the target of the new {@link ArrayAdapter}.
     *
     * @return An {@link ArrayAdapter} of {@link Room} names.
     */
    public static ArrayAdapter<String> createRoomAdapter(Context context, int layout) {

        RoomService service = InjectionStore.roomService();

        List<String> items = service.getRooms()
                                    .stream()
                                    .map(Room::getName)
                                    .collect(Collectors.toList());

        return new ArrayAdapter<>(context, layout, items);
    }

    /**
     * Retrieve the {@link TextView} content as {@link String} (null-safe).
     *
     * @param text
     *         The {@link TextView} from which the text should be extracted.
     *
     * @return The {@link String} contained in the {@link TextView}
     */
    public static String extractText(TextView text) {

        return Optional.ofNullable(text.getText()).orElse("").toString();
    }

    public static ActionBar getBarFrom(Fragment fragment) {

        AppCompatActivity activity = (AppCompatActivity) fragment.getActivity();
        if (activity == null) {
            return null;
        }
        return activity.getSupportActionBar();
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
