package com.android.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.android.roteiroentremares.ui.onboarding.OnBoardingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvencasOnBoardingFragment2 extends Fragment {

    private static final int SEQUENCE_NUMBER = 2;

    // ViewModel
    @Inject
    OnBoardingViewModel onBoardingViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    public AvencasOnBoardingFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avencas_on_boarding2, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager_avencas);

        setOnClickListeners();
        insertContent();

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User finished onBoarding sequence
                onBoardingViewModel.setOnBoarding(true);

                Intent intent = new Intent(getActivity(), UserDashboardActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER-2);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Área Marinha Protegida das Avencas",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "Antes de começares a explorar esta zona, lembra-te que é uma Zona Protegida." +
                        "<br><br>" +
                        "Evita a interferência com os organismos marinhos, nomeadamente:" +
                        "<br>" +
                        "- Reduz ao mínimo possível o pisoteio (procura andar apenas pelos locais assinalados)" +
                        "<br>" +
                        "- Sempre que levantares uma rocha, volta a colocá-la tal como estava (não deixes a face interior exposta)" +
                        "<br>" +
                        "- Não faças recolhas de nenhum organismo (podes sempre fotografá-los)" +
                        "<br>" +
                        "- Não deixes o teu lixo para trás.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}