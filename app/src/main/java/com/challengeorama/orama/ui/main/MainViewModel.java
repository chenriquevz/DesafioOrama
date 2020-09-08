package com.challengeorama.orama.ui.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.challengeorama.orama.model.Filter;
import com.challengeorama.orama.model.FilterOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {


    private final FundosRepository fundosRepository;

    @Inject
    public MainViewModel(FundosRepository fundosRepository) {
        this.fundosRepository = fundosRepository;
    }

    private MediatorLiveData<Resource<List<Fundos>>> fundosMediatorLiveData = new MediatorLiveData<>();

    private MediatorLiveData<FilterOptions> filterOptionsMediatorLiveData = new MediatorLiveData<>();


    public LiveData<FilterOptions> getFilterOptions() {
        if (filterOptionsMediatorLiveData.getValue() == null) {
            filterOptionsMediatorLiveData = new MediatorLiveData<>();
            filterOptionsMediatorLiveData.setValue(new FilterOptions(true, Sort.ASC, Filter.MinimumAmount));
        }

        return filterOptionsMediatorLiveData;
    }

    public void setFilterOptions(FilterOptions filterOptions) {
        filterOptionsMediatorLiveData.setValue(filterOptions);
    }

    private LiveData<Resource<List<Fundos>>> fundos() {
        if (fundosMediatorLiveData.getValue() == null) {
            fundosMediatorLiveData = new MediatorLiveData<>();
            searchFundos();
        }

        return fundosMediatorLiveData;
    }

    public LiveData<Resource<List<Fundos>>> getFundos() {

        return Transformations.switchMap(getFilterOptions(), filter -> {

            if (filter.getActive()) {

                return Transformations.map(fundosRepository.getFundosSorted(filter), Resource::success);

            }

            return fundos();

        });

    }


    private void searchFundos() {

        final LiveData<Resource<List<Fundos>>> repositorySource = fundosRepository.observeFundos();

        fundosMediatorLiveData.addSource(repositorySource, new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> listResource) {

                if (listResource.status == Resource.Status.SUCCESS) {


                    fundosMediatorLiveData.setValue(listResource);
                    fundosMediatorLiveData.removeSource(repositorySource);
                } else if (listResource.status == Resource.Status.ERROR) {

                    fundosMediatorLiveData.setValue(listResource);
                    fundosMediatorLiveData.removeSource(repositorySource);
                }
                fundosMediatorLiveData.setValue(listResource);
            }
        });
    }

}