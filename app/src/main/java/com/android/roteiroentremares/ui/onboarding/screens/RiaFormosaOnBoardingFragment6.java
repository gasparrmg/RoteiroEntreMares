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
import com.android.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RiaFormosaOnBoardingFragment6 extends Fragment {

    private static final int SEQUENCE_NUMBER = 6;

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
                viewPager.setCurrentItem(SEQUENCE_NUMBER - 2);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Recomendações",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                        "a) Recomenda-se que os participantes calcem <u>sapatos com sola aderente</u> ao substrato (sobretudo às rochas e algas escorregadias) e <u>mudem de calçado e de roupa</u> no fim da saída de campo.<br>" +
                        "b) Recomenda-se também a utilização de chapéu, de óculos de sol e de protetor solar com índice de proteção UV elevado.<br>" +
                        "<br>" +
                        "<u>Material:</u> Balde ou outro recipiente de plástico, Camaroeiro, Caderno de campo, Lápis, Máquina fotográfica.<br>" +
                        "<br>" +
                        "<u>Material opcional:</u> Canivete, Sacos de plástico com fecho Zip, Marcador/Caneta.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}