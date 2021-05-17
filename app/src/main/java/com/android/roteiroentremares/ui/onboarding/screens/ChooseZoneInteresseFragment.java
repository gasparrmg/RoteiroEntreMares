package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseZoneInteresseFragment extends Fragment {

    private OnBoardingViewModel onBoardingViewModel;

    // Views
    private Button btnAvencas;
    private Button btnRiaFormosa;
    private ImageView imageViewOnBoardingFinal;
    private ViewPager2 viewPager;

    public ChooseZoneInteresseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding19, container, false);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        btnAvencas = view.findViewById(R.id.btn_avencas);
        btnRiaFormosa = view.findViewById(R.id.btn_riaformosa);
        imageViewOnBoardingFinal = view.findViewById(R.id.img_onboarding_final);
        viewPager = getActivity().findViewById(R.id.viewPager);

        setOnClickListeners(view);

        if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() != -1) {
            Log.d("ONBOARDING", "toChange value: " + onBoardingViewModel.getChangeToAvencasOrRiaFormosa());

            onBoardingViewModel.deleteChangeToAvencasOrRiaFormosa();

            Log.d("ONBOARDING", "toChange value: " + onBoardingViewModel.getChangeToAvencasOrRiaFormosa());
        }

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners(View view) {
        btnAvencas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_chooseZoneInteresseFragment_to_avencasViewPagerFragment);
            }
        });

        btnRiaFormosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_chooseZoneInteresseFragment_to_riaFormosaViewPagerFragment);
            }
        });
    }
}