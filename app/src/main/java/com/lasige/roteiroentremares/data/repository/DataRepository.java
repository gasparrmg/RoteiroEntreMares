package com.lasige.roteiroentremares.data.repository;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lasige.roteiroentremares.data.dao.ArtefactoDao;
import com.lasige.roteiroentremares.data.dao.ArtefactoTurmaDao;
import com.lasige.roteiroentremares.data.dao.AvistamentoDunasRiaFormosaDao;
import com.lasige.roteiroentremares.data.dao.AvistamentoPocasAvencasDao;
import com.lasige.roteiroentremares.data.dao.AvistamentoPocasRiaFormosaDao;
import com.lasige.roteiroentremares.data.dao.AvistamentoTranseptosRiaFormosaDao;
import com.lasige.roteiroentremares.data.dao.AvistamentoZonacaoAvencasDao;
import com.lasige.roteiroentremares.data.dao.EspecieAvencasDao;
import com.lasige.roteiroentremares.data.dao.EspecieRiaFormosaDao;
import com.lasige.roteiroentremares.data.dao.WifiP2pConnectionDao;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.lasige.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.lasige.roteiroentremares.data.model.AvistamentoPocasRiaFormosa;
import com.lasige.roteiroentremares.data.model.AvistamentoTranseptosRiaFormosa;
import com.lasige.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaDunasInstancias;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.data.model.WifiP2pConnection;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.lasige.roteiroentremares.ui.dashboard.PessoalActivity;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {
    private static final String SHAREDPREF_KEY_AVENCAS_RIAFORMOSA = "key_avencas_riaformosa";
    private static final String SHAREDPREF_KEY_AVENCAS_RIAFORMOSA_TO_CHANGE = "key_avencas_riaformosa_to_change";
    private static final String SHAREDPREF_KEY_TIPOUTILIZADOR = "key_tipoutilizador";
    private static final String SHAREDPREF_KEY_ONBOARDING = "key_onboarding";
    private static final String SHAREDPREF_KEY_NOME = "key_nome";
    private static final String SHAREDPREF_KEY_NOME_UUID = "key_nome_uuid";
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

    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS = "key_finished_biodiversidade_microhabitats";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_CANAIS = "key_finished_biodiversidade_microhabitats_canais";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_FENDAS = "key_finished_biodiversidade_microhabitats_fendas";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_POCAS = "key_finished_biodiversidade_microhabitats_pocas";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO = "key_finished_biodiversidade_zonacao";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_SUPRALITORAL = "key_finished_biodiversidade_zonacao_supralitoral";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_MEDIOLITORAL = "key_finished_biodiversidade_zonacao_mediolitoral";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_INFRALITORAL = "key_finished_biodiversidade_zonacao_infralitoral";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_PREDACAO = "key_finished_biodiversidade_interacoes_predacao";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_HERBIVORIA = "key_finished_biodiversidade_interacoes_herbivoria";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_COMPETICAO = "key_finished_biodiversidade_interacoes_competicao";
    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES = "key_finished_biodiversidade_interacoes";

    private static final String SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE = "key_finished_biodiversidade";

    private static final String SHAREDPREF_KEY_FINISHED_MARES = "key_finished_mares";

    // Ria Formosa

    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_NAOFIQUESPORAQUI = "key_finished_riaformosa_naofiquesporaqui";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_PRADARIASMARINHAS = "key_finished_riaformosa_pradariasmarinhas";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_SAPAL = "key_finished_riaformosa_sapal";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS = "key_finished_riaformosa_dunas";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAEMBRIONARIA = "key_finished_riaformosa_dunas_dunaembrionaria";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAPRIMARIA = "key_finished_riaformosa_dunas_dunaprimaria";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNASECUNDARIA = "key_finished_riaformosa_dunas_dunasecundaria";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_ZONAINTERDUNAR = "key_finished_riaformosa_dunas_zonainterdunar";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_PREDACAO = "key_finished_riaformosa_intertidalarenoso_predacao";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_COMPETICAO = "key_finished_riaformosa_intertidalarenoso_competicao";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO = "key_finished_riaformosa_intertidalarenoso";

    private SharedPreferences sharedPreferences;
    private ArtefactoDao artefactoDao;
    private ArtefactoTurmaDao artefactoTurmaDao;
    private WifiP2pConnectionDao wifiP2pConnectionDao;

    // Avencas
    private EspecieAvencasDao especieAvencasDao;
    private EspecieRiaFormosaDao especieRiaFormosaDao;
    private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
    private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

    // Ria Formosa
    private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;
    private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;
    private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;

    private LiveData<List<Artefacto>> allArtefactos;
    private LiveData<List<String>> allArtefactoIdString;

    private LiveData<List<ArtefactoTurma>> allArtefactosTurma;
    private LiveData<List<String>> allArtefactoTurmaIdString;

    private LiveData<List<EspecieAvencas>> allEspecieAvencas;
    private LiveData<List<EspecieRiaFormosa>> allEspecieRiaFormosa;

    private LiveData<List<AvistamentoPocasAvencas>> allAvistamentoPocasAvencas;
    private LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;

    private LiveData<List<AvistamentoZonacaoAvencas>> allAvistamentoZonacaoAvencas;
    private LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

    private LiveData<List<AvistamentoDunasRiaFormosa>> allAvistamentoDunasRiaFormosa;
    private LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;

    private LiveData<List<AvistamentoPocasRiaFormosa>> allAvistamentoPocasRiaFormosa;
    private LiveData<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>> allAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;

    private LiveData<List<AvistamentoTranseptosRiaFormosa>> allAvistamentoTranseptosRiaFormosa;
    private LiveData<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>> allAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
    private LiveData<List<EspecieRiaFormosaTranseptosInstancias>> allEspecieRiaFormosaTranseptosInstancias;


    @Inject
    public DataRepository(
            SharedPreferences sharedPreferences,
            ArtefactoDao artefactoDao,
            ArtefactoTurmaDao artefactoTurmaDao,
            WifiP2pConnectionDao wifiP2pConnectionDao,
            EspecieAvencasDao especieAvencasDao,
            EspecieRiaFormosaDao especieRiaFormosaDao,
            AvistamentoPocasAvencasDao avistamentoPocasAvencasDao,
            AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao,
            AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao,
            AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao,
            AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao
    ) {
        this.sharedPreferences = sharedPreferences;
        this.artefactoDao = artefactoDao;
        this.artefactoTurmaDao = artefactoTurmaDao;
        this.wifiP2pConnectionDao = wifiP2pConnectionDao;
        this.especieAvencasDao = especieAvencasDao;
        this.especieRiaFormosaDao = especieRiaFormosaDao;
        this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
        this.avistamentoPocasRiaFormosaDao = avistamentoPocasRiaFormosaDao;
        this.avistamentoTranseptosRiaFormosaDao = avistamentoTranseptosRiaFormosaDao;
        this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
        this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;

        allArtefactos = artefactoDao.getAll();
        allArtefactoIdString = artefactoDao.getAllIdStrings();

        allArtefactosTurma = artefactoTurmaDao.getAll();
        allArtefactoTurmaIdString = artefactoTurmaDao.getAllIdStrings();

        allEspecieAvencas = especieAvencasDao.getAll();
        allEspecieRiaFormosa = especieRiaFormosaDao.getAll();

        allAvistamentoPocasAvencas = avistamentoPocasAvencasDao.getAllAvistamentoPocasAvencas();
        allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias = avistamentoPocasAvencasDao.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias();

        allAvistamentoZonacaoAvencas = avistamentoZonacaoAvencasDao.getAllAvistamentoZonacaoAvencas();
        allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias = avistamentoZonacaoAvencasDao.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias();

        allAvistamentoDunasRiaFormosa = avistamentoDunasRiaFormosaDao.getAllAvistamentoDunasRiaFormosa();
        allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias = avistamentoDunasRiaFormosaDao.getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias();

        allAvistamentoPocasRiaFormosa = avistamentoPocasRiaFormosaDao.getAllAvistamentoPocasRiaFormosa();
        allAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias = avistamentoPocasRiaFormosaDao.getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias();

        allAvistamentoTranseptosRiaFormosa = avistamentoTranseptosRiaFormosaDao.getAllAvistamentoTranseptosRiaFormosa();
        allAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias = avistamentoTranseptosRiaFormosaDao.getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias();
        allEspecieRiaFormosaTranseptosInstancias = avistamentoTranseptosRiaFormosaDao.getAllEspecieRiaFormosaTranseptosInstancias();
    }

    public void deleteAllProgress(Activity activity) {
        new DeleteAllProgressAsyncTask(activity, sharedPreferences, artefactoDao, avistamentoDunasRiaFormosaDao, avistamentoPocasAvencasDao, avistamentoPocasRiaFormosaDao, avistamentoTranseptosRiaFormosaDao, avistamentoZonacaoAvencasDao).execute();
    }

    private static class DeleteAllProgressAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private Activity activity;
        private SharedPreferences sharedPreferences;
        private ArtefactoDao artefactoDao;
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
        private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;
        private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

        private DeleteAllProgressAsyncTask(
                Activity activity,
                SharedPreferences sharedPreferences,
                ArtefactoDao artefactoDao,
                AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao,
                AvistamentoPocasAvencasDao avistamentoPocasAvencasDao,
                AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao,
                AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao,
                AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao
        ) {
            this.activity = activity;
            this.sharedPreferences = sharedPreferences;
            this.artefactoDao = artefactoDao;
            this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;
            this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
            this.avistamentoPocasRiaFormosaDao = avistamentoPocasRiaFormosaDao;
            this.avistamentoTranseptosRiaFormosaDao = avistamentoTranseptosRiaFormosaDao;
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            sharedPreferences.edit().clear().commit();

            try {
                artefactoDao.deleteAll();
                avistamentoDunasRiaFormosaDao.deleteAllAvistamentoDunasRiaFormosa();
                avistamentoPocasAvencasDao.deleteAllAvistamentoPocasAvencas();
                avistamentoPocasRiaFormosaDao.deleteAllAvistamentoPocasRiaFormosa();
                avistamentoTranseptosRiaFormosaDao.deleteAllAvistamentoTranseptosRiaFormosa();
                avistamentoZonacaoAvencasDao.deleteAllAvistamentoZonacaoAvencas();
            } catch (Exception e) {
                Log.e("REPOSITORY_DELETEALLPROGRESS", "There was an error: " + e.getMessage());
                // Throw error
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                ((PessoalActivity) activity).restartApp();
            } else {
                // call error method in activity
                ((PessoalActivity) activity).showErrorOnDeleteProgress();
            }
        }
    }

    /**
     * -------------------------------- SHARED PREFERENCES METHODS -------------------------------------------------
     */

    /**
     * Returns true if the User already completed the Historias do Passado sequence
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     * Returns true if the User already completed the Biodiversidade -> Microhabitats sequence
     *
     * @return
     */
    public boolean isBiodiversidadeMicrohabitatsFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Microhabitats sequence
     */
    public void setBiodiversidadeMicrohabitatsAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Microhabitats -> Canais sequence
     *
     * @return
     */
    public boolean isBiodiversidadeMicrohabitatsCanaisFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_CANAIS,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Microhabitats -> Canais sequence
     */
    public void setBiodiversidadeMicrohabitatsCanaisAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_CANAIS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Microhabitats -> Fendas sequence
     *
     * @return
     */
    public boolean isBiodiversidadeMicrohabitatsFendasFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_FENDAS,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Microhabitats -> Fendas sequence
     */
    public void setBiodiversidadeMicrohabitatsFendasAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_FENDAS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Microhabitats -> Poças sequence
     *
     * @return
     */
    public boolean isBiodiversidadeMicrohabitatsPocasFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_POCAS,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Microhabitats -> Poças sequence
     */
    public void setBiodiversidadeMicrohabitatsPocasAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_MICROHABITATS_POCAS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Zonacao sequence
     *
     * @return
     */
    public boolean isBiodiversidadeZonacaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Zonacao sequence
     */
    public void setBiodiversidadeZonacaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Zonacao -> Supralitoral sequence
     *
     * @return
     */
    public boolean isBiodiversidadeZonacaoSupralitoralFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_SUPRALITORAL,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Zonacao -> Supralitoral sequence
     */
    public void setBiodiversidadeZonacaoSupralitoralAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_SUPRALITORAL,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Zonacao -> Mediolitoral sequence
     *
     * @return
     */
    public boolean isBiodiversidadeZonacaoMediolitoralFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_MEDIOLITORAL,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Zonacao -> Mediolitoral sequence
     */
    public void setBiodiversidadeZonacaoMediolitoralAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_MEDIOLITORAL,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Zonacao -> Infralitoral sequence
     *
     * @return
     */
    public boolean isBiodiversidadeZonacaoInfralitoralFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_INFRALITORAL,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Zonacao -> Infralitoral sequence
     */
    public void setBiodiversidadeZonacaoInfralitoralAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_ZONACAO_INFRALITORAL,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Interacoes -> Predacao sequence
     *
     * @return
     */
    public boolean isBiodiversidadeInteracoesPredacaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_PREDACAO,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Interacoes -> Predacao sequence
     */
    public void setBiodiversidadeInteracoesPredacaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_PREDACAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Interacoes -> Herbivoria sequence
     *
     * @return
     */
    public boolean isBiodiversidadeInteracoesHerbivoriaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_HERBIVORIA,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Interacoes -> Herbivoria sequence
     */
    public void setBiodiversidadeInteracoesHerbivoriaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_HERBIVORIA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Interacoes -> Competicao sequence
     *
     * @return
     */
    public boolean isBiodiversidadeInteracoesCompeticaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_COMPETICAO,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Interacoes -> Competicao sequence
     */
    public void setBiodiversidadeInteracoesCompeticaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES_COMPETICAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade -> Interacoes sequence
     *
     * @return
     */
    public boolean isBiodiversidadeInteracoesFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade -> Interacoes sequence
     */
    public void setBiodiversidadeInteracoesAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE_INTERACOES,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Biodiversidade sequence
     *
     * @return
     */
    public boolean isBiodiversidadeFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE,
                false
        );
    }

    /**
     * Sets as finished the Biodiversidade sequence
     */
    public void setBiodiversidadeAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_BIODIVERSIDADE,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Nao Fiques Por Aqui sequence
     *
     * @return
     */
    public boolean isRiaFormosaNaoFiquesPorAquiFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_NAOFIQUESPORAQUI,
                false
        );
    }

    /**
     * Sets as finished the Nao Fiques Por Aqui sequence
     */
    public void setRiaFormosaNaoFiquesPorAquiAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_NAOFIQUESPORAQUI,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the PradariasMarinhas sequence
     *
     * @return
     */
    public boolean isRiaFormosaPradariasMarinhasFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_PRADARIASMARINHAS,
                false
        );
    }

    /**
     * Sets as finished the PradariasMarinhas sequence
     */
    public void setRiaFormosaPradariasMarinhasAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_PRADARIASMARINHAS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaSapalFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_SAPAL,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaSapalAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_SAPAL,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaDunasDunaEmbrionariaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAEMBRIONARIA,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaDunasDunaEmbrionariaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAEMBRIONARIA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaDunasDunaPrimariaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAPRIMARIA,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaDunasDunaPrimariaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNAPRIMARIA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaDunasZonaInterdunarFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_ZONAINTERDUNAR,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaDunasZonaInterdunarAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_ZONAINTERDUNAR,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaDunasDunaSecundariaFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNASECUNDARIA,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaDunasDunaSecundariaAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS_DUNASECUNDARIA,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Sapal sequence
     *
     * @return
     */
    public boolean isRiaFormosaDunasFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS,
                false
        );
    }

    /**
     * Sets as finished the Sapal sequence
     */
    public void setRiaFormosaDunasAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_DUNAS,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the predacao sequence
     *
     * @return
     */
    public boolean isRiaFormosaIntertidalArenosoPredacaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_PREDACAO,
                false
        );
    }

    /**
     * Sets as finished the predacao sequence
     */
    public void setRiaFormosaIntertidalArenosoPredacaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_PREDACAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the predacao sequence
     *
     * @return
     */
    public boolean isRiaFormosaIntertidalArenosoCompeticaoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_COMPETICAO,
                false
        );
    }

    /**
     * Sets as finished the predacao sequence
     */
    public void setRiaFormosaIntertidalArenosoCompeticaoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO_COMPETICAO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the predacao sequence
     *
     * @return
     */
    public boolean isRiaFormosaIntertidalArenosoFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO,
                false
        );
    }

    /**
     * Sets as finished the predacao sequence
     */
    public void setRiaFormosaIntertidalArenosoAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_RIAFORMOSA_INTERTIDALARENOSO,
                true
        ).apply();
    }

    /**
     * Returns true if the User already completed the Mares sequence
     *
     * @return
     */
    public boolean isMaresFinished() {
        return sharedPreferences.getBoolean(
                SHAREDPREF_KEY_FINISHED_MARES,
                false
        );
    }

    /**
     * Sets as finished the Mares sequence
     */
    public void setMaresAsFinished() {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_FINISHED_MARES,
                true
        ).apply();
    }

    // GENERAL INFO

    /**
     * Returns true if the User already completed the OnBoarding sequence
     *
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
     *
     * @param onBoarding
     */
    public void setOnBoarding(boolean onBoarding) {
        sharedPreferences.edit().putBoolean(
                SHAREDPREF_KEY_ONBOARDING,
                onBoarding
        ).apply();
    }

    /**
     * 0 - Avencas
     * 1 - Ria Formosa
     *
     * @return
     */
    public int getAvencasOrRiaFormosa() {
        return sharedPreferences.getInt(
                SHAREDPREF_KEY_AVENCAS_RIAFORMOSA,
                0
        );
    }

    /**
     * 0 - Avencas
     * 1 - Ria Formosa
     *
     * @param zona
     */
    public void setAvencasOrRiaFormosa(int zona) {
        sharedPreferences.edit().putInt(
                SHAREDPREF_KEY_AVENCAS_RIAFORMOSA,
                zona
        ).apply();
    }

    /**
     * 0 - Change to Avencas
     * 1 - Change to Ria Formosa
     *
     * @return
     */
    public int getChangeToAvencasOrRiaFormosa() {
        return sharedPreferences.getInt(
                SHAREDPREF_KEY_AVENCAS_RIAFORMOSA_TO_CHANGE,
                -1
        );
    }

    /**
     * 0 - Change to Avencas
     * 1 - Change to Ria Formosa
     *
     * @param zonaToChange
     */
    public void setChangeToAvencasOrRiaFormosa(int zonaToChange) {
        sharedPreferences.edit().putInt(
                SHAREDPREF_KEY_AVENCAS_RIAFORMOSA_TO_CHANGE,
                zonaToChange
        ).apply();
    }

    public void deleteChangeToAvencasOrRiaFormosa() {
        sharedPreferences.edit().remove(SHAREDPREF_KEY_AVENCAS_RIAFORMOSA_TO_CHANGE).apply();
    }

    /**
     * Return type of User.
     * 0 - Aluno
     * 1 - Professor
     * 2 - Explorador
     *
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
     *
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
     *
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
     *
     * @param nome
     */
    public void setNome(String nome) {
        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_NOME,
                nome
        ).apply();
    }

    /**
     * Returns the User's name written in the Shared Preferences
     *
     * @return
     */
    public String getNomeUUID() {
        return sharedPreferences.getString(
                SHAREDPREF_KEY_NOME_UUID,
                ""
        );
    }

    /**
     * Inserts the User's name into Shared Preferences
     *
     * @param nome
     */
    public void setNomeUUID(String nome) {
        UUID uuid = UUID.randomUUID();
        String nomeUUID = nome + uuid.toString();


        sharedPreferences.edit().putString(
                SHAREDPREF_KEY_NOME_UUID,
                nomeUUID
        ).apply();
    }

    /**
     * Returns the User's school name written in the Shared Preferences
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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

    // Ria Formosa Transeptos
    public void updateAvistamentoTranseptosRiaFormosa(AvistamentoTranseptosRiaFormosa avistamentoTranseptosRiaFormosa) {
        new UpdateAvistamentoTranseptosRiaFormosaAsyncTask(avistamentoTranseptosRiaFormosaDao).execute(avistamentoTranseptosRiaFormosa);
    }

    public void deleteAllAvistamentoTranseptosRiaFormosa() {
        new DeleteAllAvistamentoTranseptosRiaFormosaAsyncTask(avistamentoTranseptosRiaFormosaDao).execute();
    }

    public LiveData<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(int idAvistamento) {
        return avistamentoTranseptosRiaFormosaDao.getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(idAvistamento);
    }

    public LiveData<List<AvistamentoTranseptosRiaFormosa>> getAllAvistamentoTranseptosRiaFormosa() {
        return allAvistamentoTranseptosRiaFormosa;
    }

    public LiveData<List<EspecieRiaFormosaTranseptosInstancias>> getAllEspecieRiaFormosaTranseptosInstancias() {
        return allEspecieRiaFormosaTranseptosInstancias;
    }

    private static class DeleteAllAvistamentoTranseptosRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;

        private DeleteAllAvistamentoTranseptosRiaFormosaAsyncTask(AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao) {
            this.avistamentoTranseptosRiaFormosaDao = avistamentoTranseptosRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoTranseptosRiaFormosaDao.deleteAllAvistamentoTranseptosRiaFormosa();
            return null;
        }
    }

    private static class UpdateAvistamentoTranseptosRiaFormosaAsyncTask extends AsyncTask<AvistamentoTranseptosRiaFormosa, Void, Void> {
        private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;

        private UpdateAvistamentoTranseptosRiaFormosaAsyncTask(AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao) {
            this.avistamentoTranseptosRiaFormosaDao = avistamentoTranseptosRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(AvistamentoTranseptosRiaFormosa... avistamentos) {
            avistamentoTranseptosRiaFormosaDao.update(avistamentos[0]);
            return null;
        }
    }

    public LiveData<List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>> getAllAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias() {
        return allAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
    }

    public void insertAvistamentoTranseptosWithInstanciasRiaFormosa(
            int nrTransepto,
            List<EspecieRiaFormosa> especiesRiaFormosa,
            boolean[] instanciasExpostaPedra,
            boolean[] instanciasInferiorPedra,
            boolean[] instanciasSubstrato,
            String[] photoPathEspecie
    ) {
        new InsertAvistamentoTranseptosWithInstanciasRiaFormosaAsyncTask(
                avistamentoTranseptosRiaFormosaDao,
                nrTransepto,
                especiesRiaFormosa,
                instanciasExpostaPedra,
                instanciasInferiorPedra,
                instanciasSubstrato,
                photoPathEspecie
        ).execute();
    }

    private static class InsertAvistamentoTranseptosWithInstanciasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;
        private int nrTransepto;
        private List<EspecieRiaFormosa> especiesRiaFormosa;
        private boolean[] instanciasExpostaPedra;
        private boolean[] instanciasInferiorPedra;
        private boolean[] instanciasSubstrato;
        private String[] photoPathEspecie;

        private InsertAvistamentoTranseptosWithInstanciasRiaFormosaAsyncTask(
                AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao,
                int nrTransepto,
                List<EspecieRiaFormosa> especiesRiaFormosa,
                boolean[] instanciasExpostaPedra,
                boolean[] instanciasInferiorPedra,
                boolean[] instanciasSubstrato,
                String[] photoPathEspecie
        ) {
            this.avistamentoTranseptosRiaFormosaDao = avistamentoTranseptosRiaFormosaDao;
            this.nrTransepto = nrTransepto;
            this.especiesRiaFormosa = especiesRiaFormosa;
            this.instanciasExpostaPedra = instanciasExpostaPedra;
            this.instanciasInferiorPedra = instanciasInferiorPedra;
            this.instanciasSubstrato = instanciasSubstrato;
            this.photoPathEspecie = photoPathEspecie;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert Avistamento
            avistamentoTranseptosRiaFormosaDao.insertAvistamentoTranseptosRiaFormosa(new AvistamentoTranseptosRiaFormosa(
                    nrTransepto
            ));

            Log.d("DATA_REPOSITORY", "Inserted avistamento");

            // Insert EspecieRiaFormosaTranseptosInstancias
            for (int i = 0; i < especiesRiaFormosa.size(); i++) {
                avistamentoTranseptosRiaFormosaDao.insertEspecieRiaFormosaTranseptosInstancias(new EspecieRiaFormosaTranseptosInstancias(
                        nrTransepto,
                        especiesRiaFormosa.get(i),
                        instanciasExpostaPedra[i],
                        instanciasInferiorPedra[i],
                        instanciasSubstrato[i],
                        photoPathEspecie[i]
                ));

                Log.d("DATA_REPOSITORY", "Inserted EspecieWithInstancia for species " + especiesRiaFormosa.get(i).getNomeComum() + " with value " + instanciasExpostaPedra[i]);
            }

            Log.d("DATA_REPOSITORY", "Finished inserting Avistamento and EspecieWithInstancia");

            return null;
        }
    }

    // Ria Formosa Pocas

    public void updateAvistamentoPocasRiaFormosa(AvistamentoPocasRiaFormosa avistamentoPocasRiaFormosa) {
        new UpdateAvistamentoPocasRiaFormosaAsyncTask(avistamentoPocasRiaFormosaDao).execute(avistamentoPocasRiaFormosa);
    }

    public void deleteAllAvistamentoPocasRiaFormosa() {
        new DeleteAllAvistamentoPocasRiaFormosaAsyncTask(avistamentoPocasRiaFormosaDao).execute();
    }

    public LiveData<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> getAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias(int idAvistamento) {
        return avistamentoPocasRiaFormosaDao.getAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias(idAvistamento);
    }

    public LiveData<List<AvistamentoPocasRiaFormosa>> getAllAvistamentoPocasRiaFormosa() {
        return allAvistamentoPocasRiaFormosa;
    }

    private static class DeleteAllAvistamentoPocasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;

        private DeleteAllAvistamentoPocasRiaFormosaAsyncTask(AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao) {
            this.avistamentoPocasRiaFormosaDao = avistamentoPocasRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoPocasRiaFormosaDao.deleteAllAvistamentoPocasRiaFormosa();
            return null;
        }
    }

    private static class UpdateAvistamentoPocasRiaFormosaAsyncTask extends AsyncTask<AvistamentoPocasRiaFormosa, Void, Void> {
        private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;

        private UpdateAvistamentoPocasRiaFormosaAsyncTask(AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao) {
            this.avistamentoPocasRiaFormosaDao = avistamentoPocasRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(AvistamentoPocasRiaFormosa... avistamentos) {
            avistamentoPocasRiaFormosaDao.update(avistamentos[0]);
            return null;
        }
    }

    public LiveData<List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias>> getAllAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias() {
        return allAvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
    }

    public void insertAvistamentoPocaWithInstanciasRiaFormosa(
            int nrPoca,
            String photoPath,
            List<EspecieRiaFormosa> especiesRiaFormosa,
            boolean[] instancias
    ) {
        new InsertAvistamentoPocaWithInstanciasRiaFormosaAsyncTask(
                avistamentoPocasRiaFormosaDao,
                nrPoca,
                photoPath,
                especiesRiaFormosa,
                instancias
        ).execute();
    }

    private static class InsertAvistamentoPocaWithInstanciasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;
        private int nrPoca;
        private String photoPath;
        private List<EspecieRiaFormosa> especiesRiaFormosa;
        private boolean[] instancias;

        private InsertAvistamentoPocaWithInstanciasRiaFormosaAsyncTask(
                AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao,
                int nrPoca,
                String photoPath,
                List<EspecieRiaFormosa> especiesRiaFormosa,
                boolean[] instancias
        ) {
            this.avistamentoPocasRiaFormosaDao = avistamentoPocasRiaFormosaDao;
            this.nrPoca = nrPoca;
            this.photoPath = photoPath;
            this.especiesRiaFormosa = especiesRiaFormosa;
            this.instancias = instancias;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert Avistamento
            avistamentoPocasRiaFormosaDao.insertAvistamentoPocasRiaFormosa(new AvistamentoPocasRiaFormosa(
                    nrPoca,
                    photoPath
            ));

            Log.d("DATA_REPOSITORY", "Inserted avistamento");

            // Insert EspecieRiaFormosaPocasInstancias
            for (int i = 0; i < especiesRiaFormosa.size(); i++) {
                avistamentoPocasRiaFormosaDao.insertEspecieRiaFormosaPocasInstancias(new EspecieRiaFormosaPocasInstancias(
                        nrPoca,
                        especiesRiaFormosa.get(i),
                        instancias[i]
                ));

                Log.d("DATA_REPOSITORY", "Inserted EspecieWithInstancia for species " + especiesRiaFormosa.get(i).getNomeComum() + " with value " + instancias[i]);
            }

            Log.d("DATA_REPOSITORY", "Finished inserting Avistamento and EspecieWithInstancia");

            return null;
        }
    }

    // Ria Formosa Dunas

    public void updateAvistamentoDunasRiaFormosa(AvistamentoDunasRiaFormosa avistamentoDunasRiaFormosa) {
        new UpdateAvistamentoDunasRiaFormosaAsyncTask(avistamentoDunasRiaFormosaDao).execute(avistamentoDunasRiaFormosa);
    }

    public void deleteAllAvistamentoDunasRiaFormosa() {
        new DeleteAllAvistamentoDunasRiaFormosaAsyncTask(avistamentoDunasRiaFormosaDao).execute();
    }

    public void deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona) {
        new DeleteAvistamentoDunasRiaFormosaAsyncTask(avistamentoDunasRiaFormosaDao, iteracao, zona).execute();
    }

    public LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAvistamentosDunasWithZona(String zona) {
        return avistamentoDunasRiaFormosaDao.getAvistamentosDunasWithZona(zona);
    }

    public LiveData<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(int iteracao, String zona) {
        return avistamentoDunasRiaFormosaDao.getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(iteracao, zona);
    }

    public LiveData<List<AvistamentoDunasRiaFormosa>> getAllAvistamentoDunasRiaFormosa() {
        return allAvistamentoDunasRiaFormosa;
    }

    private static class DeleteAvistamentoDunasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;
        private int iteracao;
        private String zona;

        private DeleteAvistamentoDunasRiaFormosaAsyncTask(AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao, int iteracao, String zona) {
            this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;
            this.iteracao = iteracao;
            this.zona = zona;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoDunasRiaFormosaDao.deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(iteracao, zona);
            return null;
        }
    }

    private static class DeleteAllAvistamentoDunasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;

        private DeleteAllAvistamentoDunasRiaFormosaAsyncTask(AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao) {
            this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoDunasRiaFormosaDao.deleteAllAvistamentoDunasRiaFormosa();
            return null;
        }
    }

    private static class UpdateAvistamentoDunasRiaFormosaAsyncTask extends AsyncTask<AvistamentoDunasRiaFormosa, Void, Void> {
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;

        private UpdateAvistamentoDunasRiaFormosaAsyncTask(AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao) {
            this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;
        }

        @Override
        protected Void doInBackground(AvistamentoDunasRiaFormosa... avistamentos) {
            avistamentoDunasRiaFormosaDao.update(avistamentos[0]);
            return null;
        }
    }

    public LiveData<List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>> getAllAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias() {
        return allAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
    }

    public void insertAvistamentoDunasWithInstanciasRiaFormosa(
            int nrIteracao,
            String zona,
            String photoPath,
            List<EspecieRiaFormosa> especiesRiaFormosa,
            int[] instancias
    ) {
        new InsertAvistamentoDunasWithInstanciasRiaFormosaAsyncTask(
                avistamentoDunasRiaFormosaDao,
                nrIteracao,
                zona,
                photoPath,
                especiesRiaFormosa,
                instancias
        ).execute();
    }

    private static class InsertAvistamentoDunasWithInstanciasRiaFormosaAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;
        private int nrIteracao;
        private String zona;
        private String photoPath;
        private List<EspecieRiaFormosa> especiesRiaFormosa;
        private int[] instancias;

        private InsertAvistamentoDunasWithInstanciasRiaFormosaAsyncTask(
                AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao,
                int nrIteracao,
                String zona,
                String photoPath,
                List<EspecieRiaFormosa> especiesRiaFormosa,
                int[] instancias
        ) {
            this.avistamentoDunasRiaFormosaDao = avistamentoDunasRiaFormosaDao;
            this.nrIteracao = nrIteracao;
            this.zona = zona;
            this.photoPath = photoPath;
            this.especiesRiaFormosa = especiesRiaFormosa;
            this.instancias = instancias;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert Avistamento
            long insertedIdAvistamento = avistamentoDunasRiaFormosaDao.insertAvistamentoDunasRiaFormosa(new AvistamentoDunasRiaFormosa(
                    nrIteracao,
                    zona,
                    photoPath
            ));

            Log.d("DATA_REPOSITORY", "Inserted avistamento");

            // Insert EspecieRiaFormosaDunasInstancias
            for (int i = 0; i < especiesRiaFormosa.size(); i++) {
                avistamentoDunasRiaFormosaDao.insertEspecieRiaFormosaDunasInstancias(new EspecieRiaFormosaDunasInstancias(
                        (int) insertedIdAvistamento,
                        especiesRiaFormosa.get(i),
                        instancias[i]
                ));

                Log.d("DATA_REPOSITORY", "Inserted EspecieWithInstancia for species " + especiesRiaFormosa.get(i).getNomeComum() + " with value " + instancias[i]);
            }

            Log.d("DATA_REPOSITORY", "Finished inserting Avistamento and EspecieWithInstancia");

            return null;
        }
    }

    // Avencas Zonacao

    public void updateAvistamentoZonacaoAvencas(AvistamentoZonacaoAvencas avistamentoZonacaoAvencas) {
        new UpdateAvistamentoZonacaoAvencasAsyncTask(avistamentoZonacaoAvencasDao).execute(avistamentoZonacaoAvencas);
    }

    public void deleteAllAvistamentoZonacaoAvencas() {
        new DeleteAllAvistamentoZonacaoAvencasAsyncTask(avistamentoZonacaoAvencasDao).execute();
    }

    public void deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona) {
        new DeleteAvistamentoZonacaoAvencasAsyncTask(avistamentoZonacaoAvencasDao, iteracao, zona).execute();
    }

    public LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAvistamentosZonacaoWithZona(String zona) {
        return avistamentoZonacaoAvencasDao.getAvistamentosZonacaoWithZona(zona);
    }

    public LiveData<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias> getAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(int iteracao, String zona) {
        return avistamentoZonacaoAvencasDao.getAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(iteracao, zona);
    }

    public LiveData<List<AvistamentoZonacaoAvencas>> getAllAvistamentoZonacaoAvencas() {
        return allAvistamentoZonacaoAvencas;
    }

    private static class DeleteAvistamentoZonacaoAvencasAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;
        private int iteracao;
        private String zona;

        private DeleteAvistamentoZonacaoAvencasAsyncTask(AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao, int iteracao, String zona) {
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
            this.iteracao = iteracao;
            this.zona = zona;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoZonacaoAvencasDao.deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(iteracao, zona);
            return null;
        }
    }

    private static class DeleteAllAvistamentoZonacaoAvencasAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

        private DeleteAllAvistamentoZonacaoAvencasAsyncTask(AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao) {
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoZonacaoAvencasDao.deleteAllAvistamentoZonacaoAvencas();
            return null;
        }
    }

    private static class UpdateAvistamentoZonacaoAvencasAsyncTask extends AsyncTask<AvistamentoZonacaoAvencas, Void, Void> {
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

        private UpdateAvistamentoZonacaoAvencasAsyncTask(AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao) {
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
        }

        @Override
        protected Void doInBackground(AvistamentoZonacaoAvencas... avistamentos) {
            avistamentoZonacaoAvencasDao.update(avistamentos[0]);
            return null;
        }
    }

    public LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias() {
        return allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
    }

    public void insertAvistamentoZonacaoWithInstanciasAvencas(
            int nrIteracao,
            String zona,
            String photoPath,
            List<EspecieAvencas> especiesAvencas,
            int[] instancias
    ) {
        new InsertAvistamentoZonacaoWithInstanciasAvencasAsyncTask(
                avistamentoZonacaoAvencasDao,
                nrIteracao,
                zona,
                photoPath,
                especiesAvencas,
                instancias
        ).execute();
    }

    private static class InsertAvistamentoZonacaoWithInstanciasAvencasAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;
        private int nrIteracao;
        private String zona;
        private String photoPath;
        private List<EspecieAvencas> especiesAvencas;
        private int[] instancias;

        private InsertAvistamentoZonacaoWithInstanciasAvencasAsyncTask(
                AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao,
                int nrIteracao,
                String zona,
                String photoPath,
                List<EspecieAvencas> especiesAvencas,
                int[] instancias
        ) {
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
            this.nrIteracao = nrIteracao;
            this.zona = zona;
            this.photoPath = photoPath;
            this.especiesAvencas = especiesAvencas;
            this.instancias = instancias;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert Avistamento
            long insertedIdAvistamento = avistamentoZonacaoAvencasDao.insertAvistamentoZonacaoAvencas(new AvistamentoZonacaoAvencas(
                    nrIteracao,
                    zona,
                    photoPath
            ));

            Log.d("DATA_REPOSITORY", "Inserted avistamento");

            // Insert EspecieAvencasZonacaoInstancias
            for (int i = 0; i < especiesAvencas.size(); i++) {
                avistamentoZonacaoAvencasDao.insertEspecieAvencasZonacaoInstancias(new EspecieAvencasZonacaoInstancias(
                        (int) insertedIdAvistamento,
                        especiesAvencas.get(i),
                        instancias[i]
                ));

                Log.d("DATA_REPOSITORY", "Inserted EspecieWithInstancia for species " + especiesAvencas.get(i).getNomeComum() + " with value " + instancias[i]);
            }

            Log.d("DATA_REPOSITORY", "Finished inserting Avistamento and EspecieWithInstancia");

            return null;
        }
    }

    // Avencas Pocas

    public void updateAvistamentoPocasAvencas(AvistamentoPocasAvencas avistamentoPocasAvencas) {
        new UpdateAvistamentoPocasAvencasAsyncTask(avistamentoPocasAvencasDao).execute(avistamentoPocasAvencas);
    }

    public void deleteAllAvistamentoPocasAvencas() {
        new DeleteAllAvistamentoPocasAvencasAsyncTask(avistamentoPocasAvencasDao).execute();
    }

    public LiveData<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias> getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(int idAvistamento) {
        return avistamentoPocasAvencasDao.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(idAvistamento);
    }

    public LiveData<List<AvistamentoPocasAvencas>> getAllAvistamentoPocasAvencas() {
        return allAvistamentoPocasAvencas;
    }

    private static class DeleteAllAvistamentoPocasAvencasAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;

        private DeleteAllAvistamentoPocasAvencasAsyncTask(AvistamentoPocasAvencasDao avistamentoPocasAvencasDao) {
            this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avistamentoPocasAvencasDao.deleteAllAvistamentoPocasAvencas();
            return null;
        }
    }

    private static class UpdateAvistamentoPocasAvencasAsyncTask extends AsyncTask<AvistamentoPocasAvencas, Void, Void> {
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;

        private UpdateAvistamentoPocasAvencasAsyncTask(AvistamentoPocasAvencasDao avistamentoPocasAvencasDao) {
            this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
        }

        @Override
        protected Void doInBackground(AvistamentoPocasAvencas... avistamentos) {
            avistamentoPocasAvencasDao.update(avistamentos[0]);
            return null;
        }
    }

    public LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias() {
        return allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
    }

    public void insertAvistamentoPocaWithInstanciasAvencas(
            int nrPoca,
            String tipoFundo,
            int profundidadeValue,
            String profundidadeUnit,
            int areaSuperficieValue,
            String areaSuperficieUnit,
            String photoPath,
            List<EspecieAvencas> especiesAvencas,
            int[] instancias
    ) {
        new InsertAvistamentoPocaWithInstanciasAvencasAsyncTask(
                avistamentoPocasAvencasDao,
                nrPoca,
                tipoFundo,
                profundidadeValue,
                profundidadeUnit,
                areaSuperficieValue,
                areaSuperficieUnit,
                photoPath,
                especiesAvencas,
                instancias
        ).execute();
    }

    private static class InsertAvistamentoPocaWithInstanciasAvencasAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
        private int nrPoca;
        private String tipoFundo;
        private int profundidadeValue;
        private String profundidadeUnit;
        private int areaSuperficieValue;
        private String areaSuperficieUnit;
        private String photoPath;
        private List<EspecieAvencas> especiesAvencas;
        private int[] instancias;

        private InsertAvistamentoPocaWithInstanciasAvencasAsyncTask(
                AvistamentoPocasAvencasDao avistamentoPocasAvencasDao,
                int nrPoca,
                String tipoFundo,
                int profundidadeValue,
                String profundidadeUnit,
                int areaSuperficieValue,
                String areaSuperficieUnit,
                String photoPath,
                List<EspecieAvencas> especiesAvencas,
                int[] instancias
        ) {
            this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
            this.nrPoca = nrPoca;
            this.tipoFundo = tipoFundo;
            this.profundidadeValue = profundidadeValue;
            this.profundidadeUnit = profundidadeUnit;
            this.areaSuperficieValue = areaSuperficieValue;
            this.areaSuperficieUnit = areaSuperficieUnit;
            this.photoPath = photoPath;
            this.especiesAvencas = especiesAvencas;
            this.instancias = instancias;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert Avistamento
            avistamentoPocasAvencasDao.insertAvistamentoPocasAvencas(new AvistamentoPocasAvencas(
                    nrPoca,
                    tipoFundo,
                    profundidadeValue,
                    profundidadeUnit,
                    areaSuperficieValue,
                    areaSuperficieUnit,
                    photoPath
            ));

            Log.d("DATA_REPOSITORY", "Inserted avistamento");

            // Insert EspecieAvencasPocasInstancias
            for (int i = 0; i < especiesAvencas.size(); i++) {
                avistamentoPocasAvencasDao.insertEspecieAvencasPocasInstancias(new EspecieAvencasPocasInstancias(
                        nrPoca,
                        especiesAvencas.get(i),
                        instancias[i]
                ));

                Log.d("DATA_REPOSITORY", "Inserted EspecieWithInstancia for species " + especiesAvencas.get(i).getNomeComum() + " with value " + instancias[i]);
            }

            Log.d("DATA_REPOSITORY", "Finished inserting Avistamento and EspecieWithInstancia");

            return null;
        }
    }

    // --------------------------- WIFI P2P -------------------------------

    public void insertWifiP2pConnection(WifiP2pConnection wifiP2pConnection) {
        new InsertWifiP2pConnectionAsyncTask(wifiP2pConnectionDao).execute(wifiP2pConnection);
    }

    public void updateWifiP2pConnection(WifiP2pConnection wifiP2pConnection) {
        new UpdateWifiP2pConnectionAsyncTask(wifiP2pConnectionDao).execute(wifiP2pConnection);
    }

    public void deleteWifiP2pConnection(WifiP2pConnection wifiP2pConnection) {
        new DeleteWifiP2pConnectionAsyncTask(wifiP2pConnectionDao).execute(wifiP2pConnection);
    }

    public void deleteAllWifiP2pConnection(WifiP2pConnection wifiP2pConnection) {
        new DeleteAllWifiP2pConnectionAsyncTask(wifiP2pConnectionDao).execute();
    }

    public WifiP2pConnection getLastConnectionWithDevice(String deviceAddress) {
        List<WifiP2pConnection> list = wifiP2pConnectionDao.getLastConnectionWithDevice(deviceAddress);

        if (list == null) {
            return null;
        } else if (list.isEmpty()) {
            return null;
        }

        return wifiP2pConnectionDao.getLastConnectionWithDevice(deviceAddress).get(0);
    }

    private static class InsertWifiP2pConnectionAsyncTask extends AsyncTask<WifiP2pConnection, Void, Void> {
        private WifiP2pConnectionDao wifiP2pConnectionDao;

        private InsertWifiP2pConnectionAsyncTask(WifiP2pConnectionDao wifiP2pConnectionDao) {
            this.wifiP2pConnectionDao = wifiP2pConnectionDao;
        }

        @Override
        protected Void doInBackground(WifiP2pConnection... wifiP2pConnections) {
            wifiP2pConnectionDao.insert(wifiP2pConnections[0]);
            return null;
        }
    }

    private static class UpdateWifiP2pConnectionAsyncTask extends AsyncTask<WifiP2pConnection, Void, Void> {
        private WifiP2pConnectionDao wifiP2pConnectionDao;

        private UpdateWifiP2pConnectionAsyncTask(WifiP2pConnectionDao wifiP2pConnectionDao) {
            this.wifiP2pConnectionDao = wifiP2pConnectionDao;
        }

        @Override
        protected Void doInBackground(WifiP2pConnection... wifiP2pConnections) {
            wifiP2pConnectionDao.update(wifiP2pConnections[0]);
            return null;
        }
    }

    private static class DeleteWifiP2pConnectionAsyncTask extends AsyncTask<WifiP2pConnection, Void, Void> {
        private WifiP2pConnectionDao wifiP2pConnectionDao;

        private DeleteWifiP2pConnectionAsyncTask(WifiP2pConnectionDao wifiP2pConnectionDao) {
            this.wifiP2pConnectionDao = wifiP2pConnectionDao;
        }

        @Override
        protected Void doInBackground(WifiP2pConnection... wifiP2pConnections) {
            wifiP2pConnectionDao.delete(wifiP2pConnections[0]);
            return null;
        }
    }

    private static class DeleteAllWifiP2pConnectionAsyncTask extends AsyncTask<Void, Void, Void> {
        private WifiP2pConnectionDao wifiP2pConnectionDao;

        private DeleteAllWifiP2pConnectionAsyncTask(WifiP2pConnectionDao wifiP2pConnectionDao) {
            this.wifiP2pConnectionDao = wifiP2pConnectionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wifiP2pConnectionDao.deleteAllRecords();
            return null;
        }
    }

    // --------------------------- ARTEFACTOS -----------------------------

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
     *
     * @return
     */
    public LiveData<List<Artefacto>> getAllArtefactos() {
        return allArtefactos;
    }

    /**
     * WARNING: Make sure this is executed in the background thread
     * @return
     */
    public List<Artefacto> getAllArtefactosAlt() {
        return artefactoDao.getAllArtefactos();
    }

    public LiveData<List<String>> getAllArtefactoIdString() {
        return allArtefactoIdString;
    }

    public List<Artefacto> getArtefactoFromTo(long from, long to) {
        return artefactoDao.getArtefactoFromTo(from, to);
    }

    // ------------------------------ ARTEFACTOS TURMA ----------------------

    public LiveData<List<ArtefactoTurma>> getAllArtefactosTurma() {
        return allArtefactosTurma;
    }

    public List<ArtefactoTurma> getAllArtefactosTurmaAlt() {
        return artefactoTurmaDao.getAllAlt();
    }

    public List<ArtefactoTurma> getArtefactoTurmaFromTo(long from, long to) {
        return artefactoTurmaDao.getArtefactoTurmaFromTo(from, to);
    }

    public void insertArtefactoTurma(ArtefactoTurma artefactoTurma) {
        new InsertArtefactoTurmaAsyncTask(artefactoTurmaDao).execute(artefactoTurma);
    }

    public void updateArtefactoTurma(ArtefactoTurma artefactoTurma) {
        new UpdateArtefactoTurmaAsyncTask(artefactoTurmaDao).execute(artefactoTurma);
    }

    public void deleteArtefactoTurma(ArtefactoTurma artefactoTurma) {
        new DeleteArtefactoTurmaAsyncTask(artefactoTurmaDao).execute(artefactoTurma);
    }

    public void deleteAllArtefactoTurma() {
        new DeleteAllArtefactoTurmaAsyncTask(artefactoTurmaDao).execute();
    }

    private static class InsertArtefactoTurmaAsyncTask extends AsyncTask<ArtefactoTurma, Void, Void> {
        private ArtefactoTurmaDao artefactoTurmaDao;

        private InsertArtefactoTurmaAsyncTask(ArtefactoTurmaDao artefactoTurmaDao) {
            this.artefactoTurmaDao = artefactoTurmaDao;
        }

        @Override
        protected Void doInBackground(ArtefactoTurma... artefactoTurmas) {
            artefactoTurmaDao.insert(artefactoTurmas[0]);
            return null;
        }
    }

    private static class UpdateArtefactoTurmaAsyncTask extends AsyncTask<ArtefactoTurma, Void, Void> {
        private ArtefactoTurmaDao artefactoTurmaDao;

        private UpdateArtefactoTurmaAsyncTask(ArtefactoTurmaDao artefactoTurmaDao) {
            this.artefactoTurmaDao = artefactoTurmaDao;
        }

        @Override
        protected Void doInBackground(ArtefactoTurma... artefactoTurmas) {
            artefactoTurmaDao.update(artefactoTurmas[0]);
            return null;
        }
    }

    private static class DeleteArtefactoTurmaAsyncTask extends AsyncTask<ArtefactoTurma, Void, Void> {
        private ArtefactoTurmaDao artefactoTurmaDao;

        private DeleteArtefactoTurmaAsyncTask(ArtefactoTurmaDao artefactoTurmaDao) {
            this.artefactoTurmaDao = artefactoTurmaDao;
        }

        @Override
        protected Void doInBackground(ArtefactoTurma... artefactoTurmas) {
            artefactoTurmaDao.delete(artefactoTurmas[0]);
            return null;
        }
    }

    private static class DeleteAllArtefactoTurmaAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoTurmaDao artefactoTurmaDao;

        private DeleteAllArtefactoTurmaAsyncTask(ArtefactoTurmaDao artefactoTurmaDao) {
            this.artefactoTurmaDao = artefactoTurmaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            artefactoTurmaDao.deleteAll();
            return null;
        }
    }

    // ------------------------------ ESPECIES ------------------------------

    public LiveData<List<EspecieRiaFormosa>> getAllEspecieRiaFormosa() {
        return allEspecieRiaFormosa;
    }

    public LiveData<List<EspecieRiaFormosa>> getFilteredRiaFormosaEspecies(SupportSQLiteQuery query) {
        return especieRiaFormosaDao.getWithQuery(query);
    }

    /**
     * Return all species from Avencas.
     * Methods that return LiveData don't need AsyncTasks bc Room already does it behind the scenes
     *
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
