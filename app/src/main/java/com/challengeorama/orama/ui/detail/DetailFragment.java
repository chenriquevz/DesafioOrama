package com.challengeorama.orama.ui.detail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import com.challengeorama.orama.BaseApplication;
import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.DetailFragmentBinding;
import com.challengeorama.orama.model.Fundos;
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
        mViewModel = new ViewModelProvider(requireActivity(), providerFactory).get(DetailViewModel.class);
        subscribeObservers();

        mBinding.detailCoordinator.setTransitionName(requireContext().getResources().getString(R.string.main_sharedelementTransition,
                DetailFragmentArgs.fromBundle(getArguments()).getFundoID()));


    }

    private void subscribeObservers() {

        Resources res = requireContext().getResources();

        mViewModel.getFundo(DetailFragmentArgs.fromBundle(getArguments()).getFundoID()).observe(getViewLifecycleOwner(), new Observer<Fundos>() {

            @Override
            public void onChanged(Fundos fundos) {

                mBinding.appBarTitle.setText(fundos.getSimpleName());
                mBinding.detailMinimumAmount.setText(res.getString(
                        R.string.main_operability_minimumInitialApplicationAmount,
                        new FormatHelper().FloatToReais(fundos.getOperability().getMinimumInitialApplicationAmount())));
                mBinding.detailOperabilityMinimumBalance.setText(res.getString(
                        R.string.main_operability_minimumBalance,
                        new FormatHelper().FloatToReais(fundos.getOperability().getMinimumBalancePermanence())));
                mBinding.detailFees.setText(res.getString(R.string.main_fees,
                        fundos.getFees().getAdministrationFee()));

                mBinding.detailFundoInitialDate.setText(res.getString(R.string.detail_fundo_initialDate,
                        new FormatHelper().StringToDateString(fundos.getInitialDate())));


                mBinding.detailProfitabilityDay.setText(res.getString(R.string.detail_rentabilidade_diaria,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getDay())));
                mBinding.detailProfitabilityMonth.setText(res.getString(R.string.detail_rentabilidade_mensal,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getMonth())));
                mBinding.detailProfitabilityYear.setText(res.getString(R.string.detail_rentabilidade_anual,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getYear())));
                mBinding.detailProfitabilityM12.setText(res.getString(R.string.detail_rentabilidade_12m,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM12())));
                mBinding.detailProfitabilityM24.setText(res.getString(R.string.detail_rentabilidade_24m,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM24())));
                mBinding.detailProfitabilityM36.setText(res.getString(R.string.detail_rentabilidade_36m,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM36())));
                mBinding.detailProfitabilityM48.setText(res.getString(R.string.detail_rentabilidade_48m,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM48())));
                mBinding.detailProfitabilityM60.setText(res.getString(R.string.detail_rentabilidade_60m,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getM60())));

                mBinding.detailFundDescription.setText(fundos.getDescription().getObjective());
                mBinding.detailFundmanagerName.setText(fundos.getFundManager().getFullName());
                mBinding.detailFundmanagerDescription.setText(fundos.getFundManager().getDescription());

            }
        });
    }


}
