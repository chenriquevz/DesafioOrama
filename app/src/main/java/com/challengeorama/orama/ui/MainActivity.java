package com.challengeorama.orama.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Bundle;
import android.view.View;


import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainActivityBinding;


public class MainActivity extends AppCompatActivity {

    MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mBinding = MainActivityBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        assert navController != null;

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                switch (destination.getId()) {

                    case R.id.mainFragment: {


                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        break;
                    }
                    case R.id.detailFragment: {

                        int color = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);

                        getWindow().setStatusBarColor(color);
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