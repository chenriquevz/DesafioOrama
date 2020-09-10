package com.challengeorama.orama.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.challengeorama.orama.databinding.MainViewholderFundosBinding;
import com.challengeorama.orama.model.fundos.Fundos;

import java.util.List;


class MainRecyclerAdapter extends RecyclerView.Adapter<FundosViewHolder> {
    private final AsyncListDiffer<Fundos> mDiffer = new AsyncListDiffer(this, DIFF_CALLBACK);

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submitList(List<Fundos> list) {
        mDiffer.submitList(list);
    }

    @NonNull
    @Override
    public FundosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainViewholderFundosBinding itemBinding = MainViewholderFundosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FundosViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(FundosViewHolder holder, int position) {
        Fundos fundos = mDiffer.getCurrentList().get(position);
        holder.bind(fundos);
    }

    public static final DiffUtil.ItemCallback<Fundos> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Fundos>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull Fundos oldUser, @NonNull Fundos newUser) {
            return oldUser.getId() == newUser.getId();
        }

        @Override
        public boolean areContentsTheSame(
                @NonNull Fundos oldUser, @NonNull Fundos newUser) {
            return oldUser.equals(newUser);
        }
    };
}


