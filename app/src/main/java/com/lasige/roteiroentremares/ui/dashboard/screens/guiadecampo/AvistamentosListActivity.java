package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoPocasAdapter;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaDunasAdapter;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaPocasAdapter;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaTranseptoAdapter;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoZonacaoAdapter;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoPocasDetails;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoRiaFormosaDunasDetails;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoRiaFormosaPocasDetails;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoRiaFormosaTranseptosDetails;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentoZonacaoDetails;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.AvistamentosRiaFormosaChartsActivity;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.AvistamentosRiaFormosaPocasTranseptosChartsActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosListActivity extends AppCompatActivity {

    public static final String TIPO_AVISTAMENTO_KEY = "TIPO_AVISTAMENTO_KEY";

    public static final String TIPO_AVISTAMENTO_AVENCAS_POCAS = "AVENCAS_POCAS";
    public static final String TIPO_AVISTAMENTO_AVENCAS_ZONACAO = "AVENCAS_ZONACAO";
    public static final String TIPO_AVISTAMENTO_RIAFORMOSA_DUNAS = "RIAFORMOSA_DUNAS";
    public static final String TIPO_AVISTAMENTO_RIAFORMOSA_POCAS = "RIAFORMOSA_POCAS";
    public static final String TIPO_AVISTAMENTO_RIAFORMOSA_TRANSEPTOS = "RIAFORMOSA_TRANSEPTOS";

    private String currentTipoAvistamento;

    private AvistamentoPocasAdapter adapterAvencasPocas;
    private AvistamentoZonacaoAdapter adapterAvencasZonacao;
    private AvistamentoRiaFormosaDunasAdapter adapterRiaFormosaDunas;
    private AvistamentoRiaFormosaPocasAdapter adapterRiaFormosaPocas;
    private AvistamentoRiaFormosaTranseptoAdapter adapterRiaFormosaTranseptos;

    private RecyclerView recyclerView;

    private FloatingActionButton fabCharts;

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamentos_list_avencas_pocas);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        fabCharts = findViewById(R.id.fab_charts);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        currentTipoAvistamento = getIntent().getStringExtra(TIPO_AVISTAMENTO_KEY);
        Log.d("AVISTAMENTOS_LIST", "currentTipoAvistamento -> " + currentTipoAvistamento);

        if (currentTipoAvistamento.equals(TIPO_AVISTAMENTO_AVENCAS_POCAS)) {
            initToolbar("Poças de maré");

            adapterAvencasPocas = new AvistamentoPocasAdapter(this);
            recyclerView.setAdapter(adapterAvencasPocas);

            guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().observe(this, new Observer<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                    adapterAvencasPocas.setAvistamentos(avistamentoPocasAvencasWithEspecieAvencasPocasInstancias);
                }
            });

            adapterAvencasPocas.setOnItemClickListener(new AvistamentoPocasAdapter.OnItemClickListener() {
                @Override
                public void onAvistamentoItemClick(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentoPocasDetails.class);
                    intent.putExtra("avistamentoPoca", avistamentoPoca);
                    startActivity(intent);
                }
            });
        } else if (currentTipoAvistamento.equals(TIPO_AVISTAMENTO_AVENCAS_ZONACAO)) {
            initToolbar("Zonação");

            adapterAvencasZonacao = new AvistamentoZonacaoAdapter(this);
            recyclerView.setAdapter(adapterAvencasZonacao);

            guiaDeCampoViewModel.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias().observe(this, new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                    adapterAvencasZonacao.setAvistamentos(avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias);
                }
            });

            adapterAvencasZonacao.setOnItemClickListener(new AvistamentoZonacaoAdapter.OnItemClickListener() {
                @Override
                public void onAvistamentoItemClick(AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias avistamento) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentoZonacaoDetails.class);
                    intent.putExtra("avistamentoZonacao", avistamento);
                    startActivity(intent);
                }
            });
        } else if (currentTipoAvistamento.equals(TIPO_AVISTAMENTO_RIAFORMOSA_DUNAS)) {
            initToolbar("Dunas");

            fabCharts.setVisibility(View.VISIBLE);

            adapterRiaFormosaDunas = new AvistamentoRiaFormosaDunasAdapter(this);
            recyclerView.setAdapter(adapterRiaFormosaDunas);

            guiaDeCampoViewModel.getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias().observe(this, new Observer<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                    adapterRiaFormosaDunas.setAvistamentos(avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias);
                }
            });

            adapterRiaFormosaDunas.setOnItemClickListener(new AvistamentoRiaFormosaDunasAdapter.OnItemClickListener() {
                @Override
                public void onAvistamentoItemClick(AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias avistamento) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentoRiaFormosaDunasDetails.class);
                    intent.putExtra("avistamentoRiaFormosaDunas", avistamento);
                    startActivity(intent);
                }
            });

            fabCharts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentosRiaFormosaChartsActivity.class);
                    startActivity(intent);
                }
            });
        } else if (currentTipoAvistamento.equals(TIPO_AVISTAMENTO_RIAFORMOSA_POCAS)) {
            initToolbar("Poças de maré");

            fabCharts.setVisibility(View.VISIBLE);

            adapterRiaFormosaPocas = new AvistamentoRiaFormosaPocasAdapter(this);
            recyclerView.setAdapter(adapterRiaFormosaPocas);

            guiaDeCampoViewModel.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias().observe(this, new Observer<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias) {
                    Log.d("AvistamentosListAcitivity", "Size: " + avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias.size());
                    adapterRiaFormosaPocas.setAvistamentos(avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias);
                }
            });

            adapterRiaFormosaPocas.setOnItemClickListener(new AvistamentoRiaFormosaPocasAdapter.OnItemClickListener() {
                @Override
                public void onAvistamentoItemClick(AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias avistamentoPoca) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentoRiaFormosaPocasDetails.class);
                    intent.putExtra("avistamentoRiaFormosaPoca", avistamentoPoca);
                    startActivity(intent);
                }
            });

            fabCharts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentosRiaFormosaPocasTranseptosChartsActivity.class);
                    intent.putExtra(AvistamentosRiaFormosaPocasTranseptosChartsActivity.CHART_KEY, AvistamentosRiaFormosaPocasTranseptosChartsActivity.CHART_TYPE_POCAS);
                    startActivity(intent);
                }
            });
        } else if (currentTipoAvistamento.equals(TIPO_AVISTAMENTO_RIAFORMOSA_TRANSEPTOS)) {
            initToolbar("Transeptos");

            fabCharts.setVisibility(View.VISIBLE);

            adapterRiaFormosaTranseptos = new AvistamentoRiaFormosaTranseptoAdapter(this);
            recyclerView.setAdapter(adapterRiaFormosaTranseptos);

            guiaDeCampoViewModel.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias().observe(this, new Observer<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                    adapterRiaFormosaTranseptos.setAvistamentos(avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias);
                }
            });

            adapterRiaFormosaTranseptos.setOnItemClickListener(new AvistamentoRiaFormosaTranseptoAdapter.OnItemClickListener() {
                @Override
                public void onAvistamentoItemClick(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTransepto) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentoRiaFormosaTranseptosDetails.class);
                    intent.putExtra("avistamentoRiaFormosaTransepto", avistamentoTransepto);
                    startActivity(intent);
                }
            });

            fabCharts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AvistamentosListActivity.this, AvistamentosRiaFormosaPocasTranseptosChartsActivity.class);
                    intent.putExtra(AvistamentosRiaFormosaPocasTranseptosChartsActivity.CHART_KEY, AvistamentosRiaFormosaPocasTranseptosChartsActivity.CHART_TYPE_TRANSPETOS);
                    startActivity(intent);
                }
            });

        } else {
            // throw error
        }
    }

    private void initToolbar(String title) {
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}