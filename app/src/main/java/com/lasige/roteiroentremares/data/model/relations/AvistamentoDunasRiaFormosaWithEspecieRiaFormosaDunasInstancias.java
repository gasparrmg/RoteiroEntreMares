package com.lasige.roteiroentremares.data.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lasige.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaDunasInstancias;

import java.io.Serializable;
import java.util.List;

public class AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias implements Serializable {

    @Embedded
    private AvistamentoDunasRiaFormosa avistamentoDunasRiaFormosa;

    @Relation(
            parentColumn = "idAvistamentoDunas",
            entityColumn = "idAvistamentoDunas"
    )
    private List<EspecieRiaFormosaDunasInstancias> especiesRiaFormosaDunasInstancias;

    public AvistamentoDunasRiaFormosa getAvistamentoDunasRiaFormosa() {
        return avistamentoDunasRiaFormosa;
    }

    public void setAvistamentoDunasRiaFormosa(AvistamentoDunasRiaFormosa avistamentoDunasRiaFormosa) {
        this.avistamentoDunasRiaFormosa = avistamentoDunasRiaFormosa;
    }

    public List<EspecieRiaFormosaDunasInstancias> getEspeciesRiaFormosaDunasInstancias() {
        return especiesRiaFormosaDunasInstancias;
    }

    public void setEspeciesRiaFormosaDunasInstancias(List<EspecieRiaFormosaDunasInstancias> especiesRiaFormosaDunasInstancias) {
        this.especiesRiaFormosaDunasInstancias = especiesRiaFormosaDunasInstancias;
    }
}
