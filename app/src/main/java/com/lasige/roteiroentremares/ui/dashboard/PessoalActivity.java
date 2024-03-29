package com.lasige.roteiroentremares.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.onboarding.MainActivity;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PessoalActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static String TAG = "PESSOAL_ACTIVITY";

    // ViewModel
    /*@Inject
    DashboardViewModel dashboardViewModel;*/
    private DashboardViewModel dashboardViewModel;

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

    private SwitchMaterial switchShareLocationArtefacto;
    private LinearLayout linearLayoutShareLocationArtefacto;

    // Global variables
    private int position = 0;
    private int tipoUtilizador;
    private String nomeUtilizador;
    private String escolaUtilizador;
    private String anoEscolaridadeUtilizador;
    private String anoLectivoUtilizador;
    private String code;
    private boolean shareLocationArtefactos;

    private boolean isResettingApp;
    private AlertDialog dialogProgress;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoal);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        isResettingApp = false;

        SpannableString s = new SpannableString(getResources().getString(R.string.title_pessoal));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Keeps keyboard from opening right away
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // dashboardViewModel.setTipoUtilizador(0); // Uncomment ONLY for testing purposes

        getUserInfo();

        initViews();
        setOnClickListeners();

        initForm();

        // Init Form Validator
        formValidator = new Validator(this);
        formValidator.setValidationListener(this);

        Log.d(TAG, "Tipo Utilizador: " + tipoUtilizador);

        // hideShareFuncionality();
    }

    private void hideShareFuncionality() {
        textInputLayoutCodigoTurma.setVisibility(View.GONE);
        textInputLayoutGerarCodigoTurma.setVisibility(View.GONE);
        linearLayoutShareLocationArtefacto.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }

        if (getApplicationContext() instanceof RoteiroEntreMaresApplication) {
            if (((RoteiroEntreMaresApplication) getApplicationContext()).isUsingWifiP2pFeature()) {
                Log.d("debug_bg", "unregister BR from UserDashboard");
                unregisterReceiver(receiver);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getApplicationContext() instanceof RoteiroEntreMaresApplication) {
            if (((RoteiroEntreMaresApplication) getApplicationContext()).isUsingWifiP2pFeature()) {
                Log.d("debug_bg", "registering BR from UserDashboard");

                if (setupP2p()) {
                    receiver = new WifiP2pTurmaBroadcastReceiver(manager, channel, this);
                    registerReceiver(receiver, intentFilter);
                }
            }
        }
    }

    private boolean setupP2p() {
        Log.d("debug_bg", "setupP2p()");

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        if (manager == null) {
            Log.e(WifiP2PActivity.TAG, "Cannot get Wi-Fi Direct system service.");
            return false;
        }

        channel = manager.initialize(this, getMainLooper(), null);
        if (channel == null) {
            Log.e(WifiP2PActivity.TAG, "Cannot initialize Wi-Fi Direct.");
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pessoal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_change_spot:
                // MOSTRAR POPUP
                // Mudar para Avencas/RF, restart app
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setTitle("ATENÇÃO!");

                materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar todo o teu progresso no Roteiro Entre-Marés, incluindo Artefactos e Avistamentos?");
                materialAlertDialogBuilder.setPositiveButton("Sim, tenho", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PessoalActivity.this);
                        LayoutInflater layoutInflater = PessoalActivity.this.getLayoutInflater();
                        View customView = layoutInflater.inflate(R.layout.dialog_progress, null);
                        builder.setView(customView);
                        dialogProgress = builder.create();
                        dialogProgress.setCanceledOnTouchOutside(false);
                        dialogProgress.setCancelable(false);
                        dialogProgress.show();

                        deleteProgress();
                    }
                });

                materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                AlertDialog alertDialog = materialAlertDialogBuilder.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteProgress() {
        /*Intent i = new Intent(PessoalActivity.this, MainActivity.class);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/

        isResettingApp = true;

        dashboardViewModel.deleteAllProgress(this);
    }

    public void showErrorOnDeleteProgress() {
        if (dialogProgress != null) {
            if (dialogProgress.isShowing()) {
                dialogProgress.dismiss();
            }
        }

        Toast.makeText(this, getResources().getString(R.string.general_error_message), Toast.LENGTH_SHORT).show();
    }

    public void restartApp() {
        if (isResettingApp) {
            if (dialogProgress != null && dialogProgress.isShowing()) {
                dialogProgress.dismiss();
            }

            Intent i = new Intent(PessoalActivity.this, MainActivity.class);
            // set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void getUserInfo() {
        tipoUtilizador = dashboardViewModel.getTipoUtilizador();
        nomeUtilizador = dashboardViewModel.getNome();
        escolaUtilizador = dashboardViewModel.getEscola();
        anoEscolaridadeUtilizador = dashboardViewModel.getAnoEscolaridade();
        anoLectivoUtilizador = dashboardViewModel.getAnoLectivo();
        code = dashboardViewModel.getCodigoTurma();
        shareLocationArtefactos = dashboardViewModel.getSharedLocationArtefactos();
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
        switchShareLocationArtefacto = findViewById(R.id.switch_share_location_artefacto);
        linearLayoutShareLocationArtefacto = findViewById(R.id.linearlayout_share_location_artefacto);

        editTextAnoLectivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                position = editTextAnoLectivo.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextAnoLectivo.getText().length() == 4 && position != 5) {
                    editTextAnoLectivo.setText(editTextAnoLectivo.getText().toString() + "/");
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

        switchShareLocationArtefacto.setOnCheckedChangeListener(switchListener);
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
            linearLayoutShareLocationArtefacto.setVisibility(View.GONE);
        }

        editTextNome.setText(nomeUtilizador);

        // If not Explorador
        if (tipoUtilizador != 2) {
            editTextEscola.setText(escolaUtilizador);
            editTextAnoEscolaridade.setText(anoEscolaridadeUtilizador);
            editTextAnoLectivo.setText(anoLectivoUtilizador);
            switchShareLocationArtefacto.setChecked(shareLocationArtefactos);

            if (!code.isEmpty()) {
                editTextCodigoTurma.setText(code);
                editTextGerarCodigoTurma.setText(code);
                btnGerarCodigoTurma.setVisibility(View.GONE);

                if (tipoUtilizador == 1) {
                    btnCopiarCodigoTurma.setVisibility(View.VISIBLE);
                }
            } else {
                if (tipoUtilizador == 0) {
                    // Aluno

                } else {
                    // Professor
                    btnGerarCodigoTurma.setVisibility(View.VISIBLE);
                }
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
            dashboardViewModel.setSharedLocationArtefactos(switchShareLocationArtefacto.isChecked());

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

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked != shareLocationArtefactos) {
                btnSave.setEnabled(true);
            } else {
                //btnSave.setEnabled(false);
            }
        }
    };

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
                    (!editTextGerarCodigoTurma.getText().toString().equals(code) && !editTextGerarCodigoTurma.getText().toString().equals("-"))) {

                Log.d(TAG, "Code bool: " + !editTextGerarCodigoTurma.getText().toString().equals(code));
                Log.d(TAG, "Code edit: " + editTextGerarCodigoTurma.getText().toString());
                Log.d(TAG, "Code code: " + code);

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