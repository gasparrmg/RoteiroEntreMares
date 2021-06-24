package com.lasige.roteiroentremares.data.database;


import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
import com.lasige.roteiroentremares.util.GuiaDeCampoContent;

import javax.inject.Inject;
import javax.inject.Provider;

@Database(entities = {
        Artefacto.class,
        ArtefactoTurma.class,
        WifiP2pConnection.class,
        EspecieAvencas.class,
        EspecieRiaFormosa.class,
        AvistamentoPocasAvencas.class,
        AvistamentoPocasRiaFormosa.class,
        AvistamentoTranseptosRiaFormosa.class,
        EspecieAvencasPocasInstancias.class,
        EspecieRiaFormosaPocasInstancias.class,
        EspecieRiaFormosaTranseptosInstancias.class,
        AvistamentoZonacaoAvencas.class,
        EspecieAvencasZonacaoInstancias.class,
        EspecieRiaFormosaDunasInstancias.class,
        AvistamentoDunasRiaFormosa.class
}, version = 2)
public abstract class RoteiroDatabase extends RoomDatabase {

    public abstract ArtefactoDao artefactoDao();

    public abstract ArtefactoTurmaDao artefactoTurmaDao();

    public abstract WifiP2pConnectionDao wifiP2pConnectionDao();

    public abstract EspecieAvencasDao especieAvencasDao();

    public abstract AvistamentoPocasAvencasDao avistamentoPocasAvencasDao();

    public abstract AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao();

    public abstract AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao();

    public abstract AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao();

    public abstract AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao();

    public abstract EspecieRiaFormosaDao especieRiaFormosaDao();

    /*public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("DATABASE", "Migration '1 -> 2' is being executed.");
        }
    };*/

    public static class Callback extends RoomDatabase.Callback {
        private Provider<RoteiroDatabase> roteiroDatabaseProvider;

        @Inject
        public Callback(
                Provider<RoteiroDatabase> roteiroDatabaseProvider
        ) {
            this.roteiroDatabaseProvider = roteiroDatabaseProvider;
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            Log.d("DATABASE", "Database was opened");
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);

            Log.d("DATABASE", "Database was destructed.");

            ArtefactoDao artefactoDao = roteiroDatabaseProvider.get().artefactoDao();
            ArtefactoTurmaDao artefactoTurmaDao = roteiroDatabaseProvider.get().artefactoTurmaDao();
            WifiP2pConnectionDao wifiP2pConnectionDao = roteiroDatabaseProvider.get().wifiP2pConnectionDao();
            EspecieAvencasDao especieAvencasDao = roteiroDatabaseProvider.get().especieAvencasDao();
            EspecieRiaFormosaDao especieRiaFormosaDao = roteiroDatabaseProvider.get().especieRiaFormosaDao();
            AvistamentoPocasAvencasDao avistamentoPocasAvencasDao = roteiroDatabaseProvider.get().avistamentoPocasAvencasDao();
            AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoPocasRiaFormosaDao();
            AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoTranseptosRiaFormosaDao();
            AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao = roteiroDatabaseProvider.get().avistamentoZonacaoAvencasDao();
            AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoDunasRiaFormosaDao();
            new PopulateDbAsyncTask(artefactoDao, artefactoTurmaDao, wifiP2pConnectionDao, especieAvencasDao, especieRiaFormosaDao, avistamentoPocasAvencasDao, avistamentoPocasRiaFormosaDao, avistamentoTranseptosRiaFormosaDao, avistamentoZonacaoAvencasDao, avistamentoDunasRiaFormosaDao).execute();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            ArtefactoDao artefactoDao = roteiroDatabaseProvider.get().artefactoDao();
            ArtefactoTurmaDao artefactoTurmaDao = roteiroDatabaseProvider.get().artefactoTurmaDao();
            WifiP2pConnectionDao wifiP2pConnectionDao = roteiroDatabaseProvider.get().wifiP2pConnectionDao();
            EspecieAvencasDao especieAvencasDao = roteiroDatabaseProvider.get().especieAvencasDao();
            EspecieRiaFormosaDao especieRiaFormosaDao = roteiroDatabaseProvider.get().especieRiaFormosaDao();
            AvistamentoPocasAvencasDao avistamentoPocasAvencasDao = roteiroDatabaseProvider.get().avistamentoPocasAvencasDao();
            AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoPocasRiaFormosaDao();
            AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoTranseptosRiaFormosaDao();
            AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao = roteiroDatabaseProvider.get().avistamentoZonacaoAvencasDao();
            AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao = roteiroDatabaseProvider.get().avistamentoDunasRiaFormosaDao();
            new PopulateDbAsyncTask(artefactoDao, artefactoTurmaDao, wifiP2pConnectionDao, especieAvencasDao, especieRiaFormosaDao, avistamentoPocasAvencasDao, avistamentoPocasRiaFormosaDao, avistamentoTranseptosRiaFormosaDao, avistamentoZonacaoAvencasDao, avistamentoDunasRiaFormosaDao).execute();
        }
    }

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoDao artefactoDao;
        private ArtefactoTurmaDao artefactoTurmaDao;
        private WifiP2pConnectionDao wifiP2pConnectionDao;
        private EspecieAvencasDao especieAvencasDao;
        private EspecieRiaFormosaDao especieRiaFormosaDao;
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
        private AvistamentoPocasRiaFormosaDao avistamentoPocasRiaFormosaDao;
        private AvistamentoTranseptosRiaFormosaDao avistamentoTranseptosRiaFormosaDao;
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;
        private AvistamentoDunasRiaFormosaDao avistamentoDunasRiaFormosaDao;

        long startTime, endTime;

        public PopulateDbAsyncTask(
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
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Add data when the Database is created
            // Insert all necessary data into the DB

            // Guia de Campo

            for (EspecieAvencas especieAvencas : GuiaDeCampoContent.dataEspeciesAvencas) {
                especieAvencasDao.insert(especieAvencas);
            }

            for (EspecieRiaFormosa especieRiaFormosa : GuiaDeCampoContent.dataEspeciesRiaFormosa) {
                especieRiaFormosaDao.insert(especieRiaFormosa);
            }

            Log.d("ROTEIRO_DATABASE", "Guia de Campo data inserted.");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            endTime = System.currentTimeMillis();
            Log.d("ROTEIRO_DATABASE", "Data inserted in " + (endTime - startTime) + "ms");
        }
    }
}
