package com.lasige.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class OnBoardingFragment17 extends Fragment {

    private static final int SEQUENCE_NUMBER = 16;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private CircularProgressIndicator progressBar;
    private ViewPager2 viewPager;

    public OnBoardingFragment17() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding17, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        progressBar = view.findViewById(R.id.progressBar);
        viewPager = getActivity().findViewById(R.id.viewPager);

        setOnClickListeners();
        insertContent();

        // Progress Bar update
        progressBar.setMax(viewPager.getAdapter().getItemCount());
        progressBar.setProgress(SEQUENCE_NUMBER);
        buttonFabNext.setEnabled(false);

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
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
                "O que é uma maré e porque é que ocorre?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        // TextView with a Clickable Span

        SpannableString link = ClickableString.makeLinkSpan("tabela de marés", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER);
            }
        });

        textViewContent.setText(HtmlCompat.fromHtml(
                "Agora que já sabes o que é uma maré e como se forma está na altura de explorares a zona entre marés. Pega numas galochas, consulta a meteorologia, a altura das ondas e a <b>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        textViewContent.append(link);
        textViewContent.append(HtmlCompat.fromHtml(
                "</b>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        ClickableString.makeLinksFocusable(textViewContent);
    }
}