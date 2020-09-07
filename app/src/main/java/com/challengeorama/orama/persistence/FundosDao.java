package com.challengeorama.orama.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.challengeorama.orama.model.Fundos;
import java.util.List;
import io.reactivex.Single;

@Dao
public interface FundosDao {

    @Insert
    void insertFundos(List<Fundos> fundos) throws Exception;

    @Query("SELECT * FROM Fundos")
    LiveData<List<Fundos>> getFundos();

    @Query("SELECT * FROM Fundos WHERE id = :id")
    LiveData<Fundos> getFundo(int id);

}
