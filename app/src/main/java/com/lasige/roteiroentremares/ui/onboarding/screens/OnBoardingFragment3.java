package com.lasige.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OnBoardingFragment3 extends Fragment {

    private static final int SEQUENCE_NUMBER = 2;

    // Views
    private TextView textViewTitle;
    private FloatingActionButton buttonFabNext;
    private CircularProgressIndicator progressBar;
    private ViewPager2 viewPager;

    public OnBoardingFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding3, container, false);

        // View declaration
        textViewTitle = view.findViewById(R.id.text_title);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        progressBar = view.findViewById(R.id.progressBar);
        viewPager = getActivity().findViewById(R.id.viewPager);

        setOnClickListeners();
        insertContent();

        // Progress Bar update
        progressBar.setMax(viewPager.getAdapter().getItemCount());
        progressBar.setProgress(SEQUENCE_NUMBER);

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
                "Olá!<br>Para começarmos a explorar o mundo na zona entre marés, vamos começar por te explicar o que é uma maré e porque é que ocorre...",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}