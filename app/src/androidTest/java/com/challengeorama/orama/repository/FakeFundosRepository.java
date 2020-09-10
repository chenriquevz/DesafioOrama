package com.challengeorama.orama.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class FakeFundosRepository implements IFundosRepository {

    FakeDataSource fakeDataSource;


    @Inject
    public FakeFundosRepository(FakeDataSource fakeDataSource) {
        this.fakeDataSource = fakeDataSource;
    }


    @Override
    public LiveData<Fundos> getFundo(int id) {
        List<Fundos> result = fakeDataSource.getFakeDao();

        Fundos fundo = null;
        for (Fundos item : result) {
            if (item.getId() == id) {
                fundo = item;
            }
        }
        MutableLiveData<Fundos> returnedData = new MutableLiveData<>();
        returnedData.setValue(fundo);

        return returnedData;
    }

    @Override
    public LiveData<Resource<List<Fundos>>> getFundosSorted(@Nullable ListDataOptions filterOptions) {

        Resource<List<Fundos>> result = fakeDataSource.getApi();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();


        if (filterOptions != null) {
            switch (filterOptions.getOption()) {
                case MinimumAmount: {

                    if (filterOptions.getSort() == Sort.ASC) {

                        Collections.sort(result.data, new Comparator<Fundos>() {
                            @Override
                            public int compare(Fundos t2, Fundos t1) {
                                return Float.compare(t2.getOperability().getMinimumInitialApplicationAmount(), t1.getOperability().getMinimumInitialApplicationAmount());
                            }
                        });

                        returnedData.setValue(result);
                        return returnedData;
                    }

                    Collections.sort(result.data, new Comparator<Fundos>() {
                        @Override
                        public int compare(Fundos t2, Fundos t1) {
                            return Float.compare(t1.getOperability().getMinimumInitialApplicationAmount(), t2.getOperability().getMinimumInitialApplicationAmount());
                        }
                    });

                    returnedData.setValue(result);
                    return returnedData;


                }
                case Date: {

                    if (filterOptions.getSort() == Sort.ASC) {

                        Collections.sort(result.data, new Comparator<Fundos>() {
                            @Override
                            public int compare(Fundos t2, Fundos t1) {
                                return t2.getInitialDate().compareTo(t1.getInitialDate());
                            }
                        });

                        returnedData.setValue(result);
                        return returnedData;
                    }

                    Collections.sort(result.data, new Comparator<Fundos>() {
                        @Override
                        public int compare(Fundos t2, Fundos t1) {
                            return t1.getInitialDate().compareTo(t2.getInitialDate());
                        }
                    });

                    returnedData.setValue(result);
                    return returnedData;


                }
                case profitabilityYear: {

                    if (filterOptions.getSort() == Sort.ASC) {

                        Collections.sort(result.data, new Comparator<Fundos>() {
                            @Override
                            public int compare(Fundos t2, Fundos t1) {
                                return Float.compare(t2.getProfitabilities().getYear(), t1.getProfitabilities().getYear());
                            }
                        });

                        returnedData.setValue(result);
                        return returnedData;


                    }

                    Collections.sort(result.data, new Comparator<Fundos>() {
                        @Override
                        public int compare(Fundos t2, Fundos t1) {
                            return Float.compare(t1.getProfitabilities().getYear(), t2.getProfitabilities().getYear());
                        }
                    });

                    returnedData.setValue(result);
                    return returnedData;

                }
                case Name: {
                    if (filterOptions.getSort() == Sort.ASC) {
                        Collections.sort(result.data, new Comparator<Fundos>() {
                            @Override
                            public int compare(Fundos t2, Fundos t1) {
                                return t2.getSimpleName().compareTo(t1.getSimpleName());
                            }
                        });
                        returnedData.setValue(result);
                        return returnedData;
                    }

                    Collections.sort(result.data, new Comparator<Fundos>() {
                        @Override
                        public int compare(Fundos t2, Fundos t1) {
                            return t1.getSimpleName().compareTo(t2.getSimpleName());
                        }
                    });

                    returnedData.setValue(result);
                    return returnedData;
                }
                default: {
                    returnedData.setValue(result);
                    return returnedData;
                }
            }
        } else {
            returnedData.setValue(result);
            return returnedData;
        }
    }
}
