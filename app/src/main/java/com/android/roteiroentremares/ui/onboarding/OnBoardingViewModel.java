package com.android.roteiroentremares.ui.onboarding;

import androidx.lifecycle.ViewModel;

import com.android.roteiroentremares.data.DataRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OnBoardingViewModel extends ViewModel {
    private DataRepository dataRepository;

    @Inject
    public OnBoardingViewModel (
            DataRepository dataRepository
    ) {
        this.dataRepository = dataRepository;
    }

    // For testing purposes, can delete after.
    public String getNome() {
        return dataRepository.getNome();
    }

    public boolean getOnBoarding() {
        return dataRepository.getOnBoarding();
    }

    public void setOnBoarding(boolean onBoarding) {
        dataRepository.setOnBoarding(onBoarding);
    }

    public void setTipoUtilizador(int tipoUtilizador) {
        dataRepository.setTipoUtilizador(tipoUtilizador);
    }

    public void setNome(String nome) {
        dataRepository.setNome(nome);
    }

    public void setEscola(String escola) {
        dataRepository.setEscola(escola);
    }

    public void setAnoEscolaridade(String anoEscolaridade) {
        dataRepository.setEscola(anoEscolaridade);
    }

    public void setAnoLectivo(String anoLectivo) {
        dataRepository.setEscola(anoLectivo);
    }
}
