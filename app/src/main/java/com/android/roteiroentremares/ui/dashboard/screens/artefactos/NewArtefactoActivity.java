package com.android.roteiroentremares.ui.dashboard.screens.artefactos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.util.Constants;
import com.android.roteiroentremares.util.ImageFilePath;
import com.android.roteiroentremares.util.ImageUtils;
import com.android.roteiroentremares.util.PermissionsUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextDescription;
    private ImageView imageViewPicture;
    private Button buttonTakePicture;
    private Button buttonAddPicture;

    // Variables
    private int artefactoType;
    private String codigoTurma;
    private boolean shareLocationArtefactosPreference;
    private double latitude;
    private double longitude;
    private String currentPhotoPath;
    private File newPhotoFile;

    private Artefacto newTextArtefacto;

    // Other
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If newText pressed ->
        artefactoType = getIntent().getIntExtra("NEW_ARTEFACTO_TYPE", 0);

        // Para apagar depois
        // setContentView(R.layout.activity_new_text_artefacto);

        if (artefactoType == 1) {
            setContentView(R.layout.activity_new_image_artefacto);
        } else if (artefactoType == 2) {
            // setContentView(R.layout.activity_new_audio_artefacto);
        } else if (artefactoType == 3) {
            // setContentView(R.layout.activity_new_video_artefacto);
        } else {
            setContentView(R.layout.activity_new_text_artefacto);
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
        if (artefactoType == 0) {
            textInputLayoutContent = findViewById(R.id.textinputlayout_content);
            textInputEditTextContent = findViewById(R.id.textinputedittext_content);

            textInputEditTextContent.addTextChangedListener(artefactoTextWatcher);
        }

        if (artefactoType == 1) {
            textInputLayoutDescription = findViewById(R.id.textinputlayout_description);
            textInputEditTextDescription = findViewById(R.id.textinputedittext_description);
            imageViewPicture = findViewById(R.id.imageview_picture);
            buttonTakePicture = findViewById(R.id.btn_take_picture);
            buttonAddPicture = findViewById(R.id.btn_add_picture);

            textInputEditTextDescription.addTextChangedListener(artefactoTextWatcher);
        }

        textInputLayoutTitle = findViewById(R.id.textinputlayout_title);
        textInputEditTextTitle = findViewById(R.id.textinputedittext_title);
        linearLayoutShare = findViewById(R.id.linearlayout_share);
        textViewShare = findViewById(R.id.textview_share);
        switchMaterialShare = findViewById(R.id.switch_share);
        buttonSubmit = findViewById(R.id.button_submit);

        if (codigoTurma.equals("")) {
            switchMaterialShare.setEnabled(false);
        }

        textInputEditTextTitle.addTextChangedListener(artefactoTextWatcher);
    }

    private void setOnClickListeners() {
        if (artefactoType == 1) {
            buttonTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ask for permissions
                    Log.d("NEW_ARTEFACTO_ACTIVITY", "take picture clicked");
                    askCameraPermissions();
                }
            });

            buttonAddPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, Constants.GALLERY_REQUEST_CODE);
                }
            });

            imageViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageUtils.createPhotoDialog(NewArtefactoActivity.this, Uri.fromFile(newPhotoFile));
                }
            });
        }

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

                            if (artefactoType == 0) {
                                newTextArtefacto = new Artefacto(
                                        textInputEditTextTitle.getText().toString(),
                                        textInputEditTextContent.getText().toString(),
                                        artefactoType,
                                        "",
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        latitude == 0 ? null : Double.toString(latitude),
                                        longitude == 0 ? null : Double.toString(longitude),
                                        codigoTurma,
                                        switchMaterialShare.isChecked()
                                );
                            } else if (artefactoType == 1) {
                                scanFile(currentPhotoPath);

                                newTextArtefacto = new Artefacto(
                                        textInputEditTextTitle.getText().toString(),
                                        currentPhotoPath,
                                        artefactoType,
                                        textInputEditTextDescription.getText().toString(),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        latitude == 0 ? null : Double.toString(latitude),
                                        longitude == 0 ? null : Double.toString(longitude),
                                        codigoTurma,
                                        switchMaterialShare.isChecked()
                                );
                            } else if (artefactoType == 2) {
                                // Audio
                                newTextArtefacto = new Artefacto(
                                        textInputEditTextTitle.getText().toString(),
                                        textInputEditTextContent.getText().toString(),
                                        artefactoType,
                                        null,
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        latitude == 0 ? null : Double.toString(latitude),
                                        longitude == 0 ? null : Double.toString(longitude),
                                        codigoTurma,
                                        switchMaterialShare.isChecked()
                                );
                            } else {
                                // Video
                                newTextArtefacto = new Artefacto(
                                        textInputEditTextTitle.getText().toString(),
                                        textInputEditTextContent.getText().toString(),
                                        artefactoType,
                                        null,
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        latitude == 0 ? null : Double.toString(latitude),
                                        longitude == 0 ? null : Double.toString(longitude),
                                        codigoTurma,
                                        switchMaterialShare.isChecked()
                                );
                            }

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

    private void scanFile(String path) {

        MediaScannerConnection.scanFile(NewArtefactoActivity.this,
                new String[] { path }, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("TAG", "Finished scanning " + path);
                    }
                });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "RoteiroEntreMares_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Log.d("NEW_ARTEFACTO_ACTIVITY", "dispatchTakePictureIntent()");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("NEW_ARTEFACTO_ACTIVITY", "dispatchTakePictureIntent() ERROR: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.roteiroentremares.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.CAMERA_REQUEST_CODE);
            }
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE)
    private void askCameraPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getCameraPermissionList())) {
            // Open Camera
            dispatchTakePictureIntent();
        } else {
            EasyPermissions.requestPermissions(this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                    PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE, PermissionsUtils.getCameraPermissionList());
        }
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
        if (requestCode == PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE) {
            Log.d("NEW_ARTEFACTO_IMAGE", "Camera permissions granted");
        }
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

        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                newPhotoFile = new File(currentPhotoPath);

                if (newPhotoFile.exists()) {
                    imageViewPicture.setVisibility(View.VISIBLE);
                    imageViewPicture.setImageURI(Uri.fromFile(newPhotoFile));

                    if (!textInputEditTextTitle.getText().toString().equals("") &&
                            !textInputEditTextDescription.getText().toString().equals("")) {
                        buttonSubmit.setEnabled(true);
                    }
                } else {
                    Toast.makeText(NewArtefactoActivity.this, "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                }
            }
        }

        if (requestCode == Constants.GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                currentPhotoPath = ImageFilePath.getPath(NewArtefactoActivity.this, contentUri); // TO SAVE TO THE DB
                newPhotoFile = new File(currentPhotoPath);

                if (newPhotoFile.exists()) {
                    imageViewPicture.setVisibility(View.VISIBLE);
                    Glide.with(NewArtefactoActivity.this)
                            .load(newPhotoFile)
                            .into(imageViewPicture);

                    if (!textInputEditTextTitle.getText().toString().equals("") &&
                            !textInputEditTextDescription.getText().toString().equals("")) {
                        buttonSubmit.setEnabled(true);
                    }
                }
            } else {
                Toast.makeText(NewArtefactoActivity.this, "Houve um erro ao carregar o ficheiro. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
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
            } else if (artefactoType == 1) {
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("") &&
                        currentPhotoPath != null) {
                    buttonSubmit.setEnabled(true);
                } else {
                    buttonSubmit.setEnabled(false);
                }
            } else if (artefactoType == 2) {

            } else {

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}