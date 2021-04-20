package com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GuiaDeCampoViewModel extends ViewModel {

    private DataRepository dataRepository;
    private SavedStateHandle savedStateHandle;

    private MutableLiveData<String> filterQuery = new MutableLiveData<>();
    private MutableLiveData<String> filterQueryRiaFormosa = new MutableLiveData<>();

    private LiveData<List<EspecieAvencas>> allEspecieAvencas;
    private LiveData<List<EspecieRiaFormosa>> allEspecieRiaFormosa;

    // Avencas
    private LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
    private LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
    private LiveData<List<AvistamentoZonacaoAvencas>> allAvistamentoZonacaoAvencas;

    // Ria Formosa Dunas
    private LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
    private LiveData<List<AvistamentoDunasRiaFormosa>> allAvistamentoDunasRiaFormosa;

    @Inject
    public GuiaDeCampoViewModel(
            SavedStateHandle savedStateHandle,
            DataRepository dataRepository
    ) {
        this.savedStateHandle = savedStateHandle;
        this.dataRepository = dataRepository;

        allEspecieAvencas = Transformations.switchMap(filterQuery,
                new Function<String, LiveData<List<EspecieAvencas>>>() {
                    @Override
                    public LiveData<List<EspecieAvencas>> apply(String input) {
                        return dataRepository.getFilteredEspecies(new SimpleSQLiteQuery(input));
                    }
                });

        allEspecieRiaFormosa = Transformations.switchMap(filterQueryRiaFormosa, new Function<String, LiveData<List<EspecieRiaFormosa>>>() {
            @Override
            public LiveData<List<EspecieRiaFormosa>> apply(String input) {
                return dataRepository.getFilteredRiaFormosaEspecies(new SimpleSQLiteQuery(input));
            }
        });

        allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias = dataRepository.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias();
        allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias = dataRepository.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias();
        allAvistamentoZonacaoAvencas = dataRepository.getAllAvistamentoZonacaoAvencas();

        allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias = dataRepository.getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias();
        allAvistamentoDunasRiaFormosa = dataRepository.getAllAvistamentoDunasRiaFormosa();
    }

    public void filterEspecies(String query) {
        filterQuery.postValue(query);
    }

    public LiveData<List<EspecieAvencas>> getAllEspecies() {
        return allEspecieAvencas;
    }

    public void filterEspeciesRiaFormosa(String query) {
        filterQueryRiaFormosa.postValue(query);
    }

    public LiveData<List<EspecieRiaFormosa>> getAllEspecieRiaFormosa() {
        return allEspecieRiaFormosa;
    }

    public LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias() {
        return allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
    }

    public LiveData<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(int idAvistamento) {
        return dataRepository.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(idAvistamento);
    }

    public void deleteAllAvistamentoPocasAvencas() {
        dataRepository.deleteAllAvistamentoPocasAvencas();
    }

    public void insertAvistamentoPocaWithInstanciasAvencas(
            int nrPoca,
            String tipoFundo,
            int profundidadeValue,
            String profundidadeUnit,
            int areaSuperficieValue,
            String areaSuperficieUnit,
            String photoPath,
            List<EspecieAvencas> especiesAvencas,
            int[] instancias
    ) {
        dataRepository.insertAvistamentoPocaWithInstanciasAvencas(
                nrPoca,
                tipoFundo,
                profundidadeValue,
                profundidadeUnit,
                areaSuperficieValue,
                areaSuperficieUnit,
                photoPath,
                especiesAvencas,
                instancias
        );
    }

    // Avencas Zonacao

    public LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias() {
        return allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
    }

    public LiveData<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> getAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona) {
        return dataRepository.getAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(iteracao, zona);
    }

    public LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAvistamentosZonacaoWithZona(String zona) {
        return dataRepository.getAvistamentosZonacaoWithZona(zona);
    }

    public void deleteAllAvistamentoZonacaoAvencas() {
        dataRepository.deleteAllAvistamentoZonacaoAvencas();
    }

    public void deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona) {
        dataRepository.deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(iteracao, zona);
    }

    public void insertAvistamentoZonacaoWithInstanciasAvencas(
            int nrIteracao,
            String zona,
            String photoPath,
            List<EspecieAvencas> especiesAvencas,
            int[] instancias
    ) {
        dataRepository.insertAvistamentoZonacaoWithInstanciasAvencas(
                nrIteracao,
                zona,
                photoPath,
                especiesAvencas,
                instancias
        );
    }

    public LiveData<List<AvistamentoZonacaoAvencas>> getAllAvistamentoZonacaoAvencas () {
        return allAvistamentoZonacaoAvencas;
    }

    // Ria Formosa Dunas

    public LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias() {
        return allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
    }

    public LiveData<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona) {
        return dataRepository.getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(iteracao, zona);
    }

    public LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAvistamentosDunasWithZona(String zona) {
        return dataRepository.getAvistamentosDunasWithZona(zona);
    }

    public void deleteAllAvistamentoDunasRiaFormosa() {
        dataRepository.deleteAllAvistamentoDunasRiaFormosa();
    }

    public void deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona) {
        dataRepository.deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(iteracao, zona);
    }

    public void insertAvistamentoDunasWithInstanciasRiaFormosa(
            int nrIteracao,
            String zona,
            String photoPath,
            List<EspecieRiaFormosa> especiesRiaFormosa,
            int[] instancias
    ) {
        dataRepository.insertAvistamentoDunasWithInstanciasRiaFormosa(
                nrIteracao,
                zona,
                photoPath,
                especiesRiaFormosa,
                instancias
        );
    }

    public LiveData<List<AvistamentoDunasRiaFormosa>> getAllAvistamentoDunasRiaFormosa() {
        return allAvistamentoDunasRiaFormosa;
    }
}
