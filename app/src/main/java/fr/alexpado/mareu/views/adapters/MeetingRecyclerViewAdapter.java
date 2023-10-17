package fr.alexpado.mareu.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.events.MeetingDeleteClicked;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewHolder> {

    private final MeetingDeleteClicked listener;
    private final List<Meeting>        meetings;

    public MeetingRecyclerViewAdapter(MeetingDeleteClicked listener, List<Meeting> meetings) {

        this.listener = listener;
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public MeetingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.meeting_list_item, parent, false);

        return new MeetingRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingRecyclerViewHolder holder, int position) {

        Meeting meeting = this.meetings.get(position);

        String title = String.format(
                "%s - %s - %s",
                meeting.getSubject(),
                DateTimeFormatter.ofPattern("HH'h'mm").format(meeting.getTime()),
                meeting.getLocation().getName()
        );

        holder.getUiMeetingName().setText(title);

        if (meeting.getParticipants().isEmpty()) {
            holder.getUiMeetingDescription().setText("No participants");
        } else {

            String description = meeting.getParticipants()
                                        .stream()
                                        .map(User::getMail)
                                        .collect(Collectors.joining(", "));

            holder.getUiMeetingDescription().setText(description);
        }

        int color = AppUtils.randomColor(meeting.getId());

        holder.getUiMeetingThumbnail().setColorFilter(color);

        holder.getUiMeetingActionDelete()
              .setOnClickListener(v -> this.listener.onMeetingDeleted(meeting));
    }

    @Override
    public int getItemCount() {

        return this.meetings.size();
    }

}
