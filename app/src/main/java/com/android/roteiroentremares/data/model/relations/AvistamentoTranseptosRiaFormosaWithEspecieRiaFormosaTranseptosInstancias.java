package com.android.roteiroentremares.data.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.roteiroentremares.data.model.AvistamentoTranseptosRiaFormosa;
import com.android.roteiroentremares.data.model.EspecieRiaFormosaTranseptosInstancias;

import java.io.Serializable;
import java.util.List;

public class AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias implements Serializable {

    @Embedded
    private AvistamentoTranseptosRiaFormosa avistamentoTranseptosRiaFormosa;

    @Relation(
            parentColumn = "idAvistamento",
            entityColumn = "idAvistamento"
    )
    private List<EspecieRiaFormosaTranseptosInstancias> especiesRiaFormosaTranseptosInstancias;

    public AvistamentoTranseptosRiaFormosa getAvistamentoTranseptosRiaFormosa() {
        return avistamentoTranseptosRiaFormosa;
    }

    public List<EspecieRiaFormosaTranseptosInstancias> getEspeciesRiaFormosaTranseptosInstancias() {
        return especiesRiaFormosaTranseptosInstancias;
    }

    public void setAvistamentoTranseptosRiaFormosa(AvistamentoTranseptosRiaFormosa avistamentoTranseptosRiaFormosa) {
        this.avistamentoTranseptosRiaFormosa = avistamentoTranseptosRiaFormosa;
    }

    public void setEspeciesRiaFormosaTranseptosInstancias(List<EspecieRiaFormosaTranseptosInstancias> especiesRiaFormosaTranseptosInstancias) {
        this.especiesRiaFormosaTranseptosInstancias = especiesRiaFormosaTranseptosInstancias;
    }
}
