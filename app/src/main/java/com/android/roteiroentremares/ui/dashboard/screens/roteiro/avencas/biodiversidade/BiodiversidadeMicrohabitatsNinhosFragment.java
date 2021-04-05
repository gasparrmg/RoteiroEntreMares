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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.common.MediaPlayerResourceActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderAdapter;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderDescriptionAdapter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Locale;

public class BiodiversidadeMicrohabitatsNinhosFragment extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private SliderView sliderView;
    private SliderDescriptionAdapter sliderAdapter;
    private FloatingActionButton fabFullscreen;
    private FloatingActionButton fabVideo1;
    private FloatingActionButton fabVideo2;
    private ImageView imageViewVideo1;
    private ImageView imageViewVideo2;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    private final int[] imageResourceIds = {
            R.drawable.img_biodiversidade_microhabitats_ninhos_marachomba,
            R.drawable.img_biodiversidade_microhabitats_ninhos_caboz
    };

    private final String[] descriptions = {
            "Ninho de marachomba",
            "Postura de caboz"
    };

    private final int[] videoResourceIds = {
            R.raw.vid_biodiversidade_microhabitats_ninhos_parablennius,
            R.raw.vid_biodiversidade_microhabitats_ninhos_parablennius_embriao
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_microhabitats_ninhos, container, false);

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
        SpannableString s = new SpannableString("Biodiversidade");
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
                            "Algumas das espécies de peixes que colonizam as plataformas  rochosas colocam os ovos em pequenas fendas, ou debaixo de pedras. Apesar destas fendas ficarem a descoberto durante todo o período de maré-baixa, são locais que mantém uma grande percentagem de humidade, permitindo a sobrevivência dos peixes." +
                                    "Em geral, são os machos que cuidam dos ovos, protegendo-os de predadores (como os caranguejos), e limpando-os das pequenas impurezas que se vão depositando (roçando com o corpo nos ovos e provocando a agitação da água com a ajuda das barbatanas)." +
                                    "Quando o ovo eclode (em geral ao fim de cerca de 15 dias), surge uma larva que demora cerca de 1 mês a  transforma-se em juvenil, ou seja, a adquirir as características iguais ao adulto.",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment, false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewTitle2 = view.findViewById(R.id.text_title2);
        fabVideo1 = view.findViewById(R.id.fab_video1);
        fabVideo2 = view.findViewById(R.id.fab_video2);
        imageViewVideo1 = view.findViewById(R.id.imageView_video1);
        imageViewVideo2 = view.findViewById(R.id.imageView_video2);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        buttonPrev = view.findViewById(R.id.btn_prev);

        initSliderView(view);
    }

    private void initSliderView(View view) {
        sliderView = view.findViewById(R.id.imageSlider);

        sliderAdapter = new SliderDescriptionAdapter(getActivity(), imageResourceIds, descriptions);
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
                "Microhabitats",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                "Fendas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        Glide.with(getActivity())
                .load(R.raw.vid_biodiversidade_microhabitats_ninhos_parablennius)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewVideo1);

        Glide.with(getActivity())
                .load(R.raw.vid_biodiversidade_microhabitats_ninhos_parablennius_embriao)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewVideo2);
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

        fabVideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open video
                Intent intent = new Intent(getActivity(), MediaPlayerResourceActivity.class);
                intent.putExtra(MediaPlayerResourceActivity.INTENT_KEY_VIDEO_RESOURCE, videoResourceIds[0]);
                intent.putExtra(MediaPlayerResourceActivity.INTENT_KEY_INFO, "Parablennius pilicornis a tomar conta dos ovos - Vídeo da autoria de Cláudia Faria, em colaboração com o Aquário Vasco da Gama");
                startActivity(intent);
            }
        });

        fabVideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open video
                Intent intent = new Intent(getActivity(), MediaPlayerResourceActivity.class);
                intent.putExtra(MediaPlayerResourceActivity.INTENT_KEY_VIDEO_RESOURCE, videoResourceIds[1]);
                intent.putExtra(MediaPlayerResourceActivity.INTENT_KEY_INFO, "Desenvolvimento embrionário do Parablennius ruber - Vídeo da autoria de Cláudia Faria, em colaboração com o Aquário Vasco da Gama");
                startActivity(intent);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
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