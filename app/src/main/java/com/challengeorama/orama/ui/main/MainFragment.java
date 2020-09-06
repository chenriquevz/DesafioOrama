package com.challengeorama.orama.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainFragmentBinding;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;

    private MainViewModel mViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((BaseApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        subscribeObservers();
    }



    private void subscribeObservers() {

        mViewModel.getFundos().observe(getViewLifecycleOwner(), new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> fundos) {
                if (fundos != null) {
                    if (fundos.data != null) {
                        switch (fundos.status) {

                            case LOADING: {
                                Log.d("teste", "loading");
//                            showProgressBar(true);
                                break;
                            }

                            case ERROR: {
                                Log.d("teste", "ERROR");
//                            showParent();showProgressBar(false);
//                            setRecipeProperties(recipeResource.data);
                                break;
                            }

                            case SUCCESS: {
                                Log.d("teste", "SUCCESS");
//                            showParent();
//                            showProgressBar(false);
//                            setRecipeProperties(recipeResource.data);
                                break;
                            }
                        }

                    }

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}