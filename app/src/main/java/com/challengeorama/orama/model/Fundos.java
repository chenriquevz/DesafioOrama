package com.challengeorama.orama.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Fundos {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("full_name")
    @Expose
    private String fullName;


    @SerializedName("simple_name")
    @Expose
    private String simpleName;


    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;


    @SerializedName("initial_date")
    @Expose
    private String initialDate;

    @SerializedName("fees")
    @Expose
    private Fees fees;

    @SerializedName("description_seo")
    @Expose
    private String descriptionSeo;

    @SerializedName("operability")
    @Expose
    private Operability operability;

    @SerializedName("description")
    @Expose
    private Description description;

    @SerializedName("profitabilities")
    @Expose
    private Profitabilities profitabilities;

    @SerializedName("fund_manager")
    @Expose
    private FundManager fundManager;

    public Fundos(int id, String fullName, String simpleName, Boolean isClosed, String initialDate, Fees fees, String descriptionSeo, Operability operability, Description description, Profitabilities profitabilities, FundManager fundManager) {
        this.id = id;
        this.fullName = fullName;
        this.simpleName = simpleName;
        this.isClosed = isClosed;
        this.initialDate = initialDate;
        this.fees = fees;
        this.descriptionSeo = descriptionSeo;
        this.operability = operability;
        this.description = description;
        this.profitabilities = profitabilities;
        this.fundManager = fundManager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    public String getDescriptionSeo() {
        return descriptionSeo;
    }

    public void setDescriptionSeo(String descriptionSeo) {
        this.descriptionSeo = descriptionSeo;
    }

    public Operability getOperability() {
        return operability;
    }

    public void setOperability(Operability operability) {
        this.operability = operability;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Profitabilities getProfitabilities() {
        return profitabilities;
    }

    public void setProfitabilities(Profitabilities profitabilities) {
        this.profitabilities = profitabilities;
    }

    public FundManager getFundManager() {
        return fundManager;
    }

    public void setFundManager(FundManager fundManager) {
        this.fundManager = fundManager;
    }
}

class Fees {

    @SerializedName("maximum_administration_fee")
    @Expose
    private String maximumAdministrationFee;

    @SerializedName("anticipated_retrieval_fee_value")
    @Expose
    private String anticipatedRetrievalFeeValue;

    @SerializedName("administration_fee")
    @Expose
    private String administrationFee;

    @SerializedName("anticipated_retrieval_fee")
    @Expose
    private String anticipatedRetrievalFee;

    @SerializedName("performance_fee")
    @Expose
    private String performanceFee;

    @SerializedName("has_anticipated_retrieval")
    @Expose
    private String hasAnticipatedRetrieval;

    public Fees(String maximumAdministrationFee, String anticipatedRetrievalFeeValue, String administrationFee, String anticipatedRetrievalFee, String performanceFee, String hasAnticipatedRetrieval) {
        this.maximumAdministrationFee = maximumAdministrationFee;
        this.anticipatedRetrievalFeeValue = anticipatedRetrievalFeeValue;
        this.administrationFee = administrationFee;
        this.anticipatedRetrievalFee = anticipatedRetrievalFee;
        this.performanceFee = performanceFee;
        this.hasAnticipatedRetrieval = hasAnticipatedRetrieval;
    }

    public String getMaximumAdministrationFee() {
        return maximumAdministrationFee;
    }

    public void setMaximumAdministrationFee(String maximumAdministrationFee) {
        this.maximumAdministrationFee = maximumAdministrationFee;
    }

    public String getAnticipatedRetrievalFeeValue() {
        return anticipatedRetrievalFeeValue;
    }

    public void setAnticipatedRetrievalFeeValue(String anticipatedRetrievalFeeValue) {
        this.anticipatedRetrievalFeeValue = anticipatedRetrievalFeeValue;
    }

    public String getAdministrationFee() {
        return administrationFee;
    }

    public void setAdministrationFee(String administrationFee) {
        this.administrationFee = administrationFee;
    }

    public String getAnticipatedRetrievalFee() {
        return anticipatedRetrievalFee;
    }

    public void setAnticipatedRetrievalFee(String anticipatedRetrievalFee) {
        this.anticipatedRetrievalFee = anticipatedRetrievalFee;
    }

    public String getPerformanceFee() {
        return performanceFee;
    }

    public void setPerformanceFee(String performanceFee) {
        this.performanceFee = performanceFee;
    }

    public String getHasAnticipatedRetrieval() {
        return hasAnticipatedRetrieval;
    }

    public void setHasAnticipatedRetrieval(String hasAnticipatedRetrieval) {
        this.hasAnticipatedRetrieval = hasAnticipatedRetrieval;
    }
}

class Operability {

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

class Description {

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

class Profitabilities {

    @SerializedName("day")
    @Expose
    private Float day;

    @SerializedName("month")
    @Expose
    private Float month;

    @SerializedName("year")
    @Expose
    private Float year;

    @SerializedName("m12")
    @Expose
    private Float m12;

    @SerializedName("m24")
    @Expose
    private Float m24;

    @SerializedName("m36")
    @Expose
    private Float m36;

    @SerializedName("m48")
    @Expose
    private Float m48;

    @SerializedName("m60")
    @Expose
    private Float m60;

    public Profitabilities(Float day, Float month, Float year, Float m12, Float m24, Float m36, Float m48, Float m60) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.m12 = m12;
        this.m24 = m24;
        this.m36 = m36;
        this.m48 = m48;
        this.m60 = m60;

    }

    public Float getDay() {
        return day;
    }

    public void setDay(Float day) {
        this.day = day;
    }

    public Float getMonth() {
        return month;
    }

    public void setMonth(Float month) {
        this.month = month;
    }

    public Float getYear() {
        return year;
    }

    public void setYear(Float year) {
        this.year = year;
    }

    public Float getM12() {
        return m12;
    }

    public void setM12(Float m12) {
        this.m12 = m12;
    }

    public Float getM24() {
        return m24;
    }

    public void setM24(Float m24) {
        this.m24 = m24;
    }

    public Float getM36() {
        return m36;
    }

    public void setM36(Float m36) {
        this.m36 = m36;
    }

    public Float getM48() {
        return m48;
    }

    public void setM48(Float m48) {
        this.m48 = m48;
    }

    public Float getM60() {
        return m60;
    }

    public void setM60(Float m60) {
        this.m60 = m60;
    }
}

class FundManager {

    @ColumnInfo(name = "fundamanager_description")
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("fundamanager_id")
    @Expose
    private int id;

    @SerializedName("fundamanager_full_name")
    @Expose
    private String fullName;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("fundmanager_name")
    @Expose
    private String name;

    public FundManager(String description, int id, String fullName, String logo, String name) {
        this.description = description;
        this.id = id;
        this.fullName = fullName;
        this.logo = logo;
        this.name = name;


    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}