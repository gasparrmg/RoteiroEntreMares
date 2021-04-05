package com.android.roteiroentremares.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "especie_zonacao_instancias_table_avencas", foreignKeys = {
        @ForeignKey(entity = AvistamentoZonacaoAvencas.class, parentColumns = "idAvistamentoZonacao", childColumns = "idAvistamentoZonacao", onDelete = ForeignKey.CASCADE)
})
public class EspecieAvencasZonacaoInstancias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int especieAvencasZonacaoInstanciasId;

    private int idAvistamentoZonacao;

    @Embedded
    private EspecieAvencas especieAvencas;

    private int instancias;

    public EspecieAvencasZonacaoInstancias(int idAvistamentoZonacao, EspecieAvencas especieAvencas, int instancias) {
        this.idAvistamentoZonacao = idAvistamentoZonacao;
        this.especieAvencas = especieAvencas;
        this.instancias = instancias;
    }

    public void setEspecieAvencasZonacaoInstanciasId(int especieAvencasZonacaoInstanciasId) {
        this.especieAvencasZonacaoInstanciasId = especieAvencasZonacaoInstanciasId;
    }

    public int getEspecieAvencasZonacaoInstanciasId() {
        return especieAvencasZonacaoInstanciasId;
    }

    public int getIdAvistamentoZonacao() {
        return idAvistamentoZonacao;
    }

    public EspecieAvencas getEspecieAvencas() {
        return especieAvencas;
    }

    public int getInstancias() {
        return instancias;
    }
}
