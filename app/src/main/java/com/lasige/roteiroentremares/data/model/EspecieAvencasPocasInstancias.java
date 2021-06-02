package com.lasige.roteiroentremares.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "especie_pocas_instancias_table_avencas", foreignKeys = {
        @ForeignKey(entity = AvistamentoPocasAvencas.class, parentColumns = "idAvistamento", childColumns = "idAvistamento", onDelete = ForeignKey.CASCADE)
})
public class EspecieAvencasPocasInstancias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int especieAvencasPocasInstanciasId;

    private int idAvistamento;

    @Embedded
    private EspecieAvencas especieAvencas;

    private int instancias;

    public EspecieAvencasPocasInstancias(int idAvistamento, EspecieAvencas especieAvencas, int instancias) {
        this.idAvistamento = idAvistamento;
        this.especieAvencas = especieAvencas;
        this.instancias = instancias;
    }

    public int getEspecieAvencasPocasInstanciasId() {
        return especieAvencasPocasInstanciasId;
    }

    public void setEspecieAvencasPocasInstanciasId(int especieAvencasPocasInstanciasId) {
        this.especieAvencasPocasInstanciasId = especieAvencasPocasInstanciasId;
    }

    public int getIdAvistamento() {
        return idAvistamento;
    }

    public EspecieAvencas getEspecieAvencas() {
        return especieAvencas;
    }

    public int getInstancias() {
        return instancias;
    }
}
