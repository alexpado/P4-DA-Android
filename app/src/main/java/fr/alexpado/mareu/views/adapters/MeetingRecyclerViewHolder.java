package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;

public class MeetingRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView   uiMeetingThumbnail;
    private TextView    uiMeetingName;
    private ImageButton uiMeetingActionDelete;

    public MeetingRecyclerViewHolder(@NonNull View itemView) {

        super(itemView);

        this.uiMeetingThumbnail = itemView.findViewById(R.id.meeting_list_item_thumbnail);
        this.uiMeetingName = itemView.findViewById(R.id.meeting_list_item_name);
        this.uiMeetingActionDelete = itemView.findViewById(R.id.meeting_list_item_action_delete);
    }

    public ImageView getUiMeetingThumbnail() {

        return this.uiMeetingThumbnail;
    }

    public TextView getUiMeetingName() {

        return this.uiMeetingName;
    }

    public ImageButton getUiMeetingActionDelete() {

        return this.uiMeetingActionDelete;
    }

}
