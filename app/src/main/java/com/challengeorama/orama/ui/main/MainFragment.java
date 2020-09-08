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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainFragmentBinding;
import com.challengeorama.orama.model.Option;
import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private MainFragmentBinding mBinding;

    private MainViewModel mViewModel;

    private MainRecyclerAdapter mAdapter;

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
        toolbar.inflateMenu(R.menu.main_menu);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_filter_settings) {
                    showFilterDialog();
                    return true;

                }
                return false;
            }
        });


        mViewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        subscribeObservers();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    private void subscribeObservers() {

        mAdapter = new MainRecyclerAdapter();
        RecyclerView recyclerView = mBinding.mainFundosRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(null);


        mViewModel.getFundosFiltered().observe(getViewLifecycleOwner(), new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> fundos) {

                switch (fundos.status) {

                    case LOADING: {
                        showProgressBar(true);
                        break;
                    }

                    case ERROR: {
                        showProgressBar(false);
                        showErrorDialog();
                        break;
                    }

                    case SUCCESS: {
                        if (fundos.data != null) {
                            showProgressBar(false);
                            mAdapter.submitList(fundos.data);
                        }
                        break;
                    }
                }


            }
        });

    }

    private void showFilterDialog() {

        MaterialDialog dialog = new MaterialDialog(requireActivity(), MaterialDialog.getDEFAULT_BEHAVIOR())
                .noAutoDismiss();

        dialog.getView().contentLayout.addCustomView(R.layout.main_filter, null, true, false, false);


        View view = dialog.getView().contentLayout.getCustomView();
        RadioGroup filterGroup = view.findViewById(R.id.filter_group);
        RadioGroup orderGroup = view.findViewById(R.id.filter_order_group);

        ListDataOptions filterOptions = mViewModel.getListDataOptions().getValue();

        if (filterOptions.getActive()) {

            if (filterOptions.getSort() == Sort.ASC) {
                orderGroup.check(R.id.filter_asc);
            } else {
                orderGroup.check(R.id.filter_desc);
            }

            switch (filterOptions.getOption()) {
                case Date: {
                    filterGroup.check(R.id.filter_date);
                    break;
                }
                case Name: {
                    filterGroup.check(R.id.filter_name);
                    break;
                }
                case MinimumAmount: {
                    filterGroup.check(R.id.filter_minimumamount);
                    break;
                }
                case profitabilityYear: {
                    filterGroup.check(R.id.filter_profitability);
                    break;
                }


            }

        }

        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mAdapter.submitList(null);
                showProgressBar(true);

                switch (filterGroup.getCheckedRadioButtonId()) {
                    case R.id.filter_date: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.ASC, Option.Date));
                        } else {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.DSC, Option.Date));
                        }

                        dialog.dismiss();
                        break;

                    }
                    case R.id.filter_minimumamount: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.ASC, Option.MinimumAmount));
                        } else {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.DSC, Option.MinimumAmount));
                        }
                        dialog.dismiss();
                        break;
                    }
                    case R.id.filter_name: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.ASC, Option.Name));
                        } else {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.DSC, Option.Name));
                        }
                        dialog.dismiss();
                        break;

                    }
                    case R.id.filter_profitability: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.ASC, Option.profitabilityYear));
                        } else {
                            mViewModel.setListDataOptions(new ListDataOptions(true, Sort.DSC, Option.profitabilityYear));
                        }
                        dialog.dismiss();
                        break;

                    }
                    default: {
                        mViewModel.setListDataOptions(new ListDataOptions(false, Sort.NONE, Option.NONE));
                        dialog.dismiss();
                        break;
                    }

                }

            }
        });

        view.findViewById(R.id.negative_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        view.findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                filterGroup.clearCheck();
                orderGroup.clearCheck();

            }
        });


        dialog.show();
    }

    private void showErrorDialog() {

        MaterialDialog dialog = new MaterialDialog(requireActivity(), MaterialDialog.getDEFAULT_BEHAVIOR())
                .noAutoDismiss();

        dialog.getView().contentLayout.addCustomView(R.layout.main_error, null, true, false, false);


        View view = dialog.getView().contentLayout.getCustomView();

        view.findViewById(R.id.refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewModel.forceRefresh();
                dialog.dismiss();

            }
        });

        dialog.show();

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