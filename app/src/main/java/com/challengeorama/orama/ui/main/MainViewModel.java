package com.challengeorama.orama.ui.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.challengeorama.orama.model.Option;
import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
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

    private MediatorLiveData<Resource<List<Fundos>>> fundosMediatorLiveData = new MediatorLiveData<>();

    private MediatorLiveData<ListDataOptions> listDataOptionsMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> isErrorActive = new MediatorLiveData<>();


    public LiveData<ListDataOptions> getListDataOptions() {
        if (listDataOptionsMediatorLiveData.getValue() == null) {
            listDataOptionsMediatorLiveData = new MediatorLiveData<>();
            listDataOptionsMediatorLiveData.setValue(new ListDataOptions(false, Sort.NONE, Option.NONE));
        }

        return listDataOptionsMediatorLiveData;
    }

    public void setListDataOptions(ListDataOptions filterOptions) {
        listDataOptionsMediatorLiveData.setValue(filterOptions);
        searchFundos(fundosRepository.getFundosSorted(filterOptions));
    }

    public void forceRefresh() {
        isErrorActive.setValue(true);
    }

    private LiveData<Resource<List<Fundos>>> getFundos(Boolean forceRefresh) {
        if (fundosMediatorLiveData.getValue() == null || forceRefresh) {
            fundosMediatorLiveData = new MediatorLiveData<>();
            searchFundos(fundosRepository.getFundosSorted(null));
        }

        return fundosMediatorLiveData;
    }

    private LiveData<Boolean> getIsErrorActive() {
        if (isErrorActive.getValue() == null) {
            isErrorActive = new MediatorLiveData<>();
            isErrorActive.setValue(false);
        }

        return isErrorActive;
    }


    public LiveData<Resource<List<Fundos>>> getFundosFiltered() {

        return Transformations.switchMap(getIsErrorActive(), this::getFundos);

    }

    private void searchFundos(LiveData<Resource<List<Fundos>>> repository) {

        fundosMediatorLiveData.addSource(repository, new Observer<Resource<List<Fundos>>>() {
            @Override
            public void onChanged(Resource<List<Fundos>> listResource) {

                if (listResource.status == Resource.Status.SUCCESS) {

                    isErrorActive.setValue(false);
                    fundosMediatorLiveData.setValue(listResource);
                    fundosMediatorLiveData.removeSource(repository);

                } else if (listResource.status == Resource.Status.ERROR) {

                    fundosMediatorLiveData.setValue(listResource);
                    fundosMediatorLiveData.removeSource(repository);
                }

                fundosMediatorLiveData.setValue(listResource);
            }
        });
    }

}