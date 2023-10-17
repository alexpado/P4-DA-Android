package fr.alexpado.mareu.views.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.R;

public class ParticipantRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView uiMail;
    private CheckBox uiCheck;

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
