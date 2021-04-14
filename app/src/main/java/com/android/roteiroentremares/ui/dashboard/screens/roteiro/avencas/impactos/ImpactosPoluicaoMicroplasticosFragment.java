package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

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
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class ImpactosPoluicaoMicroplasticosFragment extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos_poluicao_microplasticos, container, false);

        initViews(view);
        setOnClickListeners(view);
        insertContent(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Impacto da ação humana");
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (tts.isSpeaking()) {
                    tts.stop();
                    item.setIcon(R.drawable.ic_volume);
                } else {
                    String text = HtmlCompat.fromHtml(
                            "Os microplásticos são um dos principais poluentes dos oceanos. Resultam da degradação progressiva dos plásticos, que acabam por ser convertidos em partículas de muito pequena dimensão (<5mm). Este tipo de material tem a capacidade de absorver produtos tóxicos, como pesticidas e metais pesados.<br>" +
                                    "<br>" +
                                    "Sendo partículas tão pequenas, são consumidas pelos organismos presentes no plâncton, que por sua vez são consumidos por organismos um pouco maiores, e assim sucessivamente, chegando aos peixes de maiores dimensões, e finalmente ao ser humano (Bioacumulação).<br>" +
                                    "<br>" +
                                    "Entre os problemas relacionados com esta intoxicação, estão diversos tipos de disfunções hormonais, imunológicas, neurológicas e reprodutivas.",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

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

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent(View view) {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Microplásticos",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "Os microplásticos são um dos principais poluentes dos oceanos. Resultam da degradação progressiva dos plásticos, que acabam por ser convertidos em partículas de muito pequena dimensão (menores do que 5mm). Este tipo de material tem a capacidade de absorver produtos tóxicos, como pesticidas e metais pesados.<br>" +
                        "<br>" +
                        "Sendo partículas tão pequenas, são consumidas pelos organismos presentes no plâncton, que por sua vez são consumidos por organismos um pouco maiores, e assim sucessivamente, chegando aos peixes de maiores dimensões, e finalmente ao ser humano (Bioacumulação).<br>" +
                        "<br>" +
                        "Entre os problemas relacionados com esta intoxicação, estão diversos tipos de disfunções hormonais, imunológicas, neurológicas e reprodutivas.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}