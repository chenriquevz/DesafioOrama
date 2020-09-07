package com.challengeorama.orama.ui.main;

import android.content.res.Resources;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainViewholderFundosBinding;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.util.FormatHelper;

public class FundosViewHolder extends RecyclerView.ViewHolder {
    private final MainViewholderFundosBinding mBinding;

    FundosViewHolder(MainViewholderFundosBinding itemBinding) {
        super(itemBinding.getRoot());
        this.mBinding = itemBinding;
    }

    void bind(Fundos fundos) {
        if (fundos != null) {
            Resources res = mBinding.getRoot().getContext().getResources();

            mBinding.nameFundos.setText(fundos.getSimpleName());
            mBinding.feesFundos.setText(res.getString(R.string.main_fees,
                    fundos.getFees().getAdministrationFee()));
            mBinding.minimumInitialApplicationAmountFundos.setText(res.getString(
                    R.string.main_operability_minimumInitialApplicationAmount,
                    new FormatHelper().FloatToReais(fundos.getOperability().getMinimumInitialApplicationAmount())));

/*            if (fundos.getProfitabilities().getYear() != null) {
                mBinding.yearProfitabilitiesFundos.setText(res.getString(R.string.main_rentabilidade_anual,
                        new FormatHelper().FloatToPercent(fundos.getProfitabilities().getYear())));
            }*/

            mBinding.cardviewFundos.setTransitionName(res.getString(R.string.main_sharedelementTransition,
                    fundos.getId()));

            mBinding.cardviewFundos.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    FragmentNavigator.Extras.Builder extras = new FragmentNavigator.Extras.Builder();
                    extras.addSharedElement(view, view.getTransitionName());
                    FragmentNavigator.Extras build = extras.build();

                    Navigation.findNavController(view)
                            .navigate(MainFragmentDirections.actionMainToListScreen(fundos.getId()), build);


                }
            });
        }

    }
}