package com.android.roteiroentremares.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;

import com.android.roteiroentremares.data.dao.ArtefactoDao;
import com.android.roteiroentremares.data.dao.EspecieAvencasDao;
import com.android.roteiroentremares.data.database.RoteiroDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    RoteiroDatabase provideRoteiroDatabase(@ApplicationContext Context context, RoteiroDatabase.Callback callback) {
        return Room.databaseBuilder(context,
                RoteiroDatabase.class,
                "roteiro_database")
                .fallbackToDestructiveMigration()
                .addCallback(callback)
                .build();
    }

    @Provides
    @Singleton
    ArtefactoDao provideArtefactoDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.artefactoDao();
    }

    @Provides
    @Singleton
    EspecieAvencasDao provideEspecieAvencasDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.especieAvencasDao();
    }
}
