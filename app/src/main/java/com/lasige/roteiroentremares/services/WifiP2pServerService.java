package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WifiP2pServerService extends IntentService {

    @Inject
    DataRepository dataRepository;

    public static final String NOTIFICATION_ID_STRING = "WifiP2pServerService_Notification";
    public static final int NOTIFICATION_ID = 1;
    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_COMMS = "com.android.wifip2pprototype.SERVER_COMMS";
    public static final String DEVICE_MAC_ADDRESS = "com.android.wifip2pprototype.DEVICE_MAC_ADDRESS";

    private String deviceMacAddress;
    private NotificationManager mNotificationManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WifiP2pServerService(String name) {
        super(name);
    }

    public WifiP2pServerService() {
        super("WifiP2pServerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction().equals(ACTION_COMMS)) {
            try {
                ServerSocket serverSocket = new ServerSocket(8988);
                Log.d(WifiP2PActivity.TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                Log.d(WifiP2PActivity.TAG, "Server: connection done");

                DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
                DataInputStream inputStream = new DataInputStream(client.getInputStream());

                /*deviceMacAddress = intent.getStringExtra(DEVICE_MAC_ADDRESS);

                Log.d(WifiP2PActivity.TAG, "Connecting with Client with MAC Address " + deviceMacAddress);*/

                // WAIT FOR GREETING FROM CLIENT
                String greeting = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received READY from Client with value: " + greeting);

                outputStream.writeUTF("TYPE_OF_DATA");
                Log.d(WifiP2PActivity.TAG, "Sent TYPE_OF_DATA to Client");

                /*dataRepository.insertArtefacto(new Artefacto(
                        "this is title",
                        "this is content from service",
                        0,
                        "",
                        Calendar.getInstance().getTime(),
                        "",
                        "",
                        "codigoTurma",
                        false
                ));*/

                /*List<Artefacto> artefactos = dataRepository.getAllArtefactosAlt();

                String dataToSend = artefactos.get(0).getIdString();*/
                String dataToSend = "This is the data sent by the Server in the first step of the protocol";
                // String dataToSend = dataRepository.getNome();
                byte[] dataInBytes = dataToSend.getBytes(StandardCharsets.UTF_8);

                outputStream.writeInt(dataInBytes.length);
                Log.d(WifiP2PActivity.TAG, "Sent SIZE_OF_DATA to Client");

                // SEND DATA
                outputStream.write(dataInBytes);
                Log.d(WifiP2PActivity.TAG, "Sent DATA to Client");

                // RECEIVE DATA
                String typeOfData = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received TYPE_OF_DATA message from Client with value -> " + typeOfData);
                int sizeOfData = inputStream.readInt();
                Log.d(WifiP2PActivity.TAG, "Received SIZE_OF_DATA message from Client with value -> " + sizeOfData);


                byte[] messageByte = new byte[sizeOfData];
                boolean end = false;
                int totalBytesRead = 0;

                // TEST
                StringBuilder dataString = new StringBuilder(sizeOfData);

                // RECEIVE DATA (TODO: change this to write to a file)
                while (!end) {
                    int currentBytesRead = inputStream.read(messageByte);
                    totalBytesRead = totalBytesRead + currentBytesRead;

                    if (totalBytesRead <= sizeOfData) {
                        dataString.append(new String(messageByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                    } else {
                        dataString.append(new String(messageByte, 0, sizeOfData - totalBytesRead + currentBytesRead, StandardCharsets.UTF_8));
                    }

                    if (dataString.length() >= sizeOfData) {
                        end = true;
                    }
                }

                Log.d(WifiP2PActivity.TAG, "Received DATA from Client with value -> " + dataString.toString());

                Log.d(WifiP2PActivity.TAG, "Closing streams");
                inputStream.close();
                outputStream.close();

                Log.d(WifiP2PActivity.TAG, "Closing server socket");
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, WifiP2PActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(msg)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.ic_artefactos);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
