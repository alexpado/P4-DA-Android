package fr.alexpado.mareu.views;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.data.BookingFragmentData;
import fr.alexpado.mareu.entities.Meeting;

public class BookingFragment extends Fragment {

    private BookingFragmentData data;

    private TextInputEditText    meetingSubject;
    private AutoCompleteTextView meetingRoom;
    private TextInputEditText    meetingTime;

    private View               view;
    private Button             validateButton;
    private Button             setTimeButton;
    private MaterialTimePicker timePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.data = new BookingFragmentData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.booking_fragment,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.view = view;

        this.meetingSubject = (TextInputEditText) view.findViewById(R.id.add_meeting_subject);
        this.meetingRoom    = (AutoCompleteTextView) view.findViewById(R.id.add_meeting_room);
        this.meetingTime    = (TextInputEditText) view.findViewById(R.id.add_meeting_time);
        this.setTimeButton  = (Button) view.findViewById(R.id.add_meeting_time_set);
        this.validateButton = (Button) view.findViewById(R.id.add_meeting_validate);

        this.timePicker = this.createPicker();

        this.bindUi();

        super.onViewCreated(view, savedInstanceState);
    }

    private void bindUi() {

        ArrayAdapter<String> roomAdapter = AppUtils.createRoomAdapter(
                this.requireContext(),
                R.layout.material_item_view
        );

        this.meetingRoom.setAdapter(roomAdapter);

        this.setTimeButton.setOnClickListener(v -> {
            this.timePicker.show(this.getParentFragmentManager(), "tag");
        });

        this.validateButton.setOnClickListener(v -> this.validate());

        this.timePicker.addOnPositiveButtonClickListener(v -> {
            this.data.setTime(this.timePicker.getHour(), this.timePicker.getMinute());

            // TODO: Use DateTimeFormatter
            this.meetingTime.setText(String.format(
                    "%s:%s",
                    this.timePicker.getHour(),
                    this.timePicker.getMinute()
            ));
        });
    }

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

    private void validate() {

        this.data.setSubject(AppUtils.extractText(this.meetingSubject));
        this.data.setRoom(AppUtils.extractText(this.meetingRoom));

        if (this.data.isValid()) {
            // TODO: Save using service then go back
            Snackbar.make(this.view, "champaaaagne", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(this.view, R.string.add_meeting_validation_failed, Snackbar.LENGTH_LONG).show();
        }

    }

}
