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
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.orchestrator.junit.BundleJUnitUtils;
import androidx.test.platform.app.InstrumentationRegistry;

import com.challengeorama.orama.R;
import com.challengeorama.orama.TestBaseApplication;
import com.challengeorama.orama.di.TestAppComponent;
import com.challengeorama.orama.repository.FakeDataSource;
import com.challengeorama.orama.ui.BaseFragmentTest;
import com.challengeorama.orama.ui.main.MainFragment;
import com.challengeorama.orama.ui.main.MainFragmentDirections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailFragmentTests extends BaseFragmentTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

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

        launchFragment(15);

        Espresso.onView(withId(R.id.linearLayout)).perform(swipeUp());


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
