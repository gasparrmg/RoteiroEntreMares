package com.android.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity(tableName = "artefacto_table")
public class Artefacto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String content;

    private int type;

    @Nullable
    private String description;

    private String date;

    private String latitude;

    private String longitude;

    private String codigoTurma;

    private boolean shared;

    public Artefacto(String title, String content, int type, String description, String date, String latitude, String longitude, String codigoTurma, boolean shared) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codigoTurma = codigoTurma;
        this.shared = shared;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getDate() {
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
}
