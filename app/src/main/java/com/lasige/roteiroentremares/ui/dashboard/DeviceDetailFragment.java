package com.lasige.roteiroentremares.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.services.WifiP2pClientService;
import com.lasige.roteiroentremares.services.WifiP2pServerService;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceDetailFragment extends Fragment implements WifiP2pManager.ConnectionInfoListener {

    private DashboardViewModel dashboardViewModel;

    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pInfo info;
    ProgressDialog progressDialog = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.wifip2p_device_detail, null);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                Log.d(WifiP2PActivity.TAG, "Tipo de user -> " + dashboardViewModel.getTipoUtilizador());

                // groupOwnerIntent
                if (dashboardViewModel.getTipoUtilizador() == 0) {
                    Log.d(WifiP2PActivity.TAG, "Aluno -> Intent to 0");
                    config.groupOwnerIntent = 0;
                } else if (dashboardViewModel.getTipoUtilizador() == 1) {
                    Log.d(WifiP2PActivity.TAG, "Professor -> Intent to 15");
                    config.groupOwnerIntent = 15;
                }
                config.wps.setup = WpsInfo.PBC;

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                progressDialog = ProgressDialog.show(getActivity(), "A conectar com " + device.deviceName,
                        "Prime fora desta janela para cancelar", true, true
//                        new DialogInterface.OnCancelListener() {
//
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                ((DeviceActionListener) getActivity()).cancelDisconnect();
//                            }
//                        }
                );

                ((DeviceListFragment.DeviceActionListener) getActivity()).connect(config);

            }
        });

        mContentView.findViewById(R.id.btn_disconnect).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((DeviceListFragment.DeviceActionListener) getActivity()).disconnect();
                    }
                });

        /*mContentView.findViewById(R.id.btn_start_client).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Allow user to pick an image from Gallery or other
                        // registered apps
                        TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
                        statusText.setText("Starting Client Service");
                        Log.d(WifiP2PActivity.TAG, "Starting Client Service");
                        Intent serviceIntent = new Intent(getActivity(), WifiP2pClientService.class);
                        serviceIntent.setAction(WifiP2pClientService.ACTION_COMMS);
                        serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_ADDRESS,
                                info.groupOwnerAddress.getHostAddress());
                        serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_PORT, 8988);
                        getActivity().startService(serviceIntent);
                    }
                });*/

        return mContentView;
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        Log.d(WifiP2PActivity.TAG, "Starting Services");

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        this.info = info;
        this.getView().setVisibility(View.VISIBLE);

        // The owner IP is now known.
        TextView view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(getResources().getString(R.string.group_owner_text)
                + ((info.isGroupOwner == true) ? " Sim"
                : " Não"));

        // InetAddress from WifiP2pInfo struct.
        /*view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());*/


        // After the group negotiation, we create 2 Services, in which the two devices will
        // connect and execute a communication protocol
        if (info.groupFormed && info.isGroupOwner) {
            ((WifiP2PActivity) getActivity()).checkGroupInfo();

            // STARTING SERVER SERVICE
            TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
            statusText.setText("Comunicação com o aluno iniciada...");
            Log.d(WifiP2PActivity.TAG, "Starting Server Service");
            Intent serverServiceIntent = new Intent(getActivity(), WifiP2pServerService.class);
            serverServiceIntent.setAction(WifiP2pServerService.ACTION_COMMS);

            // QUEUE -> quando um device é synced, vai para o fim da fila e depois vou correndo isto
            // IR BUSCAR IP DO CLIENT E PASSAR PARA SERVICE

            // OUTRA ABORDAGEM -> aqui só adicionar à queue e ter serviço à parte que corre de X em X tempo para ver se a queue tem algum device novo


            // serverServiceIntent.putExtra(WifiP2pServerService.DEVICE_MAC_ADDRESS, device.deviceAddress);
            getActivity().startService(serverServiceIntent);
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            // mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));

            TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
            statusText.setText("A iniciar a comunicação com o dispositivo...");
            Log.d(WifiP2PActivity.TAG, "Starting Client Service");
            Intent serviceIntent = new Intent(getActivity(), WifiP2pClientService.class);
            serviceIntent.setAction(WifiP2pClientService.ACTION_COMMS);
            serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_ADDRESS,
                    info.groupOwnerAddress.getHostAddress());
            serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_PORT, 8988);
            getActivity().startService(serviceIntent);
        }

        // hide the connect button
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);
    }

    /**
     * Updates the UI with device data
     *
     * @param device the device to be displayed
     */
    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        this.getView().setVisibility(View.VISIBLE);
        /*TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(device.deviceAddress);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(device.toString());*/

    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    public void resetViews() {
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.status_text);
        view.setText(R.string.empty);
        // mContentView.findViewById(R.id.btn_start_client).setVisibility(View.GONE);
        this.getView().setVisibility(View.GONE);
    }
}
