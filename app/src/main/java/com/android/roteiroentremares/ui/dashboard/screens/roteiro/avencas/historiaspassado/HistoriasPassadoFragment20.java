package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.historiaspassado;

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class HistoriasPassadoFragment20 extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historias_passado20, container, false);
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
        SpannableString s = new SpannableString("Histórias do Passado");
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
                                "Com base nas observações realizadas, podemos concluir que as rochas da atual falésia se terão depositado debaixo de água." +
                                        "Este facto implica que o mar já esteve muito mais para o interior do nosso país. De facto, há cerca de 120-110Ma, terá ocorrido um episódio de subida do nível do mar, ou seja uma transgressão, neste local." +
                                        "Hoje sabemos que:<br>" +
                                        "- No final deste episódio de transgressão, o mar chegava próximo do meridiano de Coimbra.<br><br>" +
                                        "No entanto, hoje esta falésia encontra-se fora de água, ou seja, entretanto terá ocorrido o recuo do nível do mar (episódio de regressão) - período que vivemos atualmente.",
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
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_historiasPassadoFragment20_to_historiasPassadoFragment21);
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
                "Que mais histórias pode a Geologia contar?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString linkTransgressao = ClickableString.makeLinkSpan("transgressão", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Quando a linha de costa avança pelo continente, estamos perante uma <b>transgressão</b>, que resulta da subida do nível da água do mar ou da subsidência (“abaixamento”) do continente.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });

        SpannableString linkRegressao = ClickableString.makeLinkSpan("regressão", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Quando a linha de costa recua, estamos perante uma <b>regressão</b>, que resulta da descida do nível do mar ou da elevação dos continentes.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });

        textViewContent.setText(HtmlCompat.fromHtml(
                "Com base nas observações realizadas, podemos concluir que as rochas da atual falésia se terão depositado debaixo de água." +
                        "<br><br>" +
                        "Este facto implica que o mar já esteve muito mais para o interior do nosso país. De facto, há cerca de 120-110Ma, terá ocorrido um episódio de subida do nível do mar, ou seja uma ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.append(linkTransgressao);
        textViewContent.append(HtmlCompat.fromHtml(
                ", neste local.<br><br>" +
                        "Hoje sabemos que:<br>" +
                "- No final deste episódio de transgressão, o mar chegava próximo do meridiano de Coimbra.<br><br>" +
                "No entanto, hoje esta falésia encontra-se fora de água, ou seja, entretanto terá ocorrido o recuo do nível do mar (episódio de ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        textViewContent.append(linkRegressao);
        textViewContent.append(") - período em que vivemos atualmente");
        ClickableString.makeLinksFocusable(textViewContent);
    }
}