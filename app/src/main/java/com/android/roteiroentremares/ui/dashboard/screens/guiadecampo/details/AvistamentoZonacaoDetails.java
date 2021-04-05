package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias;
import com.android.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoPocasDetailsAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoZonacaoDetailsAdapter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoZonacaoDetails extends AppCompatActivity {

    // Views
    private TextView textViewTitle;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias avistamentoZonacao;

    private AvistamentoZonacaoDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_zonacao_details);

        avistamentoZonacao = (AvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias) getIntent().getSerializableExtra("avistamentoZonacao");

        initToolbar();
        initViews();
        initRecyclerView();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Zonação");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textView_title);
        imageView = findViewById(R.id.imageView_photo_grelha);

        textViewTitle.setText(avistamentoZonacao.getAvistamentoZonacaoAvencas().getZona() + " - Quadrado " + avistamentoZonacao.getAvistamentoZonacaoAvencas().getIteracao());

        Glide.with(this)
                .load(new File(avistamentoZonacao.getAvistamentoZonacaoAvencas().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvistamentoZonacaoDetails.this, ImageFullscreenFileActivity.class);
                intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, avistamentoZonacao.getAvistamentoZonacaoAvencas().getPhotoPath());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoZonacaoDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoZonacao(avistamentoZonacao);
    }
}