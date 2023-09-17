package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas.tarefas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.canhub.cropper.CropImage;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieRiaFormosaHorizontalAdapterWithCounter;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.FileUtils;
import com.lasige.roteiroentremares.util.ImageFilePath;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class NewAvistamentoDunasFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final String QUERY_ALL_ESPECIES = "SELECT * FROM especie_table_riaformosa WHERE zona = 'Dunas'";

    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private RecyclerView recyclerViewEspecies;
    private ImageButton buttonAddPhoto;
    private ImageView imageViewPhotoGrelha;
    private ExtendedFloatingActionButton buttonSubmit;

    private EspecieRiaFormosaHorizontalAdapterWithCounter adapter;
    private ArrayAdapter arrayAdapter;

    private String currentPhotoPath;
    private File newPhotoFile;

    private String zona;
    private int iteracao;
    private boolean avistamentoSubmitted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_avistamento_dunas, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        avistamentoSubmitted = false;

        getSafeArgs();

        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    private void getSafeArgs() {
        if (getArguments() != null) {
            NewAvistamentoDunasFragmentArgs args = NewAvistamentoDunasFragmentArgs.fromBundle(getArguments());
            zona = args.getZona();
            iteracao = args.getIteracao();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.novo_avistamento_title));
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        setHasOptionsMenu(true);
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        buttonAddPhoto = view.findViewById(R.id.button_add_photo);
        imageViewPhotoGrelha = view.findViewById(R.id.imageView_photo_grelha);
        buttonSubmit = view.findViewById(R.id.button_submit);

        recyclerViewEspecies = view.findViewById(R.id.recyclerView_especies);
        recyclerViewEspecies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewEspecies.setHasFixedSize(true);

        adapter = new EspecieRiaFormosaHorizontalAdapterWithCounter(getContext(), 0);
        recyclerViewEspecies.setAdapter(adapter);

        guiaDeCampoViewModel.filterEspeciesRiaFormosa(QUERY_ALL_ESPECIES);

        guiaDeCampoViewModel.getAllEspecieRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<EspecieRiaFormosa>>() {
            @Override
            public void onChanged(List<EspecieRiaFormosa> especieRiaFormosas) {
                Log.d("NEW_AVISTAMENTO", "result size: " + especieRiaFormosas.size());
                //set recycler view
                adapter.setEspeciesRiaFormosa(especieRiaFormosas);
            }
        });

        textViewTitle.setText(zona + " - Quadrado " + iteracao);
    }

    private View.OnClickListener newPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            askPhotoPermissions();
        }
    };

    private void setOnClickListeners(View view) {
        buttonAddPhoto.setOnClickListener(newPhotoListener);

        imageViewPhotoGrelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open image full screen

                Log.d("CROP", "opening image with path: " + currentPhotoPath);
                Intent intent = new Intent(getActivity(), ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, currentPhotoPath);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate form
                if (currentPhotoPath == null || currentPhotoPath.isEmpty()) {
                    Toast.makeText(getActivity(), "Ainda tens campos por preencher!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert Avistamento
                guiaDeCampoViewModel.insertAvistamentoDunasWithInstanciasRiaFormosa(
                        iteracao,
                        zona,
                        currentPhotoPath,
                        adapter.getEspeciesRiaFormosa(),
                        adapter.getInstancias()
                );

                avistamentoSubmitted = true;

                // Check if is in the database, if it is -> popbackstack
                guiaDeCampoViewModel.getAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(iteracao, zona).observe(getViewLifecycleOwner(), new Observer<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias>() {
                    @Override
                    public void onChanged(AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias) {
                        if (avistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias != null && avistamentoSubmitted) {
                            Log.d("NEW_EDIT_SUBMIT", "avistamento is NOT null");
                            Navigation.findNavController(view).popBackStack();
                            Toast.makeText(getActivity(), "Quadrado " + iteracao + " submetido! Podes visualizar o Avistamento submetido no Guia de Campo.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("NEW_EDIT_SUBMIT", "avistamento is null");
                        }
                    }
                });
            }
        });
    }

    private void cropImage() {
        Intent intent = CropImage.activity()
                .setAspectRatio(1, 1)
                .getIntent(getContext());

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "RoteiroEntreMares_" + timeStamp;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("CROP", "onActivityResult");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            deleteLastInsertedPhoto();

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Bitmap bitmapBg = BitmapFactory.decodeFile(FileUtils.getPath(getActivity(), result.getUri()));

            WatermarkImage watermarkImage = new WatermarkImage(getActivity(), R.drawable.grelha_registo_branco)
                    .setSize(1)
                    .setImageAlpha(255);

            Bitmap bitmapWithWM = WatermarkBuilder.create(getActivity(), bitmapBg)
                    .loadWatermarkImage(watermarkImage)
                    .getWatermark()
                    .getOutputImage();

            newPhotoFile = saveToInternalStorage(bitmapWithWM);
            currentPhotoPath = newPhotoFile.getPath();

            Glide.with(getActivity())
                    .load(newPhotoFile)
                    .into(imageViewPhotoGrelha);

            imageViewPhotoGrelha.setVisibility(View.VISIBLE);
        }
    }

    private File saveToInternalStorage(Bitmap bitmapImage){

        // File directory = getActivity().getDir("imageDir", Context.MODE_PRIVATE);
        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());

        File mypath = new File(directory,"roteiroentremares_biodiversidade_dunas_" + zona + "_" + iteracao + "_" + currentTimeStamp + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return directory.getAbsolutePath();
        return mypath;
    }

    private void deleteLastInsertedPhoto() {
        if (newPhotoFile != null) {
            if (newPhotoFile.exists()) {
                if (newPhotoFile.delete()) {
                    Log.d("CROP", "last inserted photo deleted");
                    newPhotoFile = null;
                } else {
                    Log.d("CROP", "last inserted photo NOT deleted");
                }
            }
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_PHOTOS_REQUEST_CODE)
    private void askPhotoPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), PermissionsUtils.getPhotoPermissionList())) {
            // Open Camera
            cropImage();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_PHOTOS_REQUEST_CODE, PermissionsUtils.getPhotoPermissionList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        /*if (requestCode == PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE) {
            Log.d("NEW_ARTEFACTO_IMAGE", "Camera permissions granted");
        }*/
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}