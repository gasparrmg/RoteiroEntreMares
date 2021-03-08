package com.android.roteiroentremares.ui.onboarding.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.ImageUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class OnBoardingFragment8 extends Fragment {

    private static final int SEQUENCE_NUMBER = 7;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private CircularProgressIndicator progressBar;
    private ViewPager2 viewPager;

    public OnBoardingFragment8() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding8, container, false);

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

        SpannableString link = ClickableString.makeLinkSpan("Clica aqui", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ImageUtils.createImageDialog(getContext(), R.drawable.img_tipos_mares);
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, R.drawable.img_tipos_mares);
                startActivity(intent);
            }
        });

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>Mais alguma coisa afeta as marés?</b>" +
                        "<br><br>" +
                        "<b>Marés vivas</b> - ocorrem nos períodos de Lua Nova e Lua Cheia, quando o Sol e a Lua se encontram em conjuntura (i.e. quando estão diretamente alinhados). As forças gravitacionais estão no seu máximo originando marés de grande amplitude;" +
                        "<br><br>" +
                        "<b>Marés mortas</b> - ocorrem nos períodos de Quarto Crescente e Minguante, quando o Sol e a Lua se encontram em quadratura (i.e. quando formam um ângulo de 90º com a Terra). As forças gravitacionais atuam em oposição e estão no seu mínimo originando marés de fraca amplitude." +
                        "<br><br>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
        textViewContent.append(link);
        textViewContent.append(" para veres a imagem.");
        ClickableString.makeLinksFocusable(textViewContent);
    }

}