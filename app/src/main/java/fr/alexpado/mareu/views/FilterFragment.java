package fr.alexpado.mareu.views;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.databinding.FilterFragmentBinding;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.interfaces.InputChanger;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.RoomService;

public class FilterFragment extends Fragment {

    private FilterFragmentBinding binding;
    private MeetingService service;
    private MaterialTimePicker timePicker;

    /**
     * Create a {@link MaterialTimePicker} to use when selecting a {@link LocalTime} for a new
     * {@link Meeting}.
     *
     * @return A {@link MaterialTimePicker} instance.
     */
    private MaterialTimePicker createPicker() {

        return new MaterialTimePicker.Builder()
                .setTimeFormat(DateFormat.is24HourFormat(this.requireContext()) ? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H)
                .setTitleText(R.string.add_meeting_time)
                .build();
    }

    @Override
    public void onResume() {

        super.onResume();
        ActionBar bar = AppUtils.getBarFrom(this);
        if (bar != null) {
            bar.setSubtitle(R.string.fragment_filtering);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.binding = FilterFragmentBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Resources res = this.getResources();
        this.service = InjectionStore.meetingService();
        this.timePicker = this.createPicker();

        if (this.service.getFilterRoom() != null) {
            this.binding.filterMeetingRoom.setText(this.service.getFilterRoom().getName());
        }

        if (this.service.getFilterTime() != null) {
            LocalTime time = this.service.getFilterTime();
            @SuppressLint("DefaultLocale") // It's just to pad a number with a leading zero...
            String strFormat = String.format("%02d:%02d", time.getHour(), time.getMinute());
            this.binding.filterMeetingTime.setText(strFormat);
        }

        ArrayAdapter<String> roomAdapter = AppUtils.createRoomAdapter(
                this.requireContext(),
                R.layout.material_item_view
        );

        this.binding.filterMeetingRoom.setAdapter(roomAdapter);

        this.binding.filterMeetingRoom.addTextChangedListener((InputChanger) this::handleRoomChange);
        this.binding.filterMeetingTime.setOnClickListener(this::handleTimeButtonClick);

        this.binding.unsetRoomFilter.setOnClickListener(this::resetRoomFilterButtonClick);
        this.binding.unsetTimeFilter.setOnClickListener(this::resetTimeFilterButtonClick);

        this.binding.unsetRoomFilter.setContentDescription(res.getString(R.string.accessibility_remove_room_filter));
        this.binding.unsetTimeFilter.setContentDescription(res.getString(R.string.accessibility_remove_time_filter));

        this.timePicker.addOnPositiveButtonClickListener(v -> {
            int hour = this.timePicker.getHour();
            int minute = this.timePicker.getMinute();
            this.handleTimeChange(hour, minute);
        });

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Called when the user request to set the time.
     *
     * @param view The clicked button
     */
    private void handleTimeButtonClick(View view) {

        this.timePicker.show(this.getParentFragmentManager(), "tag");
    }

    /**
     * Remove any existing room filter from {@link MeetingService}.
     *
     * @param view The clicked button
     */
    public void resetRoomFilterButtonClick(View view) {

        this.handleRoomChange("");
    }

    /**
     * Remove any existing time filter from {@link MeetingService}.
     *
     * @param view The clicked button
     */
    public void resetTimeFilterButtonClick(View view) {

        this.handleTimeChange(null, null);
    }

    /**
     * Update the room filter in {@link MeetingService}.
     *
     * @param roomName The room name to use as filter. If empty, the filter will be disabled.
     */
    private void handleRoomChange(String roomName) {

        Resources res = this.getResources();

        if (roomName.equals("")) {
            if (this.service.hasFilter(MeetingService.FILTER_ROOM_NAME)) {
                this.service.setRoomFilter(null);
                this.binding.filterMeetingRoom.setText("");
            }

            this.binding.filterMeetingRoom.setContentDescription(
                    res.getString(R.string.accessibility_define_room_filter)
            );
            return;
        }

        RoomService roomService = InjectionStore.roomService();
        Optional<Room> roomByName = roomService.getRoomByName(roomName);

        if (roomByName.isPresent()) {
            Room room = roomByName.get();
            this.service.setRoomFilter(room);
            this.binding.filterMeetingRoom.setContentDescription(
                    res.getString(R.string.accessibility_update_room_filter, room.getName())
            );
        } else {
            this.handleRoomChange("");
        }
    }

    /**
     * Update the time filter in {@link MeetingService}
     *
     * @param hour   The hour of day to use as filter. If null, the filter will be disabled.
     * @param minute The minute of the day to use as filter. If null, the filter will be disabled.
     */
    private void handleTimeChange(Integer hour, Integer minute) {

        Resources res = this.getResources();
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        if (hour == null || minute == null) {
            if (this.service.hasFilter(MeetingService.FILTER_TIME_NAME)) {
                this.service.setTimeFilter(null);
                this.binding.filterMeetingTime.setText("");
            }

            this.binding.filterMeetingTime.setContentDescription(
                    res.getString(R.string.accessibility_define_time_filter)
            );
            return;
        }

        @SuppressLint("DefaultLocale") // It's just to pad a number with a leading zero...
        String strFormat = String.format("%02d:%02d", hour, minute);
        LocalTime time = LocalTime.of(hour, minute);

        this.service.setTimeFilter(time);
        this.binding.filterMeetingTime.setText(strFormat);

        this.binding.filterMeetingRoom.setContentDescription(
                res.getString(R.string.accessibility_update_room_filter, dtf.format(time))
        );
    }

}
