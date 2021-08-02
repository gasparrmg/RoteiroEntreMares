package com.lasige.roteiroentremares.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lasige.roteiroentremares.util.GithubTypeConverters;

import java.util.Date;

@Entity(tableName = "wifi_p2p_connections")
public class WifiP2pConnection {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String deviceAddress;

    @TypeConverters(GithubTypeConverters.class)
    private Date lastConnection;

    public WifiP2pConnection(String deviceAddress, Date lastConnection) {
        this.deviceAddress = deviceAddress;
        this.lastConnection = lastConnection;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }
}
