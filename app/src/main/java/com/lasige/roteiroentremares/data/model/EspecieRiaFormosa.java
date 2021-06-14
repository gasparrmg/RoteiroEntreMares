package com.lasige.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lasige.roteiroentremares.util.GithubTypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "especie_table_riaformosa")
public class EspecieRiaFormosa implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nomeComum;
    private String nomeCientifico;
    private String zona;
    private String tipo;
    private String grupo;
    private String caracteristicas;
    private String alimentacao;
    private String adaptacoes;
    private String sabiasQue;
    private String curiosidades;

    @TypeConverters(GithubTypeConverters.class)
    private ArrayList<String> pictures;

    @TypeConverters(GithubTypeConverters.class)
    private ArrayList<String> picturesSabiasQue;

    @TypeConverters(GithubTypeConverters.class)
    private ArrayList<String> picturesCuriosidades;

    private String linkExterno;
    private String tinyDesc;

    public EspecieRiaFormosa(String nomeComum, String nomeCientifico, String zona, String tipo, String grupo, String caracteristicas, String alimentacao, String adaptacoes, String sabiasQue, String curiosidades, ArrayList<String> pictures, ArrayList<String> picturesSabiasQue, ArrayList<String> picturesCuriosidades, String linkExterno, String tinyDesc) {
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.zona = zona;
        this.tipo = tipo;
        this.grupo = grupo;
        this.caracteristicas = caracteristicas;
        this.alimentacao = alimentacao;
        this.adaptacoes = adaptacoes;
        this.sabiasQue = sabiasQue;
        this.curiosidades = curiosidades;
        this.pictures = pictures;
        this.picturesSabiasQue = picturesSabiasQue;
        this.picturesCuriosidades = picturesCuriosidades;
        this.linkExterno = linkExterno;
        this.tinyDesc = tinyDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public String getZona() {
        return zona;
    }

    public String getTipo() {
        return tipo;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public String getAlimentacao() {
        return alimentacao;
    }

    public String getAdaptacoes() {
        return adaptacoes;
    }

    public String getSabiasQue() {
        return sabiasQue;
    }

    public String getCuriosidades() {
        return curiosidades;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public ArrayList<String> getPicturesSabiasQue() {
        return picturesSabiasQue;
    }

    public ArrayList<String> getPicturesCuriosidades() {
        return picturesCuriosidades;
    }

    public String getLinkExterno() {
        return linkExterno;
    }

    public String getTinyDesc() {
        return tinyDesc;
    }
}
