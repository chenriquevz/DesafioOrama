package com.challengeorama.orama.model.fundos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operability {

    @SerializedName("minimum_balance_permanence")
    @Expose
    private Float minimumBalancePermanence;

    @SerializedName("minimum_initial_application_amount")
    @Expose
    private Float minimumInitialApplicationAmount;

    @SerializedName("retrieval_liquidation_days")
    @Expose
    private int retrievalLiquidationDays;

    @SerializedName("application_quotation_days")
    @Expose
    private int applicationQuotationDays;

    public Operability (Float minimumBalancePermanence, Float minimumInitialApplicationAmount, int retrievalLiquidationDays, int applicationQuotationDays) {
        this.minimumBalancePermanence = minimumBalancePermanence;
        this.minimumInitialApplicationAmount = minimumInitialApplicationAmount;
        this.retrievalLiquidationDays = retrievalLiquidationDays;
        this.applicationQuotationDays = applicationQuotationDays;
    }

    public Float getMinimumBalancePermanence() {
        return minimumBalancePermanence;
    }

    public void setMinimumBalancePermanence(Float minimumBalancePermanence) {
        this.minimumBalancePermanence = minimumBalancePermanence;
    }

    public Float getMinimumInitialApplicationAmount() {
        return minimumInitialApplicationAmount;
    }

    public void setMinimumInitialApplicationAmount(Float minimumInitialApplicationAmount) {
        this.minimumInitialApplicationAmount = minimumInitialApplicationAmount;
    }

    public int getRetrievalLiquidationDays() {
        return retrievalLiquidationDays;
    }

    public void setRetrievalLiquidationDays(int retrievalLiquidationDays) {
        this.retrievalLiquidationDays = retrievalLiquidationDays;
    }

    public int getApplicationQuotationDays() {
        return applicationQuotationDays;
    }

    public void setApplicationQuotationDays(int applicationQuotationDays) {
        this.applicationQuotationDays = applicationQuotationDays;
    }
}
