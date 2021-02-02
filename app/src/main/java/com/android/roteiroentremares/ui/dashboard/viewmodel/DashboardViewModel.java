package com.android.roteiroentremares.ui.dashboard.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DashboardViewModel extends ViewModel {
    private DataRepository dataRepository;

    private LiveData<List<Artefacto>> allArtefactos;

    @Inject
    public DashboardViewModel (
            DataRepository dataRepository
    ) {
        this.dataRepository = dataRepository;

        allArtefactos = dataRepository.getAllArtefactos();
    }

    /**
     * -------------------------------- SHARED PREFERENCES METHODS -------------------------------------------------
     */

    public boolean getOnBoarding() {
        return dataRepository.getOnBoarding();
    }

    public void setOnBoarding(boolean onBoarding) {
        dataRepository.setOnBoarding(onBoarding);
    }

    public int getTipoUtilizador() {
        return dataRepository.getTipoUtilizador();
    }

    public void setTipoUtilizador(int tipoUtilizador) {
        dataRepository.setTipoUtilizador(tipoUtilizador);
    }

    public String getNome() {
        return dataRepository.getNome();
    }

    public void setNome(String nome) {
        dataRepository.setNome(nome);
    }

    public String getEscola() {
        return dataRepository.getEscola();
    }

    public void setEscola(String escola) {
        dataRepository.setEscola(escola);
    }

    public String getAnoEscolaridade() {
        return dataRepository.getAnoEscolaridade();
    }

    public void setAnoEscolaridade(String anoEscolaridade) {
        dataRepository.setAnoEscolaridade(anoEscolaridade);
    }

    public String getAnoLectivo() {
        return dataRepository.getAnoLectivo();
    }

    public void setAnoLectivo(String anoLectivo) {
        dataRepository.setAnoLectivo(anoLectivo);
    }

    public String getCodigoTurma() {
        return dataRepository.getCodigoTurma();
    }

    public void setCodigoTurma(String codigoTurma) {
        dataRepository.setCodigoTurma(codigoTurma);
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
