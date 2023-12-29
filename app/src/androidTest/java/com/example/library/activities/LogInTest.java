package com.example.library.activities;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.ecomarket.activities.LogIn;
import com.example.library.R;


import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)

public class LogInTest extends TestCase {

    @Rule
    public ActivityScenarioRule<LogIn> activityRule = new ActivityScenarioRule<>(LogIn.class);

    private Activity activity;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<LogIn>() {
            @Override
            public void perform(LogIn activity) {
                // This block allows us to get a reference to the activity
                LogInTest.this.activity = activity;
            }
        });
    }

    @Test
    public void testGetData() {
        // Perform actions on the UI elements
        Espresso.onView(ViewMatchers.withId(R.id.luserMail)).perform(ViewActions.typeText("adad@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.luserPassword)).perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.mainlogInBtn)).perform(ViewActions.click());

        // Wait for a moment (you may need to customize this wait time)


        // You can add assertions here to check the result of your action, such as checking if the activity changed as expected
        // Example:
        // Espresso.onView(ViewMatchers.withId(R.id.some_element_in_next_activity)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
