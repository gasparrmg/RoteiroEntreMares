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

import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;

import java.util.List;

@Dao
public interface EspecieRiaFormosaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EspecieRiaFormosa especieRiaFormosa);

    @Update
    void update(EspecieRiaFormosa especieRiaFormosa);

    @Delete
    void delete(EspecieRiaFormosa especieRiaFormosa);

    @Query("DELETE FROM especie_table_riaformosa")
    void deleteAll();

    @Query("SELECT * FROM especie_table_riaformosa ORDER BY id ASC")
    LiveData<List<EspecieRiaFormosa>> getAll();

    @RawQuery(observedEntities = EspecieRiaFormosa.class)
    LiveData<List<EspecieRiaFormosa>> getWithQuery(SupportSQLiteQuery query);
}
