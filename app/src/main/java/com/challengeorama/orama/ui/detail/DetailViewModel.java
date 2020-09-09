package com.challengeorama.orama.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.IFundosRepository;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private final IFundosRepository fundosRepository;

    @Inject
    public DetailViewModel(IFundosRepository fundosRepository) {
        this.fundosRepository = fundosRepository;
    }

    public LiveData<Fundos> getFundo (int id) {
        return fundosRepository.getFundo(id);
    }

}