package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.naofiquesporaqui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class NaoFiquesPorAquiFragment extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonSabiasQue;

    private TextToSpeech tts;

    private int imageResourceId = R.drawable.img_naofiquesporaqui_mapa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nao_fiques_por_aqui, container, false);

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Não fiques por aqui...");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        if (tts.isSpeaking()) {
            tts.stop();
        }
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.roteiro_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (tts.isSpeaking()) {
                    tts.stop();
                } else {
                    String text = "Entre as praias da Bafureira e da Parede encontramos a praia das Avencas de dimensão pequena mas muito especial. As características geológicas e biológicas desta praia levaram à sua classificação em 1998 como Zona de Interesse Biofísico das Avencas (ZIBA). Os trabalhos realizados desde então, monitorizações regulares e algumas consultas públicas, levaram à proposta de reclassificação desta área. O objetivo desta reclassificação foi incluir toda a plataforma rochosa nas medidas de proteção e preservação do habitat entre-marés e respetiva biodiversidade. A Resolução de Conselho de Ministros nº 64/2016 aprova a Área Marinha Protegida das Avencas (AMP das Avencas).";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonSabiasQue = view.findViewById(R.id.button_sabiasque);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(view).navigate(R.id.action_global_roteiroFragment);
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_naoFiquesPorAquiFragment_to_naoFiquesPorAquiFragment2);
            }
        });

        buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "A praia das Avencas deve o seu nome a uma planta – a avenca – com longa tradição de uso medicinal em Portugal. A avenca cresce espontaneamente em locais húmidos e sombrios como em fendas de rochas próximas de cascatas, poços e muros em zonas de escoamento de água. As folhas da avenca são usadas para fazer um chá que acalma a tosse e as constipações. Também se pode fazer o chamado “capilé”, feito com o sumo das avencas, água fresca, casca de limão e gelo.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Área Marinha Protegida das Avencas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString linkAvencas = ClickableString.makeLinkSpan("Área Marinha Protegida das Avencas", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });

        textViewContent.setText(HtmlCompat.fromHtml(
                "Entre as praias da Bafureira e da Parede encontramos a praia das Avencas de dimensão pequena mas muito especial. As características geológicas e biológicas desta praia levaram à sua classificação em 1998 como Zona de Interesse Biofísico das Avencas (ZIBA). Os trabalhos realizados desde então, monitorizações regulares e algumas consultas públicas, levaram à proposta de reclassificação desta área. O objetivo desta reclassificação foi incluir toda a plataforma rochosa nas medidas de proteção e preservação do habitat entre-marés e respetiva biodiversidade. A Resolução de Conselho de Ministros nº 64/2016 aprova a ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.append(linkAvencas);
        textViewContent.append(" (AMP das Avencas).");
        ClickableString.makeLinksFocusable(textViewContent);

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TEXT2SPEECH", "Language not supported");
                        Toast.makeText(getActivity(), "Não tens o linguagem Português disponível no teu dispositivo. Isto acontece normalmente acontece quando a linguagem padrão do dispositivo é outra que não o Português.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }
}