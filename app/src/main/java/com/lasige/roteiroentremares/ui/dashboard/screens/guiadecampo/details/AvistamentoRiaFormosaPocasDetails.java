package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaPocasDetailsAdapter;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoRiaFormosaPocasDetails extends AppCompatActivity {

    // Views
    private TextView textViewTitle;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias avistamentoPocas;

    private AvistamentoRiaFormosaPocasDetailsAdapter adapter;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_riaformosa_dunas_details);

        avistamentoPocas = (AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias) getIntent().getSerializableExtra("avistamentoRiaFormosaPoca");

        initToolbar();
        initViews();
        initRecyclerView();
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

    private void initToolbar() {
        SpannableString s = new SpannableString("Poças de maré");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textView_title);
        imageView = findViewById(R.id.imageView_photo_grelha);

        textViewTitle.setText("Poça " + avistamentoPocas.getAvistamentoPocasRiaFormosa().getIdAvistamento());

        Glide.with(this)
                .load(new File(avistamentoPocas.getAvistamentoPocasRiaFormosa().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvistamentoRiaFormosaPocasDetails.this, ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, avistamentoPocas.getAvistamentoPocasRiaFormosa().getPhotoPath());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoRiaFormosaPocasDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoPocas(avistamentoPocas);
    }
}