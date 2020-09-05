package com.challengeorama.orama.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainActivityBinding;
import com.challengeorama.orama.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        mBinding = MainActivityBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        if (savedInstanceState == null) {

            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavController navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        }
    }

    public void inject() {
        ((BaseApplication) getApplication()).appComponent.inject(this);
    }

}