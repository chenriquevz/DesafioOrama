package com.challengeorama.orama.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainActivityBinding;


public class MainActivity extends AppCompatActivity {

    MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        mBinding = MainActivityBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        Toolbar toolbar = mBinding.mainActionBar;
        assert navController != null;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                switch (destination.getId()) {

                    case R.id.mainFragment: {

                        int color = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
                        ColorDrawable colorDrawable = new ColorDrawable(color);

                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        mBinding.mainActionBar.setBackground(colorDrawable);
                        break;
                    }
                    case R.id.detailFragment: {

                        int color = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
                        ColorDrawable colorDrawable = new ColorDrawable(color);

                        getWindow().setStatusBarColor(color);
                        mBinding.mainActionBar.setBackground(colorDrawable);
                        break;
                    }
                }

            }
        });


    }

    void inject() {
        ((BaseApplication) getApplication()).appComponent.inject(this);
    }

}