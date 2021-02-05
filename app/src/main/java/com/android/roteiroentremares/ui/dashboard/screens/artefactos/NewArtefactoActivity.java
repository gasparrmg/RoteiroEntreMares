package com.android.roteiroentremares.ui.dashboard.screens.artefactos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.util.PermissionsUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class NewArtefactoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    // ViewModel
    private ArtefactosViewModel artefactosViewModel;

    // Views
    private MaterialToolbar toolbar;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutContent;
    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextContent;
    private LinearLayout linearLayoutShare;
    private TextView textViewShare;
    private SwitchMaterial switchMaterialShare;
    private Button buttonSubmit;

    // Variables
    private int artefactoType;
    private String codigoTurma;
    private boolean shareLocationArtefactosPreference;
    private double latitude;
    private double longitude;

    // Other
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If newText pressed ->
        artefactoType = getIntent().getIntExtra("NEW_ARTEFACTO_TYPE", 0);

        // Para apagar depois
        setContentView(R.layout.activity_new_text_artefacto);

        if (artefactoType == 1) {
            // setContentView(R.layout.activity_new_image_artefacto);
        } else if (artefactoType == 2) {
            // setContentView(R.layout.activity_new_audio_artefacto);
        } else if (artefactoType == 3) {
            // setContentView(R.layout.activity_new_video_artefacto);
        } else {
            // setContentView(R.layout.activity_new_text_artefacto);
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Init ViewModel
        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        codigoTurma = artefactosViewModel.getCodigoTurma();
        shareLocationArtefactosPreference = artefactosViewModel.getSharedLocationArtefactos();

        initToolbar();
        initViews();
        setOnClickListeners();
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString(getResources().getString(R.string.title_new_artefacto));
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
        linearLayoutShare = findViewById(R.id.linearlayout_share);
        textViewShare = findViewById(R.id.textview_share);
        switchMaterialShare = findViewById(R.id.switch_share);
        buttonSubmit = findViewById(R.id.button_submit);

        if (codigoTurma.equals("")) {
            switchMaterialShare.setEnabled(false);
        }

        textInputEditTextTitle.addTextChangedListener(artefactoTextWatcher);
        textInputEditTextContent.addTextChangedListener(artefactoTextWatcher);
    }

    private void setOnClickListeners() {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get location
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NewArtefactoActivity.this);

                // Check permissions (required by fusedLocationProviderClient)
                if (ActivityCompat.checkSelfPermission(NewArtefactoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewArtefactoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    EasyPermissions.requestPermissions(NewArtefactoActivity.this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                            PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getPermissionList());
                    return;
                }

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        location = task.getResult();

                        try {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(NewArtefactoActivity.this, Locale.getDefault());

                                // TODO: check if location.getLatitude and location.getLongitude are null

                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                latitude = addresses.get(0).getLatitude();
                                longitude = addresses.get(0).getLongitude();

                                Log.d("TESTE_LOCATION", latitude + "," + longitude);
                            } else {
                                Toast.makeText(NewArtefactoActivity.this, "Pedimos desculpa mas de momento não é possível recolher a localização do seu dispositivo", Toast.LENGTH_LONG).show();
                            }

                            Artefacto newTextArtefacto = new Artefacto(
                                    textInputEditTextTitle.getText().toString(),
                                    textInputEditTextContent.getText().toString(),
                                    artefactoType,
                                    null,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                    latitude == 0 ? null : Double.toString(latitude),
                                    longitude == 0 ? null : Double.toString(longitude),
                                    codigoTurma,
                                    switchMaterialShare.isChecked()
                            );

                            artefactosViewModel.insertArtefacto(newTextArtefacto);

                            if (switchMaterialShare.isChecked()) {
                                // TODO: Upload to the remote DB
                                // Toast.makeText(NewArtefactoActivity.this, "Would upload to remote DB", Toast.LENGTH_SHORT).show();

                                if (!shareLocationArtefactosPreference) {
                                    // TODO: Se shareLocationArtefactosPreference == FALSE -> não enviar Location para a BD
                                }
                            }

                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_REQUEST_CODE)
    private void askForPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getPermissionList())) {
            Toast.makeText(this, "Already has permissions needed", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                    PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getPermissionList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            askForPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            askForPermissions();
        }
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