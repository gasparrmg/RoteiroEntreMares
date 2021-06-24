package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WifiP2pClientService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_COMMS = "com.android.wifip2pprototype.COMMS";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WifiP2pClientService(String name) {
        super(name);
    }

    public WifiP2pClientService() {
        super("WifiP2pClientService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_COMMS)) {
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                Log.d(WifiP2PActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(WifiP2PActivity.TAG, "Client socket - " + socket.isConnected());

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                // SEND GREETING TO SERVER
                outputStream.writeUTF("READY");
                Log.d(WifiP2PActivity.TAG, "Sent READY message to server");

                // READS TYPE AND SIZE OF DATA THAT IS GOING TO RECEIVE FROM SERVER
                String typeOfData = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received TYPE_OF_DATA message from server with value -> " + typeOfData);
                int sizeOfData = inputStream.readInt();
                Log.d(WifiP2PActivity.TAG, "Received SIZE_OF_DATA message from server with value -> " + sizeOfData);


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

                Log.d(WifiP2PActivity.TAG, "Received DATA from server with value -> " + dataString.toString());

                outputStream.writeUTF("TYPE_OF_DATA");
                Log.d(WifiP2PActivity.TAG, "Sent TYPE_OF_DATA to server");

                String dataToSend = "This is the data sent by the Client in the second step of the protocol";
                byte[] dataInBytes = dataToSend.getBytes(StandardCharsets.UTF_8);

                outputStream.writeInt(dataInBytes.length);
                Log.d(WifiP2PActivity.TAG, "Sent SIZE_OF_DATA to server");

                // SEND DATA
                outputStream.write(dataInBytes);
                Log.d(WifiP2PActivity.TAG, "Sent DATA to server");
                /*byte[] buffer = new byte[8192]; // or 4096, or more
                int len;

                // NOTE: Here the InputStream must read from a file
                while ((len = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }*/

                Log.d(WifiP2PActivity.TAG, "Closing streams");
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
            }

        }
    }
}
