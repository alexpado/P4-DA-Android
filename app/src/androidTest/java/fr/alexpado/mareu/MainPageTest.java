package fr.alexpado.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static fr.alexpado.mareu.utils.TestUtils.expectedItemCount;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.RoomService;
import fr.alexpado.mareu.services.UserService;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainPageTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Test
    public void newMeetingFab_displayFragment() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.meeting_action_new)).perform(click());
        onView(withId(R.id.fragment_booking)).check(matches(isDisplayed()));
    }

    @Test
    public void filterActionButton_displayFragment() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.menu_filter_button)).perform(click());
        onView(withId(R.id.fragment_filter)).check(matches(isDisplayed()));
    }

    @Test
    public void removeMeeting() {

        RoomService    roomService    = InjectionStore.roomService();
        MeetingService meetingService = InjectionStore.meetingService();
        UserService    userService    = InjectionStore.userService();

        LocalTime time = LocalTime.of(0, 0);
        Room      room = roomService.getRooms().get(0);

        meetingService.book(room, time, "Test", userService.getUsers());

        onView(withId(R.id.meeting_list_view)).check(expectedItemCount(1));
        // Find a way to refresh the display, because for some reason, items aren't displayed but
        // the previous line doesn't fail.
        onView(withId(R.id.meeting_list_item_action_delete)).perform(click());
        onView(withId(R.id.meeting_list_view)).check(expectedItemCount(0));
    }

}