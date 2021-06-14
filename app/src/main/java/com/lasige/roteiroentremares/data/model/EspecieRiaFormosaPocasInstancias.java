package com.lasige.roteiroentremares.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "especie_pocas_instancias_table_riaformosa", foreignKeys = {
        @ForeignKey(entity = AvistamentoPocasRiaFormosa.class, parentColumns = "idAvistamento", childColumns = "idAvistamento", onDelete = ForeignKey.CASCADE)
})
public class EspecieRiaFormosaPocasInstancias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int especieRiaFormosaPocasInstanciasId;

    private int idAvistamento;

    @Embedded
    private EspecieRiaFormosa especieRiaFormosa;

    private boolean instancias;

    public EspecieRiaFormosaPocasInstancias(int idAvistamento, EspecieRiaFormosa especieRiaFormosa, boolean instancias) {
        this.idAvistamento = idAvistamento;
        this.especieRiaFormosa = especieRiaFormosa;
        this.instancias = instancias;
    }

    public int getEspecieRiaFormosaPocasInstanciasId() {
        return especieRiaFormosaPocasInstanciasId;
    }

    public void setEspecieRiaFormosaPocasInstanciasId(int especieRiaFormosaPocasInstanciasId) {
        this.especieRiaFormosaPocasInstanciasId = especieRiaFormosaPocasInstanciasId;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public EspecieRiaFormosa getEspecieRiaFormosa() {
        return especieRiaFormosa;
    }

    public boolean isInstancias() {
        return instancias;
    }
}
