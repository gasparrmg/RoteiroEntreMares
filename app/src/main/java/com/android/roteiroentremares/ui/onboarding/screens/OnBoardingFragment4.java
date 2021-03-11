package com.android.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.util.ClickableString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class OnBoardingFragment4 extends Fragment {

    private static final int SEQUENCE_NUMBER = 3;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private CircularProgressIndicator progressBar;
    private ViewPager2 viewPager;

    public OnBoardingFragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding4, container, false);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        progressBar = view.findViewById(R.id.progressBar);
        viewPager = getActivity().findViewById(R.id.viewPager);

        setOnClickListeners();
        insertContent();

        // Progress Bar update
        progressBar.setMax(viewPager.getAdapter().getItemCount());
        progressBar.setProgress(SEQUENCE_NUMBER);

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                "O que é uma maré e porque é que ocorre?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        // TextView with a Clickable Span

        SpannableString link = ClickableString.makeLinkSpan("seguinte vídeo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=oVxG30nwQtk"));
                startActivity(browserIntent);
            }
        });

        textViewContent.setText(HtmlCompat.fromHtml(
                "As marés são movimentos periódicos e previsíveis da subida e descida do nível das águas, que são causadas pela ação conjunta da lua e do sol, no entanto, a força gravitacional da lua é mais forte devido à sua maior proximidade.<br><br>A força de atração da lua sobre a Terra faz com que a Terra - e a água - seja projetada no lado mais próximo da Lua e também no lado mais distante dela.<br><br>Para compreenderes melhor este fenómeno vê o ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        textViewContent.append(link);
        textViewContent.append(" do Instituto Hidrográfico");
        ClickableString.makeLinksFocusable(textViewContent);
    }
}