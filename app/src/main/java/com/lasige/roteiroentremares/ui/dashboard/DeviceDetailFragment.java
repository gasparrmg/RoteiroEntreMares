package com.lasige.roteiroentremares.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
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
import com.lasige.roteiroentremares.services.WifiP2pGroupRegistrationServerService;
import com.lasige.roteiroentremares.services.WifiP2pSyncAlunoService;
import com.lasige.roteiroentremares.services.WifiP2pSyncProfessorService;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;
import com.lasige.roteiroentremares.util.wifip2p.WifiP2pSyncQueue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceDetailFragment extends Fragment implements WifiP2pManager.ConnectionInfoListener {

    private DashboardViewModel dashboardViewModel;

    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pDevice myDevice;
    private WifiP2pInfo info;
    ProgressDialog progressDialog = null;

    private WifiP2pSyncQueue<String> wifiP2pSyncQueue;
    private List<String> wifiP2pSyncList;
    public Timer timer;

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

    public void updateThisDevice(WifiP2pDevice myDevice) {
        this.myDevice = myDevice;
    }

    public void addIpToQueue(String ipAddress) {
        wifiP2pSyncList.add(ipAddress);

        Log.d(WifiP2PActivity.TAG, ipAddress + " was added to the queue...");

        if (wifiP2pSyncList.size() == 1) {
            // size was 0 and is now 1 -> start timer
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // This will run every 10sec

                    Log.d(WifiP2PActivity.TAG, "Starting fixed rate timer...");

                    String currentAddress;

                    for (int i = 0; i < wifiP2pSyncList.size(); i++) {
                            /*Intent syncServiceIntent = new Intent(getActivity(), WifiP2pGroupRegistrationServerService.class);
                            syncServiceIntent.setAction(WifiP2pGroupRegistrationServerService.ACTION_REGISTRATION);
                            getActivity().startService(syncServiceIntent);*/

                        currentAddress = wifiP2pSyncList.get(i);

                        Log.d(WifiP2PActivity.TAG, "Starting sync service with client: " + currentAddress + "...");
                        Intent syncServiceIntent = new Intent(getActivity(), WifiP2pSyncProfessorService.class);
                        syncServiceIntent.setAction(WifiP2pSyncProfessorService.ACTION_SYNC);
                        syncServiceIntent.putExtra(CollabUtils.EXTRAS_ALUNO_IP_ADDRESS, currentAddress);
                        getActivity().startService(syncServiceIntent);

                        // This is causing an the listener to run indefinitely bc it's maybe calling offer from size 0 to 1
                        //wifiP2pSyncQueue.add(currentAddress);
                    }
                }
            }, 0, 10000);
        }
    }

    public void initSyncQueue() {
        Log.d(WifiP2PActivity.TAG, "InitSyncQueue...");

        wifiP2pSyncList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }

        super.onDestroy();
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
            // ((WifiP2PActivity) getActivity()).checkGroupInfo();

            // STARTING SERVER SERVICE
            TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
            statusText.setText("Comunicação com o aluno iniciada...");
            // Log.d(WifiP2PActivity.TAG, "Starting Server Service");

            // Intent serverServiceIntent = new Intent(getActivity(), WifiP2pServerService.class);
            // serverServiceIntent.setAction(WifiP2pServerService.ACTION_COMMS);

            // TESTEEEEEEEEE ------------
            // Intent serverServiceIntent = new Intent(getActivity(), WifiP2pGroupRegistrationServerService.class);
            // serverServiceIntent.setAction(WifiP2pGroupRegistrationServerService.ACTION_REGISTRATION);
            // ------------------

            ((WifiP2PActivity) getActivity()).startRegistrationProtocol();

            // QUEUE -> quando um device é synced, vai para o fim da fila e depois vou correndo isto
            // IR BUSCAR IP DO CLIENT E PASSAR PARA SERVICE

            // OUTRA ABORDAGEM -> aqui só adicionar à queue e ter serviço à parte que corre de X em X tempo para ver se a queue tem algum device novo


            // serverServiceIntent.putExtra(WifiP2pServerService.DEVICE_MAC_ADDRESS, device.deviceAddress);

            // getActivity().startService(serverServiceIntent);

            /*if (wifiP2pSyncQueue != null) {
                wifiP2pSyncQueue.offer("teste_ip");
            }*/
        } else if (info.groupFormed) {
            String myMacAddress = myDevice.deviceAddress != null ? myDevice.deviceAddress : null;


            // The other device acts as the client. In this case, we enable the
            // get file button.
            // mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));

            TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
            statusText.setText("A iniciar a comunicação com o dispositivo...");
            Log.d(WifiP2PActivity.TAG, "Starting Client Service");

            /*Intent serviceIntent = new Intent(getActivity(), WifiP2pClientService.class);
            serviceIntent.setAction(WifiP2pClientService.ACTION_COMMS);
            serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_ADDRESS,
                    info.groupOwnerAddress.getHostAddress());
            serviceIntent.putExtra(WifiP2pClientService.EXTRAS_GROUP_OWNER_PORT, 8988);
            serviceIntent.putExtra(WifiP2pClientService.EXTRAS_MAC_ADDRESS, myMacAddress);
            getActivity().startService(serviceIntent);*/

            new ClientRegistrationAsyncTask(
                    getActivity(),
                    info.groupOwnerAddress.getHostAddress(),
                    dashboardViewModel.getCodigoTurma(),
                    dashboardViewModel.getTipoUtilizador()
            ).execute();
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

    public static class ClientRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private String hostAddress;
        private String mCodigoTurma;
        private int mTipoUtilizador;

        public ClientRegistrationAsyncTask(Context context, String hostAddress, String mCodigoTurma, int mTipoUtilizador) {
            this.context = context;
            this.hostAddress = hostAddress;
            this.mCodigoTurma = mCodigoTurma;
            this.mTipoUtilizador = mTipoUtilizador;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Socket socket = new Socket();

            try {
                socket.connect((new InetSocketAddress(hostAddress, CollabUtils.REGISTRATION_PORT)), CollabUtils.SOCKET_TIMEOUT);
                Log.d(WifiP2PActivity.TAG, "Client socket - " + socket.isConnected());

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                boolean serverResponse = false;

                outputStream.writeInt(mTipoUtilizador);
                Log.d(WifiP2PActivity.TAG, "Sent TipoUtilizador to Server with value: " + mTipoUtilizador);

                serverResponse = inputStream.readBoolean();
                Log.d(WifiP2PActivity.TAG, "Received answer from Server with value: " + serverResponse);

                if (!serverResponse) {
                    Log.e(WifiP2PActivity.TAG, "Not a Professor/Student connection. Disconnecting...");
                    if (socket != null) {
                        if (socket.isConnected()) {
                            try {
                                Log.d(WifiP2PActivity.TAG, "Closing socket");
                                socket.close();
                            } catch (IOException e) {
                                // Give up
                                e.printStackTrace();
                            }
                        }
                    }
                    return CollabUtils.ERROR_TIPOUTILIZADOR;
                }

                outputStream.writeUTF(mCodigoTurma);
                Log.d(WifiP2PActivity.TAG, "Sent CodigoTurma to Server with value: " + mCodigoTurma);

                serverResponse = inputStream.readBoolean();
                Log.d(WifiP2PActivity.TAG, "Received answer from Server with value: " + serverResponse);

                if (!serverResponse) {
                    Log.e(WifiP2PActivity.TAG, "CodigoTurma not equal. Disconnecting...");
                    if (socket != null) {
                        if (socket.isConnected()) {
                            try {
                                Log.d(WifiP2PActivity.TAG, "Closing socket");
                                socket.close();
                            } catch (IOException e) {
                                // Give up
                                e.printStackTrace();
                            }
                        }
                    }
                    return CollabUtils.ERROR_CODIGOTURMA;
                }

                String finalMessage = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received final answer from Server with value: " + finalMessage);

                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            Log.d(WifiP2PActivity.TAG, "Closing socket");
                            socket.close();

                            if (!finalMessage.equals(CollabUtils.SUCCESS)) {
                                return CollabUtils.ERROR;
                            }
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }

                return finalMessage;

            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            switch (s) {
                case CollabUtils.SUCCESS:
                    Log.d(WifiP2PActivity.TAG, "My device was added to the queue. Waiting for synchronization...");

                    // TODO: start Service that will wait for sync
                    Intent syncServiceIntent = new Intent(context, WifiP2pSyncAlunoService.class);
                    syncServiceIntent.setAction(WifiP2pSyncAlunoService.ACTION_SYNC);
                    context.startService(syncServiceIntent);

                    return;
                case CollabUtils.ERROR_TIPOUTILIZADOR:
                    Log.e(WifiP2PActivity.TAG, "Not a Professor/Student connection. Disconnecting...");
                    // TODO: disconnect from Group
                    return;
                case CollabUtils.ERROR_CODIGOTURMA:
                    Log.e(WifiP2PActivity.TAG, "CodigoTurma not equal. Disconnecting...");
                    // TODO: disconnect from Group
                    return;
                case CollabUtils.ERROR_MACADDRESS_NULL:
                    Log.e(WifiP2PActivity.TAG, "MAC Address is NULL. Disconnecting...");
                    return;
                case CollabUtils.ERROR:
                    Log.e(WifiP2PActivity.TAG, "There was an error. Disconnecting...");
                    return;
                default:
                    Log.e(WifiP2PActivity.TAG, "There was an unknown error. Disconnecting...");
                    return;
            }
        }
    }
}
