package com.lasige.roteiroentremares.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.receivers.WifiP2pTurmaBroadcastReceiver;
import com.lasige.roteiroentremares.ui.dashboard.screens.artefactos.MeusArtefactosFragment;
import com.lasige.roteiroentremares.ui.dashboard.screens.artefactos.TurmaArtefactosFragment;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArtefactosActivity extends AppCompatActivity {

    private ArtefactosViewModel artefactosViewModel;

    private MaterialToolbar toolbar;

    // Views
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // Tab Item's Fragments
    private MeusArtefactosFragment meusArtefactosFragment;
    private TurmaArtefactosFragment turmaArtefactosFragment;

    // Wifi P2p
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artefactos);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        initToolbar();

        viewPager = findViewById(R.id.viewPager_artefactos);
        tabLayout = findViewById(R.id.tabLayout_artefactos);

        meusArtefactosFragment = new MeusArtefactosFragment();
        turmaArtefactosFragment = new TurmaArtefactosFragment();

        TabViewPagerAdapter tabViewPagerAdapter = new TabViewPagerAdapter(this);
        tabViewPagerAdapter.addFragment(meusArtefactosFragment, "Meus Artefactos");
        tabViewPagerAdapter.addFragment(turmaArtefactosFragment, "Artefactos Turma");

        viewPager.setAdapter(tabViewPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Meus");
                        break;
                    }
                    case 1: {
                        tab.setText("Turma");
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        viewPager.setUserInputEnabled(false);
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
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString(getResources().getString(R.string.title_artefactos
        ));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class TabViewPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitlesList = new ArrayList<>();

        public TabViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitlesList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artefacto_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete_all_artefactos:
                if (tabLayout.getSelectedTabPosition() == 0) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setTitle("Apagar todos os artefactos?");
                    materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar todos os teus artefactos?");
                    materialAlertDialogBuilder.setPositiveButton("Sim, apagar todos", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            artefactosViewModel.deleteAllArtefacto();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    AlertDialog alertDialog = materialAlertDialogBuilder.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setTitle("Apagar todos os artefactos da turma?");
                    materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar todos os artefactos que recebeste da turma?");
                    materialAlertDialogBuilder.setPositiveButton("Sim, apagar todos", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            artefactosViewModel.deleteAllArtefactoTurma();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    AlertDialog alertDialog = materialAlertDialogBuilder.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));
                }
        }

        return super.onOptionsItemSelected(item);
    }
}