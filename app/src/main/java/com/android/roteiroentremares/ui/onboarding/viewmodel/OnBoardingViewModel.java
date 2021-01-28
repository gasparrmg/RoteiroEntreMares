package com.android.roteiroentremares.ui.onboarding.viewmodel;

import androidx.lifecycle.ViewModel;

import com.android.roteiroentremares.data.repository.DataRepository;

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

    public boolean getOnBoarding() {
        return dataRepository.getOnBoarding();
    }

    public void setOnBoarding(boolean onBoarding) {
        dataRepository.setOnBoarding(onBoarding);
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

    public void setEscola(String escola) {
        dataRepository.setEscola(escola);
    }

    public void setAnoEscolaridade(String anoEscolaridade) {
        dataRepository.setAnoEscolaridade(anoEscolaridade);
    }

    public void setAnoLectivo(String anoLectivo) {
        dataRepository.setAnoLectivo(anoLectivo);
    }
}
