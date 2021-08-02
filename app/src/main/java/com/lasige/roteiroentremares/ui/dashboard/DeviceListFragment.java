package com.lasige.roteiroentremares.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceListFragment extends ListFragment implements WifiP2pManager.PeerListListener {

    private DashboardViewModel mDashboardViewModel;

    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    ProgressDialog progressDialog = null;
    View mContentView = null;
    private WifiP2pDevice device;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        if (mDashboardViewModel.getTipoUtilizador() == 0) {
            mContentView = inflater.inflate(R.layout.wifip2p_device_list, null);
        } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
            mContentView = inflater.inflate(R.layout.wifip2p_device_list_professor, null);
        } else {
            mContentView = inflater.inflate(R.layout.wifip2p_device_list, null);
        }
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.wifip2p_row_devices, peers));

    }

    public void submitGroupDeviceList(WifiP2pGroup group) {
        peers.clear();
        peers.addAll(group.getClientList());

        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peersList) {
        Log.d(WifiP2PActivity.TAG, "onPeersAvailable, peerList size: " + peersList.getDeviceList().size());

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        peers.clear();
        peers.addAll(peersList.getDeviceList());

        Iterator<WifiP2pDevice> iterator = peers.iterator();

        if (mDashboardViewModel.getTipoUtilizador() == 0) {
            // this happens for Alunos
            while (iterator.hasNext()) {
                if (!iterator.next().isGroupOwner()) {
                    iterator.remove();
                }
            }
        } else if (mDashboardViewModel.getTipoUtilizador() == 1) {
            while (iterator.hasNext()) {
                if (iterator.next().status != WifiP2pDevice.CONNECTED) {
                    iterator.remove();
                }
            }
        }

        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
        if (peers.size() == 0) {
            Log.d(WifiP2PActivity.TAG, "No devices found");
            return;
        }
    }

    public void clearPeers() {
        peers.clear();
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    public void onInitiateDiscovery() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.show(getActivity(), "À procura de dispositivos...", "Prime fora desta janela para cancelar", true,
                true, new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
    }

    public void onInitiateCreateGroup() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.show(getActivity(), "A criar grupo...", "Prime fora desta janela para cancelar", true,
                true, new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
    }

    public void removeDialogIfActive() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Initiate a connection with the peer.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(position);
        ((DeviceActionListener) getActivity()).showDetails(device);
    }

    /**
     * @return this device
     */
    public WifiP2pDevice getDevice() {
        return device;
    }

    private static String getDeviceStatus(int deviceStatus) {
        Log.d(WifiP2PActivity.TAG, "Peer status :" + deviceStatus);
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Disponível";
            case WifiP2pDevice.INVITED:
                return "Convidado";
            case WifiP2pDevice.CONNECTED:
                return "Conectado";
            case WifiP2pDevice.FAILED:
                return "Falhou";
            case WifiP2pDevice.UNAVAILABLE:
                return "Indisponível";
            default:
                return "Desconhecido";

        }
    }

    /**
     * Update UI for this device.
     *
     * @param device WifiP2pDevice object
     */
    public void updateThisDevice(WifiP2pDevice device) {
        this.device = device;
        TextView view = (TextView) mContentView.findViewById(R.id.my_name);
        view.setText(device.deviceName);
        view = (TextView) mContentView.findViewById(R.id.my_status);
        view.setText(getDeviceStatus(device.status));

        Log.d(WifiP2PActivity.TAG, "Device details -> " + device.toString());

        if (device.isGroupOwner()) {
            view.append(" - Dono de Grupo");
        }
    }

    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

        private List<WifiP2pDevice> items;

        /**
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public WiFiPeerListAdapter(Context context, int textViewResourceId,
                                   List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            items = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.wifip2p_row_devices, null);
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
                TextView top = (TextView) v.findViewById(R.id.device_name);
                TextView bottom = (TextView) v.findViewById(R.id.device_details);
                if (top != null) {
                    top.setText(device.deviceName);
                }
                if (bottom != null) {
                    bottom.setText(getDeviceStatus(device.status));
                    if (device.isGroupOwner()) {
                        bottom.append(" - Dono de Grupo");
                    }
                }
            }

            return v;

        }
    }

    /**
     * An interface-callback for the activity to listen to fragment interaction
     * events.
     */
    public interface DeviceActionListener {

        void showDetails(WifiP2pDevice device);

        void cancelDisconnect();

        void connect(WifiP2pConfig config);

        void disconnect();
    }
}
