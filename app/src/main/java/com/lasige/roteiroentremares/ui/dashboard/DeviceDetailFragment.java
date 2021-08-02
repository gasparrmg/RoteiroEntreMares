package com.lasige.roteiroentremares.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.roteiroentremares.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;
import com.lasige.roteiroentremares.util.wifip2p.SyncList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceDetailFragment extends Fragment implements WifiP2pManager.ConnectionInfoListener {

    @Inject
    SyncList syncList;

    private DashboardViewModel dashboardViewModel;

    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pDevice myDevice;
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

        return mContentView;
    }

    public void updateThisDevice(WifiP2pDevice myDevice) {
        this.myDevice = myDevice;
    }

    @Override
    public void onDestroy() {
        Log.d(WifiP2PActivity.TAG, "DetailFragment onDestroy()...");

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

        // After the group negotiation, we create 2 Services, in which the two devices will
        // connect and execute a communication protocol
        if (info.groupFormed && info.isGroupOwner) {
            final DeviceListFragment fragment = (DeviceListFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.frag_list);

            // this method pops up the modal
            fragment.removeDialogIfActive();

            if (getActivity().getApplicationContext() instanceof RoteiroEntreMaresApplication) {
                ((RoteiroEntreMaresApplication) getActivity().getApplicationContext()).setUsingWifiP2pFeature(true);
            }

            ((WifiP2PActivity) getActivity()).startRegistrationProtocol();
        } else if (info.groupFormed) {
            if (getActivity().getApplicationContext() instanceof RoteiroEntreMaresApplication) {
                ((RoteiroEntreMaresApplication) getActivity().getApplicationContext()).setUsingWifiP2pFeature(true);
            }

            //((WifiP2PActivity) getActivity()).statusBar.setVisibility(View.VISIBLE);

            Log.d(WifiP2PActivity.TAG, "Starting Client Service");

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

    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    public void resetViews() {
        //((WifiP2PActivity) getActivity()).resetCounters();
        //((WifiP2PActivity) getActivity()).statusBar.setVisibility(View.GONE);
        ((WifiP2PActivity) getActivity()).textViewFeedback.setVisibility(View.INVISIBLE);

        mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);
        this.getView().setVisibility(View.GONE);
    }

    public class ClientRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

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
                    Log.d(WifiP2PActivity.TAG, "My device was added to the queue.");

                    startAlunoSyncService();

                    return;
                case CollabUtils.ERROR_TIPOUTILIZADOR:
                    Log.e(WifiP2PActivity.TAG, "Not a Professor/Student connection. Disconnecting...");
                    // TODO: disconnect from Group
                    showErrorModalWithMessage(R.string.wifi_p2p_error_disconnect_tipo_user);
                    ((DeviceListFragment.DeviceActionListener) getActivity()).disconnect();
                    return;
                case CollabUtils.ERROR_CODIGOTURMA:
                    Log.e(WifiP2PActivity.TAG, "CodigoTurma not equal. Disconnecting...");
                    // TODO: disconnect from Group
                    showErrorModalWithMessage(R.string.wifi_p2p_error_disconnect_codigo_turma);
                    ((DeviceListFragment.DeviceActionListener) getActivity()).disconnect();
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

    private void showErrorModalWithMessage(int stringResource) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        materialAlertDialogBuilder.setTitle("Erro");
        materialAlertDialogBuilder.setMessage(getResources().getString(stringResource));
        materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dismiss
            }
        });
        materialAlertDialogBuilder.show();
    }

    private void startAlunoSyncService() {
        ((WifiP2PActivity) getActivity()).startAlunoSyncService();
    }
}
