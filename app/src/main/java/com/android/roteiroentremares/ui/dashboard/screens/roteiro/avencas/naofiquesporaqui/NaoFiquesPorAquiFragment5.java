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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NaoFiquesPorAquiFragment5 extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private ExtendedFloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nao_fiques_por_aqui5, container, false);

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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (tts.isSpeaking()) {
                    tts.stop();
                } else {
                    String text = HtmlCompat.fromHtml(
                            "<b>Pescador:</b><br>" +
                                    "- Apanha de organismo para consumo e venda (Mexilhão, percebes, polvos, navalheira, peixes, etc)<br>" +
                                    "- As regras impostas para a apanha de organismos reduzem muito as quantidades que se podem pescar<br>" +
                                    "- Por outro lado, não convém esgotar os recursos para não terminar a pesca/apanha<br>" +
                                    "<br>" +
                                    "<b>Turismo:</b><br>" +
                                    "- As regras impostas de acesso à zona, podem limitar muito o usufruto da praia pelos turistas<br>" +
                                    "- Por outro lado, a AMP poderá chamar outro tipo de turistas<br>" +
                                    "<br>" +
                                    "<b>Biólogo:</b><br>" +
                                    "- É uma zona de grande riqueza de espécies, e de crescimento e alimentação de espécies comercializadas, pelo que deve ser conservada<br>" +
                                    "- Podem haver regras que limitam a exploração desenfreada, e ajudam as espécies a recuperar (períodos do ano - época de reprodução, limites de tamanho de captura - primeira reprodução, limite de quantidade de apanha por pescador).",
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
                // Navigation.findNavController(view).navigate(R.id.action_global_roteiroFragment);
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.setNaoFiquesPorAquiAsFinished();
                Navigation.findNavController(view).popBackStack(R.id.roteiroFragment ,false);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Dicas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Pescador:</b><br>" +
                        "- Apanha de organismo para consumo e venda (Mexilhão, percebes, polvos, navalheira, peixes, etc)<br>" +
                        "- As regras impostas para a apanha de organismos reduzem muito as quantidades que se podem pescar<br>" +
                        "- Por outro lado, não convém esgotar os recursos para não terminar a pesca/apanha<br>" +
                        "<br>" +
                        "<b>Turismo:</b><br>" +
                        "- As regras impostas de acesso à zona, podem limitar muito o usufruto da praia pelos turistas<br>" +
                        "- Por outro lado, a AMP poderá chamar outro tipo de turistas<br>" +
                        "<br>" +
                        "<b>Biólogo:</b><br>" +
                        "- É uma zona de grande riqueza de espécies, e de crescimento e alimentação de espécies comercializadas, pelo que deve ser conservada<br>" +
                        "- Podem haver regras que limitam a exploração desenfreada, e ajudam as espécies a recuperar (períodos do ano - época de reprodução, limites de tamanho de captura - primeira reprodução, limite de quantidade de apanha por pescador).",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

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