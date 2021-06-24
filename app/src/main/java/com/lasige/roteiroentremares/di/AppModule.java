package com.lasige.roteiroentremares.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;

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
import com.lasige.roteiroentremares.data.database.RoteiroDatabase;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;

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
    ArtefactoTurmaDao provideArtefactoTurmaDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.artefactoTurmaDao();
    }

    @Provides
    @Singleton
    WifiP2pConnectionDao provideWifiP2pConnectionDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.wifiP2pConnectionDao();
    }

    @Provides
    @Singleton
    EspecieAvencasDao provideEspecieAvencasDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.especieAvencasDao();
    }

    @Provides
    @Singleton
    AvistamentoPocasAvencasDao provideAvistamentoPocasAvencasDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.avistamentoPocasAvencasDao();
    }

    @Provides
    @Singleton
    AvistamentoPocasRiaFormosaDao provideAvistamentoPocasRiaFormosaDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.avistamentoPocasRiaFormosaDao();
    }

    @Provides
    @Singleton
    AvistamentoTranseptosRiaFormosaDao provideAvistamentoTranseptosRiaFormosaDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.avistamentoTranseptosRiaFormosaDao();
    }

    @Provides
    @Singleton
    AvistamentoZonacaoAvencasDao provideAvistamentoZonacaoAvencasDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.avistamentoZonacaoAvencasDao();
    }

    @Provides
    @Singleton
    EspecieRiaFormosaDao provideEspecieRiaFormosaDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.especieRiaFormosaDao();
    }

    @Provides
    @Singleton
    AvistamentoDunasRiaFormosaDao provideAvistamentoDunasRiaFormosaDao(RoteiroDatabase roteiroDatabase) {
        return roteiroDatabase.avistamentoDunasRiaFormosaDao();
    }
}
