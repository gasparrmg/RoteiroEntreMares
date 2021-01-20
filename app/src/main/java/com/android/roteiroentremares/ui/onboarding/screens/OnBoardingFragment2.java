package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class OnBoardingFragment2 extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextInputEditText editTextNome;
    private TextInputEditText editTextEscola;
    private TextInputEditText editTextAnoEscolaridade;
    private TextInputEditText editTextAnoLectivo;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    public OnBoardingFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding2, container, false);

        // Declare Views
        textViewTitle = view.findViewById(R.id.text_title);
        editTextNome = view.findViewById(R.id.textinputedittext_nome);
        editTextEscola = view.findViewById(R.id.textinputedittext_escola);
        editTextAnoEscolaridade = view.findViewById(R.id.textinputedittext_anoescolaridade);
        editTextAnoLectivo = view.findViewById(R.id.textinputedittext_anolectivo);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager);

        setOnClickListeners();

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
    }
}