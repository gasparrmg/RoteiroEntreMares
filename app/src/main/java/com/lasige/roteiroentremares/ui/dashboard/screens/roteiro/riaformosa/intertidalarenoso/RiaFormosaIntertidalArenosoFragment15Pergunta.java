package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaIntertidalArenosoFragment15Pergunta extends Fragment {

    private ArtefactosViewModel artefactosViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextInputEditText textInputEditTextResposta;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonCharts;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao6_pergunta_4, container, false);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);
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
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_intertidalarenoso_title));
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
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                "Percorre este novo local, procurando observar as espécies estão presentes.<br>" +
                                        "<br>" +
                                        "Com base nas tuas observações, achas que a diversidade é igual ao local explorado anteriormente (3b)? Como explicas as diferenças encontradas? (anota a tua opinião no campo abaixo)",
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
        textInputEditTextResposta = view.findViewById(R.id.textinputedittext_resposta);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonCharts = view.findViewById(R.id.button_charts);
        buttonCharts.setVisibility(View.GONE);
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

                if (textInputEditTextResposta.getText().toString().trim().length() == 0) {
                    // if empty
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_question_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // next no save
                            InputMethodManager inputManager = (InputMethodManager)
                                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment15Pergunta_to_riaFormosaIntertidalArenosoFragment16);
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                } else {
                    Artefacto newTextArtefacto = new Artefacto(
                            "Intertidal Arenoso - Com base nas tuas observações, achas que a diversidade é igual ao local explorado anteriormente (3b)? Como explicas as diferenças encontradas?",
                            textInputEditTextResposta.getText().toString(),
                            0,
                            "",
                            Calendar.getInstance().getTime(),
                            "",
                            "",
                            artefactosViewModel.getCodigoTurma(),
                            false
                    );

                    artefactosViewModel.insertArtefacto(newTextArtefacto);

                    Toast.makeText(getActivity(), "A tua resposta foi guardada nos teus Artefactos!", Toast.LENGTH_LONG).show();

                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment15Pergunta_to_riaFormosaIntertidalArenosoFragment16);
                }
            }
        });

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

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Para pensar...",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "Percorre este novo local, procurando observar as espécies estão presentes.<br>" +
                        "<br>" +
                        "Com base nas tuas observações, achas que a diversidade é igual ao local explorado anteriormente (3b)? Como explicas as diferenças encontradas? (anota a tua opinião no campo abaixo)",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}