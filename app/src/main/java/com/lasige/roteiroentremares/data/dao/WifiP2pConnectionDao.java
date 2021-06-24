package com.lasige.roteiroentremares.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;

import java.util.List;

@Dao
public interface WifiP2pConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WifiP2pConnection wifiP2pConnection);

    @Update
    void update(WifiP2pConnection wifiP2pConnection);

    @Delete
    void delete(WifiP2pConnection wifiP2pConnection);

    @Query("DELETE FROM wifi_p2p_connections")
    void deleteAllRecords();

    @Query("SELECT * FROM wifi_p2p_connections WHERE deviceAddress = :deviceAddress")
    List<WifiP2pConnection> getLastConnectionWithDevice(String deviceAddress);
}
