package com.challengeorama.orama.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.FundosRepository;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private final FundosRepository fundosRepository;

    @Inject
    public DetailViewModel(FundosRepository fundosRepository) {
        this.fundosRepository = fundosRepository;
    }

    public LiveData<Fundos> getFundo (int id) {
        return fundosRepository.getFundo(id);
    }

}