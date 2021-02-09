package com.android.roteiroentremares.ui.dashboard.screens.artefactos;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.ArtefactosActivity;
import com.android.roteiroentremares.ui.dashboard.PessoalActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.util.Constants;
import com.android.roteiroentremares.util.ImageFilePath;
import com.android.roteiroentremares.util.ImageUtils;
import com.android.roteiroentremares.util.PermissionsUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class EditArtefactoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

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

    // Image
    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextDescription;
    private ImageView imageViewPicture;
    private Button buttonTakePicture;
    private Button buttonAddPicture;

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
    private File currentPhotoFile;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();

        // Para apagar depois
        //setContentView(R.layout.activity_edit_text_artefacto);

        if (artefactoType == 1) {
            setContentView(R.layout.activity_edit_image_artefacto);
        } else if (artefactoType == 2) {
            // setContentView(R.layout.activity_edit_audio_artefacto);
        } else if (artefactoType == 3) {
            // setContentView(R.layout.activity_edit_video_artefacto);
        } else {
            setContentView(R.layout.activity_edit_text_artefacto);
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

        if (artefactoType == 1) {
            Log.d("PATH CENAS", artefactoContent);
            currentPhotoPath = artefactoContent;
            currentPhotoFile = new File(artefactoContent);
        }
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
    }

    private void fillForm() {
        if (artefactoType == 0) {
            // Text
            textInputEditTextTitle.setText(artefactoTitle);
            textInputEditTextContent.setText(artefactoContent);

            switchMaterialShare.setChecked(artefactoShared);
            textViewLocation.setText(artefactoLatitude + "," + artefactoLongitude);
        } else if (artefactoType == 1) {
            // Image
            textInputEditTextTitle.setText(artefactoTitle);
            textInputEditTextDescription.setText(artefactoDescription);

            // artefactoContent -> Path to Image
            if (currentPhotoFile.exists()) {
                imageViewPicture.setImageURI(Uri.fromFile(currentPhotoFile));
            } else {
                Toast.makeText(EditArtefactoActivity.this, "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                imageViewPicture.setVisibility(View.GONE);
            }

            switchMaterialShare.setChecked(artefactoShared);
            textViewLocation.setText(artefactoLatitude + "," + artefactoLongitude);
        } else if (artefactoType == 2) {

        } else {

        }
    }

    private void setOnClickListeners() {
        if (artefactoType == 1) {
            // Image
            buttonTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ask for permissions
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
                    ImageUtils.createPhotoDialog(EditArtefactoActivity.this, Uri.fromFile(new File(currentPhotoPath)));
                }
            });
        }

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
                if (artefactoType == 0) {
                    Artefacto newTextArtefacto = new Artefacto(
                            textInputEditTextTitle.getText().toString(),
                            textInputEditTextContent.getText().toString(),
                            artefactoType,
                            artefactoDescription,
                            artefactoDate,
                            artefactoLatitude,
                            artefactoLongitude,
                            codigoTurma,
                            switchMaterialShare.isChecked()
                    );
                    newTextArtefacto.setId(artefactoId);
                    artefactosViewModel.updateArtefacto(newTextArtefacto);
                } else if (artefactoType == 1) {
                    scanFile(currentPhotoPath);

                    Artefacto newImageArtefacto = new Artefacto(
                            textInputEditTextTitle.getText().toString(),
                            currentPhotoPath,
                            artefactoType,
                            textInputEditTextDescription.getText().toString(),
                            artefactoDate,
                            artefactoLatitude,
                            artefactoLongitude,
                            codigoTurma,
                            switchMaterialShare.isChecked()
                    );
                    newImageArtefacto.setId(artefactoId);
                    artefactosViewModel.updateArtefacto(newImageArtefacto);
                } else if (artefactoType == 2) {

                } else {

                }

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
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
        } else if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                currentPhotoFile = new File(currentPhotoPath);

                if (currentPhotoFile.exists()) {
                    imageViewPicture.setVisibility(View.VISIBLE);
                    imageViewPicture.setImageURI(Uri.fromFile(currentPhotoFile));
                } else {
                    Toast.makeText(EditArtefactoActivity.this, "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == Constants.GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                currentPhotoPath = ImageFilePath.getPath(EditArtefactoActivity.this, contentUri); // TO SAVE TO THE DB
                currentPhotoFile = new File(currentPhotoPath);

                if (currentPhotoFile.exists()) {
                    imageViewPicture.setVisibility(View.VISIBLE);
                    Glide.with(EditArtefactoActivity.this)
                            .load(currentPhotoFile)
                            .into(imageViewPicture);
                }
            } else {
                Toast.makeText(EditArtefactoActivity.this, "Houve um erro ao carregar o ficheiro. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void scanFile(String path) {

        MediaScannerConnection.scanFile(EditArtefactoActivity.this,
                new String[] { path }, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("TAG", "Finished scanning " + path);
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

            if (artefactoType == 1) {
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("") &&
                        currentPhotoPath != null) {
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