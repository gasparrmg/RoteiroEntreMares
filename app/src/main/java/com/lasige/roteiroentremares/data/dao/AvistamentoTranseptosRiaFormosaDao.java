package com.lasige.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lasige.roteiroentremares.data.model.AvistamentoTranseptosRiaFormosa;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;

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

    @Query("SELECT * FROM especie_transeptos_instancias_table_riaformosa")
    LiveData<List<EspecieRiaFormosaTranseptosInstancias>> getAllEspecieRiaFormosaTranseptosInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_transeptos_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>> getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_transeptos_riaformosa WHERE idAvistamento = :idAvistamento")
    LiveData<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(int idAvistamento);

    @Query("SELECT * FROM avistamento_transeptos_riaformosa ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoTranseptosRiaFormosa>> getAllAvistamentoTranseptosRiaFormosa();
}
