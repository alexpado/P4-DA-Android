package fr.alexpado.mareu.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.alexpado.mareu.R;
import fr.alexpado.mareu.entities.Meeting;
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

        // TODO: Better title (and add subtitle)
        holder.getUiMeetingName().setText(meeting.getSubject());
        holder.getUiMeetingActionDelete().setOnClickListener(v -> this.listener.onMeetingDeleted(meeting));
    }

    @Override
    public int getItemCount() {

        return this.meetings.size();
    }


}
