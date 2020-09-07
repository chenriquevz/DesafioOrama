package com.challengeorama.orama.model;

public class FilterOptions {

    private Boolean isActive;
    private Sort sort;
    private Filter filter;

    public FilterOptions(Boolean isActive, Sort sort, Filter filter) {
        this.isActive = isActive;
        this.sort = sort;
        this.filter = filter;

    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
