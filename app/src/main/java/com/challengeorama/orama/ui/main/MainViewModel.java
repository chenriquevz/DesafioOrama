package com.challengeorama.orama.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final FundosRepository fundosRepository;

    private MediatorLiveData<Resource<List<Fundos>>> fundos = new MediatorLiveData<>();

    @Inject
    public MainViewModel(FundosRepository fundosRepository) {
        this.fundosRepository = fundosRepository;
    }

    public LiveData<Resource<List<Fundos>>> getFundos() {
        return fundos;
    }

    public void startSearchFundos() {

        if (fundos.getValue() == null) {
            searchFundos();
        }

    }

    private void searchFundos () {

        final LiveData<Resource<List<Fundos>>> repositorySource = fundosRepository.observeFundos();

        fundos.addSource(repositorySource, new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> listResource) {

                if (listResource.status == Resource.Status.SUCCESS) {

                    fundos.setValue(listResource);
                    fundos.removeSource(repositorySource);
                } else if (listResource.status == Resource.Status.ERROR) {

                    fundos.setValue(listResource);
                    fundos.removeSource(repositorySource);
                }
                fundos.setValue(listResource);
            }
        });
    }

}