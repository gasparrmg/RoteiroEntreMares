package com.lasige.roteiroentremares.ui.dashboard.screens.artefactos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.ui.common.MediaPlayerActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.lasige.roteiroentremares.util.Constants;
import com.lasige.roteiroentremares.util.ImageFilePath;
import com.lasige.roteiroentremares.util.ImageUtils;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.TimeUtils;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tooltip.Tooltip;

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
    private LinearProgressIndicator linearProgressIndicator;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutContent;
    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextContent;
    private LinearLayout linearLayoutShare;
    private TextView textViewShare;
    private SwitchMaterial switchMaterialShare;
    private ImageButton imageButtonInfoShare;

    private SwitchMaterial switchMaterialLocation;

    private Button buttonSubmit;

    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextDescription;
    private ImageView imageViewPicture;
    /*private Button buttonTakePicture;
    private Button buttonAddPicture;*/
    private MaterialButton buttonTakePicture;
    private MaterialButton buttonAddPicture;

    private LinearLayout linearLayoutMediaPlayer;
    private ImageButton imageButtonPlay;
    private ImageButton imageButtonPause;
    private SeekBar seekBarAudio;
    private TextView textViewAudioDuration;
    private MaterialButton buttonStartRecordingAudio;
    private MaterialButton buttonStopRecordingAudio;
    private TextView textViewRecording;

    private FrameLayout frameLayoutVideo;
    private VideoView videoView;
    private MaterialButton buttonTakeVideo;
    private MaterialButton buttonAddVideo;

    // Variables
    private int artefactoType;
    private String codigoTurma;
    private boolean shareLocationArtefactosPreference;
    private double latitude;
    private double longitude;
    private String currentPhotoPath;
    private File newPhotoFile;
    private String currentAudioPath;
    private String currentVideoPath;
    private boolean isPlayingAudio = false;
    private boolean isRecordingAudio = false;

    private Artefacto newTextArtefacto;

    // Other
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private Handler handlerSeekBar;
    private Runnable updateSeekBar;

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
            setContentView(R.layout.activity_new_audio_artefacto);
        } else if (artefactoType == 3) {
            setContentView(R.layout.activity_new_video_artefacto);
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
        linearProgressIndicator = findViewById(R.id.linearprogressindicator);

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

        if (artefactoType == 2) {
            textInputLayoutDescription = findViewById(R.id.textinputlayout_description);
            textInputEditTextDescription = findViewById(R.id.textinputedittext_description);
            linearLayoutMediaPlayer = findViewById(R.id.linearlayout_media_player);
            imageButtonPlay = findViewById(R.id.imagebutton_play_audio);
            imageButtonPause = findViewById(R.id.imagebutton_pause_audio);
            seekBarAudio = findViewById(R.id.seekbar_audio);
            textViewAudioDuration = findViewById(R.id.textView_audio_duration);
            buttonStartRecordingAudio = findViewById(R.id.btn_start_recording_audio);
            buttonStopRecordingAudio = findViewById(R.id.btn_stop_recording_audio);
            textViewRecording = findViewById(R.id.textview_isRecording);

            textInputEditTextDescription.addTextChangedListener(artefactoTextWatcher);
        }

        if (artefactoType == 3) {
            textInputLayoutDescription = findViewById(R.id.textinputlayout_description);
            textInputEditTextDescription = findViewById(R.id.textinputedittext_description);
            videoView = findViewById(R.id.videoView_video);
            frameLayoutVideo = findViewById(R.id.framelayout_video);

            buttonTakeVideo = findViewById(R.id.btn_take_video);
            buttonAddVideo = findViewById(R.id.btn_add_video);

            textInputEditTextDescription.addTextChangedListener(artefactoTextWatcher);
        }

        textInputLayoutTitle = findViewById(R.id.textinputlayout_title);
        textInputEditTextTitle = findViewById(R.id.textinputedittext_title);
        linearLayoutShare = findViewById(R.id.linearlayout_share);
        textViewShare = findViewById(R.id.textview_share);
        switchMaterialShare = findViewById(R.id.switch_share);
        imageButtonInfoShare = findViewById(R.id.imageButton_infoShare);
        buttonSubmit = findViewById(R.id.button_submit);

        switchMaterialLocation = findViewById(R.id.switch_location);

        if (artefactosViewModel.getTipoUtilizador() == 2) {
            // Explorador
            linearLayoutShare.setVisibility(View.GONE);
            switchMaterialShare.setEnabled(false);
        }

        if (codigoTurma.equals("")) {
            imageButtonInfoShare.setVisibility(View.VISIBLE);

            imageButtonInfoShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tooltip tooltip = new Tooltip.Builder(imageButtonInfoShare)
                            .setText(getResources().getString(R.string.coming_soon_message))
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setCancelable(true)
                            .show();
                }
            });

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
                    /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, Constants.GALLERY_REQUEST_CODE);*/

                    askGalleryPermissions();
                }
            });

            imageViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageUtils.createPhotoDialog(NewArtefactoActivity.this, Uri.fromFile(newPhotoFile));
                }
            });
        }

        if (artefactoType == 2) {
            buttonStartRecordingAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    askMicrophonePermissions();
                }
            });

            buttonStopRecordingAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopRecordingAudio();
                }
            });

            imageButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAudio();
                }
            });

            imageButtonPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseAudio();
                }
            });

            seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer != null) {
                        pauseAudio();
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();

                    if (mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                        resumeAudio();
                    }
                }
            });
        }

        if (artefactoType == 3) {
            buttonTakeVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ask for permissions
                    Log.d("NEW_ARTEFACTO_ACTIVITY", "take video clicked");
                    askCameraPermissions();
                }
            });

            buttonAddVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Video"), Constants.VIDEO_FROM_GALLERY_REQUEST_CODE);
                }
            });

            frameLayoutVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TESTE_VID", "VID CLICKED");
                    if (currentVideoPath != null) {
                        Log.d("TESTE_VID", "VID CLICKED AND PATH NOT NULL");
                        Intent intentVideoPlayer = new Intent(NewArtefactoActivity.this, MediaPlayerActivity.class);
                        intentVideoPlayer.putExtra("path", currentVideoPath);
                        startActivityForResult(intentVideoPlayer, Constants.MEDIAPLAYER_REQUEST_CODE);
                    }
                }
            });
        }

        switchMaterialLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    askForLocationPermissions();
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearProgressIndicator.setVisibility(View.VISIBLE);

                if (switchMaterialLocation.isChecked()) {
                    // Get location
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NewArtefactoActivity.this);

                    // Check permissions (required by fusedLocationProviderClient)
                    if (ActivityCompat.checkSelfPermission(NewArtefactoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewArtefactoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        EasyPermissions.requestPermissions(NewArtefactoActivity.this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                                PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getLocationPermissionList());
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
                                    Toast.makeText(NewArtefactoActivity.this, "Pedimos desculpa mas não foi possível recolher a localização do seu dispositivo", Toast.LENGTH_LONG).show();
                                }

                                if (artefactoType == 0) {
                                    newTextArtefacto = new Artefacto(
                                            textInputEditTextTitle.getText().toString(),
                                            textInputEditTextContent.getText().toString(),
                                            artefactoType,
                                            "",
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                            latitude == 0 ? "" : Double.toString(latitude),
                                            longitude == 0 ? "" : Double.toString(longitude),
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
                                            latitude == 0 ? "" : Double.toString(latitude),
                                            longitude == 0 ? "" : Double.toString(longitude),
                                            codigoTurma,
                                            switchMaterialShare.isChecked()
                                    );
                                } else if (artefactoType == 2) {
                                    // Audio
                                    newTextArtefacto = new Artefacto(
                                            textInputEditTextTitle.getText().toString(),
                                            currentAudioPath,
                                            artefactoType,
                                            textInputEditTextDescription.getText().toString(),
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                            latitude == 0 ? "" : Double.toString(latitude),
                                            longitude == 0 ? "" : Double.toString(longitude),
                                            codigoTurma,
                                            switchMaterialShare.isChecked()
                                    );
                                } else {
                                    // Video
                                    newTextArtefacto = new Artefacto(
                                            textInputEditTextTitle.getText().toString(),
                                            currentVideoPath,
                                            artefactoType,
                                            textInputEditTextDescription.getText().toString(),
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                            latitude == 0 ? "" : Double.toString(latitude),
                                            longitude == 0 ? "" : Double.toString(longitude),
                                            codigoTurma,
                                            switchMaterialShare.isChecked()
                                    );
                                }

                                artefactosViewModel.insertArtefacto(newTextArtefacto);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    if (artefactoType == 0) {
                        newTextArtefacto = new Artefacto(
                                textInputEditTextTitle.getText().toString(),
                                textInputEditTextContent.getText().toString(),
                                artefactoType,
                                "",
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                "",
                                "",
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
                                "",
                                "",
                                codigoTurma,
                                switchMaterialShare.isChecked()
                        );
                    } else if (artefactoType == 2) {
                        // Audio
                        newTextArtefacto = new Artefacto(
                                textInputEditTextTitle.getText().toString(),
                                currentAudioPath,
                                artefactoType,
                                textInputEditTextDescription.getText().toString(),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                "",
                                "",
                                codigoTurma,
                                switchMaterialShare.isChecked()
                        );
                    } else {
                        // Video
                        newTextArtefacto = new Artefacto(
                                textInputEditTextTitle.getText().toString(),
                                currentVideoPath,
                                artefactoType,
                                textInputEditTextDescription.getText().toString(),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                "",
                                "",
                                codigoTurma,
                                switchMaterialShare.isChecked()
                        );
                    }

                    artefactosViewModel.insertArtefacto(newTextArtefacto);
                }

                if (switchMaterialShare.isChecked()) {
                    // TODO: Upload to the remote DB
                    // Toast.makeText(NewArtefactoActivity.this, "Would upload to remote DB", Toast.LENGTH_SHORT).show();

                    if (!shareLocationArtefactosPreference) {
                        // TODO: Se shareLocationArtefactosPreference == FALSE -> não enviar Location para a BD
                    }
                }

                finish();
            }
        });
    }

    private void checkIfLocationIsOn() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());



        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    switchMaterialLocation.setChecked(true);
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().

                                //resolvable.startResolutionForResult(getActivity(), LocationRequest.PRIORITY_HIGH_ACCURACY);
                                startIntentSenderForResult(resolvable.getResolution().getIntentSender(), LocationRequest.PRIORITY_HIGH_ACCURACY, null, 0, 0, 0, null);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_LOCATION_REQUEST_CODE)
    private void askForLocationPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getLocationPermissionList())) {
            // switchMaterialLocation.setChecked(true);

            // isLocationOn();
            checkIfLocationIsOn();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_LOCATION_REQUEST_CODE, PermissionsUtils.getLocationPermissionList());
            switchMaterialLocation.setChecked(false);
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
        } else if (requestCode == PermissionsUtils.PERMISSIONS_LOCATION_REQUEST_CODE) {
            // switchMaterialLocation.setChecked(true);
            // checkIfLocationIsOn();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PermissionsUtils.PERMISSIONS_LOCATION_REQUEST_CODE) {
            switchMaterialLocation.setChecked(false);

            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        } else if (requestCode == PermissionsUtils.PERMISSIONS_GALLERY_REQUEST_CODE) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        } else if (requestCode == PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        } else if (requestCode == PermissionsUtils.PERMISSIONS_MICROPHONE_REQUEST_CODE) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        }
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

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, Constants.VIDEO_REQUEST_CODE);
        }
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

    private void setupMediaRecorder() {
        // Setup Media Recorder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault());
        Date date = new Date();

        currentAudioPath = getExternalFilesDir("/").getAbsolutePath() + "/" + simpleDateFormat.format(date) + "_ROTEIROENTREMARES_audio_record.3gp";
        mediaRecorder.setOutputFile(currentAudioPath);

        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    }

    private void startRecordingAudio() {
        setupMediaRecorder();
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();

        isRecordingAudio = true;

        textViewRecording.setText("A gravar microfone...");
        buttonStartRecordingAudio.setVisibility(View.GONE);
        buttonStopRecordingAudio.setVisibility(View.VISIBLE);
    }

    private void stopRecordingAudio() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        isRecordingAudio = false;

        if (!textInputEditTextTitle.getText().toString().equals("") &&
                !textInputEditTextDescription.getText().toString().equals("") &&
                currentAudioPath != null) {
            buttonSubmit.setEnabled(true);
        }

        buttonStartRecordingAudio.setVisibility(View.VISIBLE);
        buttonStopRecordingAudio.setVisibility(View.GONE);
        textViewRecording.setText("Prime o botão para gravar...");

        textViewAudioDuration.setVisibility(View.INVISIBLE);
        linearLayoutMediaPlayer.setVisibility(View.VISIBLE);
    }

    private void playAudio() {
        imageButtonPause.setVisibility(View.VISIBLE);
        imageButtonPlay.setVisibility(View.GONE);

        if (!isPlayingAudio) {
            isPlayingAudio = true;

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(currentAudioPath);
                mediaPlayer.prepare();
                mediaPlayer.start();

                seekBarAudio.setMax(mediaPlayer.getDuration());
                textViewAudioDuration.setText(TimeUtils.getTimeString(mediaPlayer.getDuration()));
                textViewAudioDuration.setVisibility(View.VISIBLE);

                handlerSeekBar = new Handler();
                updateRunnable();
                handlerSeekBar.postDelayed(updateSeekBar, 0);

            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isPlayingAudio = false;

                    seekBarAudio.setProgress(0);

                    handlerSeekBar.removeCallbacks(updateSeekBar);

                    imageButtonPause.setVisibility(View.GONE);
                    imageButtonPlay.setVisibility(View.VISIBLE);
                }
            });
        } else {
            resumeAudio();
        }
    }

    private void resumeAudio() {
        imageButtonPause.setVisibility(View.VISIBLE);
        imageButtonPlay.setVisibility(View.GONE);

        mediaPlayer.start();

        updateRunnable();
        handlerSeekBar.postDelayed(updateSeekBar,0);
    }

    private void pauseAudio() {
        imageButtonPause.setVisibility(View.GONE);
        imageButtonPlay.setVisibility(View.VISIBLE);

        mediaPlayer.pause();

        handlerSeekBar.removeCallbacks(updateSeekBar);
    }

    private void updateRunnable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
                handlerSeekBar.postDelayed(this, 500);
            }
        };
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_GALLERY_REQUEST_CODE)
    private void askGalleryPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getGalleryPermissionList())) {
            // Open Gallery
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, Constants.GALLERY_REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_GALLERY_REQUEST_CODE, PermissionsUtils.getGalleryPermissionList());
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE)
    private void askCameraPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getCameraPermissionList())) {
            // Open Camera
            if (artefactoType == 3) {
                dispatchTakeVideoIntent();
            } else {
                dispatchTakePictureIntent();
            }
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE, PermissionsUtils.getCameraPermissionList());
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_MICROPHONE_REQUEST_CODE)
    private void askMicrophonePermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getMicrophonePermissionList())) {
            // Open Camera
            startRecordingAudio();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_MICROPHONE_REQUEST_CODE, PermissionsUtils.getMicrophonePermissionList());
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_REQUEST_CODE)
    private void askForPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getPermissionList())) {
            // Toast.makeText(this, "Already has permissions needed", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getPermissionList());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("NEW_ARTEFACTO_ACTIVITY", "onActivityResult");

        /*if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            askForPermissions();
        }*/

        if (requestCode == LocationRequest.PRIORITY_HIGH_ACCURACY) {
            Log.i("LocationFragment", "onActivityResult -> PRIORITY");
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // All required changes were successfully made
                    Log.i("LocationFragment", "onActivityResult: GPS Enabled by user");
                    switchMaterialLocation.setChecked(true);
                    break;
                case Activity.RESULT_CANCELED:
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(this, getResources().getText(R.string.gps_turned_off_error), Toast.LENGTH_LONG).show();
                    Log.i("LocationFragment", "onActivityResult: User rejected GPS request");
                    break;
                default:
                    break;
            }
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

        if (requestCode == Constants.VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("NEW_ARTEFACTO_ACTIVITY", "onActivityResult");
                Uri videoUri = data.getData();
                //currentVideoPath = videoUri.toString();
                currentVideoPath = ImageFilePath.getPath(NewArtefactoActivity.this, videoUri);
                Log.d("NEW_ARTEFACTO_ACTIVITY", "videoUri.toString(): " + videoUri.toString());
                frameLayoutVideo.setVisibility(View.VISIBLE);
                videoView.setVideoURI(videoUri);
                videoView.start();

                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("")) {
                    buttonSubmit.setEnabled(true);
                }
            } else {
                Log.d("NEW_ARTEFACTO_ACTIVITY", "VIDEO_RESULT_CODE -> NOT OKAY");
            }
        }

        if (requestCode == Constants.MEDIAPLAYER_REQUEST_CODE) {
            Log.d("NEW_ARTEFACTO_ACTIVITY", "onActivityResult MediaPlayer");
            videoView.start();
        }

        if (requestCode == Constants.VIDEO_FROM_GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            currentVideoPath = ImageFilePath.getPath(NewArtefactoActivity.this, videoUri);

            frameLayoutVideo.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.start();

            if (!textInputEditTextTitle.getText().toString().equals("") &&
                    !textInputEditTextDescription.getText().toString().equals("")) {
                buttonSubmit.setEnabled(true);
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
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("") &&
                        currentAudioPath != null) {
                    buttonSubmit.setEnabled(true);
                } else {
                    buttonSubmit.setEnabled(false);
                }
            } else {
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("") &&
                        currentVideoPath != null) {
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

    @Override
    protected void onStop() {
        if (isRecordingAudio) {
            stopRecordingAudio();
        }

        if (isPlayingAudio) {
            mediaPlayer.stop();
        }

        super.onStop();
    }
}