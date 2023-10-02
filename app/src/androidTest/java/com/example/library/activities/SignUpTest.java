package com.example.library.activities;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.library.R;
import com.example.library.activities.signUp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.view.View;

import java.util.Objects;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SignUpTest extends TestCase {

    @Rule
    public ActivityScenarioRule<signUp> activityRule = new ActivityScenarioRule<>(signUp.class);

    private Activity activity;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<signUp>() {
            @Override
            public void perform(signUp activity) {
                // This block allows us to get a reference to the activity
                SignUpTest.this.activity = activity;
            }
        });
    }

    @Test
    public void testValidate() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.userName)).perform(ViewActions.typeText("John Doe"));
        Espresso.onView(ViewMatchers.withId(R.id.userMail)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.createdPassword)).perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.confirmedPassword)).perform(ViewActions.typeText("password"));
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.signUpBtn)).perform(ViewActions.click());

        // Add assertions to verify the behavior of the validate method
        // You can use Espresso to check for Toast messages or other UI changes
        // Example:
        // Espresso.onView(ViewMatchers.withText("Enter Valid Name")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
         //Espresso.onView(ViewMatchers.withText("Enter Valid Email")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
