package com.lasige.roteiroentremares.util;

import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;

public class ArtefactoConverter {
    public static ArtefactoTurma toArtefactoTurma(Artefacto artefacto, String nome) {
        return new ArtefactoTurma(
                artefacto.getIdString(),
                nome,
                artefacto.getTitle(),
                artefacto.getContent(),
                artefacto.getType(),
                artefacto.getDescription(),
                artefacto.getDate(),
                artefacto.getLatitude(),
                artefacto.getLongitude(),
                artefacto.getCodigoTurma()
        );
    }
}
