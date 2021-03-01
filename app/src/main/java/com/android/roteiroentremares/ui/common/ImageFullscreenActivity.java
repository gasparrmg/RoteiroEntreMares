package com.android.roteiroentremares.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageFullscreenActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_RESOURCE_ID";

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        int resourceId = getIntent().getIntExtra(INTENT_EXTRA_KEY, 0);

        if (resourceId == 0) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);
            photoView.setImageResource(resourceId);
        }
    }
}