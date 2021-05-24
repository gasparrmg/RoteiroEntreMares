package com.android.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RiaFormosaOnBoardingFragment5 extends Fragment {

    private static final int SEQUENCE_NUMBER = 5;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_on_boarding3, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager_riaformosa);


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
                viewPager.setCurrentItem(SEQUENCE_NUMBER - 2);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Tabela de Marés",
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
                "ATENÇÃO:<br><br>" +
                        "Tal como referido no início desta App, para saberes em que altura do dia podes visitar a zona entre marés tens de consultar a ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.append(link);
        textViewContent.append(HtmlCompat.fromHtml(
                ". No caso da plataforma da Praia do Arraial de Tavira (onde irão decorrer os percursos), tens de selecionar o <u>Porto de Tavira</u>, tem em atenção o seguinte:\n" +
                        "- Altura da maré-baixa: <u>tem de ser inferior a 0.9m</u><br>" +
                        "- Intervalo de tempo com acesso seguro à zona: 2h antes até 2h depois da hora da maré-baixa<br>" +
                        "<br>" +
                        "Confirma sempre o estado do mar, antes de ires visitar estes locais. A ondulação deve estar abaixo dos 2m.",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        ClickableString.makeLinksFocusable(textViewContent);
    }
}