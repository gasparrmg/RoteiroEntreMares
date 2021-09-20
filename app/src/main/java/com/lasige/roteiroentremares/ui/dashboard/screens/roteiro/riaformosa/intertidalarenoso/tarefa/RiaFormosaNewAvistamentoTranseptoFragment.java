package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso.tarefa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieRiaFormosaTranseptosAdapter;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class RiaFormosaNewAvistamentoTranseptoFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final String QUERY_ALL_ESPECIES = "SELECT * FROM especie_table_riaformosa " +
            "WHERE zona = 'Intertidal arenoso'";

    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private RecyclerView recyclerViewEspecies;
    private ExtendedFloatingActionButton buttonSubmit;

    private EspecieRiaFormosaTranseptosAdapter adapter;

    private String currentPhotoPath;
    private File newPhotoFile;

    private int nrTransepto;

    private boolean avistamentoSubmitted;

    private int currentImagePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_avistamento_riaformosa_transeptos, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        avistamentoSubmitted = false;

        getSafeArgs();

        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    private void getSafeArgs() {
        if (getArguments() != null) {
            RiaFormosaNewAvistamentoTranseptoFragmentArgs args = RiaFormosaNewAvistamentoTranseptoFragmentArgs.fromBundle(getArguments());

            nrTransepto = args.getNrTransepto();
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
        buttonSubmit = view.findViewById(R.id.button_submit);

        recyclerViewEspecies = view.findViewById(R.id.recyclerView_especies);
        recyclerViewEspecies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewEspecies.setHasFixedSize(true);

        adapter = new EspecieRiaFormosaTranseptosAdapter(getActivity(), 0);
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

        textViewTitle.setText("Transepto " + nrTransepto);

        adapter.setOnItemClickListener(new EspecieRiaFormosaTranseptosAdapter.OnItemClickListener() {
            @Override
            public void onRiaFormosaItemClick(int position) {
                Log.d("TRANSEPTOS", "fragment itemclick");
                currentImagePosition = position;
                askCameraPermissions();
            }
        });

        adapter.setOnPhotoClickListener(new EspecieRiaFormosaTranseptosAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(int position, String path) {
                Intent intent = new Intent(getActivity(), ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, path);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_POSITION, position);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_IS_TRANSEPTO, 1);

                //context.startActivity(intent);
                startActivityForResult(intent, ImageFullscreenFileActivity.INTENT_EXTRA_RESULT);
            }
        });
    }

    private void setOnClickListeners(View view) {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert Avistamento

                guiaDeCampoViewModel.insertAvistamentoTranseptosWithInstanciasRiaFormosa(
                        nrTransepto,
                        adapter.getEspeciesRiaFormosa(),
                        adapter.getInstanciasExpostaPedra(),
                        adapter.getInstanciasInferiorPedra(),
                        adapter.getInstanciasSubstrato(),
                        adapter.getPhotoPaths()
                );

                avistamentoSubmitted = true;

                guiaDeCampoViewModel.getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(nrTransepto).observe(getViewLifecycleOwner(), new Observer<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>() {
                    @Override
                    public void onChanged(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                        if (avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias != null && avistamentoSubmitted) {
                            Navigation.findNavController(view).popBackStack();
                            Toast.makeText(getActivity(), "Transepto " + nrTransepto + " submetido! Podes visualizar o Avistamento submetido no Guia de Campo.", Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("CROP", "onActivityResult");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // deleteLastInsertedPhoto();

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            newPhotoFile = new File(result.getUri().getPath());
            currentPhotoPath = newPhotoFile.getPath();

            adapter.setPhotoPaths(currentImagePosition, currentPhotoPath);
        }

        if (requestCode == ImageFullscreenFileActivity.INTENT_EXTRA_RESULT && resultCode == Activity.RESULT_OK) {

            Log.d("DELETE_PIC_TRANSEPTO", "onActivityResult");

            int positionToDelete = data.getIntExtra("positionToDelete", -1);
            boolean delete = data.getBooleanExtra("delete", false);

            if (positionToDelete != -1 && delete) {
                // delete
                boolean wasDeleted = adapter.deletePhotoInPosition(positionToDelete);

                if (wasDeleted) {
                    Toast.makeText(getActivity(), "Imagem apagada!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Ocorreu um erro e não foi possível apagar a imagem.", Toast.LENGTH_SHORT).show();
                }
            }
        }
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

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE)
    private void askCameraPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), PermissionsUtils.getCameraPermissionList())) {
            // Open Camera
            cropImage();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE, PermissionsUtils.getCameraPermissionList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE) {
            Log.d("NEW_ARTEFACTO_IMAGE", "Camera permissions granted");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}