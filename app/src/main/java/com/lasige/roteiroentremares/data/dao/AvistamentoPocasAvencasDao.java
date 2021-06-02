package com.lasige.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lasige.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.lasige.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;

import java.util.List;

@Dao
public interface AvistamentoPocasAvencasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvistamentoPocasAvencas(AvistamentoPocasAvencas avistamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEspecieAvencasPocasInstancias(EspecieAvencasPocasInstancias especieAvencasPocasInstancias);

    @Update
    void update(AvistamentoPocasAvencas avistamento);

    @Delete
    void delete(AvistamentoPocasAvencas avistamento);

    @Query("DELETE FROM avistamento_pocas_avencas")
    void deleteAllAvistamentoPocasAvencas();

    @Transaction
    @Query("SELECT * FROM avistamento_pocas_avencas ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_pocas_avencas WHERE idAvistamento = :idAvistamento")
    LiveData<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(int idAvistamento);

    @Query("SELECT * FROM avistamento_pocas_avencas ORDER BY idAvistamento DESC")
    LiveData<List<AvistamentoPocasAvencas>> getAllAvistamentoPocasAvencas();
}
