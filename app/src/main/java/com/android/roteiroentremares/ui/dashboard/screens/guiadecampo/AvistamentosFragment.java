package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.ui.dashboard.adapters.artefactos.ArtefactoAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoPocasAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoZonacaoAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieAdapter;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoPocasDetails;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoZonacaoDetails;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentosChartsActivity;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosFragment extends Fragment {

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;
    private DashboardViewModel dashboardViewModel;

    // Adapters
    private AvistamentoPocasAdapter adapterPocas;
    private AvistamentoZonacaoAdapter adapterZonacao;

    // Views
    private LinearLayout linearLayoutIsEmpty;
    private TextView textViewIsEmptyMessage;
    private TextView textViewPocasTitle;
    private TextView textViewZonacaoTitle;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewPocas;
    private RecyclerView recyclerViewZonacao;
    private FloatingActionButton fabCharts;

    private boolean noPocas;
    private boolean noZonacao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avistamentos, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        noPocas = false;
        noZonacao = false;

        initViews(view);
        initRecyclerViews();

        return view;
    }

    private void initViews(View view) {
        linearLayoutIsEmpty = view.findViewById(R.id.linearlayout_isEmpty);
        textViewIsEmptyMessage = view.findViewById(R.id.textView_isEmpty_message);
        textViewPocasTitle = view.findViewById(R.id.textView_pocas_title);
        textViewZonacaoTitle = view.findViewById(R.id.textView_zonacao_title);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerViewPocas = view.findViewById(R.id.recyclerView_pocas);
        recyclerViewZonacao = view.findViewById(R.id.recyclerView_zonacao);
        fabCharts = view.findViewById(R.id.fab_charts);

        if (dashboardViewModel.getAvencasOrRiaFormosa() == 0) {
            // Avencas
            textViewIsEmptyMessage.setText("Poderás adicionar avistamentos durante o percurso Biodiversidade");
        } else {
            // Ria Formosa
            textViewIsEmptyMessage.setText("Poderás adicionar avistamentos durante os percursos Sapal, Intertidal Arenoso e Dunas");
        }
    }

    private void showMessageIfNoAvistamentos() {
        if (noPocas && noZonacao) {
            Toast.makeText(getActivity(), "There's no records.", Toast.LENGTH_SHORT).show();
            linearLayoutIsEmpty.setVisibility(View.VISIBLE);
            fabCharts.setVisibility(View.GONE);
        } else {
            linearLayoutIsEmpty.setVisibility(View.GONE);
            fabCharts.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerViews () {
        // RV Pocas
        recyclerViewPocas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPocas.setHasFixedSize(true);

        adapterPocas = new AvistamentoPocasAdapter(getContext());
        recyclerViewPocas.setAdapter(adapterPocas);

        guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias.size() == 0) {
                    textViewPocasTitle.setVisibility(View.GONE);
                    recyclerViewPocas.setVisibility(View.GONE);

                    noPocas = true;
                    showMessageIfNoAvistamentos();
                } else {
                    textViewPocasTitle.setVisibility(View.VISIBLE);
                    recyclerViewPocas.setVisibility(View.VISIBLE);

                    noPocas = false;
                    showMessageIfNoAvistamentos();

                    adapterPocas.setAvistamentos(avistamentoPocasAvencasWithEspecieAvencasPocasInstancias);
                }
            }
        });

        adapterPocas.setOnItemClickListener(new AvistamentoPocasAdapter.OnItemClickListener() {
            @Override
            public void onAvistamentoItemClick(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca) {
                // New activity with details
                Intent intent = new Intent(getActivity(), AvistamentoPocasDetails.class);
                intent.putExtra("avistamentoPoca", avistamentoPoca);
                startActivity(intent);
            }
        });

        // RV Zonacao
        recyclerViewZonacao.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewZonacao.setHasFixedSize(true);

        adapterZonacao = new AvistamentoZonacaoAdapter(getContext());
        recyclerViewZonacao.setAdapter(adapterZonacao);

        guiaDeCampoViewModel.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias.size() == 0) {
                    recyclerViewZonacao.setVisibility(View.GONE);
                    textViewZonacaoTitle.setVisibility(View.GONE);

                    noZonacao = true;

                    showMessageIfNoAvistamentos();

                } else {
                    recyclerViewZonacao.setVisibility(View.VISIBLE);
                    textViewZonacaoTitle.setVisibility(View.VISIBLE);

                    noZonacao = false;

                    showMessageIfNoAvistamentos();

                    adapterZonacao.setAvistamentos(avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias);
                }
            }
        });

        adapterZonacao.setOnItemClickListener(new AvistamentoZonacaoAdapter.OnItemClickListener() {
            @Override
            public void onAvistamentoItemClick(AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias avistamento) {
                Intent intent = new Intent(getActivity(), AvistamentoZonacaoDetails.class);
                intent.putExtra("avistamentoZonacao", avistamento);
                startActivity(intent);
            }
        });

        fabCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosChartsActivity.class);
                startActivity(intent);
            }
        });
    }
}