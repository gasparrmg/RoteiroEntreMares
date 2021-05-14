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
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class RiaFormosaIntertidalArenosoFragment2 extends Fragment {

    private static final String htmlContent = "Devido às variações periódicas das condições abióticas associadas  ao ciclo das marés, estabelecem-se determinadas condições ambientais, sensivelmente constantes  em função da situação  em relação ao nível do mar, que vão condicionar a distribuição dos organismos. É possível observar povoamentos de organismos em cada uma destas zonas, que vão constituir diferentes andares. Nestes locais é comum considerarmos os seguintes andares: supralitoral, mediolitoral e infralitoral.";

    private static final int imageResourceId = R.drawable.img_biodiversidade_zonacao_esquema_ilustracao;

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
        View view = inflater.inflate(R.layout.fragment_riaformosa_sapal5_refactored, container, false);

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
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment2_to_riaFormosaIntertidalArenosoFragment3);
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
        Glide.with(getActivity()).load(imageResourceId).into(imageView);

        textViewTitle.setText("Zonação");

        textViewContent.setText(HtmlCompat.fromHtml(
                "Devido às variações periódicas das condições abióticas associadas  ao ciclo das marés, estabelecem-se determinadas condições ambientais, sensivelmente constantes  em função da situação  em relação ao nível do mar, que vão condicionar a distribuição dos organismos. É possível observar povoamentos de organismos em cada uma destas zonas, que vão constituir diferentes andares. Nestes locais é comum considerarmos os seguintes andares: ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString linkSupralitoral = ClickableString.makeLinkSpan("supralitoral", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Zona que se encontra imediatamente acima do limite máximo da maré-cheia.  Está sujeito à aspersão por gotículas de água provenientes das ondas.",
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

        textViewContent.append(linkSupralitoral);
        textViewContent.append(", ");

        SpannableString linkMediolitoral = ClickableString.makeLinkSpan("mediolitoral", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Zona totalmente compreendida na zona das marés. Fica totalmente descoberta na maré-baixa, e totalmente submersa na maré-cheia.",
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

        textViewContent.append(linkMediolitoral);
        textViewContent.append(" e ");

        SpannableString linkInfralitoral = ClickableString.makeLinkSpan("infralitoral", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Dialog w info
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Estende-se desde o limite inferior do andar mediolitoral até à profundidade compatível  com a existência de algas (20-24m de profundidade). Na maré-baixa apenas é visível uma pequena franja.",
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

        textViewContent.append(linkInfralitoral);
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