package com.android.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.data.model.EspecieRiaFormosaDunasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

import java.util.List;

@Dao
public interface AvistamentoDunasRiaFormosaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAvistamentoDunasRiaFormosa(AvistamentoDunasRiaFormosa avistamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEspecieRiaFormosaDunasInstancias(EspecieRiaFormosaDunasInstancias especieRiaFormosaDunasInstancias);

    @Update
    void update(AvistamentoDunasRiaFormosa avistamento);

    @Delete
    void delete(AvistamentoDunasRiaFormosa avistamento);

    @Query("DELETE FROM avistamento_dunas_riaformosa")
    void deleteAllAvistamentoDunasRiaFormosa();

    @Transaction
    @Query("DELETE FROM avistamento_dunas_riaformosa WHERE iteracao = :iteracao AND zona = :zona")
    void deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona);

    @Transaction
    @Query("SELECT * FROM avistamento_dunas_riaformosa ORDER BY idAvistamentoDunas DESC")
    LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_dunas_riaformosa WHERE iteracao = :iteracao AND zona = :zona ORDER BY idAvistamentoDunas DESC LIMIT 1")
    LiveData<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona);

    @Query("SELECT * FROM avistamento_dunas_riaformosa ORDER BY idAvistamentoDunas DESC")
    LiveData<List<AvistamentoDunasRiaFormosa>> getAllAvistamentoDunasRiaFormosa();

    @Transaction
    @Query("SELECT * FROM avistamento_dunas_riaformosa WHERE zona = :zona ORDER BY idAvistamentoDunas ASC")
    LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAvistamentosDunasWithZona(String zona);
}
