package com.challengeorama.orama.util;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class EspressoIdlingResourceRule extends TestWatcher {

    private String CLASS_NAME = "EspressoIdlingResourceRule";

    private CountingIdlingResource idlingResource = new EspressoIdlingResource().countingIdlingResource;

    @Override
    protected void finished(Description description) {
        IdlingRegistry.getInstance().unregister(idlingResource);
        super.finished(description);
    }

    @Override
    protected void starting(Description description) {
        IdlingRegistry.getInstance().register(idlingResource);
        super.starting(description);
    }
}
