package com.challengeorama.orama.model;

public class ListDataOptions {

    private Boolean isActive;
    private Sort sort;
    private Option option;

    public ListDataOptions(Boolean isActive, Sort sort, Option filter) {
        this.isActive = isActive;
        this.sort = sort;
        this.option = filter;

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

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
