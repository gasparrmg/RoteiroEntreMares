package com.android.roteiroentremares.ui.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.android.roteiroentremares.ui.onboarding.screens.AvencasOnBoardingFragment1;
import com.android.roteiroentremares.ui.onboarding.screens.AvencasOnBoardingFragment2;
import com.android.roteiroentremares.ui.onboarding.screens.OnBoardingFragment1;

import java.util.ArrayList;

public class AvencasViewPagerFragment extends Fragment {

    ViewPager2 viewPager;

    public AvencasViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avencas_view_pager, container, false);

        viewPager = view.findViewById(R.id.viewPager_avencas);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new AvencasOnBoardingFragment1());
        fragmentList.add(new AvencasOnBoardingFragment2());

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