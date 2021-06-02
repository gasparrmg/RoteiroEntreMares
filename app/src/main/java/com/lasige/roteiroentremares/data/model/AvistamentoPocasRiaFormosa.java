package com.lasige.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "avistamento_pocas_riaformosa")
public class AvistamentoPocasRiaFormosa implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int idAvistamento;

    private String photoPath;

    public AvistamentoPocasRiaFormosa(int idAvistamento, String photoPath) {
        this.idAvistamento = idAvistamento;
        this.photoPath = photoPath;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public void setIdAvistamento(int idAvistamento) {
        this.idAvistamento = idAvistamento;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
