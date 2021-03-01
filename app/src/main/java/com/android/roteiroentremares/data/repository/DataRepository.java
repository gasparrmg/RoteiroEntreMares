package com.android.roteiroentremares.data.repository;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.dao.EspecieAvencasDao;
import com.android.roteiroentremares.data.database.RoteiroDatabase;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {
    private static final String SHAREDPREF_KEY_TIPOUTILIZADOR = "key_tipoutilizador";
    private static final String SHAREDPREF_KEY_ONBOARDING = "key_onboarding";
    private static final String SHAREDPREF_KEY_NOME = "key_nome";
    private static final String SHAREDPREF_KEY_ESCOLA = "key_escola";
    private static final String SHAREDPREF_KEY_ANOESCOLARIDADE = "key_anoescolaridade";
    private static final String SHAREDPREF_KEY_ANOLECTIVO = "key_anolectivo";
    private static final String SHAREDPREF_KEY_CODIGOTURMA = "key_codigoturma";
    private static final String SHAREDPREF_KEY_SHARE_LOCATION_ARTEFACTOS = "key_share_location_artefactos";

    private static final String SHAREDPREF_KEY_FINISHED_HISTORIAS_PASSADO = "key_finished_historias_passado";
    private static final String SHAREDPREF_KEY_FINISHED_NAO_FIQUES_POR_AQUI = "key_finished_nao_fiques_por_aqui";

    private SharedPreferences sharedPreferences;
    private ArtefactoDao artefactoDao;
    private EspecieAvencasDao especieAvencasDao;

    private LiveData<List<Artefacto>> allArtefactos;
    private LiveData<List<EspecieAvencas>> allEspecieAvencas;


    @Inject
    public DataRepository (
            SharedPreferences sharedPreferences,
            ArtefactoDao artefactoDao,
            EspecieAvencasDao especieAvencasDao
    ) {
        this.sharedPreferences = sharedPreferences;
        this.artefactoDao = artefactoDao;
        this.especieAvencasDao = especieAvencasDao;

        allArtefactos = artefactoDao.getAll();
        allEspecieAvencas = especieAvencasDao.getAll();
    }

    /**
     * -------------------------------- SHARED PREFERENCES METHODS -------------------------------------------------
     */

    /**
     * Returns true if the User already completed the Historias do Passado sequence
     * @return
     */
    public boolean isHistoriasPassadoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_HISTORIAS_PASSADO,
                false
        );
    }

    /**
     * Sets as finished the Historias do Passado sequence
     */
    public void setHistoriasPassadoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_HISTORIAS_PASSADO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Não Fiques Por Aqui sequence
     * @return
     */
    public boolean isNaoFiquesPorAquiFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_NAO_FIQUES_POR_AQUI,
                false
        );
    }

    /**
     * Sets as finished the Não Fiques Por Aqui sequence
     */
    public void setNaoFiquesPorAquiAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_NAO_FIQUES_POR_AQUI,
                true
        ).apply();
    }

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

    public void setShareLocationArtefactos(boolean shareLocationArtefactos) {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_SHARE_LOCATION_ARTEFACTOS,
                shareLocationArtefactos
        ).apply();
    }

    public boolean getShareLocationArtefactos() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_SHARE_LOCATION_ARTEFACTOS,
                false
        );
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

    /**
     * -------------------------------- LOCAL DATABASE METHODS -------------------------------------------------
     */

    public void insertArtefacto(Artefacto artefacto) {
        new InsertArtefactoAsyncTask(artefactoDao).execute(artefacto);
    }

    public void updateArtefacto(Artefacto artefacto) {
        new UpdateArtefactoAsyncTask(artefactoDao).execute(artefacto);
    }

    public void deleteArtefacto(Artefacto artefacto) {
        new DeleteArtefactoAsyncTask(artefactoDao).execute(artefacto);
    }

    public void deleteAllArtefacto() {
        new DeleteAllArtefactoAsyncTask(artefactoDao).execute();
    }

    /**
     * Return all artefacts.
     * Methods that return LiveData don't need AsyncTasks bc Room already does it behind the scenes
     * @return
     */
    public LiveData<List<Artefacto>> getAllArtefactos() {
        return allArtefactos;
    }

    /**
     * Return all species from Avencas.
     * Methods that return LiveData don't need AsyncTasks bc Room already does it behind the scenes
     * @return
     */
    public LiveData<List<EspecieAvencas>> getAllEspecieAvencas() {
        return allEspecieAvencas;
    }

    public LiveData<List<EspecieAvencas>> getFilteredEspecies(SupportSQLiteQuery query) {
        return especieAvencasDao.getFilteredEspecies(query);
    }

    private static class InsertArtefactoAsyncTask extends AsyncTask<Artefacto, Void, Void> {
        private ArtefactoDao artefactoDao;

        private InsertArtefactoAsyncTask(ArtefactoDao artefactoDao) {
            this.artefactoDao = artefactoDao;
        }

        @Override
        protected Void doInBackground(Artefacto... artefactos) {
            artefactoDao.insert(artefactos[0]);
            return null;
        }
    }

    private static class UpdateArtefactoAsyncTask extends AsyncTask<Artefacto, Void, Void> {
        private ArtefactoDao artefactoDao;

        private UpdateArtefactoAsyncTask(ArtefactoDao artefactoDao) {
            this.artefactoDao = artefactoDao;
        }

        @Override
        protected Void doInBackground(Artefacto... artefactos) {
            artefactoDao.update(artefactos[0]);
            return null;
        }
    }

    private static class DeleteArtefactoAsyncTask extends AsyncTask<Artefacto, Void, Void> {
        private ArtefactoDao artefactoDao;

        private DeleteArtefactoAsyncTask(ArtefactoDao artefactoDao) {
            this.artefactoDao = artefactoDao;
        }

        @Override
        protected Void doInBackground(Artefacto... artefactos) {
            artefactoDao.delete(artefactos[0]);
            return null;
        }
    }

    private static class DeleteAllArtefactoAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoDao artefactoDao;

        private DeleteAllArtefactoAsyncTask(ArtefactoDao artefactoDao) {
            this.artefactoDao = artefactoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            artefactoDao.deleteAll();
            return null;
        }
    }
}
