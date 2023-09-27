package fr.alexpado.mareu;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Optional;
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

}
