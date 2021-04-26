package com.android.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "avistamento_transeptos_riaformosa")
public class AvistamentoTranseptosRiaFormosa implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int idAvistamento;

    public AvistamentoTranseptosRiaFormosa(int idAvistamento) {
        this.idAvistamento = idAvistamento;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public void setIdAvistamento(int idAvistamento) {
        this.idAvistamento = idAvistamento;
    }
}
