package com.lasige.roteiroentremares.data.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lasige.roteiroentremares.data.model.AvistamentoPocasRiaFormosa;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaPocasInstancias;

import java.io.Serializable;
import java.util.List;

public class AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias implements Serializable {

    @Embedded
    private AvistamentoPocasRiaFormosa avistamentoPocasRiaFormosa;

    @Relation(
            parentColumn = "idAvistamento",
            entityColumn = "idAvistamento"
    )
    private List<EspecieRiaFormosaPocasInstancias> especiesRiaFormosaPocasInstancias;

    public AvistamentoPocasRiaFormosa getAvistamentoPocasRiaFormosa() {
        return avistamentoPocasRiaFormosa;
    }

    public List<EspecieRiaFormosaPocasInstancias> getEspeciesRiaFormosaPocasInstancias() {
        return especiesRiaFormosaPocasInstancias;
    }

    public void setAvistamentoPocasRiaFormosa(AvistamentoPocasRiaFormosa avistamentoPocasRiaFormosa) {
        this.avistamentoPocasRiaFormosa = avistamentoPocasRiaFormosa;
    }

    public void setEspeciesRiaFormosaPocasInstancias(List<EspecieRiaFormosaPocasInstancias> especiesRiaFormosaPocasInstancias) {
        this.especiesRiaFormosaPocasInstancias = especiesRiaFormosaPocasInstancias;
    }
}
