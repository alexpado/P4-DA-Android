package fr.alexpado.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static fr.alexpado.mareu.services.MeetingService.FILTER_ROOM_NAME;
import static fr.alexpado.mareu.utils.TestUtils.expectedItemCount;
import static fr.alexpado.mareu.utils.TestUtils.pause;
import static fr.alexpado.mareu.utils.TestUtils.showDropdown;

import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

import fr.alexpado.mareu.entities.Room;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.RoomService;
import fr.alexpado.mareu.services.UserService;

@RunWith(AndroidJUnit4.class)
public class FilterFragmentTests {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    private Room      roomA;
    private Room      roomB;
    private LocalTime timeA;
    private LocalTime timeB;

    @Before
    public void setup() throws InterruptedException {

        pause();

        RoomService    roomService    = InjectionStore.roomService();
        MeetingService meetingService = InjectionStore.meetingService();
        UserService    userService    = InjectionStore.userService();

        this.timeA = LocalTime.of(0, 0);
        this.timeB = LocalTime.of(1, 0);
        this.roomA = roomService.getRooms().get(0);
        this.roomB = roomService.getRooms().get(1);

        // Add 4 meetings
        meetingService.book(this.roomA, this.timeA, "TestA", userService.getUsers());
        meetingService.book(this.roomA, this.timeB, "TestB", userService.getUsers());
        meetingService.book(this.roomB, this.timeA, "TestC", userService.getUsers());
        meetingService.book(this.roomB, this.timeB, "TestD", userService.getUsers());

        // Navigate to the filter menu
        onView(withId(R.id.menu_filter_button)).perform(click());
        // Check if we successfully navigated
        onView(withId(R.id.fragment_filter)).check(matches(isDisplayed()));
    }

    private void roomFilter(Room room) throws InterruptedException {
        // Show the dropdown displaying all rooms
        onView(withId(R.id.filter_meeting_room)).perform(showDropdown());

        // Click the element having the same name as room a
        onView(withText(room.getName()))
                .inRoot(isPlatformPopup()) // https://stackoverflow.com/a/45368345/12073017
                .perform(click());

        pause();

        Assert.assertTrue(InjectionStore.meetingService().hasFilter(FILTER_ROOM_NAME));

        // Press the back button - Returning to the meeting list
        Espresso.pressBack();

        // Check if we successfully navigated
        onView(withId(R.id.fragment_meeting)).check(matches(isDisplayed()));

        // Check the number of item - should be 2 in Room A
        onView(withId(R.id.meeting_list_view)).check(expectedItemCount(2));
    }

    private void timeFilter(LocalTime time) throws InterruptedException {

        onView(withId(R.id.filter_meeting_time)).perform(click());

        onView(ViewMatchers.withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .inRoot(isPlatformPopup())
                .perform(PickerActions.setTime(12, 0));

        pause();
    }

    @Test
    public void roomFilter_filterOnRoomA() throws InterruptedException {

        this.roomFilter(this.roomA);
    }

    @Test
    public void roomFilter_filterOnRoomB() throws InterruptedException {

        this.roomFilter(this.roomB);
    }

    @Test
    public void roomFilter_removeFilter() throws InterruptedException {
        // Show the dropdown displaying all rooms
        onView(withId(R.id.filter_meeting_room)).perform(showDropdown());

        // Click the element having the same name as room a
        onView(withText(this.roomA.getName()))
                .inRoot(isPlatformPopup()) // https://stackoverflow.com/a/45368345/12073017
                .perform(click());

        pause();

        Assert.assertTrue(InjectionStore.meetingService().hasFilter(FILTER_ROOM_NAME));

        onView(withId(R.id.unset_room_filter)).perform(click());

        Assert.assertFalse(InjectionStore.meetingService().hasFilter(FILTER_ROOM_NAME));
    }

    @Test
    public void timeFilter_filterOnTimeA() throws InterruptedException {

        this.timeFilter(this.timeA);
    }

    @Test
    public void timeFilter_filterOnTimeB() throws InterruptedException {

        this.timeFilter(this.timeB);
    }

}
