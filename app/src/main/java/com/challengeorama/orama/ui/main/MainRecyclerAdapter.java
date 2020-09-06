package com.challengeorama.orama.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.challengeorama.orama.databinding.MainViewholderFundosBinding;
import com.challengeorama.orama.model.Fundos;

public class MainRecyclerAdapter extends ListAdapter<Fundos, FundosViewHolder> {
    /**
     * DiffUtil to compare the Repo data (old and new)
     * for issuing notify commands suitably to update the list
     */
    private static DiffUtil.ItemCallback<Fundos> REPO_COMPARATOR
            = new DiffUtil.ItemCallback<Fundos>() {
        @Override
        public boolean areItemsTheSame(Fundos oldItem, Fundos newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Fundos oldItem, @NonNull Fundos newItem) {
            return oldItem.equals(newItem);
        }
    };

    MainRecyclerAdapter() {
        super(REPO_COMPARATOR);
    }

    @NonNull
    @Override
    public FundosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainViewholderFundosBinding itemBinding = MainViewholderFundosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FundosViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FundosViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


}

