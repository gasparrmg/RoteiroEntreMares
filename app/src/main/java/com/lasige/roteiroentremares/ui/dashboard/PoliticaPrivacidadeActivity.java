package com.lasige.roteiroentremares.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.ClickableString;
import com.lasige.roteiroentremares.util.TypefaceSpan;

public class PoliticaPrivacidadeActivity extends AppCompatActivity {

    private static final String htmlContent = "Ao utilizar a aplicação Roteiro Entre-Marés, está a consentir com as práticas de recolha de informação da mesma, que estão descritas nesta Política de Privacidade. Se não consentir, deverá interromper a utilização da aplicação ou ajustar as permissões que são aqui referidas no seu dispositivo móvel.<br><br>" +
            "<b>1. Dados Pessoais</b><br>" +
            "O Roteiro Entre-Marés não recolhe nenhum tipo de dados pessoais do utilizador. Qualquer informação pedida ao utilizador, como o seu nome, nunca é recolhida por nós e é apenas guardada localmente no próprio dispositivo, com o único objectivo de melhorar a experiência de utilização.<br><br>" +
            "<b>2. Dados recolhidos pela aplicação</b><br>" +
            "Durante a utilização do Roteiro Entre-Marés, algumas permissões consideradas sensíveis são pedidas ao utilizador. Tal como acontece no caso dos dados pessoais, nenhum tipo de informação capturada é recolhida por nós, visto que os dados são apenas guardados localmente e apenas servem para melhorar a experiência de utilização da aplicação.<br>" +
            "- <b>Câmara:</b> A câmara do dispositivo é utilizada para a captura de fotografias e vídeos para responder aos diferentes desafios pedagógicos que vão sendo feitos aos utilizadores. Os ficheiros gerados são apenas guardados localmente no próprio dispositivo e não são recolhidos por nós.<br>" +
            "- <b>Localização:</b> A localização do utilizador é unicamente recolhida durante breves momentos com o objectivo de auxiliar e orientar o utilizador durante as visitas aos pontos de interesse. Se o utilizador o permitir, a sua localização também é recolhida para a criação de meta-dados que são associados às fotografias e vídeos que capturar.<br>" +
            "- <b>Microfone:</b> Durante as visitas aos locais, os utilizadores poderão criar gravações de áudio como forma de substituir simples notas de texto. Os ficheiros gerados são apenas guardados localmente no próprio dispositivo e não são recolhidos por nós.<br><br>" +
            "<b>3. Como é que apaga todos os dados gerados?</b><br>" +
            "No momento em que desinstala a aplicação do seu dispositivo móvel, todos os dados gerados são apagados, visto que estes são apenas guardados localmente e nós não recolhemos estes dados em altura alguma.<br><br>" +
            "<b>4. Como é que pode contactar-nos?</b><br>" +
            "Se tiver alguma dúvida em relação a esta Política de Privacidade ou acerca de qualquer outro assunto relacionado com a aplicação móvel, não hesite em contactar-nos em <b>roteiroentremares@gmail.com</b>.";

    private TextView mTextViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mTextViewContent = findViewById(R.id.textView);

        mTextViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString s = new SpannableString(getResources().getString(R.string.title_privacy_policy));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}