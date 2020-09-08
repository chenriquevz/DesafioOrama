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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainFragmentBinding;
import com.challengeorama.orama.model.Filter;
import com.challengeorama.orama.model.FilterOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
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

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        subscribeObservers();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_filter_settings) {
            showFilterDialog();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void subscribeObservers() {

        MainRecyclerAdapter adapter = new MainRecyclerAdapter();
        RecyclerView recyclerView = mBinding.mainFundosRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        mViewModel.getFundos().observe(getViewLifecycleOwner(), new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> fundos) {

                Log.d("livedataTestFrag", fundos.status.toString());

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
                            Log.d("livedataTestFrag", fundos.data.get(0).toString());
                            showProgressBar(false);
                            adapter.submitList(fundos.data);
                        }
                        showFilterDialog();
                        break;
                    }
                }


            }
        });

    }

    private void showFilterDialog() {

        MaterialDialog dialog = new MaterialDialog(requireActivity(), MaterialDialog.getDEFAULT_BEHAVIOR())
                .noAutoDismiss();

        dialog.getView().contentLayout.addCustomView(R.layout.main_filter, null, true, false);

        View view = dialog.getView().contentLayout.getCustomView();
        RadioGroup filterGroup = view.findViewById(R.id.filter_group);
        RadioGroup orderGroup = view.findViewById(R.id.filter_order_group);

        FilterOptions filterOptions = mViewModel.getFilterOptions().getValue();

        if (filterOptions.getActive()) {

            if (filterOptions.getSort() == Sort.ASC) {
                orderGroup.check(R.id.filter_asc);
            } else {
                orderGroup.check(R.id.filter_desc);
            }

            switch (filterOptions.getFilter()) {
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

                switch (filterGroup.getCheckedRadioButtonId()) {
                    case R.id.filter_date: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.ASC, Filter.Date));
                        } else {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.DSC, Filter.Date));
                        }
                        dialog.dismiss();
                        break;

                    }
                    case R.id.filter_minimumamount: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.ASC, Filter.MinimumAmount));
                        } else {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.DSC, Filter.MinimumAmount));
                        }
                        dialog.dismiss();
                        break;
                    }
                    case R.id.filter_name: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.ASC, Filter.Name));
                        } else {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.DSC, Filter.Name));
                        }
                        dialog.dismiss();
                        break;

                    }
                    case R.id.filter_profitability: {

                        if (orderGroup.getCheckedRadioButtonId() == R.id.filter_asc) {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.ASC, Filter.profitabilityYear));
                        } else {
                            mViewModel.setFilterOptions(new FilterOptions(true, Sort.DSC, Filter.profitabilityYear));
                        }
                        dialog.dismiss();
                        break;

                    }
                }

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