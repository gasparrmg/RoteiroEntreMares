package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment1 extends Fragment {

    private static final int SEQUENCE_NUMBER = 1;

    // Views
    private FloatingActionButton buttonFabNext;
    private TextView textWelcome;
    private ViewPager2 viewPager;

    public OnBoardingFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding1, container, false);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        textWelcome = view.findViewById(R.id.text_welcome);
        viewPager = getActivity().findViewById(R.id.viewPager);

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
        textWelcome.setText(HtmlCompat.fromHtml(
                "<b>Explora, aprende, diverte-te mas não te esqueças, com cuidado.</b><br> Muda o pensamento, olha à tua volta observa o que está debaixo dos teus pés, debaixo de uma pedra ou mesmo à vista de todos. Para um pouco e escuta, tenta perceber o que ouves… pessoas, barcos, aves marinhas? Mostra aos outros o que é uma zona entre marés, o que lá vive, a sua dinâmica, as adaptações dos seus organismos, as ameaças que enfrentam, e porque é tão importante conservar estes habitats com características únicas.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}