package com.android.roteiroentremares.data.repository;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {
    private static String SHAREDPREF_KEY_TIPOUTILIZADOR = "key_tipoutilizador";
    private static String SHAREDPREF_KEY_ONBOARDING = "key_onboarding";
    private static String SHAREDPREF_KEY_NOME = "key_nome";
    private static String SHAREDPREF_KEY_ESCOLA = "key_escola";
    private static String SHAREDPREF_KEY_ANOESCOLARIDADE = "key_anoescolaridade";
    private static String SHAREDPREF_KEY_ANOLECTIVO = "key_anolectivo";
    private static String SHAREDPREF_KEY_CODIGOTURMA = "key_codigoturma";

    private SharedPreferences sharedPreferences;

    @Inject
    public DataRepository (
            SharedPreferences sharedPreferences
    ) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * -------------------------------- SHARED PREFERENCES METHODS -------------------------------------------------
     */

    /**
     * Returns true if the User already completed the OnBoarding sequence
     * @return
     */
    public boolean getOnBoarding() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_ONBOARDING,
                false
        );
    }

    /**
     * Sets preference regarding the OnBoarding sequence
     * @param onBoarding
     */
    public void setOnBoarding(boolean onBoarding) {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_ONBOARDING,
                onBoarding
        ).apply();
    }

    /**
     * Return type of User.
     * 0 - Aluno
     * 1 - Professor
     * 2 - Explorador
     * @return
     */
    public int getTipoUtilizador() {
        return sharedPreferences.getInt(
                SHAREDPREF_KEY_TIPOUTILIZADOR,
                0
        );
    }

    /**
     * Set type of User
     * 0 - Aluno
     * 1 - Professor
     * 2 - Explorador
     * @param tipoUtilizador
     */
    public void setTipoUtilizador(int tipoUtilizador) {
        sharedPreferences.edit().putInt(
                SHAREDPREF_KEY_TIPOUTILIZADOR,
                tipoUtilizador
        ).apply();
    }

    /**
     * Returns the User's name written in the Shared Preferences
     * @return
     */
    public String getNome() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_NOME,
                ""
        );
    }

    /**
     * Inserts the User's name into Shared Preferences
     * @param nome
     */
    public void setNome(String nome) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_NOME,
                nome
        ).apply();
    }

    /**
     * Returns the User's school name written in the Shared Preferences
     * @return
     */
    public String getEscola() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_ESCOLA,
                ""
        );
    }

    /**
     * Inserts the User's school name into Shared Preferences
     * @param escola
     */
    public void setEscola(String escola) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_ESCOLA,
                escola
        ).apply();
    }

    /**
     * Returns the User's school year written in the Shared Preferences
     * @return
     */
    public String getAnoEscolaridade() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_ANOESCOLARIDADE,
                ""
        );
    }

    /**
     * Inserts the User's school year into Shared Preferences
     * @param anoEscolaridade
     */
    public void setAnoEscolaridade(String anoEscolaridade) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_ANOESCOLARIDADE,
                anoEscolaridade
        ).apply();
    }

    /**
     * Returns the User's academic year written in the Shared Preferences
     * @return
     */
    public String getAnoLectivo() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_ANOLECTIVO,
                ""
        );
    }

    /**
     * Inserts the User's academic year into Shared Preferences
     * @param anoLectivo
     */
    public void setAnoLectivo(String anoLectivo) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_ANOLECTIVO,
                anoLectivo
        ).apply();
    }

    /**
     * Returns the Class Code written in the Shared Preferences
     * @return
     */
    public String getCodigoTurma() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_CODIGOTURMA,
                ""
        );
    }

    /**
     * Inserts the Class Code into Shared Preferences
     * @param codigoTurma
     */
    public void setCodigoTurma(String codigoTurma) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_CODIGOTURMA,
                codigoTurma
        ).apply();
    }
}
