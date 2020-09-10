package com.challengeorama.orama.ui.detail;

import android.os.Bundle;

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
import com.challengeorama.orama.util.FormatHelper;
import com.challengeorama.orama.util.JsonUITest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class DetailFragmentTests extends BaseFragmentTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

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
    public void detailFragment_infoLoads() {

        int fundoId = 15;

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        Fundos fundo = null;
        for (Fundos item: fundos) {
            if (item.getId() == fundoId) {
                fundo = item;
            }
        }

        launchFragment(fundoId);



        onView(withId(R.id.appBar_title)).check(matches(withText(fundo.getSimpleName())));

        onView(withId(R.id.detail_fees)).check(matches(withText(app.getString(R.string.main_fees,
                fundo.getFees().getAdministrationFee()))));
        onView(withId(R.id.detail_fundo_initialDate))
                .check(matches(withText(app.getString(R.string.detail_fundo_initialDate,
                        new FormatHelper().StringToDateString(fundo.getInitialDate())))));

        onView(withId(R.id.detail_fund_description)).check(matches(withText(fundo.getDescription().getObjective())));
        onView(withId(R.id.detail_fundmanager_name)).check(matches(withText(fundo.getFundManager().getFullName())));
        onView(withId(R.id.detail_fundmanager_description)).check(matches(withText(fundo.getFundManager().getDescription())));

        onView(withId(R.id.detail_minimumAmount))
                .check(matches(withText(app.getString(R.string.main_operability_minimumInitialApplicationAmount,
                        new FormatHelper().FloatToReais(fundo.getOperability().getMinimumInitialApplicationAmount())))));

        onView(withId(R.id.detail_operability_minimumBalance))
                .check(matches(withText(app.getString(R.string.main_operability_minimumBalance,
                        new FormatHelper().FloatToReais(fundo.getOperability().getMinimumBalancePermanence())))));


        String profitabilityMonth = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityMonth = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_month)).check(matches(withText(app.getString(R.string.detail_rentabilidade_mensal,
                profitabilityMonth))));

        String profitabilityDay = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityDay = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getDay());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_day)).check(matches(withText(app.getString(R.string.detail_rentabilidade_diaria,
                profitabilityDay))));

        String profitabilityYear = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityYear = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_year)).check(matches(withText(app.getString(R.string.detail_rentabilidade_anual,
                profitabilityYear))));


        String profitabilityM12 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM12 = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getM12());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_m12)).check(matches(withText(app.getString(R.string.detail_rentabilidade_12m,
                profitabilityM12))));

        String profitabilityM24 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM24 = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getM24());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_m24)).check(matches(withText(app.getString(R.string.detail_rentabilidade_24m,
                profitabilityM24))));

        String profitabilityM36 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM36 = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_m36)).check(matches(withText(app.getString(R.string.detail_rentabilidade_36m,
                profitabilityM36))));

        String profitabilityM48 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM48 = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_m48)).check(matches(withText(app.getString(R.string.detail_rentabilidade_48m,
                profitabilityM48))));


        String profitabilityM60 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM60 = new FormatHelper().FloatToPercent(fundo.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.detail_profitability_m60)).check(matches(withText(app.getString(R.string.detail_rentabilidade_60m,
                profitabilityM60))));

    }

    @Test
    public void detailFragment_swipe() {

        int fundoId = 15;

        launchFragment(fundoId);

        onView(withId(R.id.linearLayout)).perform(swipeUp());

        onView(withId(R.id.appBar_title)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_fees)).check(matches(not(isDisplayed())));
        onView(withId(R.id.detail_fundo_initialDate)).check(matches(not(isDisplayed())));
        onView(withId(R.id.detail_operability_minimumBalance)).check(matches(not(isDisplayed())));

        //BUG do espresso. not(isDisplayed) não funciona, ele insiste que a view está visível quando não está.
        onView(withId(R.id.detail_minimumAmount)).check(matches(not(isDisplayingAtLeast(20))));
    }


    private void launchFragment(int fundoID) {

        Bundle bundle = new Bundle();
        bundle.putInt("fundoID", fundoID);

        FragmentScenario.launchInContainer(
                DetailFragment.class, bundle, R.style.AppTheme, new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                        DetailFragment fragment = new DetailFragment();

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
