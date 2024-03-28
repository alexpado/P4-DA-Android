package fr.alexpado.mareu.utils;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AutoCompleteTextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TestUtils {

    public static ViewAssertion expectedItemCount(int expected) {

        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView            recyclerView = (RecyclerView) view;
            RecyclerView.Adapter<?> adapter      = recyclerView.getAdapter();
            assert adapter != null;
            assertEquals(expected, adapter.getItemCount());
        };
    }

    public static ViewAction showDropdown() {

        return new ViewAction() {
            @Override
            public String getDescription() {

                return "Shows the dropdown menu of an AutoCompleteTextView";
            }

            @Override
            public Matcher<View> getConstraints() {

                return allOf(isEnabled(), isAssignableFrom(AutoCompleteTextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {

                ((AutoCompleteTextView) view).showDropDown();
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {

                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
