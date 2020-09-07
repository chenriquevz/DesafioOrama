package com.challengeorama.orama.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.challengeorama.orama.model.fundos.Fundos;
import java.util.List;

@Dao
public interface FundosDao {

    @Insert
    void insertFundos(List<Fundos> fundos) throws Exception;

    @Query("SELECT * FROM Fundos")
    LiveData<List<Fundos>> getFundos();

    @Query("SELECT * FROM Fundos WHERE id = :id")
    LiveData<Fundos> getFundo(int id);

    @Query("SELECT * FROM Fundos ORDER BY CASE WHEN :isAsc = 1 THEN simplename END ASC, CASE WHEN :isAsc = 0 THEN simplename END DESC" )
    LiveData<List<Fundos>> getFundosOrderName(boolean isAsc);

    @Query("SELECT * FROM Fundos ORDER BY CASE WHEN :isAsc = 1 THEN year END ASC, CASE WHEN :isAsc = 0 THEN year END DESC" )
    LiveData<List<Fundos>> getFundosOrderProfitability(boolean isAsc);

    @Query("SELECT * FROM Fundos ORDER BY CASE WHEN :isAsc = 1 THEN initialDate END ASC, CASE WHEN :isAsc = 0 THEN initialDate END DESC" )
    LiveData<List<Fundos>> getFundosOrderDate(boolean isAsc);

    @Query("SELECT * FROM Fundos ORDER BY CASE WHEN :isAsc = 1 THEN minimumInitialApplicationAmount END ASC, CASE WHEN :isAsc = 0 THEN minimumInitialApplicationAmount END DESC" )
    LiveData<List<Fundos>> getFundosOrderMinimumAmount(boolean isAsc);

}
