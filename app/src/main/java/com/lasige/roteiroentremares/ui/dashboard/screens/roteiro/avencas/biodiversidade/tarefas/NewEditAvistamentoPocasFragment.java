package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade.tarefas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.bumptech.glide.Glide;
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.ui.common.ImageBottomSheetDialog;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieHorizontalAdapterWithCounter;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.Constants;
import com.lasige.roteiroentremares.util.ImageFilePath;
import com.lasige.roteiroentremares.util.PermissionsUtils;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
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
public class NewEditAvistamentoPocasFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final String QUERY_ALL_ESPECIES = "SELECT * FROM especie_table_avencas";
    private final static String SQUARE = "&#xB2;";

    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private RecyclerView recyclerViewEspecies;
    private AutoCompleteTextView dropdownTipoFundo;
    private ImageButton buttonProfundidade;
    private TextView textViewProfundidade;
    private ImageButton buttonAreaSuperficie;
    private TextView textViewAreaSuperficie;
    private ImageButton buttonAddPhoto;
    private ImageView imageViewPhoto;
    private ExtendedFloatingActionButton buttonSubmitPoca;

    private EspecieHorizontalAdapterWithCounter adapter;
    private ArrayAdapter arrayAdapter;

    private int profundidadeValue;
    private String profundidadeUnit;

    private int areaSuperficieValue;
    private String areaSuperficieUnit;

    private String currentPhotoPath;
    private File newPhotoFile;

    private int nrPoca;
    private boolean pocaSubmitted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_edit_avistamento_pocas, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        pocaSubmitted = false;

        getSafeArgs();

        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    private void getSafeArgs() {
        if (getArguments() != null) {
            NewEditAvistamentoPocasFragmentArgs args = NewEditAvistamentoPocasFragmentArgs.fromBundle(getArguments());
            nrPoca = args.getNrPoca();
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
        buttonProfundidade = view.findViewById(R.id.button_profundidade);
        textViewProfundidade = view.findViewById(R.id.textView_profundidade);
        buttonAreaSuperficie = view.findViewById(R.id.button_area_superficie);
        textViewAreaSuperficie = view.findViewById(R.id.textView_area_superficie);
        buttonAddPhoto = view.findViewById(R.id.button_add_photo);
        imageViewPhoto = view.findViewById(R.id.imageview_picture);
        buttonSubmitPoca = view.findViewById(R.id.button_submit_poca);

        dropdownTipoFundo = view.findViewById(R.id.dropdown_tipo_fundo);
        String[] optionsFundo = {
                "Areia",
                "Rocha",
                "Blocos",
                "Misto"
        };
        arrayAdapter = new ArrayAdapter(getActivity(), R.layout.list_dropdown_grupo_item, optionsFundo);
        dropdownTipoFundo.setAdapter(arrayAdapter);

        recyclerViewEspecies = view.findViewById(R.id.recyclerView_especies);
        recyclerViewEspecies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewEspecies.setHasFixedSize(true);

        adapter = new EspecieHorizontalAdapterWithCounter(getContext(), 0);
        recyclerViewEspecies.setAdapter(adapter);

        guiaDeCampoViewModel.filterEspecies(QUERY_ALL_ESPECIES);

        guiaDeCampoViewModel.getAllEspecies().observe(getViewLifecycleOwner(), new Observer<List<EspecieAvencas>>() {
            @Override
            public void onChanged(List<EspecieAvencas> especieAvencas) {
                //set recycler view
                adapter.setEspeciesAvencas(especieAvencas);
            }
        });

        textViewTitle.setText("Poça " + nrPoca);
    }

    private View.OnClickListener profundidadeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_number_picker_distances, null);

            materialAlertDialogBuilder.setTitle("Profundidade");
            materialAlertDialogBuilder.setView(dialogView);

            NumberPicker numberPicker1 = dialogView.findViewById(R.id.number_picker_1);
            NumberPicker numberPicker2 = dialogView.findViewById(R.id.number_picker_2);

            numberPicker1.setMinValue(1);
            numberPicker1.setMaxValue(100);

            numberPicker2.setMinValue(0);
            numberPicker2.setMaxValue(2);
            numberPicker2.setDisplayedValues(new String[]{"mm", "cm", "m"});

            materialAlertDialogBuilder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // save values from

                    if (numberPicker2.getValue() == 0) {
                        profundidadeUnit = "mm";
                    } else if (numberPicker2.getValue() == 1) {
                        profundidadeUnit = "cm";
                    } else {
                        profundidadeUnit = "m";
                    }

                    profundidadeValue = numberPicker1.getValue();

                    textViewProfundidade.setText(profundidadeValue + profundidadeUnit);

                    buttonProfundidade.setVisibility(View.GONE);
                    textViewProfundidade.setVisibility(View.VISIBLE);
                }
            });
            materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // dismiss
                }
            });
            materialAlertDialogBuilder.show();
        }
    };

    private View.OnClickListener areaSuperficieListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_number_picker_distances, null);

            materialAlertDialogBuilder.setTitle("Área de superfície");
            materialAlertDialogBuilder.setView(dialogView);

            NumberPicker numberPicker1 = dialogView.findViewById(R.id.number_picker_1);
            NumberPicker numberPicker2 = dialogView.findViewById(R.id.number_picker_2);

            numberPicker1.setMinValue(1);
            numberPicker1.setMaxValue(100);

            numberPicker2.setMinValue(0);
            numberPicker2.setMaxValue(2);
            numberPicker2.setDisplayedValues(new String[]{"mm2", "cm2", "m2"});

            materialAlertDialogBuilder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // save values from

                    if (numberPicker2.getValue() == 0) {
                        areaSuperficieUnit = "mm2";
                    } else if (numberPicker2.getValue() == 1) {
                        areaSuperficieUnit = "cm2";
                    } else {
                        areaSuperficieUnit = "m2";
                    }

                    areaSuperficieValue = numberPicker1.getValue();

                    textViewAreaSuperficie.setText(areaSuperficieValue + areaSuperficieUnit);

                    buttonAreaSuperficie.setVisibility(View.GONE);
                    textViewAreaSuperficie.setVisibility(View.VISIBLE);
                }
            });
            materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // dismiss
                }
            });
            materialAlertDialogBuilder.show();
        }
    };

    private View.OnClickListener newPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentPhotoPath == null) {
                askCameraPermissions();
            } else {
                // modal
                ImageBottomSheetDialog bottomSheetDialog = new ImageBottomSheetDialog();
                bottomSheetDialog.setButtonClickListener(new ImageBottomSheetDialog.BottomSheetListener() {
                    @Override
                    public void onButtonClicked(String action) {
                        if (action.equals(ImageBottomSheetDialog.ACTION_IMAGE_FULLSCREEN)) {
                            if (currentPhotoPath != null) {
                                if (!currentPhotoPath.isEmpty()) {
                                    Intent intent = new Intent(getActivity(), ImageFullscreenFileActivity.class);
                                    intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, currentPhotoPath);
                                    startActivity(intent);
                                }
                            }
                        } else if (action.equals(ImageBottomSheetDialog.ACTION_NEW_IMAGE)) {
                            askCameraPermissions();
                        }
                    }
                });
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "imageBottomSheetDialog");
            }
        }
    };

    private void setOnClickListeners(View view) {
        buttonProfundidade.setOnClickListener(profundidadeListener);
        textViewProfundidade.setOnClickListener(profundidadeListener);

        buttonAreaSuperficie.setOnClickListener(areaSuperficieListener);
        textViewAreaSuperficie.setOnClickListener(areaSuperficieListener);

        buttonAddPhoto.setOnClickListener(newPhotoListener);
        imageViewPhoto.setOnClickListener(newPhotoListener);

        buttonSubmitPoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dropdownTipoFundo.getText().toString() == null || dropdownTipoFundo.getText().toString().isEmpty() ||
                        profundidadeValue == 0 ||
                        profundidadeUnit == null || profundidadeUnit.isEmpty() ||
                        areaSuperficieValue == 0 ||
                        areaSuperficieUnit == null || areaSuperficieUnit.isEmpty() ||
                        currentPhotoPath == null || currentPhotoPath.isEmpty()) {
                    Toast.makeText(getActivity(), "Ainda tens campos por preencher!", Toast.LENGTH_LONG).show();
                    return;
                }

                guiaDeCampoViewModel.insertAvistamentoPocaWithInstanciasAvencas(
                        nrPoca,
                        dropdownTipoFundo.getText().toString(),
                        profundidadeValue,
                        profundidadeUnit,
                        areaSuperficieValue,
                        areaSuperficieUnit,
                        currentPhotoPath,
                        adapter.getEspeciesAvencas(),
                        adapter.getInstancias()
                );

                pocaSubmitted = true;

                guiaDeCampoViewModel.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(nrPoca).observe(getViewLifecycleOwner(), new Observer<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>() {
                    @Override
                    public void onChanged(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                        if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias != null && pocaSubmitted) {
                            Log.d("NEW_EDIT_SUBMIT", "avistamento is NOT null");
                            Navigation.findNavController(view).popBackStack();
                            Toast.makeText(getActivity(), "Poça " + nrPoca + " submetida!", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("NEW_EDIT_SUBMIT", "avistamento is null");
                        }
                    }
                });
            }
        });
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

    private void dispatchTakePictureIntent() {
        Log.d("NEW_ARTEFACTO_ACTIVITY", "dispatchTakePictureIntent()");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("NEW_ARTEFACTO_ACTIVITY", "dispatchTakePictureIntent() ERROR: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.roteiroentremares.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.CAMERA_REQUEST_CODE);
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

    private File saveToInternalStorage(Bitmap bitmapImage){

        // File directory = getActivity().getDir("imageDir", Context.MODE_PRIVATE);
        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());

        File mypath = new File(directory,"roteiroentremares_biodiversidade_pocas" + nrPoca + "_" + currentTimeStamp + ".jpg");

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAKE PIC TEST", "onActivityResult");

        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                newPhotoFile = new File(currentPhotoPath);

                if (newPhotoFile.exists()) {
                    buttonAddPhoto.setVisibility(View.GONE);
                    imageViewPhoto.setVisibility(View.VISIBLE);
                    imageViewPhoto.setImageURI(Uri.fromFile(newPhotoFile));
                    Log.d("TAKE PIC TEST", "newPhotoFile exists, setting image to view");

                } else {
                    Toast.makeText(getActivity(), "Não foi possível encontrar o ficheiro. Tente novemente mais tarde.", Toast.LENGTH_LONG).show();
                }
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            deleteLastInsertedPhoto();

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Bitmap bitmapBg = BitmapFactory.decodeFile(ImageFilePath.getPath(getActivity(), result.getUri()));

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
                    .into(imageViewPhoto);

            imageViewPhoto.setVisibility(View.VISIBLE);
            buttonAddPhoto.setVisibility(View.GONE);
        }
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE)
    private void askCameraPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), PermissionsUtils.getCameraPermissionList())) {
            // Open Camera
            // dispatchTakePictureIntent();
            cropImage();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_warning),
                    PermissionsUtils.PERMISSIONS_CAMERA_REQUEST_CODE, PermissionsUtils.getCameraPermissionList());
        }
    }

    private void cropImage() {
        Intent intent = CropImage.activity()
                .setAspectRatio(1, 1)
                .getIntent(getContext());

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
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