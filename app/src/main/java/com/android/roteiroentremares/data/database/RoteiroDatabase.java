package com.android.roteiroentremares.data.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.dao.AvistamentoPocasAvencasDao;
import com.android.roteiroentremares.data.dao.AvistamentoZonacaoAvencasDao;
import com.android.roteiroentremares.data.dao.EspecieAvencasDao;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.AvistamentoPocasAvencas;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.EspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.EspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.util.GuiaDeCampoContent;

import javax.inject.Inject;
import javax.inject.Provider;

@Database(entities = {
        Artefacto.class,
        EspecieAvencas.class,
        AvistamentoPocasAvencas.class,
        EspecieAvencasPocasInstancias.class,
        AvistamentoZonacaoAvencas.class,
        EspecieAvencasZonacaoInstancias.class
}, version = 1)
public abstract class RoteiroDatabase extends RoomDatabase {

    public abstract ArtefactoDao artefactoDao();

    public abstract EspecieAvencasDao especieAvencasDao();

    public abstract AvistamentoPocasAvencasDao avistamentoPocasAvencasDao();

    public abstract AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao();

    public static class Callback extends RoomDatabase.Callback {
        private Provider<RoteiroDatabase> roteiroDatabaseProvider;

        @Inject
        public Callback(
                Provider<RoteiroDatabase> roteiroDatabaseProvider
        ) {
            this.roteiroDatabaseProvider = roteiroDatabaseProvider;
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            ArtefactoDao artefactoDao = roteiroDatabaseProvider.get().artefactoDao();
            EspecieAvencasDao especieAvencasDao = roteiroDatabaseProvider.get().especieAvencasDao();
            AvistamentoPocasAvencasDao avistamentoPocasAvencasDao = roteiroDatabaseProvider.get().avistamentoPocasAvencasDao();
            AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao = roteiroDatabaseProvider.get().avistamentoZonacaoAvencasDao();
            new PopulateDbAsyncTask(artefactoDao, especieAvencasDao, avistamentoPocasAvencasDao, avistamentoZonacaoAvencasDao).execute();
        }
    }

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoDao artefactoDao;
        private EspecieAvencasDao especieAvencasDao;
        private AvistamentoPocasAvencasDao avistamentoPocasAvencasDao;
        private AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao;

        long startTime, endTime;

        public PopulateDbAsyncTask(
                ArtefactoDao artefactoDao,
                EspecieAvencasDao especieAvencasDao,
                AvistamentoPocasAvencasDao avistamentoPocasAvencasDao,
                AvistamentoZonacaoAvencasDao avistamentoZonacaoAvencasDao
        ) {
            this.artefactoDao = artefactoDao;
            this.especieAvencasDao = especieAvencasDao;
            this.avistamentoPocasAvencasDao = avistamentoPocasAvencasDao;
            this.avistamentoZonacaoAvencasDao = avistamentoZonacaoAvencasDao;
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

            // GUIA DE CAMPO
            for (EspecieAvencas especieAvencas : GuiaDeCampoContent.dataEspeciesAvencas) {
                especieAvencasDao.insert(especieAvencas);
            }

            Log.d("ROTEIRO_DATABASE", "Guia de Campo data inserted.");

            // Biodiversidade -> Poças maré
            /*for (int i = 1; i <= 3; i++) {
                avistamentoPocasAvencasDao.insert(new AvistamentoPocasAvencas(i));
            }*/

            Log.d("ROTEIRO_DATABASE", "Avistamentos Poças Avencas data inserted.");

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
