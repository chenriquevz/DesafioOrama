package com.challengeorama.orama.util;

import androidx.test.espresso.idling.CountingIdlingResource;


public class EspressoIdlingResource {

    private String CLASS_NAME = "EspressoIdlingResource";

    private String RESOURCE = "GLOBAL";

    CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RESOURCE);

    public void increment() {
        countingIdlingResource.increment();
    }

    public void decrement() {
        if (!countingIdlingResource.isIdleNow()) {
            countingIdlingResource.decrement();
        }
    }

}
