package com.lasige.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.lasige.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaOnBoardingFragment1 extends Fragment {

    private static final int SEQUENCE_NUMBER = 1;

    private OnBoardingViewModel onBoardingViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageView imageView;
    private FloatingActionButton fabFullscreen;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_on_boarding1, container, false);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        /*if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() != -1) {
            Log.d("ONBOARDING", "toChange value: " + onBoardingViewModel.getChangeToAvencasOrRiaFormosa());

            onBoardingViewModel.deleteChangeToAvencasOrRiaFormosa();

            Log.d("ONBOARDING", "toChange value: " + onBoardingViewModel.getChangeToAvencasOrRiaFormosa());
        }*/

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        imageView = view.findViewById(R.id.imageView);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        viewPager = getActivity().findViewById(R.id.viewPager_riaformosa);

        setOnClickListeners();
        insertContent();

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER);
            }
        });

        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, R.drawable.img_riaformosa_onboarding_1);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        imageView.setImageResource(R.drawable.img_riaformosa_onboarding_1);

        textViewTitle.setText(HtmlCompat.fromHtml(
                "Ria Formosa",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "Um vale fluvial inundado designa-se de “ria”, logo, a Ria Formosa não corresponde a uma verdadeira “ria”. Ria Formosa é um nome próprio e, na realidade, corresponde a um sistema lagunar.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}