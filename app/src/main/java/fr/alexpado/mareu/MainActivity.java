package fr.alexpado.mareu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import fr.alexpado.mareu.views.MeetingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new MeetingFragment())
                .setReorderingAllowed(true)
                .commit();
        }

        this.setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = (MaterialToolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle("Main Page");
        }
    }

}