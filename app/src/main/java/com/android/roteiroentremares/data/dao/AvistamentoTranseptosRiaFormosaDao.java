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
import com.android.roteiroentremares.data.model.AvistamentoTranseptosRiaFormosa;
import com.android.roteiroentremares.data.model.EspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.EspecieRiaFormosaTranseptosInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;

import java.util.List;

@Dao
public interface AvistamentoTranseptosRiaFormosaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvistamentoTranseptosRiaFormosa(AvistamentoTranseptosRiaFormosa avistamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEspecieRiaFormosaTranseptosInstancias(EspecieRiaFormosaTranseptosInstancias especieRiaFormosaTranseptosInstancias);

    @Update
    void update(AvistamentoTranseptosRiaFormosa avistamento);

    @Delete
    void delete(AvistamentoTranseptosRiaFormosa avistamento);

    @Query("DELETE FROM avistamento_transeptos_riaformosa")
    void deleteAllAvistamentoTranseptosRiaFormosa();

    @Transaction
    @Query("SELECT * FROM avistamento_transeptos_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>> getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_transeptos_riaformosa WHERE idAvistamento = :idAvistamento")
    LiveData<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(int idAvistamento);

    @Query("SELECT * FROM avistamento_transeptos_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoTranseptosRiaFormosa>> getAllAvistamentoTranseptosRiaFormosa();
}
