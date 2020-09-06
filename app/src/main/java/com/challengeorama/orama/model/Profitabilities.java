package com.challengeorama.orama.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profitabilities {

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
