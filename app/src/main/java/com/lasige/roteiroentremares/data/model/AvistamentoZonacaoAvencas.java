package com.lasige.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "avistamento_zonacao_avencas")
public class AvistamentoZonacaoAvencas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idAvistamentoZonacao;

    private int iteracao;

    private String zona;

    private String photoPath;

    public AvistamentoZonacaoAvencas(int iteracao, String zona, String photoPath) {
        this.iteracao = iteracao;
        this.zona = zona;
        this.photoPath = photoPath;
    }

    public int getIdAvistamentoZonacao() {
        return idAvistamentoZonacao;
    }

    public void setIdAvistamentoZonacao(int idAvistamentoZonacao) {
        this.idAvistamentoZonacao = idAvistamentoZonacao;
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
