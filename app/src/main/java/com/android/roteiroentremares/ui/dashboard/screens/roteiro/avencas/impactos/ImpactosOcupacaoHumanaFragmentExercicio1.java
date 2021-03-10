package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImpactosOcupacaoHumanaFragmentExercicio1 extends Fragment {

    private ArtefactosViewModel artefactosViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextInputEditText textInputEditTextResposta;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos_ocupacao_humana_exercicio1, container, false);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

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
                            "<b>TAREFA:</b><br><br><b>1.</b> Observa bem as arribas que rodeiam esta praia e procura exemplos de ocupação, ou intervenção, humana nestas arribas. Escreve aqui os problemas que identificaste.",
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
        textInputEditTextResposta = view.findViewById(R.id.textinputedittext_resposta);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        textInputEditTextResposta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    buttonFabNext.setVisibility(View.VISIBLE);
                    buttonFabNext.setEnabled(true);
                } else {
                    buttonFabNext.setVisibility(View.INVISIBLE);
                    buttonFabNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                // Guardar nos Artefactos
                    // If !Explorador -> Partilhar

                Artefacto newTextArtefacto = new Artefacto(
                        "Impactos da ação humana (ocupação humana das arribas) - Observa bem as arribas que rodeiam esta praia e procura exemplos de ocupação, ou intervenção, humana nestas arribas. Escreve aqui os problemas que identificaste.",
                        textInputEditTextResposta.getText().toString(),
                        0,
                        "",
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                        "",
                        "",
                        artefactosViewModel.getCodigoTurma(),
                        false
                );

                artefactosViewModel.insertArtefacto(newTextArtefacto);

                Toast.makeText(getActivity(), "A tua resposta foi guardada nos teus Artefactos!", Toast.LENGTH_LONG).show();

                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                //inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                Navigation.findNavController(view).navigate(R.id.action_impactosOcupacaoHumanaFragmentExercicio1_to_impactosOcupacaoHumanaFragmentExercicio2);
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
                "<b>TAREFA:</b><br><br><b>1.</b> Observa bem as arribas que rodeiam esta praia e procura exemplos de ocupação, ou intervenção, humana nestas arribas. Escreve aqui os problemas que identificaste.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}