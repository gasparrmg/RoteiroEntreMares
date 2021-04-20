package com.android.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "avistamento_dunas_riaformosa")
public class AvistamentoDunasRiaFormosa implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idAvistamentoDunas;

    private int iteracao;

    private String zona;

    private String photoPath;

    public AvistamentoDunasRiaFormosa(int iteracao, String zona, String photoPath) {
        this.iteracao = iteracao;
        this.zona = zona;
        this.photoPath = photoPath;
    }

    public int getIdAvistamentoDunas() {
        return idAvistamentoDunas;
    }

    public void setIdAvistamentoDunas(int idAvistamentoDunas) {
        this.idAvistamentoDunas = idAvistamentoDunas;
    }

    public int getIteracao() {
        return iteracao;
    }

    public void setIteracao(int iteracao) {
        this.iteracao = iteracao;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
