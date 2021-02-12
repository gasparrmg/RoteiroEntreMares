package com.android.roteiroentremares.data.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.model.Artefacto;

import javax.inject.Inject;
import javax.inject.Provider;

@Database(entities = {Artefacto.class}, version = 1)
public abstract class RoteiroDatabase extends RoomDatabase {
    public abstract ArtefactoDao artefactoDao();

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
            new PopulateDbAsyncTask(artefactoDao).execute();
        }
    }

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArtefactoDao artefactoDao;

        public PopulateDbAsyncTask(
                ArtefactoDao artefactoDao
        ) {
            this.artefactoDao = artefactoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Add data when the Database is created

            // Insert all necessary data into the DB

            // Guia de Campo

            /*artefactoDao.insert(new Artefacto(
                    "Title One",
                    "This is the content of Title One",
                    0,
                    "",
                    "Today LUL",
                    "420",
                    "69",
                    "ABCDEFGH",
                    false
            ));*/
            return null;
        }
    }
}
