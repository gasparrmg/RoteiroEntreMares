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
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieAdapter;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;
import com.reinaldoarrosi.android.querybuilder.sqlite.criteria.Criteria;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EspeciesFragment extends Fragment {

    private static final String QUERY_ALL_ESPECIES = "SELECT * FROM especie_table_avencas";

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Adapters
    private EspecieAdapter adapter;

    // Views
    private ProgressBar progressBar;
    private LinearLayout linearLayoutIsEmpty;
    private RecyclerView recyclerView;
    private FloatingActionButton fabRemoveFilter;
    private FloatingActionButton fabFilter;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especies, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        initViews(view);
        initCurrentFilters();

        adapter.setOnItemClickListener(new EspecieAdapter.OnItemClickListener() {
            @Override
            public void onAvencasItemClick(EspecieAvencas especieAvencas) {
                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("especie", especieAvencas);
                startActivity(intent);
            }
        });

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(getActivity(), EspecieFilterActivity.class);

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

        // TODO: check Spot -> Avencas or Ria Formosa
        adapter = new EspecieAdapter(getContext(), 0);
        recyclerView.setAdapter(adapter);

        query = QUERY_ALL_ESPECIES;
        guiaDeCampoViewModel.filterEspecies(query);

        guiaDeCampoViewModel.getAllEspecies().observe(getViewLifecycleOwner(), new Observer<List<EspecieAvencas>>() {
            @Override
            public void onChanged(List<EspecieAvencas> especieAvencas) {
                //set recycler view
                adapter.setEspeciesAvencas(especieAvencas);
                progressBar.setVisibility(View.GONE);

                if (especieAvencas.size() == 0) {
                    linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutIsEmpty.setVisibility(View.GONE);
                }

                if (!query.equals(QUERY_ALL_ESPECIES)) {
                    fabRemoveFilter.setVisibility(View.VISIBLE);
                } else {
                    fabRemoveFilter.setVisibility(View.GONE);
                }
            }
        });

        guiaDeCampoViewModel.filterEspecies("SELECT * FROM especie_table_avencas");
    }

    private void initCurrentFilters() {
        currentGrupo = -1;
        currentAnimalMovel = false;
        currentTentaculos = false;
        currentApendicesArticulados = false;
        currentSimetriaRadial = false;
        currentCarapacaCalcaria = false;
        currentPlacasCalcarias = false;
        currentConcha = false;
    }

    private void removeFilters() {
        query = QUERY_ALL_ESPECIES;
        guiaDeCampoViewModel.filterEspecies(query);

        initCurrentFilters();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ESPECIE_FILTER_REQUEST_CODE) {
            Log.d("FILTRAR_ESPECIES", "onActivityResult");
            if (resultCode == Activity.RESULT_OK) {
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

                /*
                if (filterGrupo.isEmpty()) {
                    query = "SELECT * FROM especie_table_avencas WHERE " +
                            "animalMovel = " + filterAnimalMovel + " AND " +
                            "tentaculos = " + filterTentaculos + " AND " +
                            "apendicesArticulados = " + filterApendicesarticulados + " AND " +
                            "simetriaRadial = " + filterSimetriaradial + " AND " +
                            "carapacaCalcaria = " + filterCarapacacalcaria + " AND " +
                            "placasCalcarias = " + filterPlacascalcarias + " AND " +
                            "concha = " + filterConcha;
                } else {
                    query = "SELECT * FROM especie_table_avencas WHERE " +
                            "animalMovel = " + filterAnimalMovel + " AND " +
                            "tentaculos = " + filterTentaculos + " AND " +
                            "apendicesArticulados = " + filterApendicesarticulados + " AND " +
                            "simetriaRadial = " + filterSimetriaradial + " AND " +
                            "carapacaCalcaria = " + filterCarapacacalcaria + " AND " +
                            "placasCalcarias = " + filterPlacascalcarias + " AND " +
                            "concha = " + filterConcha + " AND " +
                            "grupoInt = " + filterGrupo;
                }*/

            } else {
                Log.d("FILTRAR_ESPECIES", "onActivityResult -> NOT OKAY");
            }
        }
    }
}