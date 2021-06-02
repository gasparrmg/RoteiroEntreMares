package com.lasige.roteiroentremares.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment1;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment2;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment3;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment4;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment5;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment6;
import com.lasige.roteiroentremares.ui.onboarding.screens.RiaFormosaOnBoardingFragment7;

import java.util.ArrayList;

public class RiaFormosaViewPagerFragment extends Fragment {

    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_view_pager, container, false);

        viewPager = view.findViewById(R.id.viewPager_riaformosa);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new RiaFormosaOnBoardingFragment1());
        fragmentList.add(new RiaFormosaOnBoardingFragment2());
        fragmentList.add(new RiaFormosaOnBoardingFragment3());
        fragmentList.add(new RiaFormosaOnBoardingFragment4());
        fragmentList.add(new RiaFormosaOnBoardingFragment5());
        fragmentList.add(new RiaFormosaOnBoardingFragment6());
        fragmentList.add(new RiaFormosaOnBoardingFragment7());

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