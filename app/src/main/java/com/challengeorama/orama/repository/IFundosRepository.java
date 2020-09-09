package com.challengeorama.orama.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.fundos.Fundos;

import java.util.List;

public interface IFundosRepository {
    LiveData<Fundos> getFundo(int id);

    LiveData<Resource<List<Fundos>>> getFundosSorted(@Nullable ListDataOptions filterOptions);
}
