package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.roteiroentremares.R;
import com.google.gson.Gson;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;
import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.ArtefactoConverter;
import com.lasige.roteiroentremares.util.CollabUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WifiP2pServerService extends IntentService {

    @Inject
    DataRepository dataRepository;

    public static final String TESTE_ID_PROFESSOR = "teste_id_professor";

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

                int responseCode = 0;

                /*deviceMacAddress = intent.getStringExtra(DEVICE_MAC_ADDRESS);

                Log.d(WifiP2PActivity.TAG, "Connecting with Client with MAC Address " + deviceMacAddress);*/

                // RECEIVE TIPO UTILIZADOR FROM CLIENT

                int myTipoUtilizador = dataRepository.getTipoUtilizador();

                int clientTipoUtilizador = inputStream.readInt();
                Log.d(WifiP2PActivity.TAG, "Received TipoUtilizador from Client with value: " + clientTipoUtilizador);

                if (!(clientTipoUtilizador == 0 && myTipoUtilizador == 1)) {
                    Log.d(WifiP2PActivity.TAG, "NOT Professor + Aluno connection -> send ERROR to Client and disconnect");
                    outputStream.writeInt(WifiP2PActivity.ERROR_MESSAGE);
                    return;
                } else {
                    Log.d(WifiP2PActivity.TAG, "Professor + Aluno connection -> SUCCESS");
                    outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                }

                // RECEIVE CODIGO TURMA FROM CLIENT
                String myCodigoTurma = dataRepository.getCodigoTurma();

                Log.d(WifiP2PActivity.TAG, "Waiting for CodigoTurma from Client");
                String clientCodigoTurma = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received CodigoTurma from Client with value: " + clientCodigoTurma);

                if (clientCodigoTurma.equals(myCodigoTurma)) {
                    Log.d(WifiP2PActivity.TAG, "CodigoTurma are equal -> SUCCESS");
                    outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                } else {
                    Log.d(WifiP2PActivity.TAG, "CodigoTurma are NOT equal -> send ERROR to Client and disconnect");
                    outputStream.writeInt(WifiP2PActivity.ERROR_MESSAGE);
                    return;
                }

                // RECEIVING ID ALUNO

                String clientIdAluno = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received IdAluno from Client with value: " + clientIdAluno);

                // check table past connections
                WifiP2pConnection lastConnectionWithClient = dataRepository.getLastConnectionWithDevice(clientIdAluno);

                List<ArtefactoTurma> artefactosTurmaToSend = new ArrayList<>();

                if (lastConnectionWithClient == null) {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + clientIdAluno + " is null. Never happened.");
                    // Get all Artefactos Turma from DB
                    artefactosTurmaToSend = dataRepository.getAllArtefactosTurmaAlt();
                } else {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + clientIdAluno + " was at " + lastConnectionWithClient.getLastConnection().toString());
                    // Get Artefactos Turma From To
                    artefactosTurmaToSend = dataRepository.getArtefactoTurmaFromTo(lastConnectionWithClient.getLastConnection().getTime(), Calendar.getInstance().getTime().getTime());
                }

                // Check and send how many toSend to Client

                if (artefactosTurmaToSend == null) {
                    Log.d(WifiP2PActivity.TAG, "toSend is NULL. Sending number of Artefactos Turma to Send to Client with value: 0");
                    outputStream.writeInt(0);
                } else {
                    Log.d(WifiP2PActivity.TAG, "toSend is NOT NULL. Sending number of Artefactos Turma to Send to Client with value: " + artefactosTurmaToSend.size());
                    outputStream.writeInt(artefactosTurmaToSend.size());
                }

                // Wait for READY from Client
                responseCode = inputStream.readInt();

                if (responseCode == WifiP2PActivity.ERROR_MESSAGE) {
                    // Disconnect
                    Log.d(WifiP2PActivity.TAG, "Error -> disconnect");
                    return;
                } else if (responseCode == WifiP2PActivity.SUCCESS_MESSAGE) {
                    Log.d(WifiP2PActivity.TAG, "Beggining to send Artefactos Turma to Client...");

                    String currentJson;

                    for (int i = 0; i < artefactosTurmaToSend.size(); i++) {
                        Log.d(WifiP2PActivity.TAG, "Sending Artefacto number " + (i+1) + " to Client...");

                        currentJson = artefactosTurmaToSend.get(i).toJson();

                        outputStream.writeUTF(currentJson);

                        if (artefactosTurmaToSend.get(i).getType() != 0) {
                            // Send file

                            File file = new File(artefactosTurmaToSend.get(i).getContent());
                            byte[] fileInBytes = CollabUtils.readFileIntoByteArray(file);

                            // Send length of file
                            outputStream.writeInt(fileInBytes.length);

                            // Send file
                            outputStream.write(fileInBytes);
                        }

                        Log.d(WifiP2PActivity.TAG, "Waiting for SUCCESS from Client...");
                        responseCode = inputStream.readInt();

                        if (responseCode == WifiP2PActivity.SUCCESS_MESSAGE) {
                            Log.d(WifiP2PActivity.TAG, "Received SUCCESS from Client...");
                        } else {
                            Log.d(WifiP2PActivity.TAG, "Received ERROR from Client...");
                        }

                    }
                } else {
                    // unknown error -> disconnect
                }

                // Send IdProfessor to Client
                String myIdProfessor = TESTE_ID_PROFESSOR;

                outputStream.writeUTF(myIdProfessor);
                Log.d(WifiP2PActivity.TAG, "Sent IdProfessor with value: " + myIdProfessor);

                // Receiving number of artefactos to receive
                int numberToReceive = inputStream.readInt();

                Log.d(WifiP2PActivity.TAG, "Received number of Artefactos to receive with value: " + numberToReceive);

                if (numberToReceive == 0) {
                    // Nothing to receive
                    outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                } else {
                    // Prepare to receive
                    outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);

                    String lastReceivedJson;
                    Gson gson = new Gson();

                    for (int i = 0; i < numberToReceive; i++) {
                        Log.d(WifiP2PActivity.TAG, "Receiving Artefacto number " + (i+1) + " from Client...");

                        // Receive JSON
                        lastReceivedJson = inputStream.readUTF();

                        // Turn JSON into Object
                        Artefacto receivedArtefacto = gson.fromJson(lastReceivedJson, Artefacto.class);
                        receivedArtefacto.setDate(Calendar.getInstance().getTime());

                        if (receivedArtefacto.getType() != 0) {
                            Log.d(WifiP2PActivity.TAG, "Artefacto is NOT text...");
                            // if NOT text -> receive file and save to DB
                            Log.d(WifiP2PActivity.TAG, "Receiving size of File from Client...");
                            int sizeOfFile = inputStream.readInt();
                            Log.d(WifiP2PActivity.TAG, "received size of File from Client with value -> " + sizeOfFile);

                            byte[] messageBytes = new byte[1024];
                            boolean end = false;
                            int totalBytesRead = 0;

                            String path = getExternalFilesDir("/").getAbsolutePath() + "/" + System.currentTimeMillis() + "_ROTEIROENTREMARES";

                            if (receivedArtefacto.getType() == 1) {
                                path = path + ".jpg";
                            } else if (receivedArtefacto.getType() == 2) {
                                path = path + ".3gp";
                            } else if (receivedArtefacto.getType() == 3) {
                                path = path + ".3gp";
                            }

                            // create File to receive chunks
                            File f = new File(path);

                            File dirs = new File(f.getParent());
                            if (!dirs.exists())
                                dirs.mkdirs();

                            Log.d(WifiP2PActivity.TAG, "Creating File with path -> " + path);
                            f.createNewFile();

                            FileOutputStream fileOutputStream = new FileOutputStream(f);

                            Log.d(WifiP2PActivity.TAG, "Start receiving File from Client...");

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

                            Log.d(WifiP2PActivity.TAG, "Received file from Client!");

                            ArtefactoTurma artefactoTurma = ArtefactoConverter.toArtefactoTurma(receivedArtefacto, dataRepository.getNome());

                            artefactoTurma.setContent(path);

                            Log.d(WifiP2PActivity.TAG, "Saving Artefacto to DB");
                            dataRepository.insertArtefactoTurma(artefactoTurma);

                            outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                        } else {
                            // else -> save to DB and next
                            Log.d(WifiP2PActivity.TAG, "Saving Artefacto to DB");
                            dataRepository.insertArtefactoTurma(ArtefactoConverter.toArtefactoTurma(receivedArtefacto, dataRepository.getNome()));
                            outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                        }
                    }
                }

                /*if (lastConnectionWithClient == null) {
                    WifiP2pConnection wifiP2pConnection = new WifiP2pConnection(
                            clientIdAluno,
                            Calendar.getInstance().getTime()
                    );

                    dataRepository.insertWifiP2pConnection(wifiP2pConnection);
                } else {
                    // update
                    lastConnectionWithClient.setLastConnection(Calendar.getInstance().getTime());
                    dataRepository.updateWifiP2pConnection(lastConnectionWithClient);
                }*/











                /*outputStream.writeUTF("TYPE_OF_DATA");
                Log.d(WifiP2PActivity.TAG, "Sent TYPE_OF_DATA to Client");*/

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
                /*String dataToSend = "This is the data sent by the Server in the first step of the protocol";
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

                Log.d(WifiP2PActivity.TAG, "Received DATA from Client with value -> " + dataString.toString());*/

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
