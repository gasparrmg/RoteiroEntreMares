package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

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

public class ImpactosFragment2 extends Fragment {

    // Views
    private TextView textViewTitle;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private TextView textViewContent;
    private FloatingActionButton fabFullscreen;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    private final int[] imageResourceIds = {
            R.drawable.img_impactos_4,
            R.drawable.img_impactos_5,
            R.drawable.img_impactos_6
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos2, container, false);

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
                if (tts.isSpeaking()) {
                    tts.stop();
                    item.setIcon(R.drawable.ic_volume);
                } else {
                    String text = HtmlCompat.fromHtml(
                            "O Oceano também nos dá a oportunidade de praticar desporto e outras atividades lúdicas de que muitos de nós adoram. Pode ajudar a inspirar as pessoas, como uma musa, ou a relaxar, como um spa. O turismo marinho trouxe pessoas de todo o mundo para uma interação próxima com o oceano. Neste sentido, o oceano é usado em quase todos os aspetos de nossa vida diária.<br><br>" +
                                    "Grande parte de nossa população global vive nas áreas costeiras ou próximo delas. Essas concentrações de pessoas tornam a necessidade de restaurar e proteger os ecossistemas adjacentes ainda mais importante.",
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
                "Relação entre o Homem e o Oceano",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "O Oceano também nos dá a oportunidade de praticar desporto e outras atividades lúdicas de que muitos de nós adoram. Pode ajudar a inspirar as pessoas, como uma musa, ou a relaxar, como um spa. O turismo marinho trouxe pessoas de todo o mundo para uma interação próxima com o oceano. Neste sentido, o oceano é usado em quase todos os aspetos de nossa vida diária.<br><br>" +
                        "Grande parte de nossa população global vive nas áreas costeiras ou próximo delas. Essas concentrações de pessoas tornam a necessidade de restaurar e proteger os ecossistemas adjacentes ainda mais importante.",
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
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment2_to_impactosFragment3);
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
}