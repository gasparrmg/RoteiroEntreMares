package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQueryBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;
import com.reinaldoarrosi.android.querybuilder.sqlite.criteria.Criteria;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EspecieFilterActivity extends AppCompatActivity {

    // ViewModel
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private MaterialToolbar toolbar;
    private AutoCompleteTextView dropdownGrupo;
    private CheckBox switchAnimalMovel;
    private CheckBox switchTentaculos;
    private CheckBox switchApendicesArticulados;
    private CheckBox switchSimetriaRadial;
    private CheckBox switchCarapacaCalcaria;
    private CheckBox switchPlacasCalcarias;
    private CheckBox switchConcha;
    private Button buttonApplyFilters;
    private TextView textViewSearchResultsSize;

    // Current filters
    private int currentGrupo;
    private boolean currentAnimalMovel;
    private boolean currentTentaculos;
    private boolean currentApendicesArticulados;
    private boolean currentSimetriaRadial;
    private boolean currentCarapacaCalcaria;
    private boolean currentPlacasCalcarias;
    private boolean currentConcha;
    private String query;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especie_filter);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        initToolbar();
        initViews();

        loadDataIntoViews();

        setOnClickListeners();

        // To load the current filters
        // populateViews();
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString("Filtrar espécies");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        dropdownGrupo = findViewById(R.id.dropdown_grupo);

        String[] optionsGrupo = {
                "Algas ou Líquenes",
                "Aves",
                "Peixes",
                "Equinodermes",
                "Moluscos",
                "Crustáceos",
                "Anelídeos",
                "Anémonas e Actinias",
                "Esponjas"
        };
        arrayAdapter = new ArrayAdapter(this, R.layout.list_dropdown_grupo_item, optionsGrupo);
        dropdownGrupo.setAdapter(arrayAdapter);

        switchAnimalMovel = findViewById(R.id.switch_animalmovel);
        switchTentaculos = findViewById(R.id.switch_tentaculos);
        switchApendicesArticulados = findViewById(R.id.switch_apendicesarticulados);
        switchSimetriaRadial = findViewById(R.id.switch_simetriaradial);
        switchCarapacaCalcaria = findViewById(R.id.switch_carapacacalcaria);
        switchPlacasCalcarias = findViewById(R.id.switch_placascalcarias);
        switchConcha = findViewById(R.id.switch_concha);

        buttonApplyFilters = findViewById(R.id.button_applyfilter);
        textViewSearchResultsSize = findViewById(R.id.textView_searchresultsize);

        switchAnimalMovel.setOnCheckedChangeListener(onCheckedListener);
        switchTentaculos.setOnCheckedChangeListener(onCheckedListener);
        switchApendicesArticulados.setOnCheckedChangeListener(onCheckedListener);
        switchSimetriaRadial.setOnCheckedChangeListener(onCheckedListener);
        switchCarapacaCalcaria.setOnCheckedChangeListener(onCheckedListener);
        switchPlacasCalcarias.setOnCheckedChangeListener(onCheckedListener);
        switchConcha.setOnCheckedChangeListener(onCheckedListener);

        dropdownGrupo.setOnItemClickListener(onDropdownChangeListener);
    }

    private void loadDataIntoViews() {
        currentGrupo = getIntent().getIntExtra("ESPECIE_FILTER_GRUPO", -1);
        currentAnimalMovel = getIntent().getBooleanExtra("ESPECIE_FILTER_ANIMALMOVEL", false);
        currentTentaculos = getIntent().getBooleanExtra("ESPECIE_FILTER_TENTACULOS", false);
        currentApendicesArticulados = getIntent().getBooleanExtra("ESPECIE_FILTER_APENDICESARTICULADOS", false);
        currentSimetriaRadial = getIntent().getBooleanExtra("ESPECIE_FILTER_SIMETRIARADIAL", false);
        currentCarapacaCalcaria = getIntent().getBooleanExtra("ESPECIE_FILTER_CARAPACACALCARIA", false);
        currentPlacasCalcarias = getIntent().getBooleanExtra("ESPECIE_FILTER_PLACASCALCARIAS", false);
        currentConcha = getIntent().getBooleanExtra("ESPECIE_FILTER_CONCHA", false);

        if (currentGrupo != -1) {
            // Load grupo into dropdown
            dropdownGrupo.setText(arrayAdapter.getItem(currentGrupo).toString(), false);
        }

        switchAnimalMovel.setChecked(currentAnimalMovel);
        switchTentaculos.setChecked(currentTentaculos);
        switchApendicesArticulados.setChecked(currentApendicesArticulados);
        switchSimetriaRadial.setChecked(currentSimetriaRadial);
        switchCarapacaCalcaria.setChecked(currentCarapacaCalcaria);
        switchPlacasCalcarias.setChecked(currentPlacasCalcarias);
        switchConcha.setChecked(currentConcha);

        buildQuery();
        guiaDeCampoViewModel.filterEspecies(query);

        guiaDeCampoViewModel.getAllEspecies().observe(this, new Observer<List<EspecieAvencas>>() {
            @Override
            public void onChanged(List<EspecieAvencas> especieAvencas) {
                Log.d("FILTRAR_ESPECIES", "query result size: " + especieAvencas.size());
                textViewSearchResultsSize.setText("Foram encontradas " + especieAvencas.size() + " espécies com estes critérios");
            }
        });
    }

    private void buildQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select("*").from("especie_table_avencas");

        Log.d("FILTRAR_ESPECIES", "onChecked dropdown value toString: " + dropdownGrupo.getText().toString());
        Log.d("FILTRAR_ESPECIES", "onChecked dropdown value: " + dropdownGrupo.getText());

        if (dropdownGrupo.getText().toString().equals("Algas ou Líquenes")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 0));
        } else if (dropdownGrupo.getText().toString().equals("Aves")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 1));
        } else if (dropdownGrupo.getText().toString().equals("Peixes")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 2));
        } else if (dropdownGrupo.getText().toString().equals("Equinodermes")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 3));
        } else if (dropdownGrupo.getText().toString().equals("Moluscos")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 4));
        } else if (dropdownGrupo.getText().toString().equals("Crustáceos")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 5));
        } else if (dropdownGrupo.getText().toString().equals("Anelídeos")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 6));
        } else if (dropdownGrupo.getText().toString().equals("Anémonas e Actinias")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 7));
        } else if (dropdownGrupo.getText().toString().equals("Esponjas")) {
            queryBuilder.whereAnd(Criteria.equals("grupoInt", 8));
        } else {

        }

        if (switchAnimalMovel.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("animalMovel", 1));
        }

        if (switchTentaculos.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("tentaculos", 1));
        }

        if (switchApendicesArticulados.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("apendicesArticulados", 1));
        }

        if (switchSimetriaRadial.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("simetriaRadial", 1));
        }

        if (switchCarapacaCalcaria.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("carapacaCalcaria", 1));
        }

        if (switchPlacasCalcarias.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("placasCalcarias", 1));
        }

        if (switchConcha.isChecked()) {
            queryBuilder.whereAnd(Criteria.equals("concha", 1));
        }

        query = queryBuilder.toDebugSqlString();

        Log.d("FILTRAR_ESPECIES", "onChecked query: " + query);

        guiaDeCampoViewModel.filterEspecies(query);
    }

    private void setOnClickListeners() {
        buttonApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                String dropdownValue = dropdownGrupo.getText().toString();

                if (dropdownValue.equals("Algas ou Líquenes")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 0);
                } else if (dropdownValue.equals("Aves")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 1);
                } else if (dropdownValue.equals("Peixes")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 2);
                } else if (dropdownValue.equals("Equinodermes")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 3);
                } else if (dropdownValue.equals("Moluscos")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 4);
                } else if (dropdownValue.equals("Crustáceos")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 5);
                } else if (dropdownValue.equals("Anelídeos")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 6);
                } else if (dropdownValue.equals("Anémonas e Actinias")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 7);
                } else if (dropdownValue.equals("Esponjas")) {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", 8);
                } else {
                    returnIntent.putExtra("ESPECIE_FILTER_GRUPO", -1);
                }

                returnIntent.putExtra("ESPECIE_FILTER_ANIMALMOVEL", switchAnimalMovel.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_TENTACULOS", switchTentaculos.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_APENDICESARTICULADOS", switchApendicesArticulados.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_SIMETRIARADIAL", switchSimetriaRadial.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_CARAPACACALCARIA", switchCarapacaCalcaria.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_PLACASCALCARIAS", switchPlacasCalcarias.isChecked());
                returnIntent.putExtra("ESPECIE_FILTER_CONCHA", switchConcha.isChecked());

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener onCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            buildQuery();
        }
    };

    private AdapterView.OnItemClickListener onDropdownChangeListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            buildQuery();
        }
    };
}