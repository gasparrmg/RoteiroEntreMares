package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoPocasDetailsAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieHorizontalAdapterWithCounter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class AvistamentoPocasDetails extends AppCompatActivity {

    // Views
    private TextView textViewTipoFundo;
    private TextView textViewProfundidade;
    private TextView textViewAreaSuperficie;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca;

    private AvistamentoPocasDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_pocas_details);

        avistamentoPoca = (AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias) getIntent().getSerializableExtra("avistamentoPoca");

        initToolbar();
        initViews();
        initRecyclerView();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Poça " + avistamentoPoca.getAvistamentoPocasAvencas().getIdAvistamento());
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTipoFundo = findViewById(R.id.textView_tipoFundo);
        textViewProfundidade = findViewById(R.id.textView_profundidade);
        textViewAreaSuperficie = findViewById(R.id.textView_area_superficie);
        imageView = findViewById(R.id.imageview_picture);

        textViewTipoFundo.setText(avistamentoPoca.getAvistamentoPocasAvencas().getTipoFundo());
        textViewProfundidade.setText(String.valueOf(avistamentoPoca.getAvistamentoPocasAvencas().getProfundidadeValue()) + avistamentoPoca.getAvistamentoPocasAvencas().getProfundidadeUnit());
        textViewAreaSuperficie.setText(String.valueOf(avistamentoPoca.getAvistamentoPocasAvencas().getAreaSuperficieValue()) + avistamentoPoca.getAvistamentoPocasAvencas().getAreaSuperficieUnit());

        Glide.with(this)
                .load(new File(avistamentoPoca.getAvistamentoPocasAvencas().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvistamentoPocasDetails.this, ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, avistamentoPoca.getAvistamentoPocasAvencas().getPhotoPath());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoPocasDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoPoca(avistamentoPoca);
    }
}