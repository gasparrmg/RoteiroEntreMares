package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class RiaFormosaIntertidalArenosoFragment5 extends Fragment {

    private static final String htmlContent = "No local onde te encontras, a zona que podes explorar corresponde essencialmente ao andar mediolitoral (que fica entre as zonas supralitoral e infralitoral) e à franja do infralitoral.<br><br>" +
            "No entanto, consegues observar alguns organismos típicos da zona supralitoral, como por exemplo o Búzio negro (Melaraphe neritoides) (campeão da sobrevivência na zona entremarés, pois consegue permanecer dias sem água, inativo e quando a água regressa recupera a sua atividade).";

    private static final int imageResourceId = R.drawable.img_guiadecampo_buzionegro_1;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private ImageView imageView;
    private FloatingActionButton fabFullscreen;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_sapal5, container, false);

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

        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        imageView = view.findViewById(R.id.imageView);
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
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment5_to_riaFormosaIntertidalArenosoFragment6);
            }
        });

        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        imageView.setImageResource(imageResourceId);

        textViewTitle.setText("Zonação");

        textViewContent.setText(HtmlCompat.fromHtml(
                "No local onde te encontras, a zona que podes explorar corresponde essencialmente ao andar mediolitoral (que fica entre as zonas supralitoral e infralitoral) e à franja do infralitoral.<br><br>" +
                        "No entanto, consegues observar alguns organismos típicos da zona supralitoral, como por exemplo o ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString link = ClickableString.makeLinkSpan("Búzio Negro", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                EspecieRiaFormosa especieBuzio = new EspecieRiaFormosa(
                        "Búzio-negro",
                        "Melaraphe neritoides",
                        "Intertidal arenoso",
                        "Fauna",
                        "Moluscos (Mollusca)",
                        "Género de pequenos caracóis marinhos. \n" +
                                "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, em forma de palmilha. Concha lisa mais alta que larga de cor cinzenta ou negra. \n" +
                                "Espécie característica do supralitoral, zona que apanha somente com salpicos de água.",
                        "Herbívoros (algas e microalgas)",
                        "Vive em fissuras das rochas, locais onde há concentração de humidade. Pode encontrar-se também em superfícies expostas.",
                        "",
                        "",
                        new ArrayList<String>(Arrays.asList(
                                "img_guiadecampo_buzionegro_1",
                                "img_guiadecampo_buzionegro_2"
                        )),
                        new ArrayList<String>(Arrays.asList()),
                        new ArrayList<String>(Arrays.asList()),
                        "",
                        ""
                );

                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("especie", especieBuzio);
                startActivity(intent);
            }
        });

        textViewContent.append(link);
        textViewContent.append(" ");
        textViewContent.append(HtmlCompat.fromHtml(
                        "(<i>Melaraphe neritoides</i>) (campeão da sobrevivência na zona entremarés, pois consegue permanecer dias sem água, inativo e quando a água regressa recupera a sua atividade).",
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