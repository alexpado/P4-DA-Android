package fr.alexpado.mareu.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import fr.alexpado.mareu.AppUtils;
import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.databinding.MeetingFragmentBinding;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.events.MeetingDeleteClicked;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.views.adapters.MeetingRecyclerViewAdapter;

public class MeetingFragment extends Fragment implements MeetingDeleteClicked {

    private MeetingFragmentBinding binding;
    private MeetingService         service;

    /**
     * Reset the RecyclerView content.
     */
    private void initList() {

        this.binding.meetingListView.setAdapter(new MeetingRecyclerViewAdapter(
                this,
                this.service.getAll()
        ));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        this.service = InjectionStore.meetingService();
    }

    @Override
    public void onResume() {

        super.onResume();
        ActionBar bar = AppUtils.getBarFrom(this);
        if (bar != null) {
            bar.setSubtitle(R.string.fragment_meetings);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.binding = MeetingFragmentBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Context context = this.binding.meetingListView.getContext();

        this.binding.meetingListView.setLayoutManager(new LinearLayoutManager(context));
        this.binding.meetingListView.addItemDecoration(new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        ));

        view.findViewById(R.id.meeting_action_new).setOnClickListener(v -> {
            this.getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new BookingFragment())
                .addToBackStack("add")
                .commit();
        });

        this.initList();
    }

    @Override
    public void onMeetingDeleted(Meeting meeting) {

        this.service.remove(meeting);
        this.initList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_filter_button) {
            this.getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FilterFragment())
                .addToBackStack("filter")
                .commit();
        }
        return super.onOptionsItemSelected(item);
    }

}
