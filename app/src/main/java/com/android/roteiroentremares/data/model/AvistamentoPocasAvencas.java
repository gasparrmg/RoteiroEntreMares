package com.android.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.roteiroentremares.util.GithubTypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "avistamento_pocas_avencas")
public class AvistamentoPocasAvencas implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int idAvistamento;

    private String tipoFundo;

    private int areaSuperficieValue;
    private String areaSuperficieUnit;

    private int profundidadeValue;
    private String profundidadeUnit;

    private String photoPath;

    public AvistamentoPocasAvencas(int idAvistamento, String tipoFundo, int profundidadeValue, String profundidadeUnit, int areaSuperficieValue, String areaSuperficieUnit, String photoPath) {
        this.idAvistamento = idAvistamento;
        this.tipoFundo = tipoFundo;
        this.areaSuperficieValue = areaSuperficieValue;
        this.areaSuperficieUnit = areaSuperficieUnit;
        this.profundidadeValue = profundidadeValue;
        this.profundidadeUnit = profundidadeUnit;
        this.photoPath = photoPath;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public void setIdAvistamento(int idAvistamento) {
        this.idAvistamento = idAvistamento;
    }

    public String getTipoFundo() {
        return tipoFundo;
    }

    public int getAreaSuperficieValue() {
        return areaSuperficieValue;
    }

    public String getAreaSuperficieUnit() {
        return areaSuperficieUnit;
    }

    public int getProfundidadeValue() {
        return profundidadeValue;
    }

    public String getProfundidadeUnit() {
        return profundidadeUnit;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setTipoFundo(String tipoFundo) {
        this.tipoFundo = tipoFundo;
    }

    public void setAreaSuperficieValue(int areaSuperficieValue) {
        this.areaSuperficieValue = areaSuperficieValue;
    }

    public void setAreaSuperficieUnit(String areaSuperficieUnit) {
        this.areaSuperficieUnit = areaSuperficieUnit;
    }

    public void setProfundidadeValue(int profundidadeValue) {
        this.profundidadeValue = profundidadeValue;
    }

    public void setProfundidadeUnit(String profundidadeUnit) {
        this.profundidadeUnit = profundidadeUnit;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
