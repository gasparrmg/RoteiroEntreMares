package com.lasige.roteiroentremares.ui.common;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.roteiroentremares.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MediaPlayerResourceActivity extends AppCompatActivity {

    public static final String INTENT_KEY_VIDEO_RESOURCE = "INTENT_KEY_VIDEO_RESOURCE";
    public static final String INTENT_KEY_INFO = "INTENT_KEY_INFO";

    private VideoView videoView;
    private Uri uriVideo;
    private ImageButton imageButtonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_resource);

        videoView = findViewById(R.id.videoView);
        imageButtonInfo = findViewById(R.id.imageButton_info);

        int videoResourceId = getIntent().getIntExtra(INTENT_KEY_VIDEO_RESOURCE, 0);
        String info = getIntent().getStringExtra(INTENT_KEY_INFO);

        if (videoResourceId == 0) {
            Toast.makeText(this, R.string.file_not_found_error_message, Toast.LENGTH_LONG).show();
            finish();
        }

        if (info != null && !info.isEmpty()) {
            imageButtonInfo.setVisibility(View.VISIBLE);

            imageButtonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(MediaPlayerResourceActivity.this);
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

        String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;

        uriVideo = Uri.parse(videoPath);

        videoView.setVideoURI(uriVideo);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();
    }
}