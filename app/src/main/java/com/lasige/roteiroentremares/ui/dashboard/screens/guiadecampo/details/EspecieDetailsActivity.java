package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderAdapter;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.avencas.EspecieAvencasDetailsFragment;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.avencas.EspecieAvencasSabiasQueFragment;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.EspecieRiaFormosaDetailsFragment;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.EspecieRiaFormosaSabiasQueFragment;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.GlossarioRiaFormosaActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EspecieDetailsActivity extends AppCompatActivity {
    private DashboardViewModel dashboardViewModel;

    private EspecieAvencas especie;
    private EspecieRiaFormosa especieRiaFormosa;

    private MaterialToolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public NestedScrollView nestedScrollView;
    private FloatingActionButton fabGlossario;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;

    private ViewPager2 viewPager;

    private int avencasOrRiaFormosa;

    private int[] images;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especie_details);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        avencasOrRiaFormosa = getIntent().getIntExtra("avencasOrRiaFormosa", dashboardViewModel.getAvencasOrRiaFormosa());

        if (dashboardViewModel.getAvencasOrRiaFormosa() == 0) {
            especie = (EspecieAvencas) getIntent().getSerializableExtra("especie");
            initToolbar();
            initImageSlider(especie.getPictures());

            fabGlossario.setVisibility(View.GONE);

            initViewPager();
        } else if (dashboardViewModel.getAvencasOrRiaFormosa() == 1) {
            especieRiaFormosa = (EspecieRiaFormosa) getIntent().getSerializableExtra("especie");
            initToolbar();
            initImageSlider(especieRiaFormosa.getPictures());

            fabGlossario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EspecieDetailsActivity.this, GlossarioRiaFormosaActivity.class);
                    startActivity(intent);
                }
            });

            initViewPager();
        } else {
            Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            Log.d("avencasOrRiaFormosa", "ENTROU");
            finish();
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

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        fabGlossario = findViewById(R.id.fab_glossario);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_especie);

        SpannableString s;

        if (avencasOrRiaFormosa == 0) {
            s = new SpannableString(especie.getNomeComum());
        } else {
            s = new SpannableString(especieRiaFormosa.getNomeComum());
        }

        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColorExpanded);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setTitle(s);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.especie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_fullscreen:
                Intent intent = new Intent(EspecieDetailsActivity.this, ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, images[sliderView.getCurrentPagePosition()]);
                startActivity(intent);
        }
        return false;
    }

    public void initImageSlider(ArrayList<String> picturesText) {
        sliderView = findViewById(R.id.imageSlider);

        //ArrayList<String> picturesText = especie.getPictures(); // String ArrayList
        images = new int[picturesText.size()];

        for (int i = 0; i < images.length; i++) {
            images[i] = getResources().getIdentifier(picturesText.get(i), "drawable", getPackageName());
        }

        sliderAdapter = new SliderAdapter(this, images);
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager_especieDetails);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

        if (avencasOrRiaFormosa == 0) {
            if (especie.getSabiasQue().isEmpty()) {
                fragmentList.add(new EspecieAvencasDetailsFragment(especie));
            } else {
                fragmentList.add(new EspecieAvencasDetailsFragment(especie));
                fragmentList.add(new EspecieAvencasSabiasQueFragment(especie));
            }
        } else {
            if (especieRiaFormosa.getSabiasQue().isEmpty() && especieRiaFormosa.getCuriosidades().isEmpty()) {
                fragmentList.add(new EspecieRiaFormosaDetailsFragment(especieRiaFormosa));
            } else {
                fragmentList.add(new EspecieRiaFormosaDetailsFragment(especieRiaFormosa));
                fragmentList.add(new EspecieRiaFormosaSabiasQueFragment(especieRiaFormosa));
            }
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragmentList,
                getSupportFragmentManager(),
                getLifecycle()
        );

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nestedScrollView.scrollTo(0,0);
            }
        });
    }
}