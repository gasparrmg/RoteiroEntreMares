package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

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
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.android.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaPocasDetailsAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.AvistamentoRiaFormosaTranseptosDetailsAdapter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoRiaFormosaTranseptosDetails extends AppCompatActivity {

    // Views
    private TextView textViewTitle;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTranseptos;

    private AvistamentoRiaFormosaTranseptosDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamento_riaformosa_transeptos_details);

        avistamentoTranseptos = (AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) getIntent().getSerializableExtra("avistamentoRiaFormosaTransepto");

        initToolbar();
        initViews();
        initRecyclerView();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Transeptos");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textView_title);

        textViewTitle.setText("Transepto " + avistamentoTranseptos.getAvistamentoTranseptosRiaFormosa().getIdAvistamento());
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_especies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AvistamentoRiaFormosaTranseptosDetailsAdapter(this, 0);
        recyclerView.setAdapter(adapter);

        adapter.setAvistamentoTransepto(avistamentoTranseptos);
    }
}