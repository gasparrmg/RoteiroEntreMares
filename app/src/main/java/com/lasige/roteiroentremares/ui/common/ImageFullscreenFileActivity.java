package com.lasige.roteiroentremares.ui.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

public class ImageFullscreenFileActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_FILE_ID";
    public static final String INTENT_EXTRA_POSITION = "IMAGE_FULLSCREEN_FILE_POSITION";

    public static final String INTENT_EXTRA_IS_TRANSEPTO = "IMAGE_FULLSCREEN_RESOURCE_IS_TRANSEPTO";

    public static final int INTENT_EXTRA_RESULT = 101;

    private PhotoView photoView;
    private ImageButton imageButtonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        String photoPath = getIntent().getStringExtra(INTENT_EXTRA_KEY);

        int isTransepto = getIntent().getIntExtra(INTENT_EXTRA_IS_TRANSEPTO, 0);
        int position = getIntent().getIntExtra(INTENT_EXTRA_POSITION, -1);

        imageButtonDelete = findViewById(R.id.imageButton_delete);

        if (photoPath == null) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);
            File file = new File(photoPath);
            photoView.setImageURI(Uri.fromFile(file));
        }

        if (isTransepto == 1 && position != -1) {
            imageButtonDelete.setVisibility(View.VISIBLE);

            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ImageFullscreenFileActivity.this);
                    materialAlertDialogBuilder.setTitle("Apagar imagem?");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_delete_image));
                    materialAlertDialogBuilder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("delete", true);
                            returnIntent.putExtra("positionToDelete", position);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
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
            });
        }
    }
}