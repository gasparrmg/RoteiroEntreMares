package com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;

public class EspecieRiaFormosaSabiasQueFragment extends Fragment {

    private ViewPager2 viewPager;
    private EspecieRiaFormosa especieRiaFormosa;

    private TextView textViewSabiasQueContent;
    private TextView textViewSabiasQueTitle;
    private Button buttonBack;

    public EspecieRiaFormosaSabiasQueFragment(EspecieRiaFormosa especieRiaFormosa) {
        // Required empty public constructor
        this.especieRiaFormosa = especieRiaFormosa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especie_riaformosa_sabias_que, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager_especieDetails);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        textViewSabiasQueContent = view.findViewById(R.id.textView_sabiasque_content);
        textViewSabiasQueTitle = view.findViewById(R.id.textView_sabiasque_title);
        buttonBack = view.findViewById(R.id.button_back);

        if (!especieRiaFormosa.getSabiasQue().isEmpty()) {
            // set text and title
            textViewSabiasQueContent.setText(especieRiaFormosa.getSabiasQue());
            textViewSabiasQueTitle.setText("Sabias que...");
        } else if (!especieRiaFormosa.getCuriosidades().isEmpty()) {
            // set text and title
            textViewSabiasQueContent.setText(especieRiaFormosa.getCuriosidades());
            textViewSabiasQueTitle.setText("Curiosidades");
        }

        // textViewSabiasQueContent.setText(especieAvencas.getSabiasQue());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EspecieDetailsActivity) getActivity()).initImageSlider(especieRiaFormosa.getPictures());
                viewPager.setCurrentItem(0);
            }
        });
    }
}