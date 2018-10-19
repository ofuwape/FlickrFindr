package com.example.tfuwape.flickrfindr.activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.adapters.PhotoSearchAdapter;
import com.example.tfuwape.flickrfindr.adapters.SuggestionAdapter;
import com.example.tfuwape.flickrfindr.util.BaseActivityTestRule;

import org.assertj.core.api.Assertions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.awaitility.Awaitility.await;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {


    private MainActivity mainActivity;

    @Rule
    public BaseActivityTestRule mActivityRule = new BaseActivityTestRule(MainActivity.class) {

        @Override
        protected void afterActivityLaunched() {
            mainActivity = (MainActivity) getActivity();
        }

    };

    @Test
    public void testDefaultViews() {
        onView(withId(R.id.searchRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
        Assertions.assertThat(mainActivity).isNotNull();
    }

    @Test
    public void testDefaultAdapters() {
        SuggestionAdapter suggestionAdapter = mainActivity.suggestionAdapter;
        Assertions.assertThat(suggestionAdapter).isNotNull();
        Assertions.assertThat(suggestionAdapter.getItemCount()).isEqualTo(0);

        PhotoSearchAdapter photoSearchAdapter = mainActivity.photoSearchAdapter;
        Assertions.assertThat(photoSearchAdapter).isNotNull();
        Assertions.assertThat(photoSearchAdapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void testSearchViewWithText() {
        final String mQuery = "curious";
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(mQuery), closeSoftKeyboard());
        // Check that the text was changed.
        SearchView searchView = mainActivity.findViewById(R.id.searchView);
        Assertions.assertThat(searchView).isNotNull();
        Assertions.assertThat(searchView.getQuery().toString()).isEqualTo(mQuery);
    }

    @Test
    public void testSearchResults() {
        final String mQuery = "curious";
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(mQuery), closeSoftKeyboard());
        PhotoSearchAdapter photoSearchAdapter = mainActivity.photoSearchAdapter;
        Assertions.assertThat(photoSearchAdapter).isNotNull();
        await().until(searchResultsAreLoaded());
    }

    @Test
    public void testSuggestionResults() {
        final String mQuery = "curious";
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(mQuery), closeSoftKeyboard());

        await().until(searchResultsAreLoaded());

        //clear text
        mainActivity.searchView.setQuery("", false);
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(""), closeSoftKeyboard());

        SuggestionAdapter suggestionAdapter = mainActivity.suggestionAdapter;
        Assertions.assertThat(suggestionAdapter).isNotNull();
        await().until(suggestionResultsAreLoaded());

    }

    private Callable<Boolean> searchResultsAreLoaded() {
        return new Callable<Boolean>() {
            public Boolean call() {
                PhotoSearchAdapter photoSearchAdapter = mainActivity.photoSearchAdapter;
                return photoSearchAdapter.getItemCount() > 0; // The condition that must be fulfilled
            }
        };
    }

    private Callable<Boolean> suggestionResultsAreLoaded() {
        return new Callable<Boolean>() {
            public Boolean call() {
                SuggestionAdapter suggestionAdapter = mainActivity.suggestionAdapter;
                return suggestionAdapter.getItemCount() > 0; // The condition that must be fulfilled
            }
        };
    }

    private Callable<Boolean> suggestionResultsAreVisible() {
        return new Callable<Boolean>() {
            public Boolean call() {
                RecyclerView recyclerView = mainActivity.searchRecyclerView;
                return recyclerView.getChildCount() > 0; // The condition that must be fulfilled
            }
        };
    }

    @Test
    public void testDetailDialog() {
        final String mQuery = "curious";
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(mQuery), closeSoftKeyboard());
        await().until(suggestionResultsAreVisible());

        onView(nthChildOf(withId(R.id.searchRecyclerView), 0)).perform(click());

        onView(withId(R.id.detail_page)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_image_thumbnail)).check(matches(isDisplayed()));
        onView(withId(R.id.closeButton)).check(matches(isDisplayed()));

    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }

    @Test
    public void testPagination() {
        final String mQuery = "curious";
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(mQuery), closeSoftKeyboard());
        await().until(suggestionResultsAreVisible());

        onView(withId(R.id.searchRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(20));

        await().until(secondPageIsVisible());

    }

    private Callable<Boolean> secondPageIsVisible() {
        return new Callable<Boolean>() {
            public Boolean call() {
                PhotoSearchAdapter searchAdapter = mainActivity.photoSearchAdapter;
                return searchAdapter.getItemCount() > 25; // The condition that must be fulfilled
            }
        };
    }


}
