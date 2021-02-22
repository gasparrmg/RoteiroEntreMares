package com.android.roteiroentremares.data.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.dao.EspecieAvencasDao;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.util.GuiaDeCampoContent;

import javax.inject.Inject;
import javax.inject.Provider;

@Database(entities = {Artefacto.class, EspecieAvencas.class}, version = 1)
public abstract class RoteiroDatabase extends RoomDatabase {

    public abstract ArtefactoDao artefactoDao();
    public abstract EspecieAvencasDao especieAvencasDao();

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
            new PopulateDbAsyncTask(artefactoDao, especieAvencasDao).execute();
        }
    }

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoDao artefactoDao;
        private EspecieAvencasDao especieAvencasDao;

        long startTime, endTime;

        public PopulateDbAsyncTask(
                ArtefactoDao artefactoDao,
                EspecieAvencasDao especieAvencasDao
        ) {
            this.artefactoDao = artefactoDao;
            this.especieAvencasDao = especieAvencasDao;
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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            endTime= System.currentTimeMillis();
            Log.d("ROTEIRO_DATABASE", "Guia de Campo data inserted in " + (endTime-startTime) + "ms");
        }
    }
}
