package com.lasige.roteiroentremares.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.android.roteiroentremares.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.services.WifiP2pGroupRegistrationServerService;
import com.lasige.roteiroentremares.services.WifiP2pSyncAlunoTesteService;
import com.lasige.roteiroentremares.services.WifiP2pSyncProfessorService;
import com.lasige.roteiroentremares.ui.dashboard.DeviceDetailFragment;
import com.lasige.roteiroentremares.ui.dashboard.DeviceListFragment;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;
import com.lasige.roteiroentremares.util.wifip2p.SyncList;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WifiP2pTurmaBroadcastReceiver extends BroadcastReceiver {

    @Inject
    SyncList syncList;

    public static final String RESPONSE_IP_ADDRESS = "com.lasige.roteiroentremares.RESPONSE_IP_ADDRESS";

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private Context activity;

    @Inject
    public WifiP2pTurmaBroadcastReceiver(
            WifiP2pManager manager,
            WifiP2pManager.Channel channel,
            Context activity
    ) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_STATE_CHANGED_ACTION -> State: " + state);

            if (activity instanceof WifiP2PActivity) {

                // UI update to indicate wifi p2p status.
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    // Wifi Direct mode is enabled
                    ((WifiP2PActivity) activity).setIsWifiP2pEnabled(true);
                } else {
                    ((WifiP2PActivity) activity).setIsWifiP2pEnabled(false);
                    ((WifiP2PActivity) activity).resetData();
                }

            } else {
                if (context.getApplicationContext() instanceof RoteiroEntreMaresApplication) {
                    if (((RoteiroEntreMaresApplication) context.getApplicationContext()).isUsingWifiP2pFeature()) {
                        if (state != WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                            ((RoteiroEntreMaresApplication) context.getApplicationContext()).setUsingWifiP2pFeature(false);

                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
                            materialAlertDialogBuilder.setTitle("Erro");
                            materialAlertDialogBuilder.setMessage("A ligação à Turma foi interrompida devido a um erro de ligação");
                            materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dismiss
                                }
                            });
                            materialAlertDialogBuilder.show();
                        }
                    }
                }
            }
        } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_DISCOVERY_CHANGED_ACTION");

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, 10000);

            if (activity instanceof WifiP2PActivity) {
                if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                    // Wifi P2P discovery started.
                    Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery started");
                } else {
                    // Wifi P2P discovery stopped.
                    // Do what you want to do when discovery stopped
                    Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery stopped");
                }
            } else {
                if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                    // Wifi P2P discovery started.
                    Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery started");
                } else {
                    // Wifi P2P discovery stopped.
                    // Do what you want to do when discovery stopped
                    Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery stopped");
                }
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_PEERS_CHANGED_ACTION");

            if (activity instanceof WifiP2PActivity) {
                // request available peers from the wifi p2p manager. This is an
                // asynchronous call and the calling activity is notified with a
                // callback on PeerListListener.onPeersAvailable()
                if (manager != null) {
                    if (PermissionsUtils.hasAcceptedWifiP2pPermissions(context)) {
                        Log.d(WifiP2PActivity.TAG, "Peers changed and manager not null -> requestPeers");
                        manager.requestPeers(channel, (WifiP2pManager.PeerListListener) ((WifiP2PActivity) activity).getSupportFragmentManager().findFragmentById(R.id.frag_list));
                    }
                }
            } else {

                // No action needed, I think.
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");

            if (manager == null) {
                Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> manager is null");
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (activity instanceof WifiP2PActivity) {
                if (networkInfo.isConnected()) {
                    Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> isConnected");

                    // we are connected with the other device, request connection
                    // info to find group owner IP
                    syncList.setStartProfessorServiceCallback(new SyncList.SyncListCallback() {
                        @Override
                        public void startProfessorSyncService(String ipAddress) {
                            Log.d(WifiP2PActivity.TAG, "Starting sync service with Client: " + ipAddress + "...");

                            Intent syncServiceIntent = new Intent(activity, WifiP2pSyncProfessorService.class);
                            syncServiceIntent.setAction(WifiP2pSyncProfessorService.ACTION_SYNC);
                            syncServiceIntent.putExtra(CollabUtils.EXTRAS_ALUNO_IP_ADDRESS, ipAddress);
                            activity.startService(syncServiceIntent);
                        }
                    });

                    DeviceDetailFragment fragment = (DeviceDetailFragment) ((WifiP2PActivity) activity)
                            .getSupportFragmentManager().findFragmentById(R.id.frag_detail);
                    manager.requestConnectionInfo(channel, fragment);
                } else {
                    Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> !isConnected");
                    // It's a disconnect
                    ((WifiP2PActivity) activity).stopServices();
                    ((WifiP2PActivity) activity).resetData();
                }
            } else {
                Log.d("debug_bg", "Connection but Im not in WifiP2pActivity");

                if (networkInfo.isConnected()) {
                    // we are connected with the other device, request connection
                    // info to find group owner IP

                    syncList.setStartProfessorServiceCallback(new SyncList.SyncListCallback() {
                        @Override
                        public void startProfessorSyncService(String ipAddress) {
                            Log.d(WifiP2PActivity.TAG, "Starting sync service with Client: " + ipAddress + "...");

                            Intent syncServiceIntent = new Intent(activity, WifiP2pSyncProfessorService.class);
                            syncServiceIntent.setAction(WifiP2pSyncProfessorService.ACTION_SYNC);
                            syncServiceIntent.putExtra(CollabUtils.EXTRAS_ALUNO_IP_ADDRESS, ipAddress);
                            activity.startService(syncServiceIntent);
                        }
                    });

                    Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> isConnected");

                    handleSuccessfulConnection(context);
                } else {
                    // Professor stopped the Group.
                    // It's a disconnect AND im not in the activity
                    // (impossible for the professor, only the aluno gets here, bc the professor stops the Group in the Wifi P2p Activity)
                    Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> !isConnected");

                    Intent serviceIntent = new Intent(activity, WifiP2pSyncAlunoTesteService.class);
                    activity.stopService(serviceIntent);

                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(activity);
                    materialAlertDialogBuilder.setTitle("Erro");
                    materialAlertDialogBuilder.setMessage(activity.getResources().getString(R.string.wifi_p2p_error_disconnect_lost_conn));
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();

                }
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");

            if (activity instanceof WifiP2PActivity) {
                WifiP2pDevice device = (WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);

                DeviceListFragment fragment = (DeviceListFragment) ((WifiP2PActivity) activity).getSupportFragmentManager()
                        .findFragmentById(R.id.frag_list);
                DeviceDetailFragment detailFragment = (DeviceDetailFragment) ((WifiP2PActivity) activity).getSupportFragmentManager()
                        .findFragmentById(R.id.frag_detail);

                detailFragment.updateThisDevice(device);
                fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                        WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
            } else {
                // Running in the background in any other activity
            }
        }
    }

    /**
     * Called after WIFI_P2P_CONNECTION_CHANGED_ACTION informed us that there's a new connection
     */
    private void handleSuccessfulConnection(Context context) {
        Log.d("debug_bg", "handleSuccessfulConnection");
        // NOTA: este manager pode ter de ser unico para toda a app

        manager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                // this.info = info;

                // After the group negotiation, we create 2 Services, in which the two devices will
                // connect and execute a communication protocol
                if (info.groupFormed && info.isGroupOwner) {
                    // Aqui o Professor pode n estar na atividade do WIFIP2P, portanto aqui tb tenho de executar o registrationProtocol
                    Log.d("debug_bg", "group was formed and im GO");

                    startRegistrationProtocol(context);
                }
            }
        });
    }

    /**
     * This starts the Registration protocol where the GO will register a new Client IP to the list
     * @param context
     */
    private void startRegistrationProtocol(Context context) {
        if (PermissionsUtils.hasAcceptedWifiP2pPermissions(context)) {

            Log.d("debug_bg", "startRegistrationProtocol");

            manager.requestGroupInfo(channel, new WifiP2pManager.GroupInfoListener() {
                @Override
                public void onGroupInfoAvailable(WifiP2pGroup group) {
                    if (group.getClientList().size() > 0) {
                        Log.d(WifiP2PActivity.TAG, "Group size is " + group.getClientList().size() + ", starting Registration Protocol...");

                        Intent serverServiceIntent = new Intent(context, WifiP2pGroupRegistrationServerService.class);
                        serverServiceIntent.setAction(WifiP2pGroupRegistrationServerService.ACTION_REGISTRATION);
                        context.startService(serverServiceIntent);
                    } else if (group.getClientList().size() == 0) {
                        Log.d(WifiP2PActivity.TAG, "Group size: " + group.getClientList().size() + ", starting initSyncQueue...");

                        //initSyncQueue();
                        syncList.initWifiP2pSyncList();
                    }
                }
            });
        }
    }
}
