package com.android.roteiroentremares.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.roteiroentremares.R;

public class MediaPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private Uri uriVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        String path = getIntent().getStringExtra("path");
        uriVideo = Uri.parse(path);

        videoView = findViewById(R.id.videoView);

        videoView.setVideoURI(uriVideo);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();
    }
}