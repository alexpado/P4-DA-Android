package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;
import fr.alexpado.mareu.entities.Meeting;

/**
 * Class representing a UI element within {@link MeetingRecyclerViewAdapter}. This contains
 * references to UI components for easier access.
 */
public class MeetingRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final ImageView uiMeetingThumbnail;
    private final TextView  uiMeetingName;
    private final TextView  uiMeetingDescription;
    private final Button    uiMeetingActionDelete;

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

    /**
     * Retrieve the {@link ImageView} to use to display the colored circle for a {@link Meeting}.
     *
     * @return An {@link ImageView}
     */
    public ImageView getUiMeetingThumbnail() {

        return this.uiMeetingThumbnail;
    }

    /**
     * Retrieve the {@link TextView} to use to display the name for a {@link Meeting}.
     *
     * @return A {@link TextView}
     */
    public TextView getUiMeetingName() {

        return this.uiMeetingName;
    }

    /**
     * Retrieve the {@link TextView} to use to display the description for a {@link Meeting}.
     *
     * @return A {@link TextView}
     */
    public TextView getUiMeetingDescription() {

        return this.uiMeetingDescription;
    }

    /**
     * Retrieve the {@link Button} to trigger the removal of a {@link Meeting}.
     *
     * @return A {@link Button}
     */
    public Button getUiMeetingActionDelete() {

        return this.uiMeetingActionDelete;
    }

}
