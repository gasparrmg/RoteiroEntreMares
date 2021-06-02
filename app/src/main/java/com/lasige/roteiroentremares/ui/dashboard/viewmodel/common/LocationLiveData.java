package com.lasige.roteiroentremares.ui.dashboard.viewmodel.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.lasige.roteiroentremares.data.model.LocationDetails;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationLiveData extends LiveData<LocationDetails> {

    public static LocationRequest locationRequest;

    private FusedLocationProviderClient fusedLocationProviderClient;

    public LocationLiveData(Context context) {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    @SuppressLint("MissingPermission")
    protected void onActive() {
        Log.d("LocationLiveData", "onActive()");

        super.onActive();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null) {
                    return;
                }
                setLocationData(location);
            }
        });
        startLocationUpdates();
    }

    @Override
    protected void onInactive() {
        Log.d("LocationLiveData", "onInactive()");

        super.onInactive();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void setLocationData(Location location) {
        LocationDetails locationDetails = new LocationDetails(location.getLatitude(), location.getLongitude());

        setValue(locationDetails);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            super.onLocationResult(locationResult);

            for (Location location: locationResult.getLocations()) {
                setLocationData(location);
            }
        }
    };
}
