package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.pradariasmarinhas;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class RiaFormosaPradariasMarinhasFragment7 extends Fragment {

    private static final String htmlContent = "<b>1. Manutenção da biodiversidade</b><br>" +
            "<br>" +
            "- É um local de reprodução muito importante para <b>peixes e invertebrados</b> (tais como os chocos, as raias e as lesmas-do-mar).<br>" +
            "<br>" +
            "- São zonas privilegiadas de alimentação de espécies comercialmente importantes de peixes, moluscos e crustáceos, e são usados como maternidade por muitas destas espécies, suportando o setor das <b>pescas</b>.";

    private static final int imageResourceId = R.drawable.img_pradariasmarinhas_7;

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonSabiasQue;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_pradariasmarinhas7_refactored, container, false);

        Glide.with(getActivity())
                .load(imageResourceId)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

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
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_pradariasmarinhas_title));
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
        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonSabiasQue = view.findViewById(R.id.button_sabiasque);

        /*fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        imageView = view.findViewById(R.id.imageView);*/
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
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaPradariasMarinhasFragment7_to_riaFormosaPradariasMarinhasFragment8);
            }
        });

        /*fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_INFO, "Postura de ovo de choco");
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });*/

        buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Next screen
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaPradariasMarinhasFragment7_to_riaFormosaPradariasMarinhasFragment7SabiasQue1);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        // imageView.setImageResource(imageResourceId);

        textViewTitle.setText(HtmlCompat.fromHtml(
                "Qual a importância de preservar estes locais?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

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