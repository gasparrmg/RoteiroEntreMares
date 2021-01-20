package com.android.roteiroentremares.ui.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.android.roteiroentremares.ui.onboarding.screens.OnBoardingFragment1;
import com.android.roteiroentremares.ui.onboarding.screens.OnBoardingFragment2;
import com.android.roteiroentremares.ui.onboarding.screens.OnBoardingFragment3;
import com.android.roteiroentremares.ui.onboarding.screens.OnBoardingFragment4;

import java.util.ArrayList;
import java.util.List;

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
        fragmentList.add(new OnBoardingFragment2());
        fragmentList.add(new OnBoardingFragment3());
        fragmentList.add(new OnBoardingFragment4());

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