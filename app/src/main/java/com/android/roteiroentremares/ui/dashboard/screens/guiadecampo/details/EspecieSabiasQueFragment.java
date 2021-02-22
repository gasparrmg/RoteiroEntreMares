package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;

public class EspecieSabiasQueFragment extends Fragment {

    private ViewPager2 viewPager;
    private EspecieAvencas especieAvencas;

    private TextView textViewSabiasQueContent;
    private Button buttonBack;

    public EspecieSabiasQueFragment(EspecieAvencas especieAvencas) {
        // Required empty public constructor
        this.especieAvencas = especieAvencas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especie_sabias_que, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager_especieDetails);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        textViewSabiasQueContent = view.findViewById(R.id.textView_sabiasque_content);
        buttonBack = view.findViewById(R.id.button_back);

        textViewSabiasQueContent.setText(especieAvencas.getSabiasQue());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EspecieDetailsActivity) getActivity()).initImageSlider(especieAvencas.getPictures());
                viewPager.setCurrentItem(0);
            }
        });
    }
}