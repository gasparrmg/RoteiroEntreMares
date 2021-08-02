package com.lasige.roteiroentremares.util.wifip2p;

import android.util.Log;

import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CollabUtils {

    public static final int REGISTRATION_PORT = 8987;
    public static final int SYNC_PORT = 8988;
    public static final int SOCKET_TIMEOUT = 5000;

    public static final String EXTRAS_ALUNO_IP_ADDRESS = "com.lasige.roteiroentremares.EXTRAS_ALUNO_IP_ADDRESS";
    public static final String SUCCESS = "com.lasige.roteiroentremares.WIFI_P2P_SUCCESS";
    public static final String ERROR_TIPOUTILIZADOR = "com.lasige.roteiroentremares.WIFI_P2P_ERROR_TIPOUTILIZADOR";
    public static final String ERROR_CODIGOTURMA = "com.lasige.roteiroentremares.WIFI_P2P_ERROR_CODIGOTURMA";
    public static final String ERROR_MACADDRESS_NULL = "com.lasige.roteiroentremares.WIFI_P2P_ERROR_MACADDRESS_NULL";
    public static final String ERROR = "com.lasige.roteiroentremares.WIFI_P2P_ERROR";

    public static final int PROGRESS_ADDED_TO_QUEUE = 100;
    public static final int PROGRESS_RECEIVED = 101;
    public static final int PROGRESS_SENT = 102;
    public static final int SYNC_STARTED = 103;
    public static final int SYNC_FINISHED = 104;

    public static byte[] readFileIntoByteArray(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return bytes;
    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            Log.d(WifiP2PActivity.TAG, e.toString());
            return false;
        }
        return true;
    }
}
