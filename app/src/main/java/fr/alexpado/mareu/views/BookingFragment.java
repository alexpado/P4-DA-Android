package fr.alexpado.mareu.views;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.data.BookingFragmentData;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.UserService;
import fr.alexpado.mareu.views.adapters.ParticipantRecyclerViewAdapter;

public class BookingFragment extends Fragment {

    private BookingFragmentData data;

    private TextInputEditText    meetingSubject;
    private AutoCompleteTextView meetingRoom;
    private TextInputEditText    meetingTime;

    private View               view;
    private TextInputEditText  participantInput;
    private Button             validateButton;
    private Button             setTimeButton;
    private MaterialTimePicker timePicker;

    private RecyclerView                   userRecyclerView;
    private ParticipantRecyclerViewAdapter userAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // If edit was supported, here we would pre-fill all fields.
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

        this.meetingSubject   = (TextInputEditText) view.findViewById(R.id.add_meeting_subject);
        this.meetingRoom      = (AutoCompleteTextView) view.findViewById(R.id.add_meeting_room);
        this.meetingTime      = (TextInputEditText) view.findViewById(R.id.add_meeting_time);
        this.setTimeButton    = (Button) view.findViewById(R.id.add_meeting_time_set);
        this.validateButton   = (Button) view.findViewById(R.id.add_meeting_validate);
        this.userRecyclerView = (RecyclerView) view.findViewById(R.id.participant_list_view);
        this.participantInput = (TextInputEditText) view.findViewById(R.id.add_participant_mail);

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

        this.setTimeButton.setOnClickListener(this::handleTimeButtonClick);
        this.validateButton.setOnClickListener(this::handleValidateButtonClick);
        this.timePicker.addOnPositiveButtonClickListener(this::handleTimePickerConfirmation);
        this.participantInput.setOnEditorActionListener(this::handleParticipantMailInputAction);

        Context context = this.userRecyclerView.getContext();

        this.userRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.userRecyclerView.addItemDecoration(new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        ));

        this.userAdapter = new ParticipantRecyclerViewAdapter(
                this.userRecyclerView,
                InjectionStore.userService(),
                this.data
        );
        this.userRecyclerView.setAdapter(this.userAdapter);
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

    private void handleValidateButtonClick(View view) {

        this.data.setSubject(AppUtils.extractText(this.meetingSubject));
        this.data.setRoom(AppUtils.extractText(this.meetingRoom));
        this.data.setParticipants(this.userAdapter.collectParticipants());

        if (this.data.isValid()) {

            MeetingService service = InjectionStore.meetingService();
            service.book(this.data);
            this.getParentFragmentManager().popBackStack();
        } else {
            Snackbar.make(this.view, R.string.add_meeting_validation_failed, Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    private void handleTimeButtonClick(View view) {

        this.timePicker.show(this.getParentFragmentManager(), "tag");
    }

    private void handleTimePickerConfirmation(View view) {

        this.data.setTime(this.timePicker.getHour(), this.timePicker.getMinute());

        this.meetingTime.setText(
                DateTimeFormatter
                        .ofPattern("HH'h'mm")
                        .format(this.data.getTime())
        );
    }

    private boolean handleParticipantMailInputAction(View view, int actionId, KeyEvent event) {

        boolean handled = false;

        if (actionId == EditorInfo.IME_ACTION_DONE) {

            UserService    userService  = InjectionStore.userService();
            String         mail         = AppUtils.extractText(this.participantInput);
            Optional<User> optionalUser = userService.findUser(mail);

            if (optionalUser.isPresent()) {
                this.userAdapter.setCheckedState(optionalUser.get(), true);
            } else {
                this.userAdapter.addUser(userService.createUser(mail), true);
            }

            this.participantInput.setText("");
            handled = true;
        }
        return handled;
    }

}
