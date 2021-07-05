package com.lasige.roteiroentremares.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.dashboard.DeviceDetailFragment;
import com.lasige.roteiroentremares.ui.dashboard.DeviceListFragment;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

public class WifiP2pBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiP2PActivity activity;

    public WifiP2pBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                    WifiP2PActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // UI update to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                activity.setIsWifiP2pEnabled(true);
            } else {
                activity.setIsWifiP2pEnabled(false);
                activity.resetData();
            }
            Log.d(WifiP2PActivity.TAG, "P2P state changed - " + state);
        } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_DISCOVERY_CHANGED_ACTION");

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, 10000);
            if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                // Wifi P2P discovery started.
                Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery started");
            } else {
                // Wifi P2P discovery stopped.
                // Do what you want to do when discovery stopped
                Log.d(WifiP2PActivity.TAG, "Wifi P2P discovery stopped");
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_PEERS_CHANGED_ACTION");

            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (manager != null) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.d(WifiP2PActivity.TAG, "Peers changed and manager not null -> requestPeers");
                    manager.requestPeers(channel, (WifiP2pManager.PeerListListener) activity.getSupportFragmentManager().findFragmentById(R.id.frag_list));
                }
            }
            Log.d(WifiP2PActivity.TAG, "P2P peers changed");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");

            if (manager == null) {
                Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> manager is null");
                return;
            }

            WifiP2pInfo info = (WifiP2pInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);

            // Log.d(WifiP2PActivity.TAG, "wifip2pinfo " + info.toString());

            WifiP2pGroup group = (WifiP2pGroup) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);

            // Log.d(WifiP2PActivity.TAG, "group " + group.toString());

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            // Log.d(WifiP2PActivity.TAG, "networkinfo " + networkInfo.toString());

            if (networkInfo.isConnected()) {
                Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> isConnected");

                // we are connected with the other device, request connection
                // info to find group owner IP

                DeviceDetailFragment fragment = (DeviceDetailFragment) activity
                        .getSupportFragmentManager().findFragmentById(R.id.frag_detail);
                manager.requestConnectionInfo(channel, fragment);
            } else {
                Log.d(WifiP2PActivity.TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION -> !isConnected");
                // It's a disconnect
                activity.resetData();
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.d(WifiP2PActivity.TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");

            WifiP2pDevice device = (WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);

            DeviceListFragment fragment = (DeviceListFragment) activity.getSupportFragmentManager()
                    .findFragmentById(R.id.frag_list);
            DeviceDetailFragment detailFragment = (DeviceDetailFragment) activity.getSupportFragmentManager()
                    .findFragmentById(R.id.frag_detail);

            detailFragment.updateThisDevice(device);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
    }
}
