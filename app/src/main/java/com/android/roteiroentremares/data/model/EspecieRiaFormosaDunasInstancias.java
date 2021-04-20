package com.android.roteiroentremares.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "especie_dunas_instancias_table_riaformosa", foreignKeys = {
        @ForeignKey(entity = AvistamentoDunasRiaFormosa.class, parentColumns = "idAvistamentoDunas", childColumns = "idAvistamentoDunas", onDelete = ForeignKey.CASCADE)
})
public class EspecieRiaFormosaDunasInstancias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int especieRiaFormosaDunasInstanciasId;

    private int idAvistamentoDunas;

    @Embedded
    private EspecieRiaFormosa especieRiaFormosa;

    private int instancias;

    public EspecieRiaFormosaDunasInstancias(int idAvistamentoDunas, EspecieRiaFormosa especieRiaFormosa, int instancias) {
        this.idAvistamentoDunas = idAvistamentoDunas;
        this.especieRiaFormosa = especieRiaFormosa;
        this.instancias = instancias;
    }

    public void setEspecieRiaFormosaDunasInstanciasId(int especieRiaFormosaDunasInstanciasId) {
        this.especieRiaFormosaDunasInstanciasId = especieRiaFormosaDunasInstanciasId;
    }

    public int getEspecieRiaFormosaDunasInstanciasId() {
        return especieRiaFormosaDunasInstanciasId;
    }

    public int getIdAvistamentoDunas() {
        return idAvistamentoDunas;
    }

    public EspecieRiaFormosa getEspecieRiaFormosa() {
        return especieRiaFormosa;
    }

    public int getInstancias() {
        return instancias;
    }
}
