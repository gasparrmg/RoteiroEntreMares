package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment19 extends Fragment {

    private static final int SEQUENCE_NUMBER = 19;

    // Views
    private Button btnAvencas;
    private Button btnRiaFormosa;
    private ImageView imageViewOnBoardingFinal;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    public OnBoardingFragment19() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding19, container, false);

        btnAvencas = view.findViewById(R.id.btn_avencas);
        btnRiaFormosa = view.findViewById(R.id.btn_riaformosa);
        imageViewOnBoardingFinal = view.findViewById(R.id.img_onboarding_final);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager);

        buttonFabNext.setEnabled(false);

        setOnClickListeners(view);

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER-2);
            }
        });

        btnAvencas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Avencas clicked.", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_viewPagerFragment_to_avencasViewPagerFragment);
            }
        });

        btnRiaFormosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ria Formosa clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}