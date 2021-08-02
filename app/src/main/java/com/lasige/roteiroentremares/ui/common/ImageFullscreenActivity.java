package com.lasige.roteiroentremares.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

public class ImageFullscreenActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_RESOURCE_ID";
    public static final String INTENT_EXTRA_INFO = "IMAGE_FULLSCREEN_RESOURCE_ID_INFORMACAO_ADICIONAL";

    private PhotoView photoView;
    private ImageButton imageButtonInfo;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        int resourceId = getIntent().getIntExtra(INTENT_EXTRA_KEY, 0);

        String info = getIntent().getStringExtra(INTENT_EXTRA_INFO);

        imageButtonInfo = findViewById(R.id.imageButton_info);

        if (info != null) {
            // info symbol GONE
            imageButtonInfo.setVisibility(View.VISIBLE);

            imageButtonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ImageFullscreenActivity.this);
                    materialAlertDialogBuilder.setTitle("Informação adicional");
                    materialAlertDialogBuilder.setMessage(info);
                    materialAlertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss
                        }
                    });

                    materialAlertDialogBuilder.show();
                }
            });
        }

        if (resourceId == 0) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);

            // photoView.setImageResource(resourceId);

            Glide.with(this)
                    .load(resourceId)
                    .into(photoView);
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