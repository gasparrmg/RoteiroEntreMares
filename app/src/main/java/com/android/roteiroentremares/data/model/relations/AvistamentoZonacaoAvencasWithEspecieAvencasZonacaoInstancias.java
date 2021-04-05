package com.android.roteiroentremares.data.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;

import java.io.Serializable;
import java.util.List;

public class AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias implements Serializable {

    @Embedded
    private AvistamentoZonacaoAvencas avistamentoZonacaoAvencas;

    @Relation(
            parentColumn = "idAvistamentoZonacao",
            entityColumn = "idAvistamentoZonacao"
    )
    private List<EspecieAvencasZonacaoInstancias> especiesAvencasZonacaoInstancias;

    public AvistamentoZonacaoAvencas getAvistamentoZonacaoAvencas() {
        return avistamentoZonacaoAvencas;
    }

    public void setAvistamentoZonacaoAvencas(AvistamentoZonacaoAvencas avistamentoZonacaoAvencas) {
        this.avistamentoZonacaoAvencas = avistamentoZonacaoAvencas;
    }

    public List<EspecieAvencasZonacaoInstancias> getEspeciesAvencasZonacaoInstancias() {
        return especiesAvencasZonacaoInstancias;
    }

    public void setEspeciesAvencasZonacaoInstancias(List<EspecieAvencasZonacaoInstancias> especiesAvencasZonacaoInstancias) {
        this.especiesAvencasZonacaoInstancias = especiesAvencasZonacaoInstancias;
    }
}
