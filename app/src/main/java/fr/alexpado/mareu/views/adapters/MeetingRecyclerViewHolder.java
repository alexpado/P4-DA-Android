package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;

/**
 * Class representing a UI element within {@link MeetingRecyclerViewAdapter}. This contains
 * references to UI components for easier access.
 */
public class MeetingRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView   uiMeetingThumbnail;
    private TextView    uiMeetingName;
    private TextView    uiMeetingDescription;
    private ImageButton uiMeetingActionDelete;

    /**
     * Create a new instance of {@link MeetingRecyclerViewHolder}.
     *
     * @param itemView
     *         The {@link View} used to display an element on the UI.
     */
    public MeetingRecyclerViewHolder(@NonNull View itemView) {

        super(itemView);

        this.uiMeetingThumbnail    = itemView.findViewById(R.id.meeting_list_item_thumbnail);
        this.uiMeetingName         = itemView.findViewById(R.id.meeting_list_item_name);
        this.uiMeetingDescription  = itemView.findViewById(R.id.meeting_list_item_description);
        this.uiMeetingActionDelete = itemView.findViewById(R.id.meeting_list_item_action_delete);
    }

    public ImageView getUiMeetingThumbnail() {

        return this.uiMeetingThumbnail;
    }

    public TextView getUiMeetingName() {

        return this.uiMeetingName;
    }

    public TextView getUiMeetingDescription() {

        return this.uiMeetingDescription;
    }

    public ImageButton getUiMeetingActionDelete() {

        return this.uiMeetingActionDelete;
    }

}
