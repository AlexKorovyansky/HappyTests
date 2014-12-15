/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev.androidtesting;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isEnabled;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

public class SimpleTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MutableEndpoint mutableEndpoint;

    public SimpleTests() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mutableEndpoint = ServiceProvider.getInstance().getMutableEndpoint();
    }

    @SmallTest
    public void testInitialState() {
        getActivity();
        onView(withId(R.id.name))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.surname))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.register))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
    }

    @MediumTest
    public void testEndToEndSuccess() throws IOException {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200));
        server.play();
        try {
            mutableEndpoint.setUrl(server.getUrl("").toString());
            getActivity();
            onView(withId(R.id.name))
                    .perform(ViewActions.typeText("Petr"));
            onView(withId(R.id.surname))
                    .perform(ViewActions.typeText("Ivanov"), closeSoftKeyboard());
            onView(withId(R.id.register))
                    .perform(click());
            onView(withText("Congrats!"))
                    .check(matches(isDisplayed()));
        } finally {
            server.shutdown();
        }
    }

    @MediumTest
    public void testEndToEndFail() throws IOException {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(300));
        server.play();
        try {
            mutableEndpoint.setUrl(server.getUrl("").toString());
            getActivity();
            onView(withId(R.id.name))
                    .perform(ViewActions.typeText("Ivan"));
            onView(withId(R.id.surname))
                    .perform(ViewActions.typeText("Petrov"), closeSoftKeyboard());
            onView(withId(R.id.register))
                    .perform(click());
            onView(withText("Please try again later."))
                    .check(matches(isDisplayed()));
        } finally {
            server.shutdown();
        }
    }

}
