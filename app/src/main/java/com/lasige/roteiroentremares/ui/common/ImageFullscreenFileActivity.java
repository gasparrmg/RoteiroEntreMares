package com.lasige.roteiroentremares.ui.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.io.File;

public class ImageFullscreenFileActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_FILE_ID";
    public static final String INTENT_EXTRA_POSITION = "IMAGE_FULLSCREEN_FILE_POSITION";

    public static final String INTENT_EXTRA_IS_TRANSEPTO = "IMAGE_FULLSCREEN_RESOURCE_IS_TRANSEPTO";

    public static final int INTENT_EXTRA_RESULT = 101;

    private PhotoView photoView;
    private ImageButton imageButtonDelete;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        String photoPath = getIntent().getStringExtra(INTENT_EXTRA_KEY);

        int isTransepto = getIntent().getIntExtra(INTENT_EXTRA_IS_TRANSEPTO, 0);
        int position = getIntent().getIntExtra(INTENT_EXTRA_POSITION, -1);

        imageButtonDelete = findViewById(R.id.imageButton_delete);

        if (photoPath == null) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);
            File file = new File(photoPath);
            photoView.setImageURI(Uri.fromFile(file));
        }

        if (isTransepto == 1 && position != -1) {
            imageButtonDelete.setVisibility(View.VISIBLE);

            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ImageFullscreenFileActivity.this);
                    materialAlertDialogBuilder.setTitle("Apagar imagem?");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_delete_image));
                    materialAlertDialogBuilder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("delete", true);
                            returnIntent.putExtra("positionToDelete", position);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                }
            });
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
}