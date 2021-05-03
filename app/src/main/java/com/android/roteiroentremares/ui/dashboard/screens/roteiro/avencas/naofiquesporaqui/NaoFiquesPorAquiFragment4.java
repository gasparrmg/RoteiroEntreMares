package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.naofiquesporaqui;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class NaoFiquesPorAquiFragment4 extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private ExtendedFloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nao_fiques_por_aqui4, container, false);
        ttsEnabled = false;
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
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                "<b>Desafio para aprofundar:</b><br>" +
                                        "Imagina que eras responsável por decidir se as atuais restrições que existem na AMP das Avencas se deviam manter, ou se deviam ser repensadas.<br>" +
                                        "<br>" +
                                        "Para isso decidiste realizar uma reunião com representantes das diferentes entidades interessadas na utilização desta zona, para diferentes fins. Assim, convocaste para a tua reunião as seguintes pessoas:<br>" +
                                        "- Um biólogo<br>" +
                                        "- Um representante dos pescadores da zona<br>" +
                                        "- Um representante do turismo local<br>" +
                                        "<br>" +
                                        "Com os teus amigos ou colegas, organizem-se em grupos, e cada grupo irá assumir o papel de um dos atores aqui apresentados.  Cada grupo será responsável por fazer uma pesquisa sobre a perspetiva de cada um destes atores, e organizem uma discussão, em que cada grupo terá de apresentar a sua opinião sobre a questão colocada e fundamentar essa opinião, com base na pesquisa efetuada. No final, procurem chegar a um consenso, sobre qual será a melhor solução.",
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tts_error_message), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
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
                Navigation.findNavController(view).navigate(R.id.action_naoFiquesPorAquiFragment4_to_naoFiquesPorAquiFragment5);
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

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Desafio para aprofundar:</b><br>" +
                        "Imagina que eras responsável por decidir se as atuais restrições que existem na AMP das Avencas se deviam manter, ou se deviam ser repensadas.<br>" +
                        "<br>" +
                        "Para isso decidiste realizar uma reunião com representantes das diferentes entidades interessadas na utilização desta zona, para diferentes fins. Assim, convocaste para a tua reunião as seguintes pessoas:<br>" +
                        "- Um biólogo<br>" +
                        "- Um representante dos pescadores da zona<br>" +
                        "- Um representante do turismo local<br>" +
                        "<br>" +
                        "Com os teus amigos ou colegas, organizem-se em grupos, e cada grupo irá assumir o papel de um dos atores aqui apresentados. Cada grupo será responsável por fazer uma pesquisa sobre a perspetiva de cada um destes atores, e organizem uma discussão, em que cada grupo terá de apresentar a sua opinião sobre a questão colocada e fundamentar essa opinião, com base na pesquisa efetuada. No final, procurem chegar a um consenso, sobre qual será a melhor solução.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ttsEnabled = false;
                        Log.e("TEXT2SPEECH", "Language not supported");
                    } else {
                        ttsEnabled = true;
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }
}