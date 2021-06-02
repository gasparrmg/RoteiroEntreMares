package com.lasige.roteiroentremares.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "especie_transeptos_instancias_table_riaformosa", foreignKeys = {
        @ForeignKey(entity = AvistamentoTranseptosRiaFormosa.class, parentColumns = "idAvistamento", childColumns = "idAvistamento", onDelete = ForeignKey.CASCADE)
})
public class EspecieRiaFormosaTranseptosInstancias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int especieRiaFormosaTranseptosInstanciasId;

    private int idAvistamento;

    @Embedded
    private EspecieRiaFormosa especieRiaFormosa;

    private boolean instanciasExpostaPedra;

    private boolean instanciasInferiorPedra;

    private boolean instanciasSubstrato;

    private String photoPathEspecie;

    public EspecieRiaFormosaTranseptosInstancias(int idAvistamento, EspecieRiaFormosa especieRiaFormosa, boolean instanciasExpostaPedra, boolean instanciasInferiorPedra, boolean instanciasSubstrato, String photoPathEspecie) {
        this.idAvistamento = idAvistamento;
        this.especieRiaFormosa = especieRiaFormosa;
        this.instanciasExpostaPedra = instanciasExpostaPedra;
        this.instanciasInferiorPedra = instanciasInferiorPedra;
        this.instanciasSubstrato = instanciasSubstrato;
        this.photoPathEspecie = photoPathEspecie;
    }

    public int getEspecieRiaFormosaTranseptosInstanciasId() {
        return especieRiaFormosaTranseptosInstanciasId;
    }

    public void setEspecieRiaFormosaTranseptosInstanciasId(int especieRiaFormosaTranseptosInstanciasId) {
        this.especieRiaFormosaTranseptosInstanciasId = especieRiaFormosaTranseptosInstanciasId;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public EspecieRiaFormosa getEspecieRiaFormosa() {
        return especieRiaFormosa;
    }

    public boolean isInstanciasExpostaPedra() {
        return instanciasExpostaPedra;
    }

    public boolean isInstanciasInferiorPedra() {
        return instanciasInferiorPedra;
    }

    public boolean isInstanciasSubstrato() {
        return instanciasSubstrato;
    }

    public String getPhotoPathEspecie() {
        return photoPathEspecie;
    }
}
