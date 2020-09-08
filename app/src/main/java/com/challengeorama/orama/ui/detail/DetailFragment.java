package com.challengeorama.orama.ui.detail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.TransitionInflater;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.DetailFragmentBinding;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.util.FormatHelper;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class DetailFragment extends Fragment {

    private DetailFragmentBinding mBinding;

    private DetailViewModel mViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSharedElementEnterTransition(TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DetailFragmentBinding.inflate(inflater, container, false);

        postponeEnterTransition();
        mBinding.detailMinimumAmount.getViewTreeObserver().addOnPreDrawListener(() -> {
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

        Toolbar toolbar = mBinding.detailActionBar;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);


        mViewModel = new ViewModelProvider(requireActivity(), providerFactory).get(DetailViewModel.class);
        subscribeObservers();

        mBinding.linearLayout.setTransitionName(requireContext().getResources().getString(R.string.main_sharedelementTransition,
                DetailFragmentArgs.fromBundle(getArguments()).getFundoID()));


    }

    private void subscribeObservers() {

        Resources res = requireContext().getResources();

        mViewModel.getFundo(DetailFragmentArgs.fromBundle(getArguments()).getFundoID()).observe(getViewLifecycleOwner(), new Observer<Fundos>() {

            @Override
            public void onChanged(Fundos fundos) {

                mBinding.appBarTitle.setText(fundos.getSimpleName());
                mBinding.detailFees.setText(res.getString(R.string.main_fees,
                        fundos.getFees().getAdministrationFee()));
                mBinding.detailFundoInitialDate.setText(res.getString(R.string.detail_fundo_initialDate,
                        new FormatHelper().StringToDateString(fundos.getInitialDate())));
                mBinding.detailFundDescription.setText(fundos.getDescription().getObjective());
                mBinding.detailFundmanagerName.setText(fundos.getFundManager().getFullName());
                mBinding.detailFundmanagerDescription.setText(fundos.getFundManager().getDescription());


                bindOperability(fundos);
                bindProfitability(fundos);


            }
        });
    }

    private void bindOperability(Fundos fundos) {

        Resources res = requireContext().getResources();

        mBinding.detailMinimumAmount.setText(res.getString(
                R.string.main_operability_minimumInitialApplicationAmount,
                new FormatHelper().FloatToReais(fundos.getOperability().getMinimumInitialApplicationAmount())));
        mBinding.detailOperabilityMinimumBalance.setText(res.getString(
                R.string.main_operability_minimumBalance,
                new FormatHelper().FloatToReais(fundos.getOperability().getMinimumBalancePermanence())));


    }

    private void bindProfitability(Fundos fundos) {

        Resources res = requireContext().getResources();


        String profitabilityMonth = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityMonth = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityMonth.setText(res.getString(R.string.detail_rentabilidade_mensal,
                profitabilityMonth));


        String profitabilityDay = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityDay = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getDay());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityDay.setText(res.getString(R.string.detail_rentabilidade_diaria,
                profitabilityDay));


        String profitabilityYear = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityYear = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityYear.setText(res.getString(R.string.detail_rentabilidade_anual,
                profitabilityYear));


        String profitabilityM12 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM12 = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM12());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityM12.setText(res.getString(R.string.detail_rentabilidade_12m,
                profitabilityM12));


        String profitabilityM24 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM24 = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM24());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityM24.setText(res.getString(R.string.detail_rentabilidade_24m,
                profitabilityM24));


        String profitabilityM36 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM36 = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityM36.setText(res.getString(R.string.detail_rentabilidade_36m,
                profitabilityM36));


        String profitabilityM48 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM48 = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityM48.setText(res.getString(R.string.detail_rentabilidade_48m,
                profitabilityM48));


        String profitabilityM60 = new FormatHelper().FloatToPercent(0.00f);
        try {
            profitabilityM60 = new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM36());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.detailProfitabilityM60.setText(res.getString(R.string.detail_rentabilidade_60m,
                profitabilityM60));


    }

}
