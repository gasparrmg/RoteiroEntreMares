package com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ArtefactosViewModel extends ViewModel {

    private DataRepository dataRepository;
    private SavedStateHandle savedStateHandle;

    private LiveData<List<Artefacto>> allArtefactos;
    private LiveData<List<ArtefactoTurma>> allArtefactosTurma;

    @Inject
    public ArtefactosViewModel(
            SavedStateHandle savedStateHandle,
            DataRepository dataRepository
    ) {
        this.savedStateHandle = savedStateHandle;
        this.dataRepository = dataRepository;

        allArtefactos = dataRepository.getAllArtefactos();
        allArtefactosTurma = dataRepository.getAllArtefactosTurma();
    }

    public String getCodigoTurma() {
        return dataRepository.getCodigoTurma();
    }

    public int getTipoUtilizador() {
        return dataRepository.getTipoUtilizador();
    }

    public boolean getSharedLocationArtefactos() {
        return dataRepository.getShareLocationArtefactos();
    }

    /**
     * -------------------------------- LOCAL DATABASE METHODS -------------------------------------------------
     */

    public void insertArtefacto(Artefacto artefacto) {
        dataRepository.insertArtefacto(artefacto);
    }

    public void updateArtefacto(Artefacto artefacto) {
        dataRepository.updateArtefacto(artefacto);
    }

    public void deleteArtefacto(Artefacto artefacto) {
        dataRepository.deleteArtefacto(artefacto);
    }

    public void deleteAllArtefacto() {
        dataRepository.deleteAllArtefacto();
    }

    public LiveData<List<Artefacto>> getAllArtefactos() {
        return allArtefactos;
    }

    public LiveData<List<ArtefactoTurma>> getAllArtefactosTurma() {
        return allArtefactosTurma;
    }

    public void deleteArtefactoTurma(ArtefactoTurma artefactoTurma) {
        dataRepository.deleteArtefactoTurma(artefactoTurma);
    }
}
