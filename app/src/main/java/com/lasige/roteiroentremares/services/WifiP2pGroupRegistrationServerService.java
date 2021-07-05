package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * This Service will be used to send the IP Address of the Client to the GO, to be added to the Queue.
 */

@AndroidEntryPoint
public class WifiP2pGroupRegistrationServerService extends IntentService {

    @Inject
    DataRepository dataRepository;

    private static final int SOCKET_TIMEOUT = 5000;

    public static final String ACTION_REGISTRATION = "com.lasige.roteiroentremares.ACTION_REGISTRATION";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "com.lasige.roteiroentremares.ACTION";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WifiP2pGroupRegistrationServerService(String name) {
        super(name);
    }

    public WifiP2pGroupRegistrationServerService() {
        super("WifiP2pGroupRegistrationServerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if (action.equals(ACTION_REGISTRATION)) {
            // Open ServerSocket
            try {
                ServerSocket serverSocket = new ServerSocket(CollabUtils.REGISTRATION_PORT);
                Log.d(WifiP2PActivity.TAG, "Server: Socket opened. Waiting for connections...");

                Socket client = serverSocket.accept();
                String clientIpAddress = client.getInetAddress().getHostAddress();
                Log.d(WifiP2PActivity.TAG, "Server: connection established with Client " + clientIpAddress);

                DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
                DataInputStream inputStream = new DataInputStream(client.getInputStream());

                // Tipo Utilizador

                int myTipoUtilizador = dataRepository.getTipoUtilizador();
                int clientTipoUtilizador = inputStream.readInt();
                Log.d(WifiP2PActivity.TAG, "Received TipoUtilizador from Client with value: " + clientTipoUtilizador);

                if (!(clientTipoUtilizador == 0 && myTipoUtilizador == 1)) {
                    Log.d(WifiP2PActivity.TAG, "NOT Professor + Aluno connection -> send ERROR to Client and disconnect");
                    outputStream.writeBoolean(false);

                    Log.d(WifiP2PActivity.TAG, "Closing streams...");
                    inputStream.close();
                    outputStream.close();

                    Log.d(WifiP2PActivity.TAG, "Closing server socket...");
                    serverSocket.close();
                    return;
                } else {
                    Log.d(WifiP2PActivity.TAG, "Professor + Aluno connection -> SUCCESS");
                    outputStream.writeBoolean(true);
                }

                // Codigo Turma

                String myCodigoTurma = dataRepository.getCodigoTurma();
                String clientCodigoTurma = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received CodigoTurma from Client with value: " + clientCodigoTurma);

                if (!clientCodigoTurma.equals(myCodigoTurma)) {
                    Log.d(WifiP2PActivity.TAG, "CodigoTurma are NOT equal -> send ERROR to Client and disconnect");
                    outputStream.writeBoolean(false);

                    Log.d(WifiP2PActivity.TAG, "Closing streams...");
                    inputStream.close();
                    outputStream.close();

                    Log.d(WifiP2PActivity.TAG, "Closing server socket...");
                    serverSocket.close();
                    return;
                } else {
                    Log.d(WifiP2PActivity.TAG, "CodigoTurma are equal -> SUCCESS");
                    outputStream.writeBoolean(true);
                }

                // Add Client to the Queue and send them SUCCESS or ERROR

                ResultReceiver receiver = intent.getParcelableExtra("receiver");
                Bundle data = new Bundle();
                data.putString("wifi_p2p_ip_address", clientIpAddress);
                receiver.send(100, data);

                Log.d(WifiP2PActivity.TAG, "Sent ipAddress to Receiver...");

                outputStream.writeUTF(CollabUtils.SUCCESS);

                // End connection

                Log.d(WifiP2PActivity.TAG, "Closing streams...");
                inputStream.close();
                outputStream.close();

                Log.d(WifiP2PActivity.TAG, "Closing server socket...");
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Send message
            // Wait for confirmation
        }
    }
}
