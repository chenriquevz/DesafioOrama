package com.challengeorama.orama.ui;

import android.app.Activity;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.challengeorama.orama.R;
import com.challengeorama.orama.TestBaseApplication;
import com.challengeorama.orama.di.TestAppComponent;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.util.EspressoIdlingResourceRule;
import com.challengeorama.orama.util.FormatHelper;
import com.challengeorama.orama.util.JsonUITest;
import com.challengeorama.orama.util.RecyclerViewItemCountAssertion;
import com.challengeorama.orama.util.RecyclerViewMatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;

@RunWith(AndroidJUnit4.class)
public class EndtoEndNavigationTests extends BaseFragmentTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public EspressoIdlingResourceRule espressoIdlingResourceRule = new EspressoIdlingResourceRule();

    @Inject
    JsonUITest jsonUITest;

    TestBaseApplication app = (TestBaseApplication) InstrumentationRegistry
            .getInstrumentation().getTargetContext().getApplicationContext();

    @Before
    public void setup() {

        injectTest(app);

    }

    @Test
    public void endToend() {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        ActivityScenario.launch(MainActivity.class);

        //Basic check
        onView(withText("Desafio Orama")).check(matches(isDisplayed()));
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Check First entry
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(0, R.id.name_fundos))
                .check(matches(withText(fundos.get(0).getSimpleName())));

        //Navigate to details
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPosition(0)).perform(click());

        //Check Basic info
        onView(withId(R.id.appBar_title)).check(matches(withText(fundos.get(0).getSimpleName())));
        onView(withId(R.id.linearLayout)).perform(swipeUp());
        onView(withId(R.id.detail_fundmanager_description)).check(matches(withText(fundos.get(0).getFundManager().getDescription())));

        //Navigate back
        pressBack();

        //Basic check
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(0, R.id.name_fundos))
                .check(matches(withText(fundos.get(0).getSimpleName())));

        //Open filter
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_minimumamount)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t2.getOperability().getMinimumInitialApplicationAmount(), t1.getOperability().getMinimumInitialApplicationAmount());
            }
        });

        //Check new order
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


        //Navigate to details
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPosition(1)).perform(click());

        //Check Basic info
        onView(withId(R.id.appBar_title)).check(matches(withText(fundos.get(1).getSimpleName())));
        onView(withId(R.id.linearLayout)).perform(swipeUp());
        onView(withId(R.id.detail_fundmanager_description)).check(matches(withText(fundos.get(1).getFundManager().getDescription())));

        //Navigate back
        pressBack();


        //last check
        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));
        onView(withText("Desafio Orama")).check(matches(isDisplayed()));
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));



    }

    @Override
    public void injectTest(TestBaseApplication application) {
        ((TestAppComponent) application.appComponent).inject(this);
    }
}
