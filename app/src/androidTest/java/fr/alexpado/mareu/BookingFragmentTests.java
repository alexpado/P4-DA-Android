package fr.alexpado.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static fr.alexpado.mareu.utils.TestUtils.childAtPosition;
import static fr.alexpado.mareu.utils.TestUtils.expectedItemCount;
import static fr.alexpado.mareu.utils.TestUtils.showDropdown;

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

    private String roomName;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Before
    public void setup() {

        RoomService roomService = InjectionStore.roomService();
        this.roomName = roomService.getRooms().get(0).getName();

        onView(withId(R.id.meeting_list_view)).check(matches(isDisplayed()));

        onView(withId(R.id.meeting_action_new))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void fillOutEverything_shouldWork() {

        onView(withId(R.id.add_meeting_subject)).perform(typeText("Unit Test"));

        onView(withId(R.id.add_meeting_room)).perform(showDropdown());

        onView(withText(this.roomName)).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.add_meeting_time_set)).perform(click());

        onView(allOf(
                withId(com.google.android.material.R.id.material_timepicker_ok_button),
                withText("OK"),
                childAtPosition(childAtPosition(withId(android.R.id.content), 0), 6),
                isDisplayed()
        )).perform(click());

        onView(withId(R.id.add_participant_mail))
                .perform(typeText("unit-1@test.com"))
                .perform(pressImeActionButton());

        onView(withId(R.id.add_participant_mail))
                .perform(typeText("unit-2@test.com"))
                .perform(pressImeActionButton());

        onView(withId(R.id.add_meeting_validate)).perform(click());
        onView(withId(R.id.meeting_list_view)).check(expectedItemCount(1));
    }

}
