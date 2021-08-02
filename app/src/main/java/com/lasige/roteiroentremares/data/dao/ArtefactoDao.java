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
    List<Artefacto> getAllArtefactos();

    @Query("SELECT * FROM artefacto_table WHERE shared = 1 ORDER BY id DESC")
    List<Artefacto> getAllSharedArtefactos();

    @Query("SELECT * FROM artefacto_table ORDER BY id DESC")
    LiveData<List<Artefacto>> getAll();

    @Query("SELECT idString FROM artefacto_table")
    LiveData<List<String>> getAllIdStrings();

    @Query("SELECT * FROM artefacto_table WHERE date BETWEEN :from AND :to")
    List<Artefacto> getArtefactoFromTo(long from, long to);

    @Query("SELECT * FROM artefacto_table WHERE shared = 1 AND date BETWEEN :from AND :to")
    List<Artefacto> getSharedArtefactoFromTo(long from, long to);
}
