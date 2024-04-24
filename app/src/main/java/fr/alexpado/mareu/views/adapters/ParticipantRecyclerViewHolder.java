package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.views.BookingFragment;

/**
 * Class representing a UI element within {@link ParticipantRecyclerViewAdapter}. This contains
 * references to UI components for easier access.
 */
public class ParticipantRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final TextView uiMail;
    private final CheckBox uiCheck;

    /**
     * Create a new instance of {@link ParticipantRecyclerViewHolder}.
     *
     * @param itemView
     *         The {@link View} used to display an element on the UI.
     */
    public ParticipantRecyclerViewHolder(@NonNull View itemView) {

        super(itemView);

        this.uiMail  = itemView.findViewById(R.id.participant_name);
        this.uiCheck = itemView.findViewById(R.id.participant_checked);
    }

    /**
     * Retrieve the {@link TextView} to use to display the mail for a {@link User}.
     *
     * @return A {@link TextView}
     */
    public TextView getUiMail() {

        return this.uiMail;
    }

    /**
     * Retrieve the {@link CheckBox} to use to select a {@link User} in {@link BookingFragment}.
     *
     * @return A {@link CheckBox}
     */
    public CheckBox getUiCheck() {

        return this.uiCheck;
    }

}
