package fr.alexpado.mareu.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.alexpado.mareu.R;
import fr.alexpado.mareu.data.BookingFragmentData;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.services.UserService;


/**
 * Class responsible for handling the UI of a {@link User} list.
 */
public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewHolder> {

    private final RecyclerView       view;
    private final List<User>         users         = new ArrayList<>();
    private final Map<User, Boolean> selectedUsers = new HashMap<>();

    /**
     * @param view
     *         The {@link RecyclerView} hosting the list of {@link User}.
     * @param service
     *         The {@link UserService} to use when fetching {@link User}.
     * @param data
     *         The {@link BookingFragmentData} used to initialise all check states.
     */
    public ParticipantRecyclerViewAdapter(RecyclerView view, UserService service, BookingFragmentData data) {

        this.view = view;
        this.users.addAll(service.getUsers());

        this.users.forEach(user -> this.selectedUsers.put(
                user,
                data.getParticipants().contains(user)
        ));

        this.view.setAdapter(this);
    }

    /**
     * Check if the provided {@link User} is checked in this instance.
     *
     * @param user
     *         The {@link User} to check
     *
     * @return True if checked, false otherwise.
     */
    public boolean isChecked(User user) {

        return Boolean.TRUE.equals(this.selectedUsers.get(user));
    }

    /**
     * Add the provided {@link User} to this instance and set its checked state.
     *
     * @param user
     *         The {@link User} to insert in the list
     * @param checked
     *         The default check state for the {@link User}
     */
    public void addUser(User user, boolean checked) {

        int pos = this.users.size();
        this.users.add(user);
        this.selectedUsers.put(user, checked);

        this.notifyItemInserted(pos);
        this.view.scrollToPosition(this.getItemCount() - 1);
    }

    /**
     * Set the check state for the provided {@link User} and notify the list of the change.
     *
     * @param user
     *         The {@link User} for which the check state will be updated.
     * @param newCheckedState
     *         The new check state for the {@link User}
     */
    public void setCheckedState(User user, boolean newCheckedState) {

        if (!this.selectedUsers.containsKey(user)) {
            return; // Use addUser instead.
        }
        if (newCheckedState == this.isChecked(user)) {
            return; // State is unchanged
        }

        int pos = this.users.indexOf(user);
        this.selectedUsers.replace(user, newCheckedState);
        this.notifyItemChanged(pos);
    }

    /**
     * Retrieve a {@link Set} of {@link User} that have their check state to {@code true}.
     *
     * @return A {@link Set} of {@link User}.
     */
    public Set<User> collectParticipants() {

        return this.users.stream().filter(this::isChecked).collect(Collectors.toSet());
    }

    @NonNull
    @Override
    public ParticipantRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.fragment_participants_item, parent, false);

        return new ParticipantRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ParticipantRecyclerViewHolder holder, int position) {

        User user = this.users.get(position);
        holder.getUiMail().setText(user.getMail());

        holder.getUiCheck()
              .setOnCheckedChangeListener((v, checked) ->
                                                  this.selectedUsers.replace(user, checked)
              );

        // Default state
        holder.getUiCheck().setChecked(this.isChecked(user));
        holder.getUiCheck().setContentDescription(holder.itemView.getResources().getString(R.string.accessibility_check_participant, user.getMail()));
    }

    @Override
    public int getItemCount() {

        return this.users.size();
    }

}