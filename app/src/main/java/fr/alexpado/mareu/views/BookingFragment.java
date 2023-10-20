package fr.alexpado.mareu.views;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.data.BookingFragmentData;
import fr.alexpado.mareu.databinding.BookingFragmentBinding;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.InputChanger;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.UserService;
import fr.alexpado.mareu.views.adapters.ParticipantRecyclerViewAdapter;

public class BookingFragment extends Fragment {

    /**
     * This will wrap data contained across the fragment, converting every values to something more
     * usable for {@link MeetingService}.
     */
    private BookingFragmentData            data;
    private BookingFragmentBinding         binding;
    private MaterialTimePicker             timePicker;
    private ParticipantRecyclerViewAdapter userAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // If edit was supported, here we would pre-fill all fields.
        this.data = new BookingFragmentData();
    }

    @Override
    public void onResume() {

        super.onResume();
        ActionBar bar = AppUtils.getBarFrom(this);
        if (bar != null) {
            bar.setSubtitle(R.string.fragment_booking);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.binding = BookingFragmentBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ArrayAdapter<String> roomAdapter = AppUtils.createRoomAdapter(
                this.requireContext(),
                R.layout.material_item_view
        );

        this.userAdapter = new ParticipantRecyclerViewAdapter(
                this.binding.participantListView,
                InjectionStore.userService(),
                this.data
        );

        this.timePicker = this.createPicker();

        this.binding.addMeetingSubject.addTextChangedListener((InputChanger) s -> this.data.setSubject(
                s));
        this.binding.addMeetingRoom.addTextChangedListener((InputChanger) s -> this.data.setRoom(s));

        this.binding.addMeetingRoom.setAdapter(roomAdapter);
        this.binding.addMeetingTimeSet.setOnClickListener(this::handleTimeButtonClick);
        this.binding.addMeetingValidate.setOnClickListener(this::handleValidateButtonClick);
        this.binding.addParticipantMail.setOnEditorActionListener(this::handleParticipantMailInputAction);

        this.timePicker.addOnPositiveButtonClickListener(this::handleTimePickerConfirmation);

        Context listViewContext = this.binding.participantListView.getContext();

        this.binding.participantListView.setLayoutManager(new LinearLayoutManager(listViewContext));
        this.binding.participantListView.addItemDecoration(new DividerItemDecoration(
                listViewContext,
                DividerItemDecoration.VERTICAL
        ));

        this.binding.participantListView.setAdapter(this.userAdapter);

        super.onViewCreated(view, savedInstanceState);
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

    /**
     * Called when {@link #validateButton} is clicked by the user.
     *
     * @param view
     *         The clicked button
     */
    private void handleValidateButtonClick(View view) {

        this.data.setParticipants(this.userAdapter.collectParticipants());

        if (this.data.isValid()) {

            MeetingService service = InjectionStore.meetingService();
            service.book(this.data);
            this.getParentFragmentManager().popBackStack();
        } else {
            Snackbar.make(
                            this.binding.getRoot(),
                            R.string.add_meeting_validation_failed,
                            Snackbar.LENGTH_LONG
                    )
                    .show();
        }
    }

    /**
     * Called when {@link #setTimeButton} is clicked by the user.
     *
     * @param view
     *         The clicked button
     */
    private void handleTimeButtonClick(View view) {

        this.timePicker.show(this.getParentFragmentManager(), "tag");
    }

    /**
     * Called when the user confirm its input in {@link #timePicker}.
     *
     * @param view
     *         The modal
     */
    private void handleTimePickerConfirmation(View view) {

        this.data.setTime(this.timePicker.getHour(), this.timePicker.getMinute());

        this.binding.addMeetingTime.setText(DateTimeFormatter
                                                    .ofPattern("HH'h'mm")
                                                    .format(this.data.getTime()));
    }

    /**
     * Called when an IME action happens.
     *
     * @param view
     *         The view from which the IME action is being handled
     * @param actionId
     *         The IME action ID
     * @param event
     *         The {@link KeyEvent} representing this IME action
     *
     * @return True if it has been handled here, false otherwise.
     */
    private boolean handleParticipantMailInputAction(View view, int actionId, KeyEvent event) {

        boolean handled = false;

        if (actionId == EditorInfo.IME_ACTION_DONE) {

            UserService    userService  = InjectionStore.userService();
            String         mail         = AppUtils.extractText(this.binding.addParticipantMail);
            Optional<User> optionalUser = userService.findUser(mail);

            if (optionalUser.isPresent()) {
                this.userAdapter.setCheckedState(optionalUser.get(), true);
            } else {
                this.userAdapter.addUser(userService.createUser(mail), true);
            }

            this.binding.addParticipantMail.setText("");
            handled = true;
        }
        return handled;
    }

}
