package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImpactosCapturaExcessivaFragment2 extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    //private ImageView imageView;
    private TextView textViewContent;
    //private FloatingActionButton fabFullscreen;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private final int imageResourceId = R.drawable.img_impactos_capturaexcessiva;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos_captura_excessiva2, container, false);

        Glide.with(getActivity())
                .load(R.drawable.img_impactos_capturaexcessiva_ilustracao)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        ttsEnabled = false;
        initViews(view);
        insertContent();
        setOnClickListeners(view);

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
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                "<b>A solução para controlar este problema será a realização de uma pesca sustentável:</b><br><br>" +
                                        "- Definir um limite à quantidade de organismos capturados por cada pescador<br>" +
                                        "- Definir alturas do ano em que a apanha de certas espécies é proibida (correspondentes à respetiva época de reprodução)<br>" +
                                        "- Limitar o tamanho mínimo de captura de cada espécie (de forma a permitir que o organismo se reproduza pelo menos uma vez)",
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
        //imageView = view.findViewById(R.id.imageview_impactos);
        textViewContent = view.findViewById(R.id.text_content);
        //fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        //imageView.setImageResource(imageResourceId);

        textViewTitle.setText(HtmlCompat.fromHtml(
                "Captura excessiva",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>A solução para controlar este problema será a realização de uma pesca sustentável</b><br>" +
                        "- Definir um limite à quantidade de organismos capturados por cada pescador<br>" +
                        "- Definir alturas do ano em que a apanha de certas espécies é proibida (correspondentes à respetiva época de reprodução)<br>" +
                        "- Limitar o tamanho mínimo de captura de cada espécie (de forma a permitir que o organismo se reproduza pelo menos uma vez)",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }

    private void setOnClickListeners(View view) {
        /*fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });*/

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
                dashboardViewModel.setImpactosCapturaExcessivaAsFinished();
                Navigation.findNavController(view).popBackStack(R.id.impactosFragment3 ,false);
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
}