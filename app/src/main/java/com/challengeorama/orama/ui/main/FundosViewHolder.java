package com.challengeorama.orama.ui.main;

import android.content.res.Resources;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.challengeorama.orama.R;
import com.challengeorama.orama.databinding.MainViewholderFundosBinding;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.util.FormatHelper;

public class FundosViewHolder extends RecyclerView.ViewHolder {
    private final MainViewholderFundosBinding mDataBinding;

    FundosViewHolder(MainViewholderFundosBinding itemBinding) {
        super(itemBinding.getRoot());
        this.mDataBinding = itemBinding;
    }

    void bind(Fundos fundos) {
        if (fundos != null) {
            Resources res = mDataBinding.getRoot().getContext().getResources();

            mDataBinding.nameFundos.setText(fundos.getSimpleName());
            mDataBinding.yearProfitabilitiesFundos.setText(res.getString(R.string.main_rentabilidade_anual,
                    new FormatHelper().FloatToPercent(fundos.getProfitabilities().getYear())));
            mDataBinding.feesFundos.setText(res.getString(R.string.main_fees,
                    fundos.getFees().getAdministrationFee()));
            mDataBinding.minimumInitialApplicationAmountFundos.setText(res.getString(
                    R.string.main_operability_minimumInitialApplicationAmount,
                    new FormatHelper().FloatToReais(fundos.getOperability().getMinimumInitialApplicationAmount())));


            mDataBinding.cardviewFundos.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainToListScreen(fundos.getId()));


                }
            });
        }

    }
}