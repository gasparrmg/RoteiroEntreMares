package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQueryBuilder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieRiaFormosaAdapter;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;
import com.reinaldoarrosi.android.querybuilder.sqlite.criteria.Criteria;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EspeciesFragment extends Fragment {

    private static final String QUERY_ALL_ESPECIES_AVENCAS = "SELECT * FROM especie_table_avencas";
    private static final String QUERY_ALL_ESPECIES_RIAFORMOSA = "SELECT * FROM especie_table_riaformosa";

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;
    private DashboardViewModel dashboardViewModel;

    // Adapters
    private EspecieAdapter adapterAvencas;
    private EspecieRiaFormosaAdapter adapterRiaFormosa;

    // Views
    private ProgressBar progressBar;
    private LinearLayout linearLayoutIsEmpty;
    private RecyclerView recyclerView;
    private FloatingActionButton fabRemoveFilter;
    private FloatingActionButton fabFilter;

    // Vars
    private int avencasOrRiaFormosa; // Avencas 0, RiaFormosa 1
    private String query;

    // Current filters
    private int currentGrupo;
    private boolean currentAnimalMovel;
    private boolean currentTentaculos;
    private boolean currentApendicesArticulados;
    private boolean currentSimetriaRadial;
    private boolean currentCarapacaCalcaria;
    private boolean currentPlacasCalcarias;
    private boolean currentConcha;

    private String currentZona;
    private String currentTipo;
    private boolean isAvesLimicolas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especies, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        avencasOrRiaFormosa = dashboardViewModel.getAvencasOrRiaFormosa();

        initViews(view);
        initCurrentFilters();

        if (avencasOrRiaFormosa == 0) {

            adapterAvencas.setOnItemClickListener(new EspecieAdapter.OnItemClickListener() {
                @Override
                public void onAvencasItemClick(EspecieAvencas especieAvencas) {
                    Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                    intent.putExtra("avencasOrRiaFormosa", avencasOrRiaFormosa);
                    intent.putExtra("especie", especieAvencas);
                    startActivity(intent);
                }
            });

            fabFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent filterIntent = new Intent(getActivity(), EspecieFilterActivity.class);

                    filterIntent.putExtra("avencasOrRiaFormosa", avencasOrRiaFormosa);

                    // Pass current filters to the activity
                    filterIntent.putExtra("ESPECIE_FILTER_GRUPO", currentGrupo);
                    filterIntent.putExtra("ESPECIE_FILTER_ANIMALMOVEL", currentAnimalMovel);
                    filterIntent.putExtra("ESPECIE_FILTER_TENTACULOS", currentTentaculos);
                    filterIntent.putExtra("ESPECIE_FILTER_APENDICESARTICULADOS", currentApendicesArticulados);
                    filterIntent.putExtra("ESPECIE_FILTER_SIMETRIARADIAL", currentSimetriaRadial);
                    filterIntent.putExtra("ESPECIE_FILTER_CARAPACACALCARIA", currentCarapacaCalcaria);
                    filterIntent.putExtra("ESPECIE_FILTER_PLACASCALCARIAS", currentPlacasCalcarias);
                    filterIntent.putExtra("ESPECIE_FILTER_CONCHA", currentConcha);

                    startActivityForResult(filterIntent, Constants.ESPECIE_FILTER_REQUEST_CODE);
                }
            });

        } else {

            adapterRiaFormosa.setOnItemClickListener(new EspecieRiaFormosaAdapter.OnItemClickListener() {
                @Override
                public void onRiaFormosaItemClick(EspecieRiaFormosa especieRiaFormosa) {
                    Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                    intent.putExtra("avencasOrRiaFormosa", avencasOrRiaFormosa);
                    intent.putExtra("especie", especieRiaFormosa);
                    startActivity(intent);
                }
            });

            fabFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent filterIntent = new Intent(getActivity(), EspecieFilterActivity.class);

                    filterIntent.putExtra("avencasOrRiaFormosa", avencasOrRiaFormosa);

                    // Pass current filters to the activity
                    filterIntent.putExtra("ESPECIE_FILTER_ZONA", currentZona);
                    filterIntent.putExtra("ESPECIE_FILTER_TIPO", currentTipo);
                    filterIntent.putExtra("ESPECIE_FILTER_ISAVESLIMICOLAS", isAvesLimicolas);

                    startActivityForResult(filterIntent, Constants.ESPECIE_FILTER_REQUEST_CODE);
                }
            });

        }

        fabRemoveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFilters();
            }
        });

        return view;
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        fabRemoveFilter = view.findViewById(R.id.fab_remove_filter);
        fabFilter = view.findViewById(R.id.fab_filter);
        linearLayoutIsEmpty = view.findViewById(R.id.linearlayout_isEmpty);

        recyclerView = view.findViewById(R.id.recyclerView_especies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        if (avencasOrRiaFormosa == 0) {
            adapterAvencas = new EspecieAdapter(getContext(), 0);
            recyclerView.setAdapter(adapterAvencas);

            query = QUERY_ALL_ESPECIES_AVENCAS;
            guiaDeCampoViewModel.filterEspecies(query);

            guiaDeCampoViewModel.getAllEspecies().observe(getViewLifecycleOwner(), new Observer<List<EspecieAvencas>>() {
                @Override
                public void onChanged(List<EspecieAvencas> especieAvencas) {
                    //set recycler view
                    adapterAvencas.setEspeciesAvencas(especieAvencas);
                    progressBar.setVisibility(View.GONE);

                    if (especieAvencas.size() == 0) {
                        linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutIsEmpty.setVisibility(View.GONE);
                    }

                    if (!query.equals(QUERY_ALL_ESPECIES_AVENCAS)) {
                        fabRemoveFilter.setVisibility(View.VISIBLE);
                    } else {
                        fabRemoveFilter.setVisibility(View.GONE);
                    }
                }
            });

            guiaDeCampoViewModel.filterEspecies("SELECT * FROM especie_table_avencas");
        } else {
            adapterRiaFormosa = new EspecieRiaFormosaAdapter(getContext(), 1);
            recyclerView.setAdapter(adapterRiaFormosa);

            query = QUERY_ALL_ESPECIES_RIAFORMOSA;
            guiaDeCampoViewModel.filterEspeciesRiaFormosa(query);

            guiaDeCampoViewModel.getAllEspecieRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<EspecieRiaFormosa>>() {
                @Override
                public void onChanged(List<EspecieRiaFormosa> especieRiaFormosas) {
                    adapterRiaFormosa.setEspecies(especieRiaFormosas);
                    progressBar.setVisibility(View.GONE);

                    if (especieRiaFormosas.size() == 0) {
                        linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutIsEmpty.setVisibility(View.GONE);
                    }

                    if (!query.equals(QUERY_ALL_ESPECIES_RIAFORMOSA)) {
                        fabRemoveFilter.setVisibility(View.VISIBLE);
                    } else {
                        fabRemoveFilter.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void initCurrentFilters() {
        if (avencasOrRiaFormosa == 0) {
            currentGrupo = -1;
            currentAnimalMovel = false;
            currentTentaculos = false;
            currentApendicesArticulados = false;
            currentSimetriaRadial = false;
            currentCarapacaCalcaria = false;
            currentPlacasCalcarias = false;
            currentConcha = false;
        } else {
            currentZona = null;
            currentTipo = null;
            isAvesLimicolas = false;
        }
    }

    private void removeFilters() {
        if (avencasOrRiaFormosa == 0) {
            query = QUERY_ALL_ESPECIES_AVENCAS;
            guiaDeCampoViewModel.filterEspecies(query);
        } else {
            query = QUERY_ALL_ESPECIES_RIAFORMOSA;

            guiaDeCampoViewModel.filterEspeciesRiaFormosa(query); // to change
        }

        initCurrentFilters();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ESPECIE_FILTER_REQUEST_CODE) {
            Log.d("FILTRAR_ESPECIES", "onActivityResult");
            if (resultCode == Activity.RESULT_OK) {
                if (avencasOrRiaFormosa == 0) {
                    Log.d("FILTRAR_ESPECIES", "onActivityResult -> OK");

                    currentGrupo = data.getIntExtra("ESPECIE_FILTER_GRUPO", -1);
                    currentAnimalMovel = data.getBooleanExtra("ESPECIE_FILTER_ANIMALMOVEL", false);
                    currentTentaculos = data.getBooleanExtra("ESPECIE_FILTER_TENTACULOS", false);
                    currentApendicesArticulados = data.getBooleanExtra("ESPECIE_FILTER_APENDICESARTICULADOS", false);
                    currentSimetriaRadial = data.getBooleanExtra("ESPECIE_FILTER_SIMETRIARADIAL", false);
                    currentCarapacaCalcaria = data.getBooleanExtra("ESPECIE_FILTER_CARAPACACALCARIA", false);
                    currentPlacasCalcarias = data.getBooleanExtra("ESPECIE_FILTER_PLACASCALCARIAS", false);
                    currentConcha = data.getBooleanExtra("ESPECIE_FILTER_CONCHA", false);

                    QueryBuilder queryBuilder = new QueryBuilder();
                    queryBuilder.select("*").from("especie_table_avencas");

                    if (currentGrupo != -1) {
                        queryBuilder.whereAnd(Criteria.equals("grupoInt", currentGrupo));
                    }

                    if (currentAnimalMovel) {
                        queryBuilder.whereAnd(Criteria.equals("animalMovel", 1));
                    }

                    if (currentTentaculos) {
                        queryBuilder.whereAnd(Criteria.equals("tentaculos", 1));
                    }

                    if (currentApendicesArticulados) {
                        queryBuilder.whereAnd(Criteria.equals("apendicesArticulados", 1));
                    }

                    if (currentSimetriaRadial) {
                        queryBuilder.whereAnd(Criteria.equals("simetriaRadial", 1));
                    }

                    if (currentCarapacaCalcaria) {
                        queryBuilder.whereAnd(Criteria.equals("carapacaCalcaria", 1));
                    }

                    if (currentPlacasCalcarias) {
                        queryBuilder.whereAnd(Criteria.equals("placasCalcarias", 1));
                    }

                    if (currentConcha) {
                        queryBuilder.whereAnd(Criteria.equals("concha", 1));
                    }

                    query = queryBuilder.toDebugSqlString();

                    Log.d("FILTRAR_ESPECIES", "query: " + query);

                    guiaDeCampoViewModel.filterEspecies(query);
                } else {
                    currentZona = data.getStringExtra("ESPECIE_FILTER_ZONA");
                    currentTipo = data.getStringExtra("ESPECIE_FILTER_TIPO");
                    isAvesLimicolas = data.getBooleanExtra("ESPECIE_FILTER_ISAVESLIMICOLAS", false);

                    QueryBuilder queryBuilder = new QueryBuilder();
                    queryBuilder.select("*").from("especie_table_riaformosa");

                    if (currentZona != null && !currentZona.isEmpty()) {
                        queryBuilder.whereAnd(Criteria.equals("zona", currentZona));
                    }

                    if (currentTipo != null && !currentTipo.isEmpty()) {
                        queryBuilder.whereAnd(Criteria.equals("tipo", currentTipo));
                    }

                    if (isAvesLimicolas) {
                        queryBuilder.whereAnd(Criteria.equals("grupo", "Aves Limícolas (Chordata)"));
                    }

                    query = queryBuilder.toDebugSqlString();

                    Log.d("FILTRAR_ESPECIES", "query: " + query);

                    guiaDeCampoViewModel.filterEspeciesRiaFormosa(query);
                }

            } else {
                Log.d("FILTRAR_ESPECIES", "onActivityResult -> NOT OKAY");
            }
        }
    }
}