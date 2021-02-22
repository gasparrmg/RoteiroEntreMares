package com.android.roteiroentremares.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.roteiroentremares.util.GithubTypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "especie_table_avencas")
public class EspecieAvencas implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nomeComum;
    private String nomeCientifico;
    private String grupo;
    private String caracteristicas;
    private String alimentacao;
    private String adaptacoes;
    private String sabiasQue;

    /**
     * FIELDS FOR FILTERS
     */

    private boolean animalMovel;
    private boolean tentaculos;
    private boolean apendicesArticulados;
    private boolean simetriaRadial;
    private boolean carapacaCalcaria;
    private boolean placasCalcarias;
    private boolean concha;

    /**
     * 0 -> Algas ou Liquenes
     * 1 -> Aves
     * 2 -> Peixes
     * 3 -> Equinodermes
     * 4 -> Moluscos
     * 5 -> Crustaceos
     * 6 -> Anelideos
     * 7 -> Anemona ou Actinias
     * 8 -> Esponjas
     */
    private int grupoInt;

    @TypeConverters(GithubTypeConverters.class)
    private ArrayList<String> pictures;

    @TypeConverters(GithubTypeConverters.class)
    private ArrayList<String> picturesSabiasQue;

    private String linkExterno;

    /*private boolean algasOuLiquenes;
    private boolean aves;
    private boolean peixes;
    private boolean equinodermes;
    private boolean moluscos;
    private boolean crustaceos;
    private boolean anelideos;
    private boolean anemonaOuActinias;
    private boolean esponjas;*/

    public EspecieAvencas(String nomeComum, String nomeCientifico, String grupo, String caracteristicas, String alimentacao, String adaptacoes, String sabiasQue, boolean animalMovel, boolean tentaculos, boolean apendicesArticulados, boolean simetriaRadial, boolean carapacaCalcaria, boolean placasCalcarias, boolean concha, int grupoInt, ArrayList<String> pictures, ArrayList<String> picturesSabiasQue, String linkExterno) {
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.grupo = grupo;
        this.caracteristicas = caracteristicas;
        this.alimentacao = alimentacao;
        this.adaptacoes = adaptacoes;
        this.sabiasQue = sabiasQue;
        this.animalMovel = animalMovel;
        this.tentaculos = tentaculos;
        this.apendicesArticulados = apendicesArticulados;
        this.simetriaRadial = simetriaRadial;
        this.carapacaCalcaria = carapacaCalcaria;
        this.placasCalcarias = placasCalcarias;
        this.concha = concha;
        this.grupoInt = grupoInt;
        this.pictures = pictures;
        this.picturesSabiasQue = picturesSabiasQue;
        this.linkExterno = linkExterno;
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

    public boolean isAnimalMovel() {
        return animalMovel;
    }

    public boolean isTentaculos() {
        return tentaculos;
    }

    public boolean isApendicesArticulados() {
        return apendicesArticulados;
    }

    public boolean isSimetriaRadial() {
        return simetriaRadial;
    }

    public boolean isCarapacaCalcaria() {
        return carapacaCalcaria;
    }

    public boolean isPlacasCalcarias() {
        return placasCalcarias;
    }

    public boolean isConcha() {
        return concha;
    }

    public int getGrupoInt() {
        return grupoInt;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public ArrayList<String> getPicturesSabiasQue() {
        return picturesSabiasQue;
    }

    public String getLinkExterno() {
        return linkExterno;
    }
}
