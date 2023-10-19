package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;

/**
 * Class representing a UI element within {@link ParticipantRecyclerViewAdapter}. This contains
 * references to UI components for easier access.
 */
public class ParticipantRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView uiMail;
    private CheckBox uiCheck;

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

    public TextView getUiMail() {

        return this.uiMail;
    }

    public CheckBox getUiCheck() {

        return this.uiCheck;
    }

}
