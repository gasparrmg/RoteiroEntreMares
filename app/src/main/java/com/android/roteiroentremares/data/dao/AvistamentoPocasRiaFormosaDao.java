package com.android.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.roteiroentremares.data.model.AvistamentoPocasRiaFormosa;
import com.android.roteiroentremares.data.model.EspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;

import java.util.List;

@Dao
public interface AvistamentoPocasRiaFormosaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvistamentoPocasRiaFormosa(AvistamentoPocasRiaFormosa avistamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEspecieRiaFormosaPocasInstancias(EspecieRiaFormosaPocasInstancias especieRiaFormosaPocasInstancias);

    @Update
    void update(AvistamentoPocasRiaFormosa avistamento);

    @Delete
    void delete(AvistamentoPocasRiaFormosa avistamento);

    @Query("DELETE FROM avistamento_pocas_riaformosa")
    void deleteAllAvistamentoPocasRiaFormosa();

    @Transaction
    @Query("SELECT * FROM avistamento_pocas_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>> getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_pocas_riaformosa WHERE idAvistamento = :idAvistamento")
    LiveData<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> getAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias(int idAvistamento);

    @Query("SELECT * FROM avistamento_pocas_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoPocasRiaFormosa>> getAllAvistamentoPocasRiaFormosa();
}
