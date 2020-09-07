package com.challengeorama.orama.model.fundos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profitabilities {

    @SerializedName("day")
    @Expose
    @Nullable
    private Float day;

    @SerializedName("month")
    @Expose
    @Nullable
    private Float month;

    @SerializedName("year")
    @Expose
    @Nullable
    private Float year;

    @SerializedName("m12")
    @Expose
    @Nullable
    private Float m12;

    @SerializedName("m24")
    @Expose
    @Nullable
    private Float m24;

    @SerializedName("m36")
    @Expose
    @Nullable
    private Float m36;

    @SerializedName("m48")
    @Expose
    @Nullable
    private Float m48;

    @SerializedName("m60")
    @Expose
    @Nullable
    private Float m60;

    public Profitabilities(@Nullable Float day, @Nullable Float month, @Nullable Float year,
                           @Nullable Float m12, @Nullable Float m24, @Nullable Float m36, @Nullable Float m48, @Nullable Float m60) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.m12 = m12;
        this.m24 = m24;
        this.m36 = m36;
        this.m48 = m48;
        this.m60 = m60;

    }

    @Nullable
    public Float getDay() {
        return day;
    }


    public void setDay(@Nullable Float day) {
        this.day = day;
    }

    @Nullable
    public Float getMonth() {
        return month;
    }


    public void setMonth(@Nullable Float month) {
        this.month = month;
    }

    @Nullable
    public Float getYear() {
        return year;
    }

    public void setYear(@Nullable Float year) {
        this.year = year;
    }

    @Nullable
    public Float getM12() {
        return m12;
    }


    public void setM12(@Nullable Float m12) {
        this.m12 = m12;
    }

    @Nullable
    public Float getM24() {
        return m24;
    }


    public void setM24(@Nullable Float m24) {
        this.m24 = m24;
    }

    @Nullable
    public Float getM36() {
        return m36;
    }


    public void setM36(@Nullable Float m36) {
        this.m36 = m36;
    }

    @Nullable
    public Float getM48() {
        return m48;
    }


    public void setM48(@Nullable Float m48) {
        this.m48 = m48;
    }

    @Nullable
    public Float getM60() {
        return m60;
    }


    public void setM60(@Nullable Float m60) {
        this.m60 = m60;
    }
}
