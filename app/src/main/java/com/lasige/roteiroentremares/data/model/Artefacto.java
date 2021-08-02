package com.lasige.roteiroentremares.data.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.lasige.roteiroentremares.util.GithubTypeConverters;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Nullable;

@Entity(tableName = "artefacto_table")
public class Artefacto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String idString;

    private String autor;

    private String title;

    private String content;

    /**
     * TYPE:
     * 0 -> Text
     * 1 -> Image
     * 2 -> Audio
     * 3 -> Video
     */
    private int type;

    private String description;

    // private String date;

    @TypeConverters(GithubTypeConverters.class)
    private Date date;

    private String latitude;

    private String longitude;

    private String codigoTurma;

    private boolean shared;

    public Artefacto(String autor, String title, String content, int type, String description, Date date, String latitude, String longitude, String codigoTurma, boolean shared) {
        this.autor = autor;
        this.title = title;
        this.content = content;
        this.type = type;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codigoTurma = codigoTurma;
        this.shared = shared;

        UUID uuid = UUID.randomUUID();
        this.idString = uuid.toString() + "_" + date.getTime();
    }

    /**
     * WARNING: receivedAt is going NULL
     * @return
     */
    public String toJson() {
        Artefacto tempArtefacto = this;
        tempArtefacto.setDate(null);

        Gson gson = new Gson();

        return gson.toJson(tempArtefacto);
    }



    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public boolean isShared() {
        return shared;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
