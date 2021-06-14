package com.lasige.roteiroentremares.ui.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment1;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment10;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment11;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment12;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment13;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment14;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment15;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment16;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment17;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment18;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment2;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment3;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment4;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment5;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment6;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment7;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment8;
import com.lasige.roteiroentremares.ui.onboarding.screens.OnBoardingFragment9;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    ViewPager2 viewPager;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new OnBoardingFragment1());
        //fragmentList.add(new OnBoardingFragment2());
        fragmentList.add(new OnBoardingFragment3());
        fragmentList.add(new OnBoardingFragment4());
        fragmentList.add(new OnBoardingFragment5());
        fragmentList.add(new OnBoardingFragment6());
        fragmentList.add(new OnBoardingFragment7());
        fragmentList.add(new OnBoardingFragment8());
        fragmentList.add(new OnBoardingFragment9());
        fragmentList.add(new OnBoardingFragment10());
        fragmentList.add(new OnBoardingFragment11());
        fragmentList.add(new OnBoardingFragment12());
        fragmentList.add(new OnBoardingFragment13());
        fragmentList.add(new OnBoardingFragment14());
        fragmentList.add(new OnBoardingFragment15());
        fragmentList.add(new OnBoardingFragment16());
        fragmentList.add(new OnBoardingFragment17());
        fragmentList.add(new OnBoardingFragment18());
        fragmentList.add(new OnBoardingFragment2());


        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragmentList,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        );

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);

        return view;
    }
}