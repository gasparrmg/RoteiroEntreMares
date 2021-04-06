package com.android.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderAdapter;
import com.android.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class RiaFormosaOnBoardingFragment2 extends Fragment {

    private static final int SEQUENCE_NUMBER = 2;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private FloatingActionButton fabFullscreen;

    private final int[] imageResourceIds = {
            R.drawable.img_riaformosa_onboarding_2,
            R.drawable.img_riaformosa_onboarding_3
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_on_boarding2, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        viewPager = getActivity().findViewById(R.id.viewPager_riaformosa);

        initSliderView(view);

        setOnClickListeners();
        insertContent();

        return view;
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
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User finished onBoarding sequence
                viewPager.setCurrentItem(SEQUENCE_NUMBER);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER-2);
            }
        });

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
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Ria Formosa",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "É um sistema lagunar formado por sapais, razos de maré, canais de maré e pequenas ilhas e penínsulas de caráter lodoso ou arenoso, que se localiza no sotavento algarvio." +
                        "<br><br>" +
                        "É constituída por um sistema de cinco ilhas-barreira (Cabanas, Tavira, Armona, Culatra, Deserta) e duas penínsulas (Cacela, Ancão), que estão separadas por seis barras (duas delas são artificiais). As ilhas-barreira separam a laguna (que se localiza entre a parte continental emersa e as ilhas-barreira) do oceano.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}