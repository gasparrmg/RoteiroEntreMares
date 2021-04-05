package com.android.roteiroentremares.ui.common;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

public class ImageFullscreenFileActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_FILE_ID";

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        String photoPath = getIntent().getStringExtra(INTENT_EXTRA_KEY);

        if (photoPath == null) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);
            File file = new File(photoPath);
            photoView.setImageURI(Uri.fromFile(file));
        }
    }
}