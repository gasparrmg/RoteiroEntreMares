package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;
import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.ArtefactoConverter;
import com.lasige.roteiroentremares.util.wifip2p.CollabUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WifiP2pSyncProfessorService extends IntentService {

    /**
     * This Service will be used by the Group Owner and will work as a
     */

    @Inject
    DataRepository dataRepository;

    public static final String ACTION_SYNC = "com.lasige.roteiroentremares.ACTION_SYNC";
    public static final String TESTE_ID_PROFESSOR = "teste_id_professor2";

    public WifiP2pSyncProfessorService(String name) {
        super(name);
    }

    public WifiP2pSyncProfessorService() {
        super("WifiP2pSyncProfessorService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(WifiP2PActivity.TAG, "Entered Service Sync Professor...");
        if (intent.getAction().equals(ACTION_SYNC)) {
            Log.d(WifiP2PActivity.TAG, "Starting Sync Professor Service...");
            String alunoIpAddress = intent.getExtras().getString(CollabUtils.EXTRAS_ALUNO_IP_ADDRESS);
            Socket socket = new Socket();

            try {
                socket.bind(null);
                Log.d(WifiP2PActivity.TAG, "Trying to connect to " + alunoIpAddress + "...");
                socket.connect((new InetSocketAddress(alunoIpAddress, CollabUtils.SYNC_PORT)), CollabUtils.SOCKET_TIMEOUT);

                Log.d(WifiP2PActivity.TAG, "Successfully connected to " + alunoIpAddress);

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                String idAluno = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received IdAluno with value: " + idAluno);

                // check table past connections
                WifiP2pConnection lastConnectionWithClient = dataRepository.getLastConnectionWithDevice(idAluno);

                List<ArtefactoTurma> artefactosTurmaToSend = new ArrayList<>();

                if (lastConnectionWithClient == null) {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + idAluno + " is null. Never happened.");
                    // Get all Artefactos Turma from DB
                    artefactosTurmaToSend = dataRepository.getAllArtefactosTurmaAlt();
                } else {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + idAluno + " was at " + lastConnectionWithClient.getLastConnection().toString());
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

                if (!inputStream.readBoolean()) {
                    // Disconnect
                    Log.d(WifiP2PActivity.TAG, "Error -> disconnect");
                    return;
                } else {
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

                        if (inputStream.readBoolean()) {
                            Log.d(WifiP2PActivity.TAG, "Received SUCCESS from Client...");
                        } else {
                            Log.d(WifiP2PActivity.TAG, "Received ERROR from Client...");
                        }
                    }
                }

                // Send IdProfessor to Client
                String myIdProfessor = dataRepository.getNomeUUID();

                outputStream.writeUTF(myIdProfessor);
                Log.d(WifiP2PActivity.TAG, "Sent myIdProfessor with value: " + myIdProfessor);

                // Receiving number of artefactos to receive
                int numberToReceive = inputStream.readInt();

                Log.d(WifiP2PActivity.TAG, "Received number of Artefactos to receive with value: " + numberToReceive);

                if (numberToReceive == 0) {
                    // Nothing to receive
                    outputStream.writeBoolean(true);
                } else {
                    // Prepare to receive
                    outputStream.writeBoolean(true);

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
                        } else {
                            // else -> save to DB and next
                            Log.d(WifiP2PActivity.TAG, "Saving Artefacto to DB");
                            dataRepository.insertArtefactoTurma(ArtefactoConverter.toArtefactoTurma(receivedArtefacto, dataRepository.getNome()));
                        }

                        outputStream.writeBoolean(true);
                    }
                }

                // add idAluno to LastConnections
                if (lastConnectionWithClient == null) {
                    WifiP2pConnection wifiP2pConnection = new WifiP2pConnection(
                            idAluno,
                            Calendar.getInstance().getTime()
                    );

                    dataRepository.insertWifiP2pConnection(wifiP2pConnection);
                } else {
                    // update
                    lastConnectionWithClient.setLastConnection(Calendar.getInstance().getTime());
                    dataRepository.updateWifiP2pConnection(lastConnectionWithClient);
                }

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
