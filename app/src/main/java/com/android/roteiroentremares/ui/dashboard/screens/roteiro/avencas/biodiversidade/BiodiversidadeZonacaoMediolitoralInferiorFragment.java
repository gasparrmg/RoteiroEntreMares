package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderAdapter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Locale;

public class BiodiversidadeZonacaoMediolitoralInferiorFragment extends Fragment {

    private static final String htmlContent = "Zona que fica totalmente descoberta na maré-baixa, e totalmente submersa na maré-cheia.";

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private TextView textViewContent;
    private FloatingActionButton fabFullscreen;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private final int[] imageResourceIds = {
            R.drawable.img_biodiversidade_zonacao_mediolitoral_inferior_1,
            R.drawable.img_biodiversidade_zonacao_mediolitoral_inferior_2
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao_mediolitoral_inferior, container, false);

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
        SpannableString s = new SpannableString(getResources().getString(R.string.avencas_biodiversidade_title));
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
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        initSliderView(view);
    }

    private void initSliderView(View view) {
        sliderView = view.findViewById(R.id.imageSlider);

        sliderAdapter = new SliderAdapter(getActivity(), imageResourceIds);
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Zonação",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                "Mediolitoral - Horizonte inferior",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }

    private void setOnClickListeners(View view) {
        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                int currentImageResource = imageResourceIds[sliderView.getCurrentPagePosition()];

                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, currentImageResource);
                startActivity(intent);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoMediolitoralInferiorFragment_to_biodiversidadeZonacaoMediolitoralInferiorFragment2);
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