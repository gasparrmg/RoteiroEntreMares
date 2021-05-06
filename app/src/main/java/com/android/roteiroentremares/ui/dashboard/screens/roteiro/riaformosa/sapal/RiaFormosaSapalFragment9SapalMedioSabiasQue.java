package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.sapal;

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

import java.util.Locale;

public class RiaFormosaSapalFragment9SapalMedioSabiasQue extends Fragment {

    private static final String htmlContent = "<b>Endemismo</b> – Em biologia, chamam-se endemismos a espécies que se desenvolveram numa região restrita.<br>" +
            "<br>" +
            "No caso do <i>Limonium algarvense</i>, repara que o epíteto específico está associado à região do Algarve; e no caso do <i>Halimione portucaloides</i>, está associado ao nosso país. Para ficares a conhecer as espécies endémicas de Portugal carrega neste link.";

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
        View view = inflater.inflate(R.layout.fragment_riaformosa_nao_fiques_por_aqui, container, false);

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
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_sapal_title));
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
                                htmlContent,
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
        textViewTitle.setVisibility(View.GONE);

        textViewContent = view.findViewById(R.id.text_content);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonFabNext.setVisibility(View.INVISIBLE);
        buttonFabNext.setEnabled(false);

        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Endemismo</b> – Em biologia, chamam-se endemismos a espécies que se desenvolveram numa região restrita.<br>" +
                        "<br>" +
                        "No caso do <i>Limonium algarvense</i>, repara que o epíteto específico está associado à região do Algarve; e no caso do <i>Halimione portucaloides</i>, está associado ao nosso país. <br><br>Para ficares a conhecer as espécies endémicas de Portugal carrega neste ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString link = ClickableString.makeLinkSpan("link", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://naturdata.com/especies-endemicas-de-portugal/"));
                startActivity(browserIntent);
            }
        });

        textViewContent.append(link);
        textViewContent.append(".");

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