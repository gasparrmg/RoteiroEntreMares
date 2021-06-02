package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.TypefaceSpan;

public class GlossarioRiaFormosaActivity extends AppCompatActivity {

    private TextView textViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossario_ria_formosa);

        SpannableString s = new SpannableString("Glossário");
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

        textViewContent = findViewById(R.id.textView_glossario_content);

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Alagadiço</b> – local que está encharcado e lamacento.<br>" +
                        "<b>Caule</b> – parte da planta que suporta os ramos e folhas.<br>" +
                        "<b>Cosmopolita</b> – encontra-se distribuída por todo o país.<br>" +
                        "<b>Gramíneas</b> – família de plantas onde pertence o trigo, o arroz e a cana-de-açúcar por exemplo.<br>" +
                        "<b>Halófita</b> – plantas tolerantes ao sal.<br>" +
                        "<b>Herbácea</b> – aspecto e coloração semelhante às ervas, ou seja, não ou pouco lenhoso.<br>" +
                        "<b>Lenhoso</b> – que tem consistência de madeira.<br>" +
                        "<b>Ovadas</b> – com a forma do contorno de um ovo.<br>" +
                        "<b>Perene</b> – Planta que vive três anos ou mais. <br>" +
                        "<b>Planta anual (terófita)</b> – planta que o ciclo vegetativo completa-se num ano ou menos.<br>" +
                        "<b>Prostrado</b> – deitado no solo. Aplica-se a caules e ramos.<br>" +
                        "<b>Subarbusto</b> – para as plantas presentes significa que pode não exceder 1m, só lenhosa na base e sempre herbácea na restante parte.<br>" +
                        "<b>Vivaz</b> – que vive mais do que dois anos. Aplica-se ás plantas onde a parte aérea é herbácea e renova-se anualmente.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}