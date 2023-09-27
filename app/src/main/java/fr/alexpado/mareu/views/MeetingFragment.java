package fr.alexpado.mareu.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.R;
import fr.alexpado.mareu.entities.Meeting;
import fr.alexpado.mareu.events.MeetingDeleteClicked;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.views.adapters.MeetingRecyclerViewAdapter;

public class MeetingFragment extends Fragment implements MeetingDeleteClicked {

    private MeetingService service;
    private RecyclerView   recyclerView;

    private void initList() {

        this.recyclerView.setAdapter(new MeetingRecyclerViewAdapter(this, this.service.getAll()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.service = InjectionStore.meetingService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.meeting_fragment,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.recyclerView = view.findViewById(R.id.meeting_list_view);

        Context context = this.recyclerView.getContext();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(
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

}
