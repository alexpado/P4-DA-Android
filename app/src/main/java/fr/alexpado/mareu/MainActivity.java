package fr.alexpado.mareu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
    }

}