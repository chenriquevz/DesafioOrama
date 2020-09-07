package com.challengeorama.orama.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;

import java.util.List;

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