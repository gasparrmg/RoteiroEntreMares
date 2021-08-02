package com.lasige.roteiroentremares.ui.onboarding;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.lasige.roteiroentremares.ui.dashboard.WifiP2PActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {

    // ViewModel
    /*@Inject
    OnBoardingViewModel onBoardingViewModel;*/
    private OnBoardingViewModel onBoardingViewModel;
    private DashboardViewModel dashboardViewModel;

    // Wifi P2p
    private WifiP2pManager.Channel channel;
    private WifiP2pManager manager;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        if (dashboardViewModel.getTipoUtilizador() == 1) {
            cleanWifiP2pStuff();
        }

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

    private void cleanWifiP2pStuff() {
        if (setupP2p()) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                manager.requestGroupInfo(channel, new WifiP2pManager.GroupInfoListener() {
                    @Override
                    public void onGroupInfoAvailable(WifiP2pGroup group) {
                        if (group != null) {
                            manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(WifiP2PActivity.TAG, "Group deleted successfully.");
                                }

                                @Override
                                public void onFailure(int reason) {
                                    Log.d(WifiP2PActivity.TAG, "Group not deleted. Error with code " + reason);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private boolean setupP2p() {
        manager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        if (manager == null) {
            Log.e(WifiP2PActivity.TAG, "Cannot get Wi-Fi Direct system service.");
            return false;
        }

        channel = manager.initialize(getActivity(), getActivity().getMainLooper(), null);
        if (channel == null) {
            Log.e(WifiP2PActivity.TAG, "Cannot initialize Wi-Fi Direct.");
            return false;
        }

        return true;
    }
}