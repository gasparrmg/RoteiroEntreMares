package com.lasige.roteiroentremares;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class RoteiroEntreMaresApplication extends Application {
    public static final String CHANNEL_ID = "com.lasige.roteiroentremare.CHANNEL_ID";

    private NotificationManager notificationManager;

    // Wifi P2p variables
    private boolean isUsingWifiP2pFeature;

    @Override
    public void onCreate() {
        super.onCreate();

        isUsingWifiP2pFeature = false;

        createNotificationChannel();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Wifi P2p Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public boolean isUsingWifiP2pFeature() {
        return isUsingWifiP2pFeature;
    }

    public void setUsingWifiP2pFeature(boolean usingWifiP2pFeature) {
        isUsingWifiP2pFeature = usingWifiP2pFeature;
    }
}
