package com.lasige.roteiroentremares.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.lasige.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {

    // ViewModel
    /*@Inject
    OnBoardingViewModel onBoardingViewModel;*/
    private OnBoardingViewModel onBoardingViewModel;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //onBoardingViewModel.setOnBoarding(false); // Uncomment ONLY for onBoarding testing purposes

                if (onBoardingViewModel.getOnBoarding()) {
                    if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() == -1) {
                        Intent intent = new Intent(getActivity(), UserDashboardActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() == 0) {
                        // navigate to Avencas
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_chooseZoneInteresseFragment);
                    } else if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() == 1) {
                        // navigate to RF
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_chooseZoneInteresseFragment);
                    }
                } else {
                    if (onBoardingViewModel.getChangeToAvencasOrRiaFormosa() != -1) {
                        onBoardingViewModel.deleteChangeToAvencasOrRiaFormosa();
                    }
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_splashFragment_to_viewPagerFragment);
                }
            }
        }, 3000);

        // Inflate the layout for this fragment
        return view;
    }
}