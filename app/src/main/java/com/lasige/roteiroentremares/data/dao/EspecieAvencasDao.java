package com.lasige.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lasige.roteiroentremares.data.model.EspecieAvencas;

import java.util.List;

@Dao
public interface EspecieAvencasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EspecieAvencas especieAvencas);

    @Update
    void update(EspecieAvencas especieAvencas);

    @Delete
    void delete(EspecieAvencas especieAvencas);

    @Query("DELETE FROM especie_table_avencas")
    void deleteAll();

    @Query("SELECT * FROM especie_table_avencas ORDER BY id ASC")
    LiveData<List<EspecieAvencas>> getAll();

    @RawQuery(observedEntities = EspecieAvencas.class)
    LiveData<List<EspecieAvencas>> getFilteredEspecies(SupportSQLiteQuery query);
}
