package com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DashboardViewModel extends ViewModel {
    private SavedStateHandle savedStateHandle;
    private DataRepository dataRepository;

    private LiveData<List<Artefacto>> allArtefactos;
    private LiveData<List<EspecieAvencas>> allEspecieAvencas;

    @Inject
    public DashboardViewModel (
            SavedStateHandle savedStateHandle,
            DataRepository dataRepository
    ) {
        this.savedStateHandle = savedStateHandle;
        this.dataRepository = dataRepository;

        allArtefactos = dataRepository.getAllArtefactos();
        allEspecieAvencas = dataRepository.getAllEspecieAvencas();
    }

    public void deleteAllProgress(Activity activity) {
        dataRepository.deleteAllProgress(activity);
    }

    /**
     * -------------------------------- SHARED PREFERENCES METHODS -------------------------------------------------
     */

    public int getChangeToAvencasOrRiaFormosa() {
        return dataRepository.getChangeToAvencasOrRiaFormosa();
    }

    public void setChangeToAvencasOrRiaFormosa(int zonaToChange) {
        dataRepository.setChangeToAvencasOrRiaFormosa(zonaToChange);
    }

    public int getAvencasOrRiaFormosa() {
        return dataRepository.getAvencasOrRiaFormosa();
    }

    public void setAvencasOrRiaFormosa(int zona) {
        dataRepository.setAvencasOrRiaFormosa(zona);
    }

    public boolean isHistoriasPassadoFinished() {
        return dataRepository.isHistoriasPassadoFinished();
    }

    public void setHistoriasPassadoAsFinished() {
        dataRepository.setHistoriasPassadoAsFinished();
    }

    public boolean isEQuandoAMareSobeFinished() {
        return dataRepository.isEQuandoAMareSobeFinished();
    }

    public void setEQuandoAMareSobeAsFinished() {
        dataRepository.setEQuandoAMareSobeAsFinished();
    }

    public boolean isNaoFiquesPorAquiFinished() {
        return dataRepository.isNaoFiquesPorAquiFinished();
    }

    public void setNaoFiquesPorAquiAsFinished() {
        dataRepository.setNaoFiquesPorAquiAsFinished();
    }

    public boolean isImpactosFinished() {
        return dataRepository.isImpactosFinished();
    }

    public void setImpactosAsFinished() {
        dataRepository.setImpactosAsFinished();
    }

    public boolean isImpactosCapturaExcessivaFinished() {
        return dataRepository.isImpactosCapturaExcessivaFinished();
    }

    public void setImpactosCapturaExcessivaAsFinished() {
        dataRepository.setImpactosCapturaExcessivaAsFinished();
    }

    public boolean isImpactosPisoteioFinished() {
        return dataRepository.isImpactosPisoteioFinished();
    }

    public void setImpactosPisoteioAsFinished() {
        dataRepository.setImpactosPisoteioAsFinished();
    }

    public boolean isImpactosPoluicaoFinished() {
        return dataRepository.isImpactosPoluicaoFinished();
    }

    public void setImpactosPoluicaoAsFinished() {
        dataRepository.setImpactosPoluicaoAsFinished();
    }

    public boolean isImpactosTempAguaFinished() {
        return dataRepository.isImpactosTempAguaFinished();
    }

    public void setImpactosTempAguaAsFinished() {
        dataRepository.setImpactosTempAguaAsFinished();
    }

    public boolean isImpactos2Answered() {
        return dataRepository.isImpactos2Answered();
    }

    public void setImpactos2AsAnswered() {
        dataRepository.setImpactos2AsAnswered();
    }

    public boolean isImpactos3Answered() {
        return dataRepository.isImpactos3Answered();
    }

    public void setImpactos3AsAnswered() {
        dataRepository.setImpactos3AsAnswered();
    }

    public boolean isImpactos6Answered() {
        return dataRepository.isImpactos6Answered();
    }

    public void setImpactos6AsAnswered() {
        dataRepository.setImpactos6AsAnswered();
    }

    public void setBiodiversidadeMicrohabitatsAsFinished() {
        dataRepository.setBiodiversidadeMicrohabitatsAsFinished();
    }

    public boolean isBiodiversidadeMicrohabitatsFinished() {
        return dataRepository.isBiodiversidadeMicrohabitatsFinished();
    }


    public void setBiodiversidadeMicrohabitatsCanaisAsFinished() {
        dataRepository.setBiodiversidadeMicrohabitatsCanaisAsFinished();
    }

    public boolean isBiodiversidadeMicrohabitatsCanaisFinished() {
        return dataRepository.isBiodiversidadeMicrohabitatsCanaisFinished();
    }

    public void setBiodiversidadeMicrohabitatsFendasAsFinished() {
        dataRepository.setBiodiversidadeMicrohabitatsFendasAsFinished();
    }

    public boolean isBiodiversidadeMicrohabitatsFendasFinished() {
        return dataRepository.isBiodiversidadeMicrohabitatsFendasFinished();
    }

    public void setBiodiversidadeMicrohabitatsPocasAsFinished() {
        dataRepository.setBiodiversidadeMicrohabitatsPocasAsFinished();
    }

    public boolean isBiodiversidadeMicrohabitatsPocasFinished() {
        return dataRepository.isBiodiversidadeMicrohabitatsPocasFinished();
    }

    public void setBiodiversidadeZonacaoAsFinished() {
        dataRepository.setBiodiversidadeZonacaoAsFinished();
    }

    public boolean isBiodiversidadeZonacaoFinished() {
        return dataRepository.isBiodiversidadeZonacaoFinished();
    }

    public void setBiodiversidadeZonacaoSupralitoralAsFinished() {
        dataRepository.setBiodiversidadeZonacaoSupralitoralAsFinished();
    }

    public boolean isBiodiversidadeZonacaoSupralitoralFinished() {
        return dataRepository.isBiodiversidadeZonacaoSupralitoralFinished();
    }

    public void setBiodiversidadeZonacaoMediolitoralAsFinished() {
        dataRepository.setBiodiversidadeZonacaoMediolitoralAsFinished();
    }

    public boolean isBiodiversidadeZonacaoMediolitoralFinished() {
        return dataRepository.isBiodiversidadeZonacaoMediolitoralFinished();
    }

    public void setBiodiversidadeZonacaoInfralitoralAsFinished() {
        dataRepository.setBiodiversidadeZonacaoInfralitoralAsFinished();
    }

    public boolean isBiodiversidadeZonacaoInfralitoralFinished() {
        return dataRepository.isBiodiversidadeZonacaoInfralitoralFinished();
    }

    public void setBiodiversidadeInteracoesPredacaoAsFinished() {
        dataRepository.setBiodiversidadeInteracoesPredacaoAsFinished();
    }

    public boolean isBiodiversidadeInteracoesPredacaoFinished() {
        return dataRepository.isBiodiversidadeInteracoesPredacaoFinished();
    }

    public void setBiodiversidadeInteracoesHerbivoriaAsFinished() {
        dataRepository.setBiodiversidadeInteracoesHerbivoriaAsFinished();
    }

    public boolean isBiodiversidadeInteracoesHerbivoriaFinished() {
        return dataRepository.isBiodiversidadeInteracoesHerbivoriaFinished();
    }

    public void setBiodiversidadeInteracoesCompeticaoAsFinished() {
        dataRepository.setBiodiversidadeInteracoesCompeticaoAsFinished();
    }

    public boolean isBiodiversidadeInteracoesCompeticaoFinished() {
        return dataRepository.isBiodiversidadeInteracoesCompeticaoFinished();
    }

    public void setBiodiversidadeInteracoesAsFinished() {
        dataRepository.setBiodiversidadeInteracoesAsFinished();
    }

    public boolean isBiodiversidadeInteracoesFinished() {
        return dataRepository.isBiodiversidadeInteracoesFinished();
    }

    public boolean isBiodiversidadeFinished() {
        return dataRepository.isBiodiversidadeFinished();
    }

    public void setBiodiversidadeAsFinished() {
        dataRepository.setBiodiversidadeAsFinished();
    }

    // Ria Formosa

    public boolean isRiaFormosaSapalFinished() {
        return dataRepository.isRiaFormosaSapalFinished();
    }

    public void setRiaFormosaSapalAsFinished() {
        dataRepository.setRiaFormosaSapalAsFinished();
    }

    public boolean isRiaFormosaPradariasMarinhasFinished() {
        return dataRepository.isRiaFormosaPradariasMarinhasFinished();
    }

    public void setRiaFormosaPradariasMarinhasAsFinished() {
        dataRepository.setRiaFormosaPradariasMarinhasAsFinished();
    }

    public boolean isRiaFormosaNaoFiquesPorAquiFinished() {
        return dataRepository.isRiaFormosaNaoFiquesPorAquiFinished();
    }

    public void setRiaFormosaNaoFiquesPorAquiAsFinished() {
        dataRepository.setRiaFormosaNaoFiquesPorAquiAsFinished();
    }

    public boolean isRiaFormosaDunasDunaEmbrionariaFinished() {
        return dataRepository.isRiaFormosaDunasDunaEmbrionariaFinished();
    }

    public void setRiaFormosaDunasDunaEmbrionariaAsFinished() {
        dataRepository.setRiaFormosaDunasDunaEmbrionariaAsFinished();
    }

    public boolean isRiaFormosaDunasDunaPrimariaFinished() {
        return dataRepository.isRiaFormosaDunasDunaPrimariaFinished();
    }

    public void setRiaFormosaDunasDunaPrimariaAsFinished() {
        dataRepository.setRiaFormosaDunasDunaPrimariaAsFinished();
    }

    public boolean isRiaFormosaDunasZonaInterdunarFinished() {
        return dataRepository.isRiaFormosaDunasZonaInterdunarFinished();
    }

    public void setRiaFormosaDunasZonaInterdunarAsFinished() {
        dataRepository.setRiaFormosaDunasZonaInterdunarAsFinished();
    }

    public boolean isRiaFormosaDunasDunaSecundariaFinished() {
        return dataRepository.isRiaFormosaDunasDunaSecundariaFinished();
    }

    public void setRiaFormosaDunasDunaSecundariaAsFinished() {
        dataRepository.setRiaFormosaDunasDunaSecundariaAsFinished();
    }

    public boolean isRiaFormosaDunasFinished() {
        return dataRepository.isRiaFormosaDunasFinished();
    }

    public void setRiaFormosaDunasAsFinished() {
        dataRepository.setRiaFormosaDunasAsFinished();
    }

    public boolean isRiaFormosaIntertidalArenosoPredacaoFinished() {
        return dataRepository.isRiaFormosaIntertidalArenosoPredacaoFinished();
    }

    public void setRiaFormosaIntertidalArenosoPredacaoAsFinished() {
        dataRepository.setRiaFormosaIntertidalArenosoPredacaoAsFinished();
    }

    public boolean isRiaFormosaIntertidalArenosoCompeticaoFinished() {
        return dataRepository.isRiaFormosaIntertidalArenosoPredacaoFinished();
    }

    public void setRiaFormosaIntertidalArenosoCompeticaoAsFinished() {
        dataRepository.setRiaFormosaIntertidalArenosoPredacaoAsFinished();
    }

    public boolean isRiaFormosaIntertidalArenosoFinished() {
        return dataRepository.isRiaFormosaIntertidalArenosoFinished();
    }

    public void setRiaFormosaIntertidalArenosoAsFinished() {
        dataRepository.setRiaFormosaIntertidalArenosoAsFinished();
    }

    public boolean isMaresFinished() {
        return dataRepository.isMaresFinished();
    }

    public void setMaresAsFinished() {
        dataRepository.setMaresAsFinished();
    }



    // GENERAL

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

    public boolean getSharedLocationArtefactos() {
        return dataRepository.getShareLocationArtefactos();
    }

    public void setSharedLocationArtefactos(boolean sharedLocationArtefactos) {
        dataRepository.setShareLocationArtefactos(sharedLocationArtefactos);
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

    public LiveData<List<EspecieAvencas>> getAllEspecieAvencas() {
        return allEspecieAvencas;
    }
}
