package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaPocasDetailsAdapter;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoRiaFormosaPocasDetails extends AppCompatActivity {

    // Views
    private TextView textViewTitle;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias avistamentoPocas;

    private AvistamentoRiaFormosaPocasDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_riaformosa_dunas_details);

        avistamentoPocas = (AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias) getIntent().getSerializableExtra("avistamentoRiaFormosaPoca");

        initToolbar();
        initViews();
        initRecyclerView();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Poças de maré");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textView_title);
        imageView = findViewById(R.id.imageView_photo_grelha);

        textViewTitle.setText("Poça " + avistamentoPocas.getAvistamentoPocasRiaFormosa().getIdAvistamento());

        Glide.with(this)
                .load(new File(avistamentoPocas.getAvistamentoPocasRiaFormosa().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvistamentoRiaFormosaPocasDetails.this, ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, avistamentoPocas.getAvistamentoPocasRiaFormosa().getPhotoPath());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoRiaFormosaPocasDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoPocas(avistamentoPocas);
    }
}