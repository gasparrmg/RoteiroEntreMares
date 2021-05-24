package com.android.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.android.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;
import com.android.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class AvencasOnBoardingFragment2 extends Fragment {

    private static final int SEQUENCE_NUMBER = 2;

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
                viewPager.setCurrentItem(SEQUENCE_NUMBER);
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

        SpannableString link = ClickableString.makeLinkSpan("tabela de marés", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hidrografico.pt/m.mare"));
                startActivity(browserIntent);
            }
        });

        link.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)), 0, link.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Atenção:</b><br><br>" +
                        "Tal como referido no início desta App, para saberes em que altura do dia podes visitar a zona entre marés tens de consultar a ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        textViewContent.append(link);
        textViewContent.append(HtmlCompat.fromHtml(
                ". No caso da plataforma rochosa das Avencas, tens de selecionar o <b>Porto de Cascais</b>, tem em atenção o seguinte:<br>" +
                        "- Altura da maré-baixa: <b>tem de ser inferior a 0.9m</b><br>" +
                        "- Intervalo de tempo com acesso seguro à zona: 2h antes até 2h depois da hora da maré-baixa<br>" +
                        "<br>" +
                        "Confirma sempre o estado do mar, antes de ires visitar estes locais. A ondulação deve estar abaixo dos 2m.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        ClickableString.makeLinksFocusable(textViewContent);
    }
}