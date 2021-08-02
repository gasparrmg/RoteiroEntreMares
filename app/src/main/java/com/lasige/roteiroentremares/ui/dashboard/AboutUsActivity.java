package com.lasige.roteiroentremares.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.util.ClickableString;
import com.lasige.roteiroentremares.util.TypefaceSpan;

public class AboutUsActivity extends AppCompatActivity {

    private TextView mTextViewContent;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mTextViewContent = findViewById(R.id.textView);

        mTextViewContent.setText("O projeto “Roteiro Entre Marés” foi financiado pela Fundo Azul –Direção Geral de Política do Mar (FA_06_2017_011). O Projeto foi coordenado por Cláudia Faria, do Instituto de Educação da Universidade de Lisboa, responsável pela criação dos conteúdos didáticos, em estreita colaboração com o Departamento de Informática da Faculdade de Ciências da Universidade de Lisboa, responsável pela criação e desenvolvimento da aplicação informática. Para além destas duas instituições, o projeto teve também como instituições parceiras: EMAC – Empresa Municipal de Ambiente de Cascais; Centro de Ciência Viva de Tavira a Cascais Ambiente; Escola Superior de Educação João de Deus; Universidade Aberta – Departamento de Ciências e Tecnologia; MARE – Centro de Ciências do Mar e do Ambiente. O projeto contou também com a colaboração de Carla Kullberg (FC/ULisboa), e de Frederico Almada (MARE/ISPA) para o desenvolvimento de alguns dos conteúdos. As ilustrações são da autoria de Beatriz Santos. Para mais informações: ");

        SpannableString link = ClickableString.makeLinkSpan("http://www.ie.ulisboa.pt/projetos/roteiro-entre-mares", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ie.ulisboa.pt/projetos/roteiro-entre-mares"));
                startActivity(browserIntent);
            }
        });

        mTextViewContent.append(link);

        ClickableString.makeLinksFocusable(mTextViewContent);

        SpannableString s = new SpannableString(getResources().getString(R.string.about_us_title));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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