package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
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
import java.util.Collection;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosChartsActivity extends AppCompatActivity {

    private static final String TAG = "AvistamentosChartsActivity";

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    private LineChart lineChartDistribution;
    private BarChart barChartPresence;

    private List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> avistamentosPocas;
    private List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentosSupralitoral;
    private List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentosMediolitoralSuperior;
    private List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentosMediolitoralInferior;
    private List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentosInfralitoral;

    private ArrayList<String> nomesEspecies;

    private boolean gotDataFromPocas;
    private boolean gotDataFromSupralitoral;
    private boolean gotDataFromMediolitoralSuperior;
    private boolean gotDataFromMediolitoralInferior;
    private boolean gotDataFromInfralitoral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamentos_charts);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        initToolbar();

        lineChartDistribution = findViewById(R.id.lineChart_distribution);
        barChartPresence = findViewById(R.id.barChart_presence);

        gotDataFromPocas = false;
        gotDataFromSupralitoral = false;
        gotDataFromMediolitoralSuperior = false;
        gotDataFromMediolitoralInferior = false;
        gotDataFromInfralitoral = false;

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

        if (avistamentosPocas.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> pocasValues = new ArrayList<>();
            ArrayList<BarEntry> pocasBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosPocas.get(0).getEspeciesAvencasPocasInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;
                for (int j = 0; j < avistamentosPocas.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosPocas.get(j).getEspeciesAvencasPocasInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosPocas.size();
                pocasValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    pocasBarValues.add(new BarEntry(i, 1));
                } else {
                    pocasBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosPocas.get(0).getEspeciesAvencasPocasInstancias().size()) {
                    nomesEspecies.add(avistamentosPocas.get(0).getEspeciesAvencasPocasInstancias().get(i).getEspecieAvencas().getNomeComum());
                    Log.d(TAG, "Poças -> Adicionada a espécie " + avistamentosPocas.get(0).getEspeciesAvencasPocasInstancias().get(i).getEspecieAvencas().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosPocas.get(0).getEspeciesAvencasPocasInstancias().get(i).getEspecieAvencas().getNomeComum() +
                        " teve uma média de " + avg + " nas Poças");
            }

            LineDataSet pocasDataSet = new LineDataSet(pocasValues, "Poças");
            pocasDataSet.setFillAlpha(110);
            pocasDataSet.setColor(Color.CYAN);
            pocasDataSet.setLineWidth(2f);

            lineDataSets.add(pocasDataSet);

            // Bar Chart
            BarDataSet pocasBarDataSet = new BarDataSet(pocasBarValues, "Poças");
            pocasBarDataSet.setColor(Color.CYAN);

            barDataSets.add(pocasBarDataSet);
        }

        if (avistamentosSupralitoral.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> supralitoralValues = new ArrayList<>();
            ArrayList<BarEntry> supralitoralBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosSupralitoral.get(0).getEspeciesAvencasZonacaoInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosSupralitoral.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosSupralitoral.get(j).getEspeciesAvencasZonacaoInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosSupralitoral.size();
                supralitoralValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    supralitoralBarValues.add(new BarEntry(i, 1));
                } else {
                    supralitoralBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosSupralitoral.get(0).getEspeciesAvencasZonacaoInstancias().size()) {
                    nomesEspecies.add(avistamentosSupralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum());
                    Log.d(TAG, "Supralitoral -> Adicionada a espécie " + avistamentosSupralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosSupralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                        " teve uma média de " + avg + " na zona Supralitoral");
            }

            LineDataSet supralitoralDataSet = new LineDataSet(supralitoralValues, "Supralitoral");
            supralitoralDataSet.setFillAlpha(110);
            supralitoralDataSet.setColor(Color.BLUE);
            supralitoralDataSet.setLineWidth(2f);

            lineDataSets.add(supralitoralDataSet);

            // Bar Chart
            BarDataSet supralitoralBarDataSet = new BarDataSet(supralitoralBarValues, "Supralitoral");
            supralitoralBarDataSet.setColor(Color.BLUE);

            barDataSets.add(supralitoralBarDataSet);
        }

        if (avistamentosMediolitoralSuperior.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> mediolitoralSuperiorValues = new ArrayList<>();
            ArrayList<BarEntry> mediolitoralSuperiorBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosMediolitoralSuperior.get(0).getEspeciesAvencasZonacaoInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosMediolitoralSuperior.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosMediolitoralSuperior.get(j).getEspeciesAvencasZonacaoInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosMediolitoralSuperior.size();
                mediolitoralSuperiorValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    mediolitoralSuperiorBarValues.add(new BarEntry(i, 1));
                } else {
                    mediolitoralSuperiorBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosMediolitoralSuperior.get(0).getEspeciesAvencasZonacaoInstancias().size()) {
                    nomesEspecies.add(avistamentosMediolitoralSuperior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum());
                    Log.d(TAG, "Mediolitoral Superior -> Adicionada a espécie " + avistamentosMediolitoralSuperior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosMediolitoralSuperior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                        " teve uma média de " + avg + " na zona Mediolitoral Superior");
            }

            LineDataSet mediolitoralSuperiorDataSet = new LineDataSet(mediolitoralSuperiorValues, "Mediolitoral Superior");
            mediolitoralSuperiorDataSet.setFillAlpha(110);
            mediolitoralSuperiorDataSet.setColor(Color.RED);
            mediolitoralSuperiorDataSet.setLineWidth(2f);

            lineDataSets.add(mediolitoralSuperiorDataSet);

            // Bar Chart
            BarDataSet mediolitoralSuperiorBarDataSet = new BarDataSet(mediolitoralSuperiorBarValues, "Mediolitoral Superior");
            mediolitoralSuperiorBarDataSet.setColor(Color.RED);

            barDataSets.add(mediolitoralSuperiorBarDataSet);
        }

        if (avistamentosMediolitoralInferior.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> mediolitoralInferiorValues = new ArrayList<>();
            ArrayList<BarEntry> mediolitoralInferiorBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosMediolitoralInferior.get(0).getEspeciesAvencasZonacaoInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosMediolitoralInferior.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosMediolitoralInferior.get(j).getEspeciesAvencasZonacaoInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosMediolitoralInferior.size();
                mediolitoralInferiorValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    mediolitoralInferiorBarValues.add(new BarEntry(i, 1));
                } else {
                    mediolitoralInferiorBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosMediolitoralInferior.get(0).getEspeciesAvencasZonacaoInstancias().size()) {
                    nomesEspecies.add(avistamentosMediolitoralInferior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum());
                    Log.d(TAG, "Mediolitoral Inferior -> Adicionada a espécie " + avistamentosMediolitoralInferior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosMediolitoralInferior.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                        " teve uma média de " + avg + " na zona Mediolitoral Inferior");
            }

            LineDataSet mediolitoralInferiorDataSet = new LineDataSet(mediolitoralInferiorValues, "Mediolitoral Inferior");
            mediolitoralInferiorDataSet.setFillAlpha(110);
            mediolitoralInferiorDataSet.setColor(Color.GREEN);
            mediolitoralInferiorDataSet.setLineWidth(2f);

            lineDataSets.add(mediolitoralInferiorDataSet);

            // Bar Chart
            BarDataSet mediolitoralInferiorBarDataSet = new BarDataSet(mediolitoralInferiorBarValues, "Mediolitoral Inferior");
            mediolitoralInferiorBarDataSet.setColor(Color.GREEN);

            barDataSets.add(mediolitoralInferiorBarDataSet);
        }

        if (avistamentosInfralitoral.size() > 0) {
            numberOfGroups++;

            ArrayList<Entry> infralitoralValues = new ArrayList<>();
            ArrayList<BarEntry> infralitoralBarValues = new ArrayList<>();

            for (int i = 0; i < avistamentosInfralitoral.get(0).getEspeciesAvencasZonacaoInstancias().size(); i++) {
                somaEspecie = 0;
                avg = 0;

                for (int j = 0; j < avistamentosInfralitoral.size(); j++) {
                    somaEspecie = somaEspecie + avistamentosInfralitoral.get(j).getEspeciesAvencasZonacaoInstancias().get(i).getInstancias();
                }

                avg = somaEspecie / avistamentosInfralitoral.size();
                infralitoralValues.add(new Entry(i, (float) avg));

                if (avg > 0) {
                    infralitoralBarValues.add(new BarEntry(i, 1));
                } else {
                    infralitoralBarValues.add(new BarEntry(i, 0));
                }

                if (nomesEspecies.size() < avistamentosInfralitoral.get(0).getEspeciesAvencasZonacaoInstancias().size()) {
                    nomesEspecies.add(avistamentosInfralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum());
                    Log.d(TAG, "Infralitoral -> Adicionada a espécie " + avistamentosInfralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                            " na lista de espécies");
                }

                Log.d(TAG, "A espécie " + avistamentosInfralitoral.get(0).getEspeciesAvencasZonacaoInstancias().get(i).getEspecieAvencas().getNomeComum() +
                        " teve uma média de " + avg + " na zona Infralitoral");
            }

            LineDataSet infralitoralDataSet = new LineDataSet(infralitoralValues, "Infralitoral");
            infralitoralDataSet.setFillAlpha(110);
            infralitoralDataSet.setColor(Color.MAGENTA);
            infralitoralDataSet.setLineWidth(2f);

            lineDataSets.add(infralitoralDataSet);

            // Bar Chart
            BarDataSet infralitoralBarDataSet = new BarDataSet(infralitoralBarValues, "Infralitoral");
            infralitoralBarDataSet.setColor(Color.MAGENTA);

            barDataSets.add(infralitoralBarDataSet);
        }

        lineChartDistribution.getLegend().setWordWrapEnabled(true);
        lineChartDistribution.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(nomesEspecies));
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

        BarData barData = new BarData(barDataSets);
        barChartPresence.setData(barData);

        barChartPresence.getLegend().setWordWrapEnabled(true);

        barChartPresence.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(nomesEspecies));
        barChartPresence.getXAxis().setCenterAxisLabels(true);
        barChartPresence.getXAxis().setGranularity(1f);
        barChartPresence.getXAxis().setGranularityEnabled(true);

        barChartPresence.setDragEnabled(true);
        barChartPresence.setVisibleXRangeMaximum(2);

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
    }

    private void checkIfAllDataReceived() {
        Log.d(TAG, "Checking if all data received");

        if (gotDataFromPocas &&
                gotDataFromSupralitoral &&
                gotDataFromMediolitoralSuperior &&
                gotDataFromMediolitoralInferior &&
                gotDataFromInfralitoral) {
            Log.d(TAG, "Received all data. Starting initChartData()");
            initChartData();
        }
    }

    private void getDataFromDB() {
        // Get Avistamentos poças maré
        guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().observe(this, new Observer<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                avistamentosPocas = avistamentoPocasAvencasWithEspecieAvencasPocasInstancias;

                Log.d(TAG, "Poças size: " + String.valueOf(avistamentosPocas.size()));
                guiaDeCampoViewModel.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias().removeObserver(this);

                gotDataFromPocas = true;
                checkIfAllDataReceived();
            }
        });

        // Get Avistamentos Supralitoral
        guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Supralitoral").observe(this, new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                avistamentosSupralitoral = avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

                Log.d(TAG, "Supralitoral size: " + String.valueOf(avistamentosSupralitoral.size()));
                guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Supralitoral").removeObserver(this);

                gotDataFromSupralitoral = true;
                checkIfAllDataReceived();
            }
        });

        // Get Avistamentos Mediolitoral Superior
        guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Mediolitoral superior").observe(this, new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                avistamentosMediolitoralSuperior = avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

                Log.d(TAG, "Mediolitoral superior size: " + String.valueOf(avistamentosMediolitoralSuperior.size()));
                guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Mediolitoral superior").removeObserver(this);

                gotDataFromMediolitoralSuperior = true;
                checkIfAllDataReceived();
            }
        });

        // Get Avistamentos Mediolitoral Inferior
        guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Mediolitoral inferior").observe(this, new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                avistamentosMediolitoralInferior = avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

                Log.d(TAG, "Mediolitoral inferior size: " + String.valueOf(avistamentosMediolitoralInferior.size()));
                guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Mediolitoral inferior").removeObserver(this);

                gotDataFromMediolitoralInferior = true;
                checkIfAllDataReceived();
            }
        });

        // Get Avistamentos Infralitoral
        guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Infralitoral").observe(this, new Observer<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) {
                avistamentosInfralitoral = avistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

                Log.d(TAG, "Infralitoral size: " + String.valueOf(avistamentosInfralitoral.size()));
                guiaDeCampoViewModel.getAvistamentosZonacaoWithZona("Infralitoral").removeObserver(this);

                gotDataFromInfralitoral = true;
                checkIfAllDataReceived();
            }
        });
    }
}