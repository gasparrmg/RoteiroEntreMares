package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RiaFormosaOnBoardingFragment4 extends Fragment {

    private static final int SEQUENCE_NUMBER = 4;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_on_boarding3, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
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
                "A Ria Formosa é uma <b>zona húmida</b> muito importante que ocupa uma área de aproximadamente 100 Km2. No Algarve a Ria Formosa é o ecossistema costeiro mais importante." +
                        "<br><br>" +
                        "Possui diversos tipos de <u><b>habitats</b></u>, nomeadamente: Lagoas de água doce e salobra; Cursos de água; Áreas agrícolas; Mata; Salinas; Sapais; Bancos de areia e de vasa; Dunas; Ilhas-barreira; Pradarias marinhas.<br><br>Propomos que explores alguns destes locais através desta App.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}