package com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;
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
    }

    public void filterEspecies(String query) {
        filterQuery.postValue(query);
    }

    public LiveData<List<EspecieAvencas>> getAllEspecies() {
        // IF Avencas -> return allEspeciesAvencas; else -> return allEspeciesRiaFormosa

        return allEspecieAvencas;
    }
}
