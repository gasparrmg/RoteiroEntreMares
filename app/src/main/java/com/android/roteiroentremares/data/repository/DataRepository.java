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
    private static final String SHAREDPREF_KEY_FINISHED_E_QUANDO_A_MARE_SOBE = "key_finished_e_quando_a_mare_sobe";
    private static final String SHAREDPREF_KEY_FINISHED_NAO_FIQUES_POR_AQUI = "key_finished_nao_fiques_por_aqui";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS = "key_finished_impactos";

    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_CAPTURA_EXCESSIVA = "key_finished_impactos_captura_excessiva";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_PISOTEIO = "key_finished_impactos_pisoteio";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_POLUICAO = "key_finished_impactos_poluicao";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_TEMP_AGUA = "key_finished_impactos_temp_agua";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_2 = "key_finished_impactos_2";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_3 = "key_finished_impactos_3";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_6 = "key_finished_impactos_6";
    private static final String SHAREDPREF_KEY_FINISHED_IMPACTOS_OCUPACAO_HUMANA = "key_finished_impactos_ocupacao_humana";

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
     * Returns true if the User already completed the E Quando a Mare Sobe sequence
     * @return
     */
    public boolean isEQuandoAMareSobeFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_E_QUANDO_A_MARE_SOBE,
                false
        );
    }

    /**
     * Sets as finished the E Quando a Mare Sobe sequence
     */
    public void setEQuandoAMareSobeAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_E_QUANDO_A_MARE_SOBE,
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
     * Returns true if the User already completed the Impactos sequence
     * @return
     */
    public boolean isImpactosFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS,
                false
        );
    }

    /**
     * Sets as finished the Impactos sequence
     */
    public void setImpactosAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Impactos -> Captura Excessiva sequence
     * @return
     */
    public boolean isImpactosCapturaExcessivaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_CAPTURA_EXCESSIVA,
                false
        );
    }

    /**
     * Sets as finished the Impactos -> Captura Excessiva sequence
     */
    public void setImpactosCapturaExcessivaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_CAPTURA_EXCESSIVA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Impactos -> Pisoteio sequence
     * @return
     */
    public boolean isImpactosPisoteioFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_PISOTEIO,
                false
        );
    }

    /**
     * Sets as finished the Impactos -> Pisoteio sequence
     */
    public void setImpactosPisoteioAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_PISOTEIO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Impactos -> Poluicao sequence
     * @return
     */
    public boolean isImpactosPoluicaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_POLUICAO,
                false
        );
    }

    /**
     * Sets as finished the Impactos -> Poluicao sequence
     */
    public void setImpactosPoluicaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_POLUICAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Impactos -> Temp Agua sequence
     * @return
     */
    public boolean isImpactosTempAguaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_TEMP_AGUA,
                false
        );
    }

    /**
     * Sets as finished the Impactos -> Temp Agua sequence
     */
    public void setImpactosTempAguaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_TEMP_AGUA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Impactos -> Temp Agua sequence
     * @return
     */
    public boolean isImpactosOcupacaoHumanaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_OCUPACAO_HUMANA,
                false
        );
    }

    /**
     * Sets as finished the Impactos -> Temp Agua sequence
     */
    public void setImpactosOcupacaoHumanaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_OCUPACAO_HUMANA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already answered the Impactos 2
     * @return
     */
    public boolean isImpactos2Answered() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_2,
                false
        );
    }

    /**
     * Sets as ANSWERED the Impactos 2
     */
    public void setImpactos2AsAnswered() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_2,
                true
        ).apply();
    }

    /**
     * Returns true if the User already answered the Impactos 3
     * @return
     */
    public boolean isImpactos3Answered() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_3,
                false
        );
    }

    /**
     * Sets as ANSWERED the Impactos 3
     */
    public void setImpactos3AsAnswered() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_3,
                true
        ).apply();
    }

    /**
     * Returns true if the User already answered the Impactos 6
     * @return
     */
    public boolean isImpactos6Answered() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_6,
                false
        );
    }

    /**
     * Sets as ANSWERED the Impactos 6
     */
    public void setImpactos6AsAnswered() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_IMPACTOS_6,
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
