package com.challengeorama.orama.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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
    @Embedded
    private Fees fees;

    @SerializedName("description_seo")
    @Expose
    private String descriptionSeo;

    @SerializedName("operability")
    @Expose
    @Embedded
    private Operability operability;

    @SerializedName("description")
    @Expose
    @Embedded
    private Description description;

    @SerializedName("profitabilities")
    @Expose
    @Embedded
    private Profitabilities profitabilities;

    @SerializedName("fund_manager")
    @Expose
    @Embedded
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fundos fundos = (Fundos) o;
        return id == fundos.id &&
                Objects.equals(fullName, fundos.fullName) &&
                Objects.equals(simpleName, fundos.simpleName) &&
                Objects.equals(isClosed, fundos.isClosed) &&
                Objects.equals(initialDate, fundos.initialDate) &&
                Objects.equals(fees, fundos.fees) &&
                Objects.equals(descriptionSeo, fundos.descriptionSeo) &&
                Objects.equals(operability, fundos.operability) &&
                Objects.equals(description, fundos.description) &&
                Objects.equals(profitabilities, fundos.profitabilities) &&
                Objects.equals(fundManager, fundos.fundManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, simpleName, isClosed, initialDate, fees, descriptionSeo, operability, description, profitabilities, fundManager);
    }

    public Fundos() {
        
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

