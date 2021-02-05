package com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ArtefactosViewModel extends ViewModel {

    private DataRepository dataRepository;
    private SavedStateHandle savedStateHandle;

    private LiveData<List<Artefacto>> allArtefactos;

    @Inject
    public ArtefactosViewModel(
            SavedStateHandle savedStateHandle,
            DataRepository dataRepository
    ) {
        this.savedStateHandle = savedStateHandle;
        this.dataRepository = dataRepository;

        allArtefactos = dataRepository.getAllArtefactos();
    }

    public String getCodigoTurma() {
        return dataRepository.getCodigoTurma();
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
}
