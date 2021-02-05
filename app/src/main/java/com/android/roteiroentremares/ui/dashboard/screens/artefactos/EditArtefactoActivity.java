package com.android.roteiroentremares.ui.dashboard.screens.artefactos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.ArtefactosActivity;
import com.android.roteiroentremares.ui.dashboard.PessoalActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditArtefactoActivity extends AppCompatActivity {

    // ViewModel
    private ArtefactosViewModel artefactosViewModel;

    // Views
    private MaterialToolbar toolbar;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutContent;
    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextContent;
    private LinearLayout linearLayoutLocation;
    private LinearLayout linearLayoutShare;
    private TextView textViewLocation;
    private TextView textViewShare;
    private ImageButton imageButtonLocationDirections;
    private ImageButton imageButtonLocationCopy;
    private SwitchMaterial switchMaterialShare;
    private Button buttonSubmit;

    // Variables
    private Artefacto artefactoToEdit;
    private int artefactoId;
    private String artefactoTitle;
    private String artefactoContent;
    private int artefactoType;
    private String artefactoDescription;
    private String artefactoDate;
    private String artefactoLatitude;
    private String artefactoLongitude;
    private String artefactoCodigoTurma;
    private boolean artefactoShared;

    private String codigoTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();

        // Para apagar depois
        setContentView(R.layout.activity_edit_text_artefacto);

        if (artefactoType == 1) {
            // setContentView(R.layout.activity_edit_image_artefacto);
        } else if (artefactoType == 2) {
            // setContentView(R.layout.activity_edit_audio_artefacto);
        } else if (artefactoType == 3) {
            // setContentView(R.layout.activity_edit_video_artefacto);
        } else {
            // setContentView(R.layout.activity_edit_text_artefacto);
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Init ViewModel
        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        codigoTurma = artefactosViewModel.getCodigoTurma();

        if (artefactoId == -1) {
            Toast.makeText(this, "De momento não é possível editar o teu artefacto.", Toast.LENGTH_SHORT).show();
            finish();
        }

        initToolbar();
        initViews();
        fillForm();
        setOnClickListeners();

        Toast.makeText(this, artefactoLatitude + " ; " + artefactoLongitude, Toast.LENGTH_LONG).show();
    }

    private void getIntentData() {
        artefactoId = getIntent().getIntExtra("EDIT_ARTEFACTO_ID", -1);
        artefactoTitle = getIntent().getStringExtra("EDIT_ARTEFACTO_TITLE");
        artefactoContent = getIntent().getStringExtra("EDIT_ARTEFACTO_CONTENT");
        artefactoType = getIntent().getIntExtra("EDIT_ARTEFACTO_TYPE", 0);
        artefactoDescription = getIntent().getStringExtra("EDIT_ARTEFACTO_DESCRIPTION");
        artefactoDate = getIntent().getStringExtra("EDIT_ARTEFACTO_DATE");
        artefactoLatitude = getIntent().getStringExtra("EDIT_ARTEFACTO_LATITUDE");
        artefactoLongitude = getIntent().getStringExtra("EDIT_ARTEFACTO_LONGITUDE");
        artefactoCodigoTurma = getIntent().getStringExtra("EDIT_ARTEFACTO_CODIGOTURMA");
        artefactoShared = getIntent().getBooleanExtra("EDIT_ARTEFACTO_SHARED", false);
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString(getResources().getString(R.string.title_edit_artefacto));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        textInputLayoutTitle = findViewById(R.id.textinputlayout_title);
        textInputLayoutContent = findViewById(R.id.textinputlayout_content);
        textInputEditTextTitle = findViewById(R.id.textinputedittext_title);
        textInputEditTextContent = findViewById(R.id.textinputedittext_content);
        linearLayoutLocation = findViewById(R.id.linearlayout_location);
        linearLayoutShare = findViewById(R.id.linearlayout_share);
        textViewLocation = findViewById(R.id.textview_location_coordinates);
        textViewShare = findViewById(R.id.textview_share);
        imageButtonLocationDirections = findViewById(R.id.imagebutton_location_directions);
        imageButtonLocationCopy = findViewById(R.id.imagebutton_location_copy);
        switchMaterialShare = findViewById(R.id.switch_share);
        buttonSubmit = findViewById(R.id.button_submit);

        if (artefactoLatitude == null || artefactoLongitude == null) {
            linearLayoutLocation.setVisibility(View.GONE);
        }

        if (codigoTurma.equals("")) {
            switchMaterialShare.setEnabled(false);
        }

        textInputEditTextTitle.addTextChangedListener(artefactoTextWatcher);
        textInputEditTextContent.addTextChangedListener(artefactoTextWatcher);
    }

    private void fillForm() {
        if (artefactoType == 0) {
            // Text
            textInputEditTextTitle.setText(artefactoTitle);
            textInputEditTextContent.setText(artefactoContent);
            switchMaterialShare.setChecked(artefactoShared);

            textViewLocation.setText(artefactoLatitude + "," + artefactoLongitude);
        }
    }

    private void setOnClickListeners() {
        imageButtonLocationDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + textViewLocation.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        imageButtonLocationCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Localização do Artefacto", textViewLocation.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(EditArtefactoActivity.this, "Localização copiada!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Artefacto newTextArtefacto = new Artefacto(
                        textInputEditTextTitle.getText().toString(),
                        textInputEditTextContent.getText().toString(),
                        artefactoType,
                        null,
                        null,
                        null,
                        null,
                        codigoTurma,
                        switchMaterialShare.isChecked()
                );
                newTextArtefacto.setId(artefactoId);
                artefactosViewModel.updateArtefacto(newTextArtefacto);

                if (switchMaterialShare.isChecked() != artefactoShared) {
                    if (switchMaterialShare.isChecked()) {
                        // Not yet shared, but they want to share now
                        // TODO: Upload to remote database
                        Toast.makeText(EditArtefactoActivity.this, "Would upload to remote DB", Toast.LENGTH_SHORT).show();
                    } else {
                        // It is posted, but user wants to erase it
                        // TODO: Delete from remote database
                        Toast.makeText(EditArtefactoActivity.this, "Would delete from remote DB", Toast.LENGTH_SHORT).show();
                    }
                }

                finish();
            }
        });
    }

    private TextWatcher artefactoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (artefactoType == 0) {
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextContent.getText().toString().equals("")) {
                    buttonSubmit.setEnabled(true);
                } else {
                    buttonSubmit.setEnabled(false);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}