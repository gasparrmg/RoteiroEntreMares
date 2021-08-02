package com.lasige.roteiroentremares.util.wifip2p;

import android.util.Log;

import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SyncList {

    public interface SyncListCallback {
        void startProfessorSyncService(String ipAddress);
    }

    private SyncListCallback callback;

    private List<String> wifiP2pSyncList;
    private ScheduledExecutorService imediateExecutorService;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> imediateFuture;
    private ScheduledFuture<?> scheduledFuture;

    private Runnable syncTask = new Runnable() {
        @Override
        public void run() {
            // This will run every 10sec

            Log.d(WifiP2PActivity.TAG, "Starting fixed rate timer...");

            Log.d(WifiP2PActivity.TAG, "Starting sync, 1st round...");

            for (int i = 0; i < wifiP2pSyncList.size(); i++) {
                callback.startProfessorSyncService(wifiP2pSyncList.get(i));
            }

            Log.d(WifiP2PActivity.TAG, "Starting sync, 2nd round...");

            for (int i = 0; i < wifiP2pSyncList.size(); i++) {
                callback.startProfessorSyncService(wifiP2pSyncList.get(i));
            }
        }
    };

    private Runnable scheduleTask = new Runnable() {
        @Override
        public void run() {
            // this will run periodically
            // execute sync
            imediateExecutorService.schedule(syncTask, 1, TimeUnit.SECONDS);
        }
    };

    @Inject
    public SyncList() {
        this.wifiP2pSyncList = new ArrayList<>();
        this.imediateExecutorService = Executors.newScheduledThreadPool(1);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Log.d(WifiP2PActivity.TAG, "SyncList was initialized...");
    }

    public void setStartProfessorServiceCallback(SyncListCallback callback) {
        this.callback = callback;
    }

    public void addIpToList(String ipAddress) {
        Log.d(WifiP2PActivity.TAG, "Adding " + ipAddress + " to the SyncList...");

        if (!wifiP2pSyncList.contains(ipAddress)) {
            wifiP2pSyncList.add(ipAddress);
        }

        Log.d(WifiP2PActivity.TAG, ipAddress + " was added to the queue...");

        if (wifiP2pSyncList.size() > 0) {

            if (wifiP2pSyncList.size() == 1) {
                // start task periodically (2 em 2 min)
                // task to execute -> schedule syncTask
                scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(scheduleTask, 0, 60, TimeUnit.SECONDS);
            } else {
                // execute sync
                imediateFuture = imediateExecutorService.schedule(syncTask, 1, TimeUnit.SECONDS);
            }
        }
    }

    public List<String> getWifiP2pSyncList() {
        return wifiP2pSyncList;
    }

    public void setWifiP2pSyncList(List<String> wifiP2pSyncList) {
        this.wifiP2pSyncList = wifiP2pSyncList;
    }

    public void initWifiP2pSyncList() {
        wifiP2pSyncList = new ArrayList<>();

        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        if (imediateFuture != null) {
            imediateFuture.cancel(false);
        }
    }

    public ScheduledExecutorService getImediateExecutorService() {
        return imediateExecutorService;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public void cancelJobs() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        if (imediateFuture != null) {
            imediateFuture.cancel(false);
        }
    }
}
