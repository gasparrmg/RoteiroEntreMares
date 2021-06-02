package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa;

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
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvistamentosRiaFormosaPocasTranseptosChartsActivity extends AppCompatActivity {

    public static final String CHART_KEY = "AvistamentosRiaFormosaPocasTranseptosChartsActivity_ChartKey";

    public static final String CHART_TYPE_POCAS = "AvistamentosRiaFormosaPocasTranseptosChartsActivity_Chart_Pocas";
    public static final String CHART_TYPE_TRANSPETOS = "AvistamentosRiaFormosaPocasTranseptosChartsActivity_Chart_Transeptos";

    private static final String TAG = "AvistamentosRiaFormosaChartsActivity";

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    private TextView textViewPresenceTitle;

    private BarChart barChartPresence;

    private LinearLayout linearLayoutIsEmpty;

    private List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentosRiaFormosaPocas;
    private List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentosRiaFormosaTranseptos;

    private ArrayList<String> nomesEspecies;

    private boolean gotDataFromPocas;
    private boolean gotDataFromTranseptos;

    private String currentChartType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamentos_riaformosa_pocastranseptos_charts);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        currentChartType = getIntent().getStringExtra(CHART_KEY);

        initToolbar();
        barChartPresence = findViewById(R.id.barChart_presence);
        textViewPresenceTitle = findViewById(R.id.textView_presence_title);
        linearLayoutIsEmpty = findViewById(R.id.linearlayout_isEmpty);

        gotDataFromPocas = false;
        gotDataFromTranseptos = false;

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

        boolean isEspeciePresent = false;

        boolean isEspeciePresentExpostaPedra = false;
        boolean isEspeciePresentInferiorPedra = false;
        boolean isEspeciePresentSubstrato = false;

        ArrayList<BarEntry> barValues = new ArrayList<>();

        if (currentChartType.equals(CHART_TYPE_POCAS)) {
            if (avistamentosRiaFormosaPocas.size() > 0) {
                numberOfGroups++;

                for (int i = 0; i < avistamentosRiaFormosaPocas.get(0).getEspeciesRiaFormosaPocasInstancias().size(); i++) {
                    isEspeciePresent = false;

                    for (int j = 0; j < avistamentosRiaFormosaPocas.size(); j++) {
                        if (avistamentosRiaFormosaPocas.get(j).getEspeciesRiaFormosaPocasInstancias().get(i).isInstancias()) {
                            isEspeciePresent = true;
                            break;
                        }
                    }

                    if (isEspeciePresent) {
                        barValues.add(new BarEntry(i, 1));
                    } else {
                        barValues.add(new BarEntry(i, 0));
                    }

                    if (nomesEspecies.size() < avistamentosRiaFormosaPocas.get(0).getEspeciesRiaFormosaPocasInstancias().size()) {
                        nomesEspecies.add(avistamentosRiaFormosaPocas.get(0).getEspeciesRiaFormosaPocasInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                    }
                }

                BarDataSet pocasBarDataSet = new BarDataSet(barValues, "Poças de maré");
                pocasBarDataSet.setColor(Color.BLUE);

                BarData barData = new BarData();
                barData.addDataSet(pocasBarDataSet);

                barChartPresence.setData(barData);

                barChartPresence.setDrawValueAboveBar(false);
                barChartPresence.getLegend().setWordWrapEnabled(true);
                barChartPresence.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(nomesEspecies));
                barChartPresence.getXAxis().setCenterAxisLabels(false);
                barChartPresence.getXAxis().setGranularity(1f);
                barChartPresence.getXAxis().setGranularityEnabled(true);
                barChartPresence.getXAxis().setLabelRotationAngle(-90);

                barChartPresence.getAxisLeft().setGranularityEnabled(true);
                barChartPresence.getAxisLeft().setGranularity(1f);
                barChartPresence.getAxisLeft().setAxisMaximum(1);
                barChartPresence.getAxisRight().setGranularityEnabled(true);
                barChartPresence.getAxisRight().setGranularity(1f);
                barChartPresence.getAxisRight().setAxisMaximum(1);

                barChartPresence.setDragEnabled(true);
                barChartPresence.setVisibleXRangeMaximum(6);

                barChartPresence.setHorizontalScrollBarEnabled(true);
                barChartPresence.getXAxis().setDrawGridLines(false);

                barChartPresence.notifyDataSetChanged();
                barChartPresence.invalidate();
            }

        } else if (currentChartType.equals(CHART_TYPE_TRANSPETOS)) {
            ArrayList<IBarDataSet> barDataSets = new ArrayList<>();

            ArrayList<BarEntry> expostaPedraValues = new ArrayList<>();
            ArrayList<BarEntry> inferiorPedraValues = new ArrayList<>();
            ArrayList<BarEntry> substratoValues = new ArrayList<>();

            if (avistamentosRiaFormosaTranseptos.size() != 0) {
                // numberOfGroups++;
                numberOfGroups = 3;

                for (int i = 0; i < avistamentosRiaFormosaTranseptos.get(0).getEspeciesRiaFormosaTranseptosInstancias().size(); i++) {
                    isEspeciePresentExpostaPedra = false;
                    isEspeciePresentInferiorPedra = false;
                    isEspeciePresentSubstrato = false;

                    for (int j = 0; j < avistamentosRiaFormosaTranseptos.size(); j++) {
                        /*if (avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasExpostaPedra() ||
                                avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasInferiorPedra() ||
                                avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasSubstrato()) {
                            isEspeciePresent = true;
                            break;
                        }*/

                        if (avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasExpostaPedra() &&
                                !isEspeciePresentExpostaPedra) {
                            isEspeciePresentExpostaPedra = true;
                        }

                        if (avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasInferiorPedra() &&
                                !isEspeciePresentInferiorPedra) {
                            isEspeciePresentInferiorPedra = true;
                        }

                        if (avistamentosRiaFormosaTranseptos.get(j).getEspeciesRiaFormosaTranseptosInstancias().get(i).isInstanciasSubstrato() &&
                                !isEspeciePresentSubstrato) {
                            isEspeciePresentSubstrato = true;
                        }
                    }

                    if (isEspeciePresentExpostaPedra) {
                        expostaPedraValues.add(new BarEntry(i, 1));
                    } else {
                        expostaPedraValues.add(new BarEntry(i, 0));
                    }

                    if (isEspeciePresentInferiorPedra) {
                        inferiorPedraValues.add(new BarEntry(i, 1));
                    } else {
                        inferiorPedraValues.add(new BarEntry(i, 0));
                    }

                    if (isEspeciePresentSubstrato) {
                        substratoValues.add(new BarEntry(i, 1));
                    } else {
                        substratoValues.add(new BarEntry(i, 0));
                    }

                    if (nomesEspecies.size() < avistamentosRiaFormosaTranseptos.get(0).getEspeciesRiaFormosaTranseptosInstancias().size()) {
                        nomesEspecies.add(avistamentosRiaFormosaTranseptos.get(0).getEspeciesRiaFormosaTranseptosInstancias().get(i).getEspecieRiaFormosa().getNomeComum());
                    }
                }

                BarDataSet expostaPedraBarDataSet = new BarDataSet(expostaPedraValues, "Face exposta");
                expostaPedraBarDataSet.setColor(Color.BLUE);
                BarDataSet inferiorPedraBarDataSet = new BarDataSet(inferiorPedraValues, "Face inferior");
                inferiorPedraBarDataSet.setColor(Color.RED);
                BarDataSet substratoBarDataSet = new BarDataSet(substratoValues, "Substrato");
                substratoBarDataSet.setColor(Color.GREEN);

                barDataSets.add(expostaPedraBarDataSet);
                barDataSets.add(inferiorPedraBarDataSet);
                barDataSets.add(substratoBarDataSet);

                // setup group bar chart

                BarData barData = new BarData(barDataSets);
                barChartPresence.setData(barData);

                barChartPresence.getLegend().setWordWrapEnabled(true);

                barChartPresence.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nomesEspecies));
                barChartPresence.getXAxis().setCenterAxisLabels(true);
                barChartPresence.getXAxis().setGranularity(1f);
                barChartPresence.getXAxis().setGranularityEnabled(true);

                barChartPresence.getXAxis().setAxisMaximum(barData.getXMax() + 1f);

                barChartPresence.getAxisLeft().setGranularityEnabled(true);
                barChartPresence.getAxisLeft().setGranularity(1f);
                barChartPresence.getAxisRight().setGranularityEnabled(true);
                barChartPresence.getAxisRight().setGranularity(1f);

                barChartPresence.setDragEnabled(true);
                barChartPresence.setVisibleXRangeMaximum(3);

                barChartPresence.setHorizontalScrollBarEnabled(true);
                barChartPresence.setScrollBarSize(20);

                float barWidth = 0.2f;
                float barSpace = 0.025f;
                //float groupSpace = 0.375f; // 5 groups
                float groupSpace = (1 - ((barSpace + barWidth) * numberOfGroups));
                barData.setBarWidth(barWidth);

                barChartPresence.getXAxis().setAxisMinimum(0);
                // barChartPresence.getXAxis().setAxisMaximum(0 + barChartPresence.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
                // barChartPresence.getAxisLeft().setAxisMinimum(0);
                barChartPresence.groupBars(0, groupSpace, barSpace);

                Description description = new Description();
                barChartPresence.setDescription(description);
                description.setText("");
                barChartPresence.getDescription().setTextSize(14f);
                barChartPresence.getXAxis().setDrawGridLines(false);

                barChartPresence.notifyDataSetChanged();
                barChartPresence.invalidate();

                /*BarDataSet transeptosBarDataSet = new BarDataSet(barValues, "Transeptos");
                transeptosBarDataSet.setColor(Color.BLUE);

                BarData barData = new BarData();
                barData.addDataSet(transeptosBarDataSet);

                barChartPresence.setData(barData);

                barChartPresence.setDrawValueAboveBar(false);
                barChartPresence.getLegend().setWordWrapEnabled(true);
                barChartPresence.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(nomesEspecies));
                barChartPresence.getXAxis().setCenterAxisLabels(false);
                barChartPresence.getXAxis().setGranularity(1f);
                barChartPresence.getXAxis().setGranularityEnabled(true);
                barChartPresence.getXAxis().setLabelRotationAngle(-90);

                barChartPresence.getAxisLeft().setGranularityEnabled(true);
                barChartPresence.getAxisLeft().setGranularity(1f);
                barChartPresence.getAxisLeft().setAxisMaximum(1);
                barChartPresence.getAxisRight().setGranularityEnabled(true);
                barChartPresence.getAxisRight().setGranularity(1f);
                barChartPresence.getAxisRight().setAxisMaximum(1);

                barChartPresence.setDragEnabled(true);
                barChartPresence.setVisibleXRangeMaximum(6);

                barChartPresence.setHorizontalScrollBarEnabled(true);
                barChartPresence.getXAxis().setDrawGridLines(false);

                barChartPresence.notifyDataSetChanged();
                barChartPresence.invalidate();*/
            }
        }

        if (numberOfGroups == 0) {
            // NO DATA, SHOW WARNING

            barChartPresence.setVisibility(View.GONE);
            textViewPresenceTitle.setVisibility(View.GONE);

            linearLayoutIsEmpty.setVisibility(View.VISIBLE);

            return;
        } else {
            linearLayoutIsEmpty.setVisibility(View.GONE);
        }
    }

    private void checkIfAllDataReceived() {
        Log.d(TAG, "Checking if all data received");

        if (currentChartType.equals(CHART_TYPE_POCAS) && gotDataFromPocas) {
            initChartData();
        } else if (currentChartType.equals(CHART_TYPE_TRANSPETOS) && gotDataFromTranseptos) {
            initChartData();
        }
    }

    private void getDataFromDB() {

        if (currentChartType.equals(CHART_TYPE_POCAS)) {
            guiaDeCampoViewModel.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias().observe(this, new Observer<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias) {
                    avistamentosRiaFormosaPocas = avistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;

                    // guiaDeCampoViewModel.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias().removeObserver(this);
                    gotDataFromPocas = true;
                    checkIfAllDataReceived();
                }
            });
        } else if (currentChartType.equals(CHART_TYPE_TRANSPETOS)) {
            guiaDeCampoViewModel.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias().observe(this, new Observer<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>>() {
                @Override
                public void onChanged(List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                    avistamentosRiaFormosaTranseptos = avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;

                    // guiaDeCampoViewModel.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias().removeObserver(this);
                    gotDataFromTranseptos = true;
                    checkIfAllDataReceived();
                }
            });
        }
    }
}