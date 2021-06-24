package com.lasige.roteiroentremares.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;

import java.util.List;

@Dao
public interface ArtefactoTurmaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArtefactoTurma artefactoTurma);

    @Update
    void update(ArtefactoTurma artefactoTurma);

    @Delete
    void delete(ArtefactoTurma artefactoTurma);

    @Query("DELETE FROM artefacto_turma_table")
    void deleteAll();

    @Query("SELECT * FROM artefacto_turma_table ORDER BY id DESC")
    List<ArtefactoTurma> getAllAlt();

    @Query("SELECT * FROM artefacto_turma_table ORDER BY id DESC")
    LiveData<List<ArtefactoTurma>> getAll();

    @Query("SELECT idString FROM artefacto_table")
    LiveData<List<String>> getAllIdStrings();
}
