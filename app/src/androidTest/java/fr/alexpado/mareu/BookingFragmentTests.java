package fr.alexpado.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static fr.alexpado.mareu.utils.TestUtils.showDropdown;

import android.view.KeyEvent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.alexpado.mareu.services.RoomService;

@RunWith(AndroidJUnit4.class)
public class BookingFragmentTests {

    private CountingIdlingResource idlingResource;
    private String                 roomName;

    private void run(Runnable action) {

        this.idlingResource.decrement();
        action.run();
        this.idlingResource.increment();
    }


    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Before
    public void setup() {

        Intents.init();

        this.idlingResource = new CountingIdlingResource("huh");
        this.idlingResource.increment();
        IdlingRegistry.getInstance().register(this.idlingResource);

        run(() -> {
            RoomService roomService = InjectionStore.roomService();
            this.roomName = roomService.getRooms().get(0).getName();
        });

        run(() ->
                    onView(withId(R.id.meeting_list_view)).check(matches(isDisplayed()))
        );

        run(() ->
                    onView(withId(R.id.meeting_action_new))
                            .check(matches(isDisplayed()))
                            .perform(click())
        );

    }

    @After
    public void tearDown() {

        IdlingRegistry.getInstance().unregister(this.idlingResource);
        Intents.release();
    }

    @Test
    public void fillOutEverything_shouldWork() throws InterruptedException {

        run(() ->
                    onView(withId(R.id.add_meeting_subject)).perform(typeText("Unit Test"))
        );

        run(() ->
                    onView(withId(R.id.add_meeting_room)).perform(showDropdown())
        );

        run(() ->
                    onView(withId(R.id.add_meeting_room)).perform(showDropdown())
        );

        run(() ->
                    onView(withText(this.roomName)).inRoot(isPlatformPopup()).perform(click())
        );

        run(() ->
                    onView(withId(R.id.add_meeting_time)).perform(click())
        );

        // helpWith(theTimePicker).becauseIDontKnow(howToSetTheTime)

        run(() ->
                    onView(withId(R.id.add_participant_mail))
                            .perform(typeText("unit-1@test.com"))
                            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        );
        run(() ->
                    onView(withId(R.id.add_participant_mail))
                            .perform(typeText("unit-2@test.com"))
                            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        );

        run(() ->
                    onView(withId(R.id.add_meeting_validate)).perform(click())
        );

        Thread.sleep(2000);
    }

}
