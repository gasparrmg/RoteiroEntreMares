package com.lasige.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lasige.roteiroentremares.data.model.Artefacto;

import java.util.List;

@Dao
public interface ArtefactoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Artefacto artefacto);

    @Update
    void update(Artefacto artefacto);

    @Delete
    void delete(Artefacto artefacto);

    @Query("DELETE FROM artefacto_table")
    void deleteAll();

    @Query("SELECT * FROM artefacto_table ORDER BY id DESC")
    LiveData<List<Artefacto>> getAll();
}
