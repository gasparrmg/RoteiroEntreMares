package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaIntertidalArenosoFragmentCompeticao1 extends Fragment {

    private ArtefactosViewModel artefactosViewModel;
    private DashboardViewModel dashboardViewModel;


    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;
    private Button buttonFigura;
    private FloatingActionButton buttonFabNext;
    private TextInputEditText textInputEditTextResposta;
    private ImageButton buttonPrev;

    private int imageResourceId = R.drawable.img_biodiversidade_interacoes_competicao;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_interacoes_competicao, container, false);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
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
                                "As espécies de cracas presentes nestas plataformas, Chthamalus spp (cracas mais pequenas) e Balanus sp (cracas em forma de vulcão) competem entre si pelo mesmo espaço. Sabendo que:" +
                                        "Intervalo de distribuição potencial: corresponde ao intervalo de distribuição em que a espécie sobrevive;" +
                                        "Intervalo de distribuição real: Corresponde ao intervalo de distribuição em que a espécie de facto está presente." +
                                        "Analisa a figura através do botão abaixo e explica qual das duas espécies será competitivamente mais forte, “empurrando” a outra espécie para uma zona menos favorável à sobrevivência." +
                                        "Discute com os teus colegas e escreve a tua resposta",
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

        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewTitle2.setVisibility(View.GONE);

        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonFigura = view.findViewById(R.id.button_figura);
        textInputEditTextResposta = view.findViewById(R.id.textinputedittext_resposta);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        buttonFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });

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

                            //inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            dashboardViewModel.setRiaFormosaIntertidalArenosoCompeticaoAsFinished();
                            Navigation.findNavController(view).popBackStack(R.id.riaFormosaIntertidalArenosoFragment18Menu ,false);
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
                            "Intertidal Arenoso // Interações // Competição - Explica qual das duas espécies será competitivamente mais forte, “empurrando” a outra espécie para uma zona menos favorável à sobrevivência",
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

                    dashboardViewModel.setRiaFormosaIntertidalArenosoCompeticaoAsFinished();
                    Navigation.findNavController(view).popBackStack(R.id.riaFormosaIntertidalArenosoFragment18Menu ,false);
                }
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {

        textViewTitle.setText(HtmlCompat.fromHtml(
                "Competição",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "As espécies de cracas presentes nestas plataformas, ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        // link to especie
        SpannableString linkEspecie1 = ClickableString.makeLinkSpan("Chthamalus spp", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Especie

                EspecieAvencas especieAvencas1 = new EspecieAvencas(
                        "Cracas pequenas",
                        "Chthamalus spp",
                        "Crustáceos (Arthropoda)",
                        "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                                "Presentes no mediolitoral.",
                        "Filtradores (alimentam-se de partículas em suspensão na água)",
                        "Fecham-se no interior das placas",
                        "",
                        false,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        5,
                        new ArrayList<String>(Arrays.asList(
                                "img_guiadecampo_cracaspequenas_1"
                        )),
                        new ArrayList<String>(Arrays.asList()),
                        ""
                );

                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("especie", especieAvencas1);
                startActivity(intent);
            }
        });

        textViewContent.append(linkEspecie1);

        // link to especie 2
        SpannableString linkEspecie2 = ClickableString.makeLinkSpan("Balanus sp", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Especie
                EspecieAvencas especieAvencas2 = new EspecieAvencas(
                        "Cracas grandes",
                        "Balanus sp",
                        "Crustáceos (Arthropoda)",
                        "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                                "Presentes no mediolitoral.",
                        "Filtradores (alimentam-se de partículas em suspensão na água)",
                        "Fecham-se no interior das placas",
                        "",
                        false,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        5,
                        new ArrayList<String>(Arrays.asList(
                                "img_guiadecampo_cracasgrandes_1"
                        )),
                        new ArrayList<String>(Arrays.asList()),
                        ""
                );

                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("especie", especieAvencas2);
                startActivity(intent);
            }
        });

        textViewContent.append(" (cracas mais pequenas) e ");
        textViewContent.append(linkEspecie2);
        textViewContent.append(" ");

        // append rest
        textViewContent.append(HtmlCompat.fromHtml(
                " (cracas em forma de vulcão) competem entre si pelo mesmo espaço.<br><br>" +
                "<b>Sabendo que:</b><br>" +
                "- Intervalo de distribuição potencial: corresponde ao intervalo de distribuição em que a espécie sobrevive;<br>" +
                "- Intervalo de distribuição real: Corresponde ao intervalo de distribuição em que a espécie de facto está presente.<br><br>" +
                "Analisa a figura através do botão abaixo e explica qual das duas espécies será competitivamente mais forte, “empurrando” a outra espécie para uma zona menos favorável à sobrevivência.<br><br>Discute com os teus colegas e escreve a tua resposta",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        ClickableString.makeLinksFocusable(textViewContent);

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