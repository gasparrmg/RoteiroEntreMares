package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosRiaFormosaChartsActivity extends AppCompatActivity {

    private static final String TAG = "AvistamentosRiaFormosaChartsActivity";

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    private TextView textViewPresenceTitle;
    private TextView textViewDistributionTitle;

    private LineChart lineChartDistribution;
    private BarChart barChartPresence;

    private LinearLayout linearLayoutIsEmpty;

    private List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentosDunaEmbrionaria;
    private List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentosDunaPrimaria;
    private List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentosZonaInterdunar;
    private List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentosDunaSecundaria;

    private ArrayList<String> nomesEspecies;

    private boolean gotDataFromDunaEmbrionaria;
    private boolean gotDataFromDunaPrimaria;
    private boolean gotDataFromZonaInterdunar;
    private boolean gotDataFromDunaSecundaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamentos_charts);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        initToolbar();

        lineChartDistribution = findViewById(R.id.lineChart_distribution);
        barChartPresence = findViewById(R.id.barChart_presence);
        textViewPresenceTitle = findViewById(R.id.textView_presence_title);
        textViewDistributionTitle = findViewById(R.id.textView_distribution_title);
        linearLayoutIsEmpty = findViewById(R.id.linearlayout_isEmpty);

        gotDataFromDunaEmbrionaria = false;
        gotDataFromDunaPrimaria = false;
        gotDataFromZonaInterdunar = false;
        gotDataFromDunaSecundaria = false;

        nomesEspecies = new ArrayList<>();

        getDataFromDB();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Gráficos");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initChartData() {
        int numberOfGroups = 0;

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();

        double somaEspecie;
        double avg;

        if (avistamentosDunaEmbrionaria.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> embrionariaValues = new ArrayList<>();
            ArrayList<BarEntry> embrionariaBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosDunaEmbrionaria.get(0).getEspeciesRiaFormosaDunasInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosDunaEmbrionaria.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosDunaEmbrionaria.get(j).getEspeciesRiaFormosaDunasInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosDunaEmbrionaria.size();
                embrionariaValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    embrionariaBarValues.add(new BarEntry(i, 1));
                } else {
                    embrionariaBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosDunaEmbrionaria.get(0).getEspeciesRiaFormosaDunasInstancias().size()) {
                    nomesEspecies.add(avistamentosDunaEmbrionaria.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                    Log.d(TAG, "Supralitoral -> Adicionada a espécie " + avistamentosDunaEmbrionaria.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosDunaEmbrionaria.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum() +
                        " teve uma média de " + avg + " na zona Duna Embrionaria");
            }

            LineDataSet dunaEmbrionariaDataSet = new LineDataSet(embrionariaValues, "Duna Embrionária");
            dunaEmbrionariaDataSet.setFillAlpha(110);
            dunaEmbrionariaDataSet.setColor(Color.BLUE);
            dunaEmbrionariaDataSet.setLineWidth(2f);

            lineDataSets.add(dunaEmbrionariaDataSet);

            // Bar Chart
            BarDataSet dunaEmbrionariaBarDataSet = new BarDataSet(embrionariaBarValues, "Duna Embrionária");
            dunaEmbrionariaBarDataSet.setColor(Color.BLUE);

            barDataSets.add(dunaEmbrionariaBarDataSet);
        }

        if (avistamentosDunaPrimaria.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> dunaPrimariaValues = new ArrayList<>();
            ArrayList<BarEntry> dunaPrimariaBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosDunaPrimaria.get(0).getEspeciesRiaFormosaDunasInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosDunaPrimaria.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosDunaPrimaria.get(j).getEspeciesRiaFormosaDunasInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosDunaPrimaria.size();
                dunaPrimariaValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    dunaPrimariaBarValues.add(new BarEntry(i, 1));
                } else {
                    dunaPrimariaBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosDunaPrimaria.get(0).getEspeciesRiaFormosaDunasInstancias().size()) {
                    nomesEspecies.add(avistamentosDunaPrimaria.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                }
            }

            LineDataSet dunaPrimariaDataSet = new LineDataSet(dunaPrimariaValues, "Duna primária");
            dunaPrimariaDataSet.setFillAlpha(110);
            dunaPrimariaDataSet.setColor(Color.RED);
            dunaPrimariaDataSet.setLineWidth(2f);

            lineDataSets.add(dunaPrimariaDataSet);

            // Bar Chart
            BarDataSet dunaPrimariaBarDataSet = new BarDataSet(dunaPrimariaBarValues, "Duna primária");
            dunaPrimariaBarDataSet.setColor(Color.RED);

            barDataSets.add(dunaPrimariaBarDataSet);
        }

        if (avistamentosZonaInterdunar.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> zonaInterdunarValues = new ArrayList<>();
            ArrayList<BarEntry> zonaInterdunarBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosZonaInterdunar.get(0).getEspeciesRiaFormosaDunasInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosZonaInterdunar.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosZonaInterdunar.get(j).getEspeciesRiaFormosaDunasInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosZonaInterdunar.size();
                zonaInterdunarValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    zonaInterdunarBarValues.add(new BarEntry(i, 1));
                } else {
                    zonaInterdunarBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosZonaInterdunar.get(0).getEspeciesRiaFormosaDunasInstancias().size()) {
                    nomesEspecies.add(avistamentosZonaInterdunar.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                }
            }

            LineDataSet zonaInterdunarDataSet = new LineDataSet(zonaInterdunarValues, "Zona interdunar");
            zonaInterdunarDataSet.setFillAlpha(110);
            zonaInterdunarDataSet.setColor(Color.GREEN);
            zonaInterdunarDataSet.setLineWidth(2f);

            lineDataSets.add(zonaInterdunarDataSet);

            // Bar Chart
            BarDataSet zonaInterdunarBarDataSet = new BarDataSet(zonaInterdunarBarValues, "Zona interdunar");
            zonaInterdunarBarDataSet.setColor(Color.GREEN);

            barDataSets.add(zonaInterdunarBarDataSet);
        }

        if (avistamentosDunaSecundaria.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> dunaSecundariaValues = new ArrayList<>();
            ArrayList<BarEntry> dunaSecundariaBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosDunaSecundaria.get(0).getEspeciesRiaFormosaDunasInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosDunaSecundaria.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosDunaSecundaria.get(j).getEspeciesRiaFormosaDunasInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosDunaSecundaria.size();
                dunaSecundariaValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    dunaSecundariaBarValues.add(new BarEntry(i, 1));
                } else {
                    dunaSecundariaBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosDunaSecundaria.get(0).getEspeciesRiaFormosaDunasInstancias().size()) {
                    nomesEspecies.add(avistamentosDunaSecundaria.get(0).getEspeciesRiaFormosaDunasInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                }
            }

            LineDataSet dunaSecundariaDataSet = new LineDataSet(dunaSecundariaValues, "Duna secundária");
            dunaSecundariaDataSet.setFillAlpha(110);
            dunaSecundariaDataSet.setColor(Color.MAGENTA);
            dunaSecundariaDataSet.setLineWidth(2f);

            lineDataSets.add(dunaSecundariaDataSet);

            // Bar Chart
            BarDataSet dunaSecundariaBarDataSet = new BarDataSet(dunaSecundariaBarValues, "Duna secundária");
            dunaSecundariaBarDataSet.setColor(Color.MAGENTA);

            barDataSets.add(dunaSecundariaBarDataSet);
        }

        if (numberOfGroups == 0) {
            // NO DATA, SHOW WARNING
            lineChartDistribution.setVisibility(View.GONE);
            textViewDistributionTitle.setVisibility(View.GONE);

            barChartPresence.setVisibility(View.GONE);
            textViewPresenceTitle.setVisibility(View.GONE);

            linearLayoutIsEmpty.setVisibility(View.VISIBLE);

            return;
        } else {
            linearLayoutIsEmpty.setVisibility(View.GONE);
        }

        lineChartDistribution.getLegend().setWordWrapEnabled(true);
        lineChartDistribution.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nomesEspecies));
        lineChartDistribution.getXAxis().setGranularity(1f);
        lineChartDistribution.getXAxis().setLabelRotationAngle(-90);
        Description description = new Description();
        description.setText("");
        lineChartDistribution.setDescription(description);
        lineChartDistribution.getDescription().setTextSize(14f);
        lineChartDistribution.getXAxis().setDrawGridLines(false);

        LineData chartData = new LineData(lineDataSets);
        lineChartDistribution.setData(chartData);
        lineChartDistribution.setVisibleXRangeMaximum(5);
        lineChartDistribution.notifyDataSetChanged();
        lineChartDistribution.invalidate();

        // Bar Chart

        if (barDataSets.size() > 1) {
            BarData barData = new BarData(barDataSets);
            barChartPresence.setData(barData);

            barChartPresence.getLegend().setWordWrapEnabled(true);

            barChartPresence.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nomesEspecies));
            barChartPresence.getXAxis().setCenterAxisLabels(true);
            barChartPresence.getXAxis().setGranularity(1f);
            barChartPresence.getXAxis().setGranularityEnabled(true);

            barChartPresence.getAxisLeft().setGranularityEnabled(true);
            barChartPresence.getAxisLeft().setGranularity(1f);
            barChartPresence.getAxisRight().setGranularityEnabled(true);
            barChartPresence.getAxisRight().setGranularity(1f);

            barChartPresence.setDragEnabled(true);
            barChartPresence.setVisibleXRangeMaximum(2);

            barChartPresence.setHorizontalScrollBarEnabled(true);
            barChartPresence.setScrollBarSize(20);

            float barWidth = 0.1f;
            float barSpace = 0.025f;
            //float groupSpace = 0.375f; // 5 groups
            float groupSpace = (1 - ((barSpace + barWidth) * numberOfGroups));
            barData.setBarWidth(barWidth);

            barChartPresence.getXAxis().setAxisMinimum(0);
            // barChartPresence.getXAxis().setAxisMaximum(0 + barChartPresence.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
            // barChartPresence.getAxisLeft().setAxisMinimum(0);
            barChartPresence.groupBars(0, groupSpace, barSpace);

            barChartPresence.setDescription(description);
            barChartPresence.getDescription().setTextSize(14f);
            barChartPresence.getXAxis().setDrawGridLines(false);

            barChartPresence.notifyDataSetChanged();
            barChartPresence.invalidate();
        } else {
            barChartPresence.setVisibility(View.GONE);
            textViewPresenceTitle.setVisibility(View.GONE);
        }
    }

    private void checkIfAllDataReceived() {
        Log.d(TAG, "Checking if all data received");

        if (gotDataFromDunaEmbrionaria &&
                gotDataFromDunaPrimaria &&
                gotDataFromZonaInterdunar &&
                gotDataFromDunaSecundaria) {
            Log.d(TAG, "Received all data. Starting initChartData()");
            initChartData();
        }
    }

    private void getDataFromDB() {

        guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna embrionária").observe(this, new Observer<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                avistamentosDunaEmbrionaria = avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;

                guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna embrionária").removeObserver(this);

                gotDataFromDunaEmbrionaria = true;
                checkIfAllDataReceived();
            }
        });

        guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna primária").observe(this, new Observer<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                avistamentosDunaPrimaria = avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;

                guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna primária").removeObserver(this);

                gotDataFromDunaPrimaria = true;
                checkIfAllDataReceived();
            }
        });

        guiaDeCampoViewModel.getAvistamentosDunasWithZona("Zona interdunar").observe(this, new Observer<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                avistamentosZonaInterdunar = avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;

                guiaDeCampoViewModel.getAvistamentosDunasWithZona("Zona interdunar").removeObserver(this);

                gotDataFromZonaInterdunar = true;
                checkIfAllDataReceived();
            }
        });

        guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna secundária").observe(this, new Observer<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                avistamentosDunaSecundaria = avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;

                guiaDeCampoViewModel.getAvistamentosDunasWithZona("Duna secundária").removeObserver(this);

                gotDataFromDunaSecundaria = true;
                checkIfAllDataReceived();
            }
        });

    }
}