package com.android.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

import java.util.List;

@Dao
public interface AvistamentoZonacaoAvencasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAvistamentoZonacaoAvencas(AvistamentoZonacaoAvencas avistamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEspecieAvencasZonacaoInstancias(EspecieAvencasZonacaoInstancias especieAvencasZonacaoInstancias);

    @Update
    void update(AvistamentoZonacaoAvencas avistamento);

    @Delete
    void delete(AvistamentoZonacaoAvencas avistamento);

    @Query("DELETE FROM avistamento_zonacao_avencas")
    void deleteAllAvistamentoZonacaoAvencas();

    @Transaction
    @Query("DELETE FROM avistamento_zonacao_avencas WHERE iteracao = :iteracao AND zona = :zona")
    void deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona);

    @Transaction
    @Query("SELECT * FROM avistamento_zonacao_avencas ORDER BY idAvistamentoZonacao DESC")
    LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias();

    @Transaction
    @Query("SELECT * FROM avistamento_zonacao_avencas WHERE iteracao = :iteracao AND zona = :zona ORDER BY idAvistamentoZonacao DESC LIMIT 1")
    LiveData<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> getAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona);

    @Query("SELECT * FROM avistamento_zonacao_avencas ORDER BY idAvistamentoZonacao DESC")
    LiveData<List<AvistamentoZonacaoAvencas>> getAllAvistamentoZonacaoAvencas();

    @Transaction
    @Query("SELECT * FROM avistamento_zonacao_avencas WHERE zona = :zona ORDER BY idAvistamentoZonacao ASC")
    LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAvistamentosZonacaoWithZona(String zona);
}
