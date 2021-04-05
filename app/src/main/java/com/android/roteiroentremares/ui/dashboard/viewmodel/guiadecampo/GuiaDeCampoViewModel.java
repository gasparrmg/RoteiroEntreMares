package com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencas;
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

    private LiveData<List<EspecieAvencas>> allEspecieAvencas;
    // private LiveData<List<EspecieRiaFormosa>> allEspecieRiaFormosa;

    private LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
    private LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
    private LiveData<List<AvistamentoZonacaoAvencas>> allAvistamentoZonacaoAvencas;

    @Inject
    public GuiaDeCampoViewModel(
            SavedStateHandle savedStateHandle,
            DataRepository dataRepository
    ) {
        this.savedStateHandle = savedStateHandle;
        this.dataRepository = dataRepository;

        // allEspecieAvencas = dataRepository.getAllEspecieAvencas();
        // // allEspecieRiaFormosa = dataRepository.getAllEspecieRiaFormosa();

        allEspecieAvencas = Transformations.switchMap(filterQuery,
                new Function<String, LiveData<List<EspecieAvencas>>>() {
                    @Override
                    public LiveData<List<EspecieAvencas>> apply(String input) {
                        return dataRepository.getFilteredEspecies(new SimpleSQLiteQuery(input));
                    }
                });

        allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias = dataRepository.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias();
        allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias = dataRepository.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias();
        allAvistamentoZonacaoAvencas = dataRepository.getAllAvistamentoZonacaoAvencas();
    }

    public void filterEspecies(String query) {
        filterQuery.postValue(query);
    }

    public LiveData<List<EspecieAvencas>> getAllEspecies() {
        // IF Avencas -> return allEspeciesAvencas; else -> return allEspeciesRiaFormosa

        return allEspecieAvencas;
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
}
