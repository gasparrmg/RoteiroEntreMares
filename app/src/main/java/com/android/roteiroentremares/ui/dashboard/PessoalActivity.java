package com.android.roteiroentremares.ui.dashboard;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.viewmodel.DashboardViewModel;
import com.android.roteiroentremares.ui.onboarding.MainActivity;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PessoalActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static String TAG = "PESSOAL_ACTIVITY";

    // ViewModel
    @Inject
    DashboardViewModel dashboardViewModel;

    // Form Validator
    private Validator formValidator;

    // Form
    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextNome;

    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextEscola;
    private TextInputLayout textInputLayoutEscola;

    @Min(value = 1, messageResId = R.string.error_minimum_school_year)
    @Max(value = 12, messageResId = R.string.error_maximum_school_year)
    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextAnoEscolaridade;
    private TextInputLayout textInputLayoutAnoEscolaridade;

    @Length(min = 9, max = 9, messageResId = R.string.error_invalid_academic_year)
    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextAnoLectivo;
    private TextInputLayout textInputLayoutAnoLectivo;

    private TextInputEditText editTextCodigoTurma;
    private TextInputLayout textInputLayoutCodigoTurma;

    private TextInputEditText editTextGerarCodigoTurma;
    private TextInputLayout textInputLayoutGerarCodigoTurma;

    private Button btnGerarCodigoTurma;
    private Button btnSave;
    private Button btnCopiarCodigoTurma;

    // Global variables
    private int position = 0;
    private int tipoUtilizador;
    private String nomeUtilizador;
    private String escolaUtilizador;
    private String anoEscolaridadeUtilizador;
    private String anoLectivoUtilizador;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoal);

        SpannableString s = new SpannableString(getResources().getString(R.string.title_pessoal));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Keeps keyboard from opening right away
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // dashboardViewModel.setTipoUtilizador(1); // Uncomment ONLY for testing purposes

        getUserInfo();

        initViews();
        setOnClickListeners();

        initForm();

        // Init Form Validator
        formValidator = new Validator(this);
        formValidator.setValidationListener(this);
    }

    private void getUserInfo() {
        tipoUtilizador = dashboardViewModel.getTipoUtilizador();
        nomeUtilizador = dashboardViewModel.getNome();
        escolaUtilizador = dashboardViewModel.getEscola();
        anoEscolaridadeUtilizador = dashboardViewModel.getAnoEscolaridade();
        anoLectivoUtilizador = dashboardViewModel.getAnoLectivo();
        code = dashboardViewModel.getCodigoTurma();
    }

    private void initViews() {
        editTextNome = findViewById(R.id.textinputedittext_nome);
        textInputLayoutEscola = findViewById(R.id.textinputlayout_escola);
        editTextEscola = findViewById(R.id.textinputedittext_escola);
        textInputLayoutAnoEscolaridade = findViewById(R.id.textinputlayout_anoescolaridade);
        editTextAnoEscolaridade = findViewById(R.id.textinputedittext_anoescolaridade);
        textInputLayoutAnoLectivo = findViewById(R.id.textinputlayout_anolectivo);
        editTextAnoLectivo = findViewById(R.id.textinputedittext_anolectivo);
        textInputLayoutCodigoTurma = findViewById(R.id.textinputlayout_codigoturma);
        editTextCodigoTurma = findViewById(R.id.textinputedittext_codigoturma);
        textInputLayoutGerarCodigoTurma = findViewById(R.id.textinputlayout_gerarcodigoturma);
        editTextGerarCodigoTurma = findViewById(R.id.textinputedittext_gerarcodigoturma);
        btnGerarCodigoTurma = findViewById(R.id.btn_gerarcodigoturma);
        btnSave = findViewById(R.id.btn_save);
        btnCopiarCodigoTurma = findViewById(R.id.btn_copiarcodigoturma);

        editTextAnoLectivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                position = editTextAnoLectivo.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextAnoLectivo.getText().length() == 4 && position != 5) {
                    editTextAnoLectivo.setText(editTextAnoLectivo.getText().toString()+"/");
                    editTextAnoLectivo.setSelection(5);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // EMPTY
            }
        });

        editTextNome.addTextChangedListener(pessoalTextWatcher);
        editTextEscola.addTextChangedListener(pessoalTextWatcher);
        editTextAnoEscolaridade.addTextChangedListener(pessoalTextWatcher);
        editTextAnoLectivo.addTextChangedListener(pessoalTextWatcher);
        editTextCodigoTurma.addTextChangedListener(pessoalTextWatcher);
        editTextGerarCodigoTurma.addTextChangedListener(pessoalTextWatcher);
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If valid -> onValidationSucceeded()
                formValidator.validate();
            }
        });

        btnGerarCodigoTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = RandomStringUtils.random(8, "0123456789ABCDEFGHIJLMNOPQRSTUVXYZ");
                textInputLayoutGerarCodigoTurma.getEditText().setText(code);
                Log.d(TAG, "Code: " + code);
            }
        });

        btnCopiarCodigoTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("códigoTurma", editTextGerarCodigoTurma.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(PessoalActivity.this, "Código de Turma copiado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Get data from Shared Preferences and fill the Form accordingly
     */
    private void initForm() {
        if (tipoUtilizador == 0) {
            textInputLayoutGerarCodigoTurma.setVisibility(View.GONE);
            btnGerarCodigoTurma.setVisibility(View.GONE);
            btnCopiarCodigoTurma.setVisibility(View.GONE);
        } else if (tipoUtilizador == 1) {
            textInputLayoutCodigoTurma.setVisibility(View.GONE);
        } else {
            textInputLayoutEscola.setVisibility(View.GONE);
            textInputLayoutAnoEscolaridade.setVisibility(View.GONE);
            textInputLayoutAnoLectivo.setVisibility(View.GONE);
            textInputLayoutCodigoTurma.setVisibility(View.GONE);
            textInputLayoutGerarCodigoTurma.setVisibility(View.GONE);
            btnGerarCodigoTurma.setVisibility(View.GONE);
        }

        editTextNome.setText(nomeUtilizador);

        // If not Explorador
        if (tipoUtilizador != 2) {
            editTextEscola.setText(escolaUtilizador);
            editTextAnoEscolaridade.setText(anoEscolaridadeUtilizador);
            editTextAnoLectivo.setText(anoLectivoUtilizador);

            if (!code.isEmpty()) {
                editTextCodigoTurma.setText(code);
                editTextGerarCodigoTurma.setText(code);
                btnGerarCodigoTurma.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        dashboardViewModel.setNome(editTextNome.getText().toString());

        if (tipoUtilizador != 2) {
            dashboardViewModel.setEscola(editTextEscola.getText().toString());
            dashboardViewModel.setAnoEscolaridade(editTextAnoEscolaridade.getText().toString());
            dashboardViewModel.setAnoLectivo(editTextAnoLectivo.getText().toString());

            if (tipoUtilizador == 0) {
                dashboardViewModel.setCodigoTurma(editTextCodigoTurma.getText().toString());
            } else {
                dashboardViewModel.setCodigoTurma(editTextGerarCodigoTurma.getText().toString());


                btnGerarCodigoTurma.setVisibility(View.GONE);
                btnCopiarCodigoTurma.setVisibility(View.VISIBLE);
            }
        }

        Toast.makeText(PessoalActivity.this, "Informações guardadas com sucesso!", Toast.LENGTH_SHORT).show();

        getUserInfo();
        btnSave.setEnabled(false);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(PessoalActivity.this);

            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
                // ((TextInputLayout) view.getParent().getParent()).setError(message);
            } else {
                Toast.makeText(PessoalActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private TextWatcher pessoalTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!editTextNome.getText().toString().equals(nomeUtilizador) ||
                    !editTextEscola.getText().toString().equals(escolaUtilizador) ||
                    !editTextAnoEscolaridade.getText().toString().equals(anoEscolaridadeUtilizador) ||
                    !editTextAnoLectivo.getText().toString().equals(anoLectivoUtilizador) ||
                    !editTextCodigoTurma.getText().toString().equals(code) ||
                    !editTextGerarCodigoTurma.getText().toString().equals(code)) {
                btnSave.setEnabled(true);
            } else {
                btnSave.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}