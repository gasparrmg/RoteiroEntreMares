package com.lasige.roteiroentremares.ui.dashboard.screens.artefactos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.common.MediaPlayerActivity;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.lasige.roteiroentremares.util.Constants;
import com.lasige.roteiroentremares.util.FileUtils;
import com.lasige.roteiroentremares.util.ImageUtils;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.TimeUtils;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
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
public class EditArtefactoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    // ViewModel
    private ArtefactosViewModel artefactosViewModel;

    // Views
    private MaterialToolbar toolbar;
    private LinearProgressIndicator linearProgressIndicator;
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
    private ImageButton imageButtonInfoShare;
    private Button buttonSubmit;

    // Image
    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextDescription;
    private ImageView imageViewPicture;
    private MaterialButton buttonTakePicture;
    private MaterialButton buttonAddPicture;

    // Audio
    private LinearLayout linearLayoutMediaPlayer;
    private ImageButton imageButtonPlay;
    private ImageButton imageButtonPause;
    private SeekBar seekBarAudio;
    private TextView textViewAudioDuration;
    private MaterialButton buttonStartRecordingAudio;
    private MaterialButton buttonStopRecordingAudio;
    private TextView textViewRecording;

    // Video
    private FrameLayout frameLayoutVideo;
    private VideoView videoView;
    private MaterialButton buttonTakeVideo;
    private MaterialButton buttonAddVideo;

    // Variables
    private Artefacto artefactoToEdit;
    private int artefactoId;
    private String artefactoTitle;
    private String artefactoContent;
    private int artefactoType;
    private String artefactoDescription;
    private Date artefactoDate;
    private String artefactoLatitude;
    private String artefactoLongitude;
    private String artefactoCodigoTurma;
    private boolean artefactoShared;

    private String codigoTurma;

    private File currentPhotoFile;
    private String currentPhotoPath;
    private File currentVideoFile;
    private String currentVideoPath;

    private String currentAudioPath;
    private File currentAudioFile;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private Handler handlerSeekBar;
    private Runnable updateSeekBar;
    private boolean isPlayingAudio = false;
    private boolean isRecordingAudio = false;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();

        // Para apagar depois
        //setContentView(R.layout.activity_edit_text_artefacto);

        if (artefactoType == 1) {
            setContentView(R.layout.activity_edit_image_artefacto);
        } else if (artefactoType == 2) {
            setContentView(R.layout.activity_edit_audio_artefacto);
        } else if (artefactoType == 3) {
            setContentView(R.layout.activity_edit_video_artefacto);
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

    @Override
    protected void onPause() {
        super.onPause();

        if (getApplicationContext() instanceof RoteiroEntreMaresApplication) {
            if (((RoteiroEntreMaresApplication) getApplicationContext()).isUsingWifiP2pFeature()) {
                Log.d("debug_bg", "unregister BR from UserDashboard");
                unregisterReceiver(receiver);
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

    private void getIntentData() {
        artefactoId = getIntent().getIntExtra("EDIT_ARTEFACTO_ID", -1);
        artefactoTitle = getIntent().getStringExtra("EDIT_ARTEFACTO_TITLE");
        artefactoContent = getIntent().getStringExtra("EDIT_ARTEFACTO_CONTENT");
        artefactoType = getIntent().getIntExtra("EDIT_ARTEFACTO_TYPE", 0);
        artefactoDescription = getIntent().getStringExtra("EDIT_ARTEFACTO_DESCRIPTION");

        artefactoDate = new Date();
        artefactoDate.setTime(getIntent().getLongExtra("EDIT_ARTEFACTO_DATE", -1));

        artefactoLatitude = getIntent().getStringExtra("EDIT_ARTEFACTO_LATITUDE");
        artefactoLongitude = getIntent().getStringExtra("EDIT_ARTEFACTO_LONGITUDE");
        artefactoCodigoTurma = getIntent().getStringExtra("EDIT_ARTEFACTO_CODIGOTURMA");
        artefactoShared = getIntent().getBooleanExtra("EDIT_ARTEFACTO_SHARED", false);

        if (artefactoType == 1) {
            currentPhotoPath = artefactoContent;
            currentPhotoFile = new File(artefactoContent);
        }

        if (artefactoType == 2) {
            currentAudioPath = artefactoContent;
            currentAudioFile = new File(currentAudioPath);
        }

        if (artefactoType == 3) {
            currentVideoPath = artefactoContent;
            currentVideoFile = new File(currentVideoPath);
        }
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        linearProgressIndicator = findViewById(R.id.linearprogressindicator);

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
            frameLayoutVideo = findViewById(R.id.framelayout_video);
            videoView = findViewById(R.id.videoView_video);
            buttonTakeVideo = findViewById(R.id.btn_take_video);
            buttonAddVideo = findViewById(R.id.btn_add_video);

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
        imageButtonInfoShare = findViewById(R.id.imageButton_infoShare);
        buttonSubmit = findViewById(R.id.button_submit);

        if (artefactosViewModel.getTipoUtilizador() == 2) {
            // Explorador
            linearLayoutShare.setVisibility(View.GONE);
            switchMaterialShare.setEnabled(false);
        }

        if (artefactoLatitude.isEmpty() || artefactoLongitude.isEmpty()) {
            linearLayoutLocation.setVisibility(View.GONE);
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
            // Audio
            textInputEditTextTitle.setText(artefactoTitle);
            textInputEditTextDescription.setText(artefactoDescription);

            if (!currentAudioFile.exists()) {
                Toast.makeText(EditArtefactoActivity.this, "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                linearLayoutMediaPlayer.setVisibility(View.GONE);
            }

            switchMaterialShare.setChecked(artefactoShared);
            textViewLocation.setText(artefactoLatitude + "," + artefactoLongitude);
        } else {
            // Video
            textInputEditTextTitle.setText(artefactoTitle);
            textInputEditTextDescription.setText(artefactoDescription);

            if (!currentVideoFile.exists()) {
                Toast.makeText(EditArtefactoActivity.this, "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                frameLayoutVideo.setVisibility(View.GONE);
            } else {
                videoView.setVideoURI(Uri.fromFile(currentVideoFile));
                videoView.start();
            }

            switchMaterialShare.setChecked(artefactoShared);
            textViewLocation.setText(artefactoLatitude + "," + artefactoLongitude);
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

        if (artefactoType == 2) {
            // Audio
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
                    startActivityForResult(Intent.createChooser(intent,"Escolhe um vídeo"), Constants.VIDEO_FROM_GALLERY_REQUEST_CODE);
                }
            });

            frameLayoutVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentVideoPath != null) {
                        Intent intentVideoPlayer = new Intent(EditArtefactoActivity.this, MediaPlayerActivity.class);
                        intentVideoPlayer.putExtra("path", currentVideoPath);
                        startActivityForResult(intentVideoPlayer, Constants.MEDIAPLAYER_REQUEST_CODE);
                    }
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
                if (switchMaterialShare.isChecked()) {
                    // Check for file size
                    if (artefactoType != 0) {

                        long fileInBytes;
                        long fileInMb = 0;

                        // Not text
                        if (artefactoType == 1) {
                            // Photo
                            File photoFile = new File(currentPhotoPath);

                            fileInBytes = photoFile.length();
                            Log.d("NEW_ARTEFACTO", "This file is " + fileInBytes + "B large.");
                            fileInMb = (fileInBytes / 1024) / 1024;
                        } else if (artefactoType == 2) {
                            // Audio
                            File audioFile = new File(currentAudioPath);

                            fileInBytes = audioFile.length();
                            Log.d("NEW_ARTEFACTO", "This file is " + fileInBytes + "B large.");
                            fileInMb = (fileInBytes / 1024) / 1024;
                        } else if (artefactoType == 3) {
                            // Video
                            File videoFile = new File(currentVideoPath);

                            fileInBytes = videoFile.length();
                            Log.d("NEW_ARTEFACTO", "This file is " + fileInBytes + "B large.");
                            fileInMb = (fileInBytes / 1024) / 1024;
                        }

                        Log.d("NEW_ARTEFACTO", "This file is " + fileInMb + "MB large.");

                        if (fileInMb > 25) {
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditArtefactoActivity.this);
                            materialAlertDialogBuilder.setTitle("Erro");
                            materialAlertDialogBuilder.setMessage(getResources().getString(R.string.artefactos_too_large_file));
                            materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dismiss
                                }
                            });
                            materialAlertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    switchMaterialShare.setChecked(false);
                                }
                            });
                            materialAlertDialogBuilder.show();

                            return;
                        }
                    }
                }

                linearProgressIndicator.setVisibility(View.VISIBLE);

                if (artefactoType == 0) {
                    Artefacto newTextArtefacto = new Artefacto(
                            artefactosViewModel.getNome(),
                            textInputEditTextTitle.getText().toString(),
                            textInputEditTextContent.getText().toString(),
                            artefactoType,
                            artefactoDescription,
                            Calendar.getInstance().getTime(),
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
                            artefactosViewModel.getNome(),
                            textInputEditTextTitle.getText().toString(),
                            currentPhotoPath,
                            artefactoType,
                            textInputEditTextDescription.getText().toString(),
                            Calendar.getInstance().getTime(),
                            artefactoLatitude,
                            artefactoLongitude,
                            codigoTurma,
                            switchMaterialShare.isChecked()
                    );
                    newImageArtefacto.setId(artefactoId);
                    artefactosViewModel.updateArtefacto(newImageArtefacto);
                } else if (artefactoType == 2) {
                    Artefacto newAudioArtefacto = new Artefacto(
                            artefactosViewModel.getNome(),
                            textInputEditTextTitle.getText().toString(),
                            currentAudioPath,
                            artefactoType,
                            textInputEditTextDescription.getText().toString(),
                            Calendar.getInstance().getTime(),
                            artefactoLatitude,
                            artefactoLongitude,
                            codigoTurma,
                            switchMaterialShare.isChecked()
                    );
                    newAudioArtefacto.setId(artefactoId);
                    artefactosViewModel.updateArtefacto(newAudioArtefacto);
                } else {
                    Artefacto newVideoArtefacto = new Artefacto(
                            artefactosViewModel.getNome(),
                            textInputEditTextTitle.getText().toString(),
                            currentVideoPath,
                            artefactoType,
                            textInputEditTextDescription.getText().toString(),
                            Calendar.getInstance().getTime(),
                            artefactoLatitude,
                            artefactoLongitude,
                            codigoTurma,
                            switchMaterialShare.isChecked()
                    );
                    newVideoArtefacto.setId(artefactoId);
                    artefactosViewModel.updateArtefacto(newVideoArtefacto);
                }

                if (switchMaterialShare.isChecked() != artefactoShared) {
                    if (switchMaterialShare.isChecked()) {
                        // Not yet shared, but they want to share now
                        // Toast.makeText(EditArtefactoActivity.this, "Would upload to remote DB", Toast.LENGTH_SHORT).show();
                    } else {
                        // It is posted, but user wants to erase it
                        // Toast.makeText(EditArtefactoActivity.this, "Would delete from remote DB", Toast.LENGTH_SHORT).show();
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

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, Constants.VIDEO_REQUEST_CODE);
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

        linearLayoutMediaPlayer.setVisibility(View.VISIBLE);
        textViewAudioDuration.setVisibility(View.INVISIBLE);
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

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_MICROPHONE_REQUEST_CODE)
    private void askMicrophonePermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getMicrophonePermissionList())) {
            // Open Camera
            startRecordingAudio();
        } else {
            EasyPermissions.requestPermissions(this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                    PermissionsUtils.PERMISSIONS_MICROPHONE_REQUEST_CODE, PermissionsUtils.getMicrophonePermissionList());
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
                currentPhotoPath = FileUtils.getPath(EditArtefactoActivity.this, contentUri); // TO SAVE TO THE DB
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
        } else if (requestCode == Constants.VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            currentVideoPath = FileUtils.getPath(EditArtefactoActivity.this, videoUri);
            currentVideoFile = new File(currentVideoPath);

            videoView.setVideoURI(videoUri);
            videoView.start();
        } else if (requestCode == Constants.MEDIAPLAYER_REQUEST_CODE) {
            Log.d("NEW_ARTEFACTO_ACTIVITY", "onActivityResult MediaPlayer");
            videoView.start();
        } else if (requestCode == Constants.VIDEO_FROM_GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            currentVideoPath = FileUtils.getPath(EditArtefactoActivity.this, videoUri);
            currentVideoFile = new File(currentVideoPath);

            videoView.setVideoURI(videoUri);
            videoView.start();
        } else {

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

            if (artefactoType == 2) {
                if (!textInputEditTextTitle.getText().toString().equals("") &&
                        !textInputEditTextDescription.getText().toString().equals("") &&
                        currentAudioPath != null) {
                    buttonSubmit.setEnabled(true);
                } else {
                    buttonSubmit.setEnabled(false);
                }
            }

            if (artefactoType == 3) {
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