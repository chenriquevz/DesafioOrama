package com.challengeorama.orama.ui.main;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.databinding.MainFragmentBinding;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private MainFragmentBinding mBinding;

    private MainViewModel mViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MainFragmentBinding.inflate(inflater, container, false);

        postponeEnterTransition();
        mBinding.mainFundosRecycler.getViewTreeObserver().addOnPreDrawListener(() -> {
            startPostponedEnterTransition();
            return true;
        });

        return mBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((BaseApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);

        Toolbar toolbar = mBinding.mainActionBar;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        mViewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        mViewModel.startSearchFundos();
        subscribeObservers();
    }


    private void subscribeObservers() {

        MainRecyclerAdapter adapter = new MainRecyclerAdapter();
        RecyclerView recyclerView = mBinding.mainFundosRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        mViewModel.getFundos().observe(getViewLifecycleOwner(), new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> fundos) {


                switch (fundos.status) {

                    case LOADING: {
                        showProgressBar(true);
                        break;
                    }

                    case ERROR: {
                        showProgressBar(false);
                        break;
                    }

                    case SUCCESS: {
                        if (fundos.data != null) {
                            showProgressBar(false);
                            adapter.submitList(fundos.data);
                        }

                        break;
                    }
                }


            }
        });

    }

    public void showProgressBar(boolean visibility) {
        mBinding.mainProgressbar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }


}