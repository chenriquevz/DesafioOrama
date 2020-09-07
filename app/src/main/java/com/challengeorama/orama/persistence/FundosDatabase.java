package com.challengeorama.orama.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.challengeorama.orama.model.fundos.Fundos;

@Database(entities = {Fundos.class}, version = 1)
public abstract class FundosDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "fundos_db";

    public abstract FundosDao getFundosDao();
}
