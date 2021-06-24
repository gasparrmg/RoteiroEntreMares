package com.lasige.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lasige.roteiroentremares.util.GithubTypeConverters;

import java.util.Date;

@Entity(tableName = "artefacto_turma_table")
public class ArtefactoTurma {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String idString;

    private String autor;

    private String title;

    private String content;

    private int type;

    private String description;

    @TypeConverters(GithubTypeConverters.class)
    private Date receivedAt;

    private String latitude;

    private String longitude;

    private String codigoTurma;

    public ArtefactoTurma(String idString, String autor, String title, String content, int type, String description, Date receivedAt, String latitude, String longitude, String codigoTurma) {
        this.idString = idString;
        this.autor = autor;
        this.title = title;
        this.content = content;
        this.type = type;
        this.description = description;
        this.receivedAt = receivedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codigoTurma = codigoTurma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getIdString() {
        return idString;
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

    public Date getReceivedAt() {
        return receivedAt;
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
}
