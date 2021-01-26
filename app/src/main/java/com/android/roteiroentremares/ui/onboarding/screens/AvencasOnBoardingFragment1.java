package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class AvencasOnBoardingFragment1 extends Fragment {

    private static final int SEQUENCE_NUMBER = 1;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ViewPager2 viewPager;

    public AvencasOnBoardingFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avencas_on_boarding1, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        viewPager = getActivity().findViewById(R.id.viewPager_avencas);

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
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Área Marinha Protegida das Avencas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "A plataforma rochosa que se encontra na zona entre marés apresenta um ambiente caracterizado por condições de grande instabilidade, resultantes do facto de constituir um habitat de interface entre o meio marinho e o meio terrestre." +
                        "<br><br>" +
                        "As espécies marinhas que colonizam os habitats rochosos da zona entre-marés estão sujeitas a um conjunto de variações periódicas das condições abióticas com diferentes ciclos, desde os anuais aos diários e de maré, nomeadamente da temperatura, salinidade, humidade e hidrodinamismo, e a acentuadas flutuações em função das condições meteorológicas.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}