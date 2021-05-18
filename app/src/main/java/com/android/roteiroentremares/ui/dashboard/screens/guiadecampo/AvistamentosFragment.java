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

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosFragment extends Fragment {

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;
    private DashboardViewModel dashboardViewModel;

    // Adapters
    /*private AvistamentoPocasAdapter adapterPocas;
    private AvistamentoZonacaoAdapter adapterZonacao;*/

    // Views
    private LinearLayout linearLayoutIsEmpty;
    private TextView textViewIsEmptyMessage;
    private TextView textViewTitle;
    private ProgressBar progressBar;
    private FloatingActionButton fabCharts;

    // Avencas
    private MaterialCardView cardViewPocas;
    private MaterialCardView cardViewZonacao;
    private MaterialCardView cardViewRiaFormosaDunas;
    private MaterialCardView cardViewRiaFormosaPocas;
    private MaterialCardView cardViewRiaFormosaTranseptos;


    private boolean noPocas;
    private boolean noZonacao;
    private boolean noRiaFormosaDunas;
    private boolean noRiaFormosaPocas;
    private boolean noRiaFormosaTranseptos;

    private int avencasOrRiaFormosa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View view = inflater.inflate(R.layout.fragment_avistamentos, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        avencasOrRiaFormosa = dashboardViewModel.getAvencasOrRiaFormosa();

        View view = inflater.inflate(R.layout.fragment_avistamentos_tipos_avencas, container, false);

        noPocas = false;
        noZonacao = false;
        noRiaFormosaDunas = false;
        noRiaFormosaPocas = false;
        noRiaFormosaTranseptos = false;

        if (dashboardViewModel.getAvencasOrRiaFormosa() == 0) {
            // Avencas

            initViews(view);
            initAvencas();

        } else if (dashboardViewModel.getAvencasOrRiaFormosa() == 1) {
            // Ria Formosa
            initViews(view);
            initRiaFormosa();
        }

        return view;
    }

    private void initViews(View view) {
        linearLayoutIsEmpty = view.findViewById(R.id.linearlayout_isEmpty);
        textViewIsEmptyMessage = view.findViewById(R.id.textView_isEmpty_message);
        textViewTitle = view.findViewById(R.id.textView_title);
        progressBar = view.findViewById(R.id.progress_bar);

        fabCharts = view.findViewById(R.id.fab_charts);

        if (avencasOrRiaFormosa == 0) {
            fabCharts.setVisibility(View.VISIBLE);
        } else {
            fabCharts.setVisibility(View.GONE);
        }

        cardViewPocas = view.findViewById(R.id.cardview_avistamento_pocasmare);
        cardViewZonacao = view.findViewById(R.id.cardview_avistamento_zonacao);
        cardViewRiaFormosaDunas = view.findViewById(R.id.cardview_avistamento_riaformosa_dunas);
        cardViewRiaFormosaPocas = view.findViewById(R.id.cardview_avistamento_riaformosa_pocas);
        cardViewRiaFormosaTranseptos = view.findViewById(R.id.cardview_avistamento_riaformosa_transeptos);

        cardViewPocas.setVisibility(View.GONE);
        cardViewZonacao.setVisibility(View.GONE);
        cardViewRiaFormosaDunas.setVisibility(View.GONE);
        cardViewRiaFormosaPocas.setVisibility(View.GONE);
        cardViewRiaFormosaTranseptos.setVisibility(View.GONE);

        if (dashboardViewModel.getAvencasOrRiaFormosa() == 0) {
            // Avencas
            textViewIsEmptyMessage.setText("Poderás adicionar avistamentos durante o percurso Biodiversidade");
        } else {
            // Ria Formosa
            textViewIsEmptyMessage.setText("Poderás adicionar avistamentos durante os percursos Intertidal Arenoso e Dunas");
        }

        cardViewPocas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosListActivity.class);
                intent.putExtra(AvistamentosListActivity.TIPO_AVISTAMENTO_KEY, AvistamentosListActivity.TIPO_AVISTAMENTO_AVENCAS_POCAS);
                startActivity(intent);
            }
        });

        cardViewZonacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosListActivity.class);
                intent.putExtra(AvistamentosListActivity.TIPO_AVISTAMENTO_KEY, AvistamentosListActivity.TIPO_AVISTAMENTO_AVENCAS_ZONACAO);
                startActivity(intent);
            }
        });

        cardViewRiaFormosaDunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosListActivity.class);
                intent.putExtra(AvistamentosListActivity.TIPO_AVISTAMENTO_KEY, AvistamentosListActivity.TIPO_AVISTAMENTO_RIAFORMOSA_DUNAS);
                startActivity(intent);
            }
        });

        cardViewRiaFormosaPocas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosListActivity.class);
                intent.putExtra(AvistamentosListActivity.TIPO_AVISTAMENTO_KEY, AvistamentosListActivity.TIPO_AVISTAMENTO_RIAFORMOSA_POCAS);
                startActivity(intent);
            }
        });

        cardViewRiaFormosaTranseptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosListActivity.class);
                intent.putExtra(AvistamentosListActivity.TIPO_AVISTAMENTO_KEY, AvistamentosListActivity.TIPO_AVISTAMENTO_RIAFORMOSA_TRANSEPTOS);
                startActivity(intent);
            }
        });
    }

    private void showMessageIfNoAvistamentos() {
        if (avencasOrRiaFormosa == 0) {
            if (noPocas && noZonacao) {
                linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.GONE);
                fabCharts.setVisibility(View.GONE);
            } else {
                textViewTitle.setVisibility(View.VISIBLE);
                linearLayoutIsEmpty.setVisibility(View.GONE);
                fabCharts.setVisibility(View.VISIBLE);
            }
        } else {
            if (noRiaFormosaDunas && noRiaFormosaPocas && noRiaFormosaTranseptos) {
                linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.GONE);
            } else {
                textViewTitle.setVisibility(View.VISIBLE);
                linearLayoutIsEmpty.setVisibility(View.GONE);
            }
        }
    }

    private void initRiaFormosa() {
        guiaDeCampoViewModel.getAllAvistamentoDunasRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoDunasRiaFormosa>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosa> avistamentoDunasRiaFormosas) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoDunasRiaFormosas.size() == 0) {
                    cardViewRiaFormosaDunas.setVisibility(View.GONE);
                    noRiaFormosaDunas = true;
                } else {
                    cardViewRiaFormosaDunas.setVisibility(View.VISIBLE);
                    noRiaFormosaDunas = false;
                }

                //guiaDeCampoViewModel.getAllAvistamentoDunasRiaFormosa().removeObserver(this);

                showMessageIfNoAvistamentos();
            }
        });

        guiaDeCampoViewModel.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias.size() == 0) {
                    cardViewRiaFormosaPocas.setVisibility(View.GONE);
                    noRiaFormosaPocas = true;
                } else {
                    cardViewRiaFormosaPocas.setVisibility(View.VISIBLE);
                    noRiaFormosaPocas = false;
                }

                //guiaDeCampoViewModel.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias().removeObserver(this);

                showMessageIfNoAvistamentos();
            }
        });

        guiaDeCampoViewModel.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias.size() == 0) {
                    cardViewRiaFormosaTranseptos.setVisibility(View.GONE);
                    noRiaFormosaTranseptos = true;
                } else {
                    cardViewRiaFormosaTranseptos.setVisibility(View.VISIBLE);
                    noRiaFormosaTranseptos = false;
                }

                //guiaDeCampoViewModel.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias().removeObserver(this);

                showMessageIfNoAvistamentos();
            }
        });
    }

    private void initAvencas () {
        // RV Pocas

        guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias.size() == 0) {
                    cardViewPocas.setVisibility(View.GONE);

                    noPocas = true;
                } else {
                    cardViewPocas.setVisibility(View.VISIBLE);

                    noPocas = false;
                }

                // guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().removeObserver(this);

                showMessageIfNoAvistamentos();
            }
        });

        /*adapterPocas.setOnItemClickListener(new AvistamentoPocasAdapter.OnItemClickListener() {
            @Override
            public void onAvistamentoItemClick(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca) {
                // New activity with details
                Intent intent = new Intent(getActivity(), AvistamentoPocasDetails.class);
                intent.putExtra("avistamentoPoca", avistamentoPoca);
                startActivity(intent);
            }
        });*/

        // RV Zonacao
        guiaDeCampoViewModel.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                progressBar.setVisibility(View.GONE);

                if (avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias.size() == 0) {
                    cardViewZonacao.setVisibility(View.GONE);
                    noZonacao = true;
                } else {
                    cardViewZonacao.setVisibility(View.VISIBLE);
                    noZonacao = false;
                }

                // guiaDeCampoViewModel.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias().removeObserver(this);

                showMessageIfNoAvistamentos();
            }
        });

        /*adapterZonacao.setOnItemClickListener(new AvistamentoZonacaoAdapter.OnItemClickListener() {
            @Override
            public void onAvistamentoItemClick(AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias avistamento) {
                Intent intent = new Intent(getActivity(), AvistamentoZonacaoDetails.class);
                intent.putExtra("avistamentoZonacao", avistamento);
                startActivity(intent);
            }
        });*/

        fabCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosChartsActivity.class);
                startActivity(intent);
            }
        });
    }
}