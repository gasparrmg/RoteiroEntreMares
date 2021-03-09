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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImpactosOcupacaoHumanaFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos_ocupacao_humana, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

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
                            "<b>Para investigar:<b><br><br>" +
                                    "A ocupação humana das zonas costeiras em áreas de maior vulnerabilidade é o principal fator responsável pela aceleração dos fenómenos erosivos, ao alterar a dinâmica dos processos naturais. As arribas deste local (entre a Praia da Bafureira e a praia das Avencas) apresentam sinais evidentes  de grande instabilidade geológica.<br>" +
                                    "<br>" +
                                    "No entanto, este local é um dos setores costeiros da linha de Cascais onde a ocupação humana é mais intensa, sendo muitos os casos de edificações situadas junto ao bordo superior das arribas.<br>" +
                                    "<br>" +
                                    "<b>1.</b> Observa bem e procura exemplos de problemas de ocupação humana das arribas.<br>" +
                                    "<b>2.</b> Investiga, e discute com os teus colegas, quais os riscos naturais que podem ser agravados por uma intervenção antrópica (ações realizadas pelo ser humano) desadequada na área envolvente a estas praias.",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
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
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set path as done
                dashboardViewModel.setImpactosOcupacaoHumanaAsFinished();
                Navigation.findNavController(view).popBackStack(R.id.impactosFragment3 ,false);
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
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Ocupação humana das arribas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Para investigar:</b><br><br>" +
                        "A ocupação humana das zonas costeiras em áreas de maior vulnerabilidade é o principal fator responsável pela aceleração dos fenómenos erosivos, ao alterar a dinâmica dos processos naturais. As arribas deste local (entre a Praia da Bafureira e a praia das Avencas) apresentam sinais evidentes  de grande instabilidade geológica.<br>" +
                        "<br>" +
                        "No entanto, este local é um dos setores costeiros da linha de Cascais onde a ocupação humana é mais intensa, sendo muitos os casos de edificações situadas junto ao bordo superior das arribas.<br>" +
                        "<br>" +
                        "<b>1.</b> Observa bem e procura exemplos de problemas de ocupação humana das arribas.<br>" +
                        "<b>2.</b> Investiga, e discute com os teus colegas, quais os riscos naturais que podem ser agravados por uma intervenção antrópica (ações realizadas pelo ser humano) desadequada na área envolvente a estas praias.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}