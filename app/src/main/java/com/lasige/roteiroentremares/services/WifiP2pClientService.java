package com.lasige.roteiroentremares.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;
import com.lasige.roteiroentremares.data.repository.DataRepository;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.util.CollabUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WifiP2pClientService extends IntentService {

    @Inject
    DataRepository dataRepository;

    public static final String TESTE_ID_ALUNO = "teste_id_aluno";

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

                Log.d(WifiP2PActivity.TAG, "Trying to connect to Server...");
                int responseCode = 0;

                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(WifiP2PActivity.TAG, "Client socket - " + socket.isConnected());

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                // SEND TIPO UTILIZADOR TO SERVER

                int myTipoUtilizador = dataRepository.getTipoUtilizador();

                outputStream.writeInt(myTipoUtilizador);
                Log.d(WifiP2PActivity.TAG, "Sent TipoUtilizador to Server with value: " + myTipoUtilizador);

                responseCode = inputStream.readInt();


                Log.d(WifiP2PActivity.TAG, "Received answer from Server with value: " + responseCode);

                if (responseCode == WifiP2PActivity.ERROR_MESSAGE) {
                    Log.d(WifiP2PActivity.TAG, "NOT Professor + Aluno connection -> disconnect");
                    return;
                }

                // SEND CODIGO TURMA

                String myCodigoTurma = dataRepository.getCodigoTurma();

                outputStream.writeUTF(myCodigoTurma);
                Log.d(WifiP2PActivity.TAG, "Sent CodigoTurma to Server with value: " + myCodigoTurma);

                responseCode = inputStream.readInt();

                if (responseCode == WifiP2PActivity.ERROR_MESSAGE) {
                    Log.d(WifiP2PActivity.TAG, "CodigoTurma not equal -> disconnect");
                    return;
                }

                // Teste ID Aluno

                String myIdAluno = TESTE_ID_ALUNO;

                outputStream.writeUTF(myIdAluno);
                Log.d(WifiP2PActivity.TAG, "Sent IdAluno with value: " + myIdAluno);

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
                            dataRepository.insertArtefactoTurma(receivedArtefactoTurma);

                            outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                        } else {
                            // else -> save to DB and next
                            dataRepository.insertArtefactoTurma(receivedArtefactoTurma);
                            outputStream.writeInt(WifiP2PActivity.SUCCESS_MESSAGE);
                        }
                    }
                }

                String serverIdProfessor = inputStream.readUTF();
                Log.d(WifiP2PActivity.TAG, "Received IdProfessor from Server with value: " + serverIdProfessor);

                WifiP2pConnection lastConnectionWithClient = dataRepository.getLastConnectionWithDevice(serverIdProfessor);

                List<Artefacto> artefactosToSend = new ArrayList<>();

                if (lastConnectionWithClient == null) {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + serverIdProfessor + " is null. Never happened.");
                    // Get all Artefactos from DB

                    artefactosToSend = dataRepository.getAllArtefactosAlt();
                } else {
                    Log.d(WifiP2PActivity.TAG, "Last connection with " + serverIdProfessor + " was at " + lastConnectionWithClient.getLastConnection().toString());
                    // Get Artefactos Turma From To
                    artefactosToSend = dataRepository.getArtefactoFromTo(lastConnectionWithClient.getLastConnection().getTime(), Calendar.getInstance().getTime().getTime());
                }

                // Check and send how many toSend to Server

                if (artefactosToSend == null) {
                    Log.d(WifiP2PActivity.TAG, "toSend is NULL. Sending number of Artefactos to Send to Server with value: 0");
                    outputStream.writeInt(0);
                } else {
                    Log.d(WifiP2PActivity.TAG, "toSend is NOT NULL. Sending number of Artefactos to Send to Server with value: " + artefactosToSend.size());
                    outputStream.writeInt(artefactosToSend.size());
                }

                responseCode = inputStream.readInt();

                if (responseCode == WifiP2PActivity.ERROR_MESSAGE) {
                    Log.d(WifiP2PActivity.TAG, "Error -> disconnect");
                    return;
                } else if (responseCode == WifiP2PActivity.SUCCESS_MESSAGE) {
                    Log.d(WifiP2PActivity.TAG, "Beggining to send Artefactos to Server...");

                    String currentJson;

                    for (int i = 0; i < artefactosToSend.size(); i++) {
                        Log.d(WifiP2PActivity.TAG, "Sending Artefacto number " + (i+1) + " to Server...");

                        currentJson = artefactosToSend.get(i).toJson();

                        outputStream.writeUTF(currentJson);

                        if (artefactosToSend.get(i).getType() != 0) {
                            // Send file
                            File file = new File(artefactosToSend.get(i).getContent());
                            byte[] fileInBytes = CollabUtils.readFileIntoByteArray(file);

                            // Send length of file
                            Log.d(WifiP2PActivity.TAG, "Sending length of file to Server...");
                            outputStream.writeInt(fileInBytes.length);

                            // Send file
                            Log.d(WifiP2PActivity.TAG, "Sending file to Server...");
                            outputStream.write(fileInBytes);
                        }

                        Log.d(WifiP2PActivity.TAG, "Waiting for SUCCESS from Server...");
                        responseCode = inputStream.readInt();

                        if (responseCode == WifiP2PActivity.SUCCESS_MESSAGE) {
                            Log.d(WifiP2PActivity.TAG, "Received SUCCESS from Server...");
                        } else {
                            Log.d(WifiP2PActivity.TAG, "Received ERROR from Server...");
                        }
                    }
                } else {
                    // unknown error -> disconnect
                }

                /*if (lastConnectionWithClient == null) {
                    WifiP2pConnection wifiP2pConnection = new WifiP2pConnection(
                            serverIdProfessor,
                            Calendar.getInstance().getTime()
                    );

                    dataRepository.insertWifiP2pConnection(wifiP2pConnection);
                } else {
                    // update
                    lastConnectionWithClient.setLastConnection(Calendar.getInstance().getTime());
                    dataRepository.updateWifiP2pConnection(lastConnectionWithClient);
                }*/










                // READS TYPE AND SIZE OF DATA THAT IS GOING TO RECEIVE FROM SERVER
                /*String typeOfData = inputStream.readUTF();
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
                byte[] buffer = new byte[8192]; // or 4096, or more
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
