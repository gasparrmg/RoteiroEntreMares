package com.android.roteiroentremares.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.android.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {

    // View Model
    @Inject
    OnBoardingViewModel onBoardingViewModel;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //onBoardingViewModel.setOnBoarding(false); // Uncomment ONLY for onBoarding testing purposes

                if (onBoardingViewModel.getOnBoarding()) {
                    Intent intent = new Intent(getActivity(), UserDashboardActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_splashFragment_to_viewPagerFragment);
                }
            }
        }, 3000);

        // Inflate the layout for this fragment
        return view;
    }
}