package com.lasige.roteiroentremares.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class PermissionsUtils {
    public static final int PERMISSIONS_REQUEST_CODE = 5;
    public static final int PERMISSIONS_PHOTOS_REQUEST_CODE = 101;
    public static final int PERMISSIONS_VIDEO_REQUEST_CODE = 106;
    public static final int PERMISSIONS_MICROPHONE_REQUEST_CODE = 102;
    public static final int PERMISSIONS_GALLERY_REQUEST_CODE = 103;
    public static final int PERMISSIONS_LOCATION_REQUEST_CODE = 105;

    private static final String[] permissionsNeededOld = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final String[] permissionsNeededNew = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    public static final String[] locationPermissionsNeeded = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String[] photosPermissionsNeeded = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] photosPermissionsNeeded33 = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
    };

    private static final String[] videosPermissionsNeeded = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] videosPermissionsNeeded33 = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    private static final String[] microphonePermissionsNeeded = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] microphonePermissionsNeeded33 = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_AUDIO
    };

    private static final String[] galleryPermissionsNeeded = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] galleryPermissionsNeeded33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    };

    public static String[] getPermissionList() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return permissionsNeededOld;
        } else {
            return permissionsNeededNew;
        }
    }

    public static String[] getLocationPermissionList() {
        return locationPermissionsNeeded;
    }

    /*@Deprecated
    public static String[] getCameraPermissionList() {
        return cameraPermissionsNeeded;
    }*/

    public static String[] getPhotoPermissionList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return photosPermissionsNeeded33;
        }

        return photosPermissionsNeeded;
    }

    public static String[] getVideoPermissionList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return videosPermissionsNeeded33;
        }

        return videosPermissionsNeeded;
    }

    public static String[] getMicrophonePermissionList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return microphonePermissionsNeeded33;
        }

        return microphonePermissionsNeeded;
    }

    public static String[] getGalleryPermissionList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return galleryPermissionsNeeded33;
        }

        return galleryPermissionsNeeded;
    }

    public static String[] getWifiP2pPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return new String[]{Manifest.permission.NEARBY_WIFI_DEVICES};
        }

        return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    }

    public static boolean hasAcceptedWifiP2pPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context,
                    Manifest.permission.NEARBY_WIFI_DEVICES) == PackageManager.PERMISSION_GRANTED;
        }

        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
