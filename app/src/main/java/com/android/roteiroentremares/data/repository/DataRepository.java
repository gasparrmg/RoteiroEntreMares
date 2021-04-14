package com.android.roteiroentremares.data.repository;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.dao.AvistamentoPocasAvencasDao;
import com.android.roteiroentremares.data.dao.AvistamentoZonacaoAvencasDao;
import com.android.roteiroentremares.data.dao.EspecieAvencasDao;
import com.android.roteiroentremares.data.dao.EspecieRiaFormosaDao;
import com.android.roteiroentremares.data.database.RoteiroDatabase;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {
    private static final String SHAREDPREF_KEY_AVENCAS_RIAFORMOSA = "key_avencas_riaformosa";
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

    // Ria Formosa

    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_NAOFIQUESPORAQUI = "key_finished_riaformosa_naofiquesporaqui";
    private static final String SHAREDPREF_KEY_FINISHED_RIAFORMOSA_PRADARIASMARINHAS = "key_finished_riaformosa_pradariasmarinhas";

    private SharedPreferences sharedPreferences;
    private ArtefactoDao artefactoDao;
    private EspecieAvencasDao especieAvencasDao;
    private EspecieRiaFormosaDao especieRiaFormosaDao;
    private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
    private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

    private LiveData<List<Artefacto>> allArtefactos;
    private LiveData<List<EspecieAvencas>> allEspecieAvencas;
    private LiveData<List<EspecieRiaFormosa>> allEspecieRiaFormosa;

    private LiveData<List<AvistamentoPocasAvencas>> allAvistamentoPocasAvencas;
    private LiveData<List<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>> allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;

    private LiveData<List<AvistamentoZonacaoAvencas>> allAvistamentoZonacaoAvencas;
    private LiveData<List<AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias>> allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;


    @Inject
    public DataRepository (
            SharedPreferences sharedPreferences,
            ArtefactoDao artefactoDao,
            EspecieAvencasDao especieAvencasDao,
            EspecieRiaFormosaDao especieRiaFormosaDao,
            AvistamentoPocasAvencasDao avistamentoPocasAvencasDao,
            AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao
    ) {
        this.sharedPreferences = sharedPreferences;
        this.artefactoDao = artefactoDao;
        this.especieAvencasDao = especieAvencasDao;
        this.especieRiaFormosaDao = especieRiaFormosaDao;
        this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
        this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;

        allArtefactos = artefactoDao.getAll();

        allEspecieAvencas = especieAvencasDao.getAll();
        allEspecieRiaFormosa = especieRiaFormosaDao.getAll();

        allAvistamentoPocasAvencas = avistamentoPocasAvencasDao.getAllAvistamentoPocasAvencas();
        allAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias = avistamentoPocasAvencasDao.getAllAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias();

        allAvistamentoZonacaoAvencas = avistamentoZonacaoAvencasDao.getAllAvistamentoZonacaoAvencas();
        allAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias = avistamentoZonacaoAvencasDao.getAllAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias();
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
     * Returns true if the User already completed the Biodiversidade -> Microhabitats sequence
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
     * 0 - Avencas
     * 1 - Ria Formosa
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
     * @param zona
     */
    public void setAvencasOrRiaFormosa(int zona) {
        sharedPreferences.edit().putInt(
                SHAREDPREF_KEY_AVENCAS_RIAFORMOSA,
                zona
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

    public LiveData<List<EspecieRiaFormosa>> getAllEspecieRiaFormosa() {
        return allEspecieRiaFormosa;
    }

    public LiveData<List<EspecieRiaFormosa>> getFilteredRiaFormosaEspecies(SupportSQLiteQuery query) {
        return especieRiaFormosaDao.getWithQuery(query);
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
