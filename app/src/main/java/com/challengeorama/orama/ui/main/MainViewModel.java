package com.challengeorama.orama.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final FundosRepository fundosRepository;

    @Inject
    public MainViewModel(FundosRepository fundosRepository) {
        this.fundosRepository = fundosRepository;
    }

    public LiveData<Resource<List<Fundos>>> getFundos () {
        return fundosRepository.observeFundos();
    }

}