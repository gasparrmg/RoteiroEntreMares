package com.lasige.roteiroentremares.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.ClickableString;
import com.lasige.roteiroentremares.util.TypefaceSpan;

public class AboutUsActivity extends AppCompatActivity {

    private TextView mTextViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mTextViewContent = findViewById(R.id.textView);

        mTextViewContent.setText("O projeto “Roteiro Entre Marés” foi financiado pela Fundo Azul –Direção Geral de Política do Mar (FA_06_2017_011). O Projeto foi coordenado por Cláudia Faria, do Instituto de Educação da Universidade de Lisboa, responsável pela criação dos conteúdos didáticos, em estreita colaboração com o Departamento de Informática da Faculdade de Ciências da Universidade de Lisboa, responsável pela criação e desenvolvimento da aplicação informática. Para além destas duas instituições, o projeto teve também como instituições parceiras: EMAC – Empresa Municipal de Ambiente de Cascais; Centro de Ciência Viva de Tavira a Cascais Ambiente; Escola Superior de Educação João de Deus; Universidade Aberta – Departamento de Ciências e Tecnologia; MARE – Centro de Ciências do Mar e do Ambiente. O projeto contou também com a colaboração de Carla Kullberg (FC/ULisboa), e de Frederico Almada (MARE/ISPA) para o desenvolvimento de alguns dos conteúdos. As ilustrações são da autoria de Beatriz Santos. Para mais informações: ");

        SpannableString link = ClickableString.makeLinkSpan("http://www.ie.ulisboa.pt/projetos/roteiro-entre-mares", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ie.ulisboa.pt/projetos/roteiro-entre-mares"));
                startActivity(browserIntent);
            }
        });

        mTextViewContent.append(link);

        ClickableString.makeLinksFocusable(mTextViewContent);

        SpannableString s = new SpannableString(getResources().getString(R.string.about_us_title));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}