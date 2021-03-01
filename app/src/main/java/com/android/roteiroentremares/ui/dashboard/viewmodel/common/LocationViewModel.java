package com.android.roteiroentremares.ui.dashboard.viewmodel.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationViewModel extends AndroidViewModel {

    private LocationLiveData location;

    @Inject
    public LocationViewModel(@NonNull Application application) {
        super(application);

        location = new LocationLiveData(application);
    }

    public LocationLiveData getLocation() {
        return location;
    }
}
