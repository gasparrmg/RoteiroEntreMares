package com.lasige.roteiroentremares.data.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lasige.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.lasige.roteiroentremares.data.model.EspecieAvencasPocasInstancias;

import java.io.Serializable;
import java.util.List;

public class AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias implements Serializable {

    @Embedded
    private AvistamentoPocasAvencas avistamentoPocasAvencas;

    @Relation(
            parentColumn = "idAvistamento",
            entityColumn = "idAvistamento"
    )
    private List<EspecieAvencasPocasInstancias> especiesAvencasPocasInstancias;

    public AvistamentoPocasAvencas getAvistamentoPocasAvencas() {
        return avistamentoPocasAvencas;
    }

    public List<EspecieAvencasPocasInstancias> getEspeciesAvencasPocasInstancias() {
        return especiesAvencasPocasInstancias;
    }

    public void setAvistamentoPocasAvencas(AvistamentoPocasAvencas avistamentoPocasAvencas) {
        this.avistamentoPocasAvencas = avistamentoPocasAvencas;
    }

    public void setEspeciesAvencasPocasInstancias(List<EspecieAvencasPocasInstancias> especiesAvencasPocasInstancias) {
        this.especiesAvencasPocasInstancias = especiesAvencasPocasInstancias;
    }
}
