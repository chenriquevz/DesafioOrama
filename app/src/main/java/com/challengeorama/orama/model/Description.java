package com.challengeorama.orama.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("objective")
    @Expose
    private String objective;

    @SerializedName("strengths")
    @Expose
    private String strengths;

    @SerializedName("strategy")
    @Expose
    private String strategy;

    public Description(String objective, String strengths, String strategy) {
        this.objective = objective;
        this.strengths = strengths;
        this.strategy = strategy;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
