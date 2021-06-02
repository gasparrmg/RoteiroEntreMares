package com.lasige.roteiroentremares.util;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionsUtils {
    public static final int PERMISSIONS_REQUEST_CODE = 5;
    public static final int PERMISSIONS_CAMERA_REQUEST_CODE = 101;
    public static final int PERMISSIONS_MICROPHONE_REQUEST_CODE = 102;
    public static final int PERMISSIONS_GALLERY_REQUEST_CODE = 103;
    public static final int PERMISSIONS_LOCATION_REQUEST_CODE = 105;

    private static final String[] permissionsNeededOld = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String[] permissionsNeededNew = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    public static final String[] locationPermissionsNeededOld = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final String[] locationPermissionsNeededNew = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    private static final String[] cameraPermissionsNeeded = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] microphonePermissionsNeeded = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] galleryPermissionsNeeded = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static String[] getPermissionList() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return permissionsNeededOld;
        } else {
            return permissionsNeededNew;
        }
    }

    public static String[] getLocationPermissionList() {
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return locationPermissionsNeededOld;
        } else {
            return locationPermissionsNeededNew;
        }*/

        return locationPermissionsNeededOld;
    }

    public static String[] getCameraPermissionList() {
        return cameraPermissionsNeeded;
    }

    public static String[] getMicrophonePermissionList() {
        return microphonePermissionsNeeded;
    }

    public static String[] getGalleryPermissionList() {
        return galleryPermissionsNeeded;
    }
}
