package com.challengeorama.orama.model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundManager {

    @ColumnInfo(name = "fundamanager_description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "fundamanager_id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "fundamanager_full_name")
    @SerializedName("full_name")
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
