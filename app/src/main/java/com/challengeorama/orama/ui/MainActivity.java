package com.challengeorama.orama.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainActivityBinding;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.ui.main.MainViewModel;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    MainActivityBinding mBinding;

    private MainViewModel mViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

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


    }

    void inject() {
        ((BaseApplication) getApplication()).appComponent.inject(this);
    }

}