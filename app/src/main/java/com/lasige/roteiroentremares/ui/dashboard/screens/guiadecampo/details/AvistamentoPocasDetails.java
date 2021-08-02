package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoPocasDetailsAdapter;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoPocasDetails extends AppCompatActivity {

    // Views
    private TextView textViewTipoFundo;
    private TextView textViewProfundidade;
    private TextView textViewAreaSuperficie;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca;

    private AvistamentoPocasDetailsAdapter adapter;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_pocas_details);

        avistamentoPoca = (AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias) getIntent().getSerializableExtra("avistamentoPoca");

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
        SpannableString s = new SpannableString("Poça " + avistamentoPoca.getAvistamentoPocasAvencas().getIdAvistamento());
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTipoFundo = findViewById(R.id.textView_tipoFundo);
        textViewProfundidade = findViewById(R.id.textView_profundidade);
        textViewAreaSuperficie = findViewById(R.id.textView_area_superficie);
        imageView = findViewById(R.id.imageview_picture);

        textViewTipoFundo.setText(avistamentoPoca.getAvistamentoPocasAvencas().getTipoFundo());
        textViewProfundidade.setText(String.valueOf(avistamentoPoca.getAvistamentoPocasAvencas().getProfundidadeValue()) + avistamentoPoca.getAvistamentoPocasAvencas().getProfundidadeUnit());
        textViewAreaSuperficie.setText(String.valueOf(avistamentoPoca.getAvistamentoPocasAvencas().getAreaSuperficieValue()) + avistamentoPoca.getAvistamentoPocasAvencas().getAreaSuperficieUnit());

        Glide.with(this)
                .load(new File(avistamentoPoca.getAvistamentoPocasAvencas().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvistamentoPocasDetails.this, ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, avistamentoPoca.getAvistamentoPocasAvencas().getPhotoPath());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoPocasDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoPoca(avistamentoPoca);
    }
}