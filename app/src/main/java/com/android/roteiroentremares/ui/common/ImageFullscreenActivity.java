package com.android.roteiroentremares.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ImageFullscreenActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "IMAGE_FULLSCREEN_RESOURCE_ID";
    public static final String INTENT_EXTRA_INFO = "IMAGE_FULLSCREEN_RESOURCE_ID_INFORMACAO_ADICIONAL";

    private PhotoView photoView;
    private ImageButton imageButtonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        int resourceId = getIntent().getIntExtra(INTENT_EXTRA_KEY, 0);

        String info = getIntent().getStringExtra(INTENT_EXTRA_INFO);

        imageButtonInfo = findViewById(R.id.imageButton_info);

        if (info != null) {
            // info symbol GONE
            imageButtonInfo.setVisibility(View.VISIBLE);

            imageButtonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ImageFullscreenActivity.this);
                    materialAlertDialogBuilder.setTitle("Informação adicional");
                    materialAlertDialogBuilder.setMessage(info);
                    materialAlertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss
                        }
                    });

                    materialAlertDialogBuilder.show();
                }
            });
        }

        if (resourceId == 0) {
            Toast.makeText(this, "Não foi possível encontrar a imagem pretendida, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            photoView = findViewById(R.id.photo_view);
            photoView.setImageResource(resourceId);
        }
    }
}