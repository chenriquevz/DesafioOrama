package com.challengeorama.orama.ui.main;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.challengeorama.orama.R;
import com.challengeorama.orama.TestBaseApplication;
import com.challengeorama.orama.di.TestAppComponent;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.FakeDataSource;
import com.challengeorama.orama.ui.BaseFragmentTest;
import com.challengeorama.orama.util.EspressoIdlingResourceRule;
import com.challengeorama.orama.util.FormatHelper;
import com.challengeorama.orama.util.JsonUITest;
import com.challengeorama.orama.util.RecyclerViewItemCountAssertion;
import com.challengeorama.orama.util.RecyclerViewMatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.challengeorama.orama.util.TestConstants.EMPTYJSON;
import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;
import static com.challengeorama.orama.util.TestConstants.NETWORK_ERROR_CACHELESS;

@RunWith(AndroidJUnit4.class)
public class MainFragmentTests extends BaseFragmentTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public EspressoIdlingResourceRule espressoIdlingResourceRule = new EspressoIdlingResourceRule();

    @Inject
    JsonUITest jsonUITest;

    @Inject
    FakeDataSource fakeDataSource;

    TestBaseApplication app = (TestBaseApplication) InstrumentationRegistry
            .getInstrumentation().getTargetContext().getApplicationContext();


    NavController navController = new TestNavHostController(app);

    @Before
    public void setup() {

        injectTest(app);

        navController.setGraph(R.navigation.main);

    }

    @Test
    public void mainFragment_checktitle_basicSwipes() {

        launchFragment();

        onView(withId(R.id.main_fundos_recycler)).perform(swipeUp());
        onView(withId(R.id.main_fundos_recycler)).perform(swipeDown());

        Assert.assertEquals(R.id.mainFragment, navController.getCurrentDestination().getId());
        onView(withText("Desafio Orama")).check(matches(isDisplayed()));

    }

    @Test
    public void mainFragment_navigation() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPosition(0)).perform(click());

        Assert.assertEquals(R.id.detailFragment, navController.getCurrentDestination().getId());
    }

    @Test
    public void mainFragment_recyclerview_count_and_checkdata() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));

        String profitabilityYear = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityYear = new FormatHelper().FloatToPercent(fundos.get(1).getProfitabilities().getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.yearProfitabilities_fundos))
                .check(matches(
                        withText(
                                app.getString(R.string.main_rentabilidade_anual,
                                        profitabilityYear)
                        )));


        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.fees_fundos))
                .check(matches(
                        withText(
                                app.getString(R.string.main_fees,
                                        fundos.get(1).getFees().getAdministrationFee())
                        )));

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.minimumInitialApplicationAmount_fundos))
                .check(matches(
                        withText(
                                app.getString(R.string.main_operability_minimumInitialApplicationAmount,
                                        new FormatHelper().FloatToReais(fundos.get(1).getOperability().getMinimumInitialApplicationAmount()))
                        )));


    }

    @Test
    public void mainFragment_errorHandling() {

        fakeDataSource.daoReturn = EMPTYJSON;
        fakeDataSource.apiReturn = NETWORK_ERROR_CACHELESS;

        launchFragment();

        onView(withId(R.id.error_container)).check(matches(isDisplayed()));
        onView(withId(R.id.title)).check(matches(withText(app.getString(R.string.main_error))));
        onView(withId(R.id.error_message)).check(matches(withText(app.getString(R.string.main_error_detail))));
        onView(withId(R.id.refresh_button)).check(matches(withText(app.getString(R.string.main_error_refresh))));

    }

    @Test
    public void mainFragment_filterOptions_basicUse() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        onView(withId(R.id.filter_linearLayout)).check(matches(isDisplayed()));

        //Close
        onView(withId(R.id.negative_button)).perform(click());

        onView(withId(R.id.filter_linearLayout)).check(doesNotExist());

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        onView(withId(R.id.filter_linearLayout)).check(matches(isDisplayed()));

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_profitability)).perform(click());

        //Clear
        onView(withId(R.id.clear_button)).perform(click());

        //Check Options
        onView(withId(R.id.filter_asc)).check(matches(isNotChecked()));
        onView(withId(R.id.filter_profitability)).check(matches(isNotChecked()));

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_profitability)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert
        onView(withId(R.id.filter_linearLayout)).check(doesNotExist());
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

    }

    @Test
    public void mainFragment_filterOptions_minimumAmount() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_minimumamount)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert minimum amount ASC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t2.getOperability().getMinimumInitialApplicationAmount(), t1.getOperability().getMinimumInitialApplicationAmount());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_desc)).perform(click());
        onView(withId(R.id.filter_minimumamount)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert minimum amount DESC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t1.getOperability().getMinimumInitialApplicationAmount(), t2.getOperability().getMinimumInitialApplicationAmount());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


    }

    @Test
    public void mainFragment_filterOptions_date() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_date)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert date ASC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t2.getInitialDate().compareTo(t1.getInitialDate());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_desc)).perform(click());
        onView(withId(R.id.filter_date)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert date DESC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t1.getInitialDate().compareTo(t2.getInitialDate());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


    }

    @Test
    public void mainFragment_filterOptions_profitabilityYear() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_profitability)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert profitability ASC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t2.getProfitabilities().getYear(), t1.getProfitabilities().getYear());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_desc)).perform(click());
        onView(withId(R.id.filter_profitability)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert profitability DESC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t1.getProfitabilities().getYear(), t2.getProfitabilities().getYear());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


    }

    @Test
    public void mainFragment_filterOptions_Name() {

        launchFragment();

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);


        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_asc)).perform(click());
        onView(withId(R.id.filter_name)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert Name ASC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t2.getSimpleName().compareTo(t1.getSimpleName());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


        //Open
        onView(withId(R.id.action_filter_settings)).perform(click());

        //Click Options
        onView(withId(R.id.filter_desc)).perform(click());
        onView(withId(R.id.filter_name)).perform(click());

        //Confirm
        onView(withId(R.id.positive_button)).perform(click());

        //Assert name DESC
        onView(withId(R.id.main_fundos_recycler)).check(new RecyclerViewItemCountAssertion(fundos.size()));

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t1.getSimpleName().compareTo(t2.getSimpleName());
            }
        });

        onView(new RecyclerViewMatcher(R.id.main_fundos_recycler).atPositionOnView(1, R.id.name_fundos))
                .check(matches(withText(fundos.get(1).getSimpleName())));


    }

    private void launchFragment() {

        FragmentScenario.launchInContainer(
                MainFragment.class, null, R.style.AppTheme, new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                        MainFragment fragment = new MainFragment();

                        fragment.getViewLifecycleOwnerLiveData().observeForever(new Observer<LifecycleOwner>() {
                            @Override
                            public void onChanged(LifecycleOwner viewLifecycleOwner) {

                                // The fragment’s view has just been created
                                if (viewLifecycleOwner != null) {
                                    Navigation.setViewNavController(fragment.requireView(), navController);
                                }

                            }
                        });
                        return fragment;
                    }
                });

    }

    @Override
    public void injectTest(TestBaseApplication application) {
        ((TestAppComponent) application.appComponent).inject(this);
    }
}
