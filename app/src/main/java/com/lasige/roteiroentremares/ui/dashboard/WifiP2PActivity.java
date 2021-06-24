package com.lasige.roteiroentremares.ui.dashboard;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.receivers.WifiP2pBroadcastReceiver;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.ClickableString;
import com.lasige.roteiroentremares.util.TypefaceSpan;

import java.lang.reflect.Method;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class WifiP2PActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, WifiP2pManager.ChannelListener, DeviceListFragment.DeviceActionListener {

    public static final String TAG = "Wifi_P2P_Activity";
    public static final int PERMISSIONS_REQUEST_CODE = 1001;
    public static final String[] perms = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private DashboardViewModel mDashboardViewModel;

    private WifiP2pManager manager;
    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        /*if (mDashboardViewModel.getCodigoTurma() == null || mDashboardViewModel.getCodigoTurma().isEmpty()) {
            // setContentView(R.layout.activity_wifip2p); EMPTY
        } else {
            if (mDashboardViewModel.getTipoUtilizador() == 2) {
                setContentView(R.layout.activity_wifip2p);
            } else {
                setContentView(R.layout.activity_wifip2p);
            }
        }*/

        if (mDashboardViewModel.getTipoUtilizador() == 2) {
            // TODO: do layout with message 'available only for students and teachers'
            setContentView(R.layout.activity_wifip2p);
        } else {
            setContentView(R.layout.activity_wifip2p);
        }

        initToolbar();
        initViews();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            EasyPermissions.requestPermissions(this, "São necessárias permissões para esta funcionalidade", PERMISSIONS_REQUEST_CODE, perms);
        }

        // add necessary intent values to be matched.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);

        if (!initP2p()) {
            finish();
        }

        disconnect();
    }

    private void initViews() {
        textView = findViewById(R.id.textView);

        if (mDashboardViewModel.getTipoUtilizador() == 0) {
            textView.setText("Liga-te ao teu professor para partilhares automaticamente alguns dos teus Artefactos e para explorar os Artefactos capturados pelos teus colegas.");
        } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
            textView.setText("Ligue-se aos seus alunos e tenha automaticamente acesso aos Artefactos capturados por eles.\n" +
                    "Partilhe com os seus alunos o Código de Turma gerado por si no ecrã Pessoal.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wifip2p_menu, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atn_direct_enable:
                if (manager != null && channel != null) {

                    // Since this is the system wireless settings activity, it's
                    // not going to send us a result. We will be notified by
                    // WiFiDeviceBroadcastReceiver instead.

                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } else {
                    Log.e(TAG, "channel or manager is null");
                }
                return true;

            case R.id.atn_direct_discover:
                if (!isWifiP2pEnabled) {
                    Toast.makeText(WifiP2PActivity.this, R.string.p2p_off_warning,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                final DeviceListFragment fragment = (DeviceListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frag_list);

                // this method pops up the modal
                if (mDashboardViewModel.getTipoUtilizador() == 0) {
                    fragment.onInitiateDiscovery();
                } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
                    fragment.onInitiateCreateGroup();
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (mDashboardViewModel.getTipoUtilizador() == 1) {
                        createGroup();
                    } else {
                        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

                            @Override
                            public void onSuccess() {
                                Toast.makeText(WifiP2PActivity.this, "Discovery Initiated",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Discovery initiated");

                            /*if (mDashboardViewModel.getTipoUtilizador() == 1) {
                                createGroup();
                            }*/
                            }

                            @Override
                            public void onFailure(int reasonCode) {
                            /*Toast.makeText(WifiP2PActivity.this, "Discovery Failed : " + reasonCode,
                                    Toast.LENGTH_SHORT).show();*/
                                Log.d(TAG, "Discovery Failed : " + reasonCode);
                            }
                        });
                    }

                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** register the BroadcastReceiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiP2pBroadcastReceiver(manager, channel, this);

        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removePersistentGroups();
    }

    public void removePersistentGroups() {
        try {
            Method[] methods = WifiP2pManager.class.getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals("deletePersistentGroup")) {
                    // Remove any persistent groupo
                    for (int netid = 0; netid < 32; netid++) {
                        methods[i].invoke(manager, channel, netid, null);
                    }
                }
            }

            Log.i(TAG, "Persistent groups removed");
        } catch(Exception e) {
            Log.e(TAG, "Failure removing persistent groups: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean initP2p() {
        // Device capability definition check
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
            Log.e(TAG, "Wi-Fi Direct is not supported by this device.");
            return false;
        }

        // Hardware capability check
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            Log.e(TAG, "Cannot get Wi-Fi system service.");
            return false;
        }

        if (!wifiManager.isP2pSupported()) {
            Log.e(TAG, "Wi-Fi Direct is not supported by the hardware or Wi-Fi is off.");
            return false;
        }

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        if (manager == null) {
            Log.e(TAG, "Cannot get Wi-Fi Direct system service.");
            return false;
        }

        channel = manager.initialize(this, getMainLooper(), null);
        if (channel == null) {
            Log.e(TAG, "Cannot initialize Wi-Fi Direct.");
            return false;
        }

        return true;
    }

    public void resetData() {
        DeviceListFragment fragmentList = (DeviceListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_list);
        DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_detail);
        if (fragmentList != null) {
            fragmentList.clearPeers();
        }
        if (fragmentDetails != null) {
            fragmentDetails.resetViews();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
        DeviceDetailFragment fragment = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_detail);
        fragment.showDetails(device);
    }

    public void checkGroupInfo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            manager.requestGroupInfo(channel, new WifiP2pManager.GroupInfoListener() {
                @Override
                public void onGroupInfoAvailable(WifiP2pGroup group) {
                    Log.d(WifiP2PActivity.TAG, "Size of group -> " + group.getClientList().size());
                }
            });
        }
    }

    @Override
    public void connect(WifiP2pConfig config) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            /*if (mDashboardViewModel.getTipoUtilizador() == 0) {
                manager.connect(channel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiP2PActivity.this, "Connect failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
                manager.createGroup(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiP2PActivity.this, "Connect with createGroup failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }*/

            if (config.groupOwnerIntent == -1) {
                if (mDashboardViewModel.getTipoUtilizador() == 0) {
                    config.groupOwnerIntent = 0;
                } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
                    config.groupOwnerIntent = 15;
                }
            }

            Log.d(TAG, "Connecting now, the current groupOwnerIntent is -> " + config.groupOwnerIntent);

            manager.connect(channel, config, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                    // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                }

                @Override
                public void onFailure(int reason) {
                    Toast.makeText(WifiP2PActivity.this, "Connect failed. Retry.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void disconnect() {
        final DeviceDetailFragment fragment = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_detail);
        fragment.resetViews();
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);

            }

            @Override
            public void onSuccess() {
                fragment.getView().setVisibility(View.GONE);
            }

        });

        removePersistentGroups();
    }

    @Override
    public void onChannelDisconnected() {
        // we will try once more
        if (manager != null && !retryChannel) {
            Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
            resetData();
            retryChannel = true;
            manager.initialize(this, getMainLooper(), this);
        } else {
            Toast.makeText(this,
                    "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelDisconnect() {

        /*
         * A cancel abort request by user. Disconnect i.e. removeGroup if
         * already connected. Else, request WifiP2pManager to abort the ongoing
         * request
         */
        if (manager != null) {
            final DeviceListFragment fragment = (DeviceListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.frag_list);
            if (fragment.getDevice() == null
                    || fragment.getDevice().status == WifiP2pDevice.CONNECTED) {
                disconnect();
            } else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
                    || fragment.getDevice().status == WifiP2pDevice.INVITED) {

                manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(WifiP2PActivity.this, "Aborting connection",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(WifiP2PActivity.this,
                                "Connect abort request failed. Reason Code: " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    public void createGroup() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            manager.createGroup(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {

                    if (ContextCompat.checkSelfPermission(WifiP2PActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        manager.requestGroupInfo(channel, new WifiP2pManager.GroupInfoListener() {
                            @Override
                            public void onGroupInfoAvailable(WifiP2pGroup group) {
                                if (group != null) {
                                    Log.d(TAG, "groupInfo after onSuccess -> " + group.toString());
                                } else {
                                    Log.d(TAG, "group IS null");
                                }
                            }
                        });
                    }

                    Log.d(TAG, "Group created successfully");

                    Toast.makeText(WifiP2PActivity.this,
                            "Group created successfully",
                            Toast.LENGTH_SHORT).show();

                    final DeviceListFragment fragment = (DeviceListFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frag_list);

                    // this method pops up the modal
                    //fragment.removeDialogIfActive();
                }

                @Override
                public void onFailure(int reason) {
                    Toast.makeText(WifiP2PActivity.this,
                            "Failed to create group. Reason: " + reason,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * @param isWifiP2pEnabled the isWifiP2pEnabled to set
     */
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.title_wifip2p));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}