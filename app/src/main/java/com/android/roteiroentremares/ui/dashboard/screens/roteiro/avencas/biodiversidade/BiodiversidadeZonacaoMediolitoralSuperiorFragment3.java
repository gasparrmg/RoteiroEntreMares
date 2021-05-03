package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.LocationDetails;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.common.LocationViewModel;
import com.android.roteiroentremares.util.Constants;
import com.android.roteiroentremares.util.PermissionsUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BiodiversidadeZonacaoMediolitoralSuperiorFragment3 extends Fragment implements EasyPermissions.PermissionCallbacks {

    private final String htmlContent = "Dirige-te agora para o ponto assinalado com as coordenadas GPS: 38.68959,-9.36348.<br><br>Carrega no botão das direções para encontrares o local. Poderás passar para o próximo ecrã assim que te encontrares perto do local.";
    private final String spotCoordinates = "38.68959,-9.36348";
    private final double spotLatitude = 38.68959;
    private final double spotLongitude = -9.36348;

    private final int imageResourceId = R.drawable.img_biodiversidade_zonacao_drone;
    private ImageView imageView;
    private FloatingActionButton fabFullscreen;

    private LocationViewModel locationViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;
    private ExtendedFloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ImageButton buttonDirections;

    private TextToSpeech tts;
    private boolean ttsEnabled;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao_mediolitoral_superior3, container, false);

        ttsEnabled = false;

        initViews(view);
        insertContent();
        setOnClickListeners(view);

        askLocationPermissions();

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Biodiversidade");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        if (tts.isSpeaking()) {
            tts.stop();
        }
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.roteiro_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                htmlContent,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tts_error_message), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonDirections = view.findViewById(R.id.btn_directions);

        imageView = view.findViewById(R.id.imageView);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Zonação",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                "Mediolitoral - Horizonte Superior",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }

    private void setOnClickListeners(View view) {

        imageView.setImageResource(imageResourceId);
        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoMediolitoralSuperiorFragment3_to_biodiversidadeZonacaoMediolitoralSuperiorFragment4);
            }
        });

        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + spotCoordinates;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ttsEnabled = false;
                        Log.e("TEXT2SPEECH", "Language not supported");
                    } else {
                        ttsEnabled = true;
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }

    private void initRequestLocationUpdates() {
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        locationViewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LocationDetails>() {
            @Override
            public void onChanged(LocationDetails locationDetails) {
                float[] results = new float[1];
                Location.distanceBetween(locationDetails.getLatitude(), locationDetails.getLongitude(), spotLatitude, spotLongitude, results);

                // Toast.makeText(getActivity(), "Distance: " + results[0] + " meters", Toast.LENGTH_SHORT).show();

                // TODO: Change this value to 100m after testing
                if (results[0] < Constants.MAXIMUM_DISTANCE_TO_HOTSPOT && !buttonFabNext.isEnabled()) {
                    Toast.makeText(getActivity(), "Já estás perto do local. Podes continuar!", Toast.LENGTH_SHORT).show();

                    vibrator.vibrate(500);

                    buttonFabNext.setVisibility(View.VISIBLE);
                    buttonFabNext.setEnabled(true);
                }
            }
        });
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_REQUEST_CODE)
    private void askLocationPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), PermissionsUtils.getLocationPermissionList())) {
            // initRequestLocationUpdates();
            checkIfLocationIsOn();
        } else {
            EasyPermissions.requestPermissions(this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                    PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getLocationPermissionList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PermissionsUtils.PERMISSIONS_REQUEST_CODE) {
            Log.d("HistoriasPassadoFragment5", "Permission granted.");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            askLocationPermissions();
        }
    }

    private void checkIfLocationIsOn() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());



        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    initRequestLocationUpdates();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().

                                //resolvable.startResolutionForResult(getActivity(), LocationRequest.PRIORITY_HIGH_ACCURACY);
                                startIntentSenderForResult(resolvable.getResolution().getIntentSender(), LocationRequest.PRIORITY_HIGH_ACCURACY, null, 0, 0, 0, null);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("LocationFragment", "onActivityResult");
        switch (requestCode) {
            case LocationRequest.PRIORITY_HIGH_ACCURACY:
                Log.i("LocationFragment", "onActivityResult -> PRIORITY");
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.i("LocationFragment", "onActivityResult: GPS Enabled by user");
                        initRequestLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getActivity(), getResources().getText(R.string.gps_turned_off_error), Toast.LENGTH_LONG).show();
                        Log.i("LocationFragment", "onActivityResult: User rejected GPS request");
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}