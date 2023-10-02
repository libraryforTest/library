package com.example.library.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.library.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import junit.framework.TestCase;

@RunWith(AndroidJUnit4.class)
public class AddBooksTest extends TestCase {

    @Rule
    public ActivityScenarioRule<AddBooks> activityRule = new ActivityScenarioRule<>(AddBooks.class);

    private Activity activity;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<AddBooks>() {
            @Override
            public void perform(AddBooks activity) {
                // This block allows us to get a reference to the activity
                AddBooksTest.this.activity = activity;
            }
        });
        Intents.init();
    }
    @After
    public void tearDown() {

        Intents.release();
    }
    @Test
    public void testAddBook() throws InterruptedException {
        onView(withId(R.id.bookTitle)).perform(typeText("Test Book Title"));
        onView(withId(R.id.bookAuthor)).perform(typeText("Test Book Author"));
        onView(withId(R.id.isbn)).perform(typeText("1234567890"));
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);

        onView(withId(R.id.present)).perform(typeText("10"));
        Thread.sleep(2000);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);

        Thread.sleep(2000);
        onView(withText("Programming")).perform(click());
        onView(withText("Programming")).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText("Programming")));
        Thread.sleep(2000);
        onView(withId(R.id.description)).perform(typeText("Test Book Description"));
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);


        Thread.sleep(2000);
        onView(withId(R.id.addbookBtn)).perform(click());
        // Check if the intent for sharing the book is fired

    }
}
