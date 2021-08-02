package com.lasige.roteiroentremares.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.roteiroentremares.R;
import com.google.gson.Gson;
import com.lasige.roteiroentremares.RoteiroEntreMaresApplication;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;
import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.lasige.roteiroentremares.RoteiroEntreMaresApplication.CHANNEL_ID;

@AndroidEntryPoint
public class WifiP2pSyncAlunoTesteService extends Service {

    @Inject
    DataRepository dataRepository;

    public static final String ACTION_SYNC = "com.lasige.roteiroentremares.ACTION_SYNC";

    private NotificationCompat.Builder mNotificationBuilder = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(WifiP2PActivity.TAG, "WifiP2pSyncAlunoTesteService created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(WifiP2PActivity.TAG, "WifiP2pSyncAlunoTesteService starting...");

        Intent notificationIntent = new Intent(this, WifiP2PActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        mNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Turma")
                .setContentText("Estás conectado com o teu professor...")
                .setSmallIcon(R.drawable.ic_roteiro)
                .setContentIntent(pendingIntent);

        startForeground(1, mNotificationBuilder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(WifiP2PActivity.TAG, "Opening ServerSocket...");
                    ServerSocket serverSocket = new ServerSocket(CollabUtils.SYNC_PORT);
                    Log.d(WifiP2PActivity.TAG, "Socket opened.");

                    DataOutputStream outputStream;
                    DataInputStream inputStream;

                    while (true) {
                        Socket client = serverSocket.accept();
                        Log.d(WifiP2PActivity.TAG, "Sync Server (Aluno): Connection done with Group Owner with IP " + client.getInetAddress().toString());

                        updateNotification("A sincronizar...");

                        outputStream = new DataOutputStream(client.getOutputStream());
                        inputStream = new DataInputStream(client.getInputStream());

                        String myIdAluno = dataRepository.getNomeUUID();
                        String myNomeAluno = dataRepository.getNome();

                        outputStream.writeUTF(myIdAluno);
                        Log.d(WifiP2PActivity.TAG, "Sent myIdAluno with value: " + myIdAluno);
                        outputStream.writeUTF(myNomeAluno);
                        Log.d(WifiP2PActivity.TAG, "Sent myNomeAluno with value: " + myNomeAluno);

                        // Receiving number of artefactos to receive

                        int numberToReceive = inputStream.readInt();
                        Log.d(WifiP2PActivity.TAG, "Received number of Artefactos to receive from GO with value: " + numberToReceive);

                        if (numberToReceive == 0) {
                            // Nothing to receive
                            outputStream.writeBoolean(true);
                        } else {
                            // Prepare to receive
                            outputStream.writeBoolean(true);

                            String lastReceivedJson;
                            Gson gson = new Gson();

                            for (int i = 0; i < numberToReceive; i++) {
                                Log.d(WifiP2PActivity.TAG, "Receiving Artefacto number " + (i+1) + "...");
                                // Receive JSON
                                lastReceivedJson = inputStream.readUTF();

                                // Turn JSON into Object
                                ArtefactoTurma receivedArtefactoTurma = gson.fromJson(lastReceivedJson, ArtefactoTurma.class);
                                receivedArtefactoTurma.setReceivedAt(Calendar.getInstance().getTime());

                                if (receivedArtefactoTurma.getType() != 0) {
                                    // if NOT text -> receive file and save to DB

                                    int sizeOfFile = inputStream.readInt();

                                    byte[] messageBytes = new byte[1024];
                                    boolean end = false;
                                    int totalBytesRead = 0;

                                    String path = getExternalFilesDir("/").getAbsolutePath() + "/" + System.currentTimeMillis() + "_ROTEIROENTREMARES";

                                    if (receivedArtefactoTurma.getType() == 1) {
                                        path = path + ".jpg";
                                    } else if (receivedArtefactoTurma.getType() == 2) {
                                        path = path + ".3gp";
                                    } else if (receivedArtefactoTurma.getType() == 3) {
                                        path = path + ".3gp";
                                    }

                                    // create File to receive chunks
                                    File f = new File(path);

                                    File dirs = new File(f.getParent());
                                    if (!dirs.exists())
                                        dirs.mkdirs();
                                    f.createNewFile();

                                    FileOutputStream fileOutputStream = new FileOutputStream(f);

                                    while (!end) {
                                        int currentBytesRead = inputStream.read(messageBytes);
                                        totalBytesRead = totalBytesRead + currentBytesRead;

                                        if (totalBytesRead <= sizeOfFile) {
                                            fileOutputStream.write(messageBytes, 0, currentBytesRead);
                                        } else {
                                            fileOutputStream.write(messageBytes, 0, sizeOfFile - totalBytesRead + currentBytesRead);
                                        }

                                        if (f.length() >= sizeOfFile) {
                                            end = true;
                                        }
                                    }

                                    receivedArtefactoTurma.setContent(path);
                                }

                                dataRepository.insertArtefactoTurma(receivedArtefactoTurma);
                                outputStream.writeBoolean(true);
                            }
                        }

                        String serverIdProfessor = inputStream.readUTF();
                        Log.d(WifiP2PActivity.TAG, "Received IdProfessor from Server with value: " + serverIdProfessor);

                        WifiP2pConnection lastConnectionWithClient = dataRepository.getLastConnectionWithDevice(serverIdProfessor);

                        List<Artefacto> artefactosToSend = new ArrayList<>();

                        if (lastConnectionWithClient == null) {
                            Log.d(WifiP2PActivity.TAG, "Last connection with " + serverIdProfessor + " is null. Never happened.");
                            // Get all Artefactos from DB

                            artefactosToSend = dataRepository.getAllSharedArtefactos();
                        } else {
                            Log.d(WifiP2PActivity.TAG, "Last connection with " + serverIdProfessor + " was at " + lastConnectionWithClient.getLastConnection().toString());
                            // Get Artefactos Turma From To
                            artefactosToSend = dataRepository.getSharedArtefactoFromTo(lastConnectionWithClient.getLastConnection().getTime(), Calendar.getInstance().getTime().getTime());
                        }

                        // Check and send how many toSend to Server

                        if (artefactosToSend == null) {
                            Log.d(WifiP2PActivity.TAG, "toSend is NULL. Sending number of Artefactos to Send to Server with value: 0");
                            outputStream.writeInt(0);
                        } else {
                            Log.d(WifiP2PActivity.TAG, "toSend is NOT NULL. Sending number of Artefactos to Send to Server with value: " + artefactosToSend.size());
                            outputStream.writeInt(artefactosToSend.size());
                        }

                        if (!inputStream.readBoolean()) {
                            Log.d(WifiP2PActivity.TAG, "Error -> disconnect");
                            return;
                        } else {
                            Log.d(WifiP2PActivity.TAG, "Beggining to send Artefactos to Server...");

                            String currentJson;

                            for (int i = 0; i < artefactosToSend.size(); i++) {
                                Log.d(WifiP2PActivity.TAG, "Sending Artefacto number " + (i+1) + " to Server...");

                                currentJson = artefactosToSend.get(i).toJson();

                                outputStream.writeUTF(currentJson);

                                if (artefactosToSend.get(i).getType() != 0) {
                                    // Send file
                                    File file = new File(artefactosToSend.get(i).getContent());
                                    // byte[] fileInBytes = CollabUtils.readFileIntoByteArray(file);

                                    // Send length of file
                                    Log.d(WifiP2PActivity.TAG, "Sending length of file to Server...");
                                    // outputStream.writeInt(fileInBytes.length);
                                    outputStream.writeInt((int) file.length());

                                    // TESTE - Send file
                                    byte[] bytes = new byte[1024];
                                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));

                                    int read = buf.read(bytes, 0, bytes.length);
                                    while (read != -1) {
                                        outputStream.write(bytes, 0, read);
                                        outputStream.flush();
                                        read = buf.read(bytes, 0, bytes.length);
                                    }
                                    // ----------------------

                                    // Send file
                                    Log.d(WifiP2PActivity.TAG, "Sending file to Server...");
                                    // outputStream.write(fileInBytes);
                                }

                                Log.d(WifiP2PActivity.TAG, "Waiting for SUCCESS from Server...");

                                if (inputStream.readBoolean()) {
                                    Log.d(WifiP2PActivity.TAG, "Received SUCCESS from Server...");
                                } else {
                                    Log.d(WifiP2PActivity.TAG, "Received ERROR from Server...");
                                }
                            }
                        }

                        // add idProfessor to LastConnections
                        if (lastConnectionWithClient == null) {
                            WifiP2pConnection wifiP2pConnection = new WifiP2pConnection(
                                    serverIdProfessor,
                                    Calendar.getInstance().getTime()
                            );

                            dataRepository.insertWifiP2pConnection(wifiP2pConnection);
                        } else {
                            // update
                            lastConnectionWithClient.setLastConnection(Calendar.getInstance().getTime());
                            dataRepository.updateWifiP2pConnection(lastConnectionWithClient);
                        }

                        if (artefactosToSend.size() > 0 || numberToReceive > 0) {
                            if (numberToReceive == 1) {
                                updateNotification("Estás conectado com o teu professor... Recebeste " + numberToReceive + " novo Artefacto e enviaste " + artefactosToSend.size() + ".");
                            } else {
                                updateNotification("Estás conectado com o teu professor... Recebeste " + numberToReceive + " novos Artefactos e enviaste " + artefactosToSend.size() + ".");
                            }
                        } else {
                            updateNotification("Estás conectado com o teu professor...");
                        }

                        inputStream.close();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(WifiP2PActivity.TAG, "WifiP2pSyncAlunoTesteService destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification(String text) {
        Intent notificationIntent = new Intent(this, WifiP2PActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        mNotificationBuilder
                .setContentText(text)
                .setContentIntent(pendingIntent);

        ((RoteiroEntreMaresApplication) this.getApplication()).getNotificationManager().notify(1, mNotificationBuilder.build());
    }
}
