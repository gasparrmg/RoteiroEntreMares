package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeZonacaoFragment extends Fragment {

    private static final String htmlContent = "Devido às variações periódicas das condições abióticas associadas  ao ciclo das marés, estabelecem-se determinadas condições ambientais, sensivelmente constantes  em função da situação  em relação ao nível do mar, que vão condicionar a distribuição dos organismos. " +
            "É possível observar povoamentos de organismos em cada uma destas zonas, que vão constituir diferentes andares. Nestas plataformas é comum considerarmos os seguintes andares: <b>supralitoral</b>, <b>mediolitoral</b> e <b>infralitoral</b>";

    private static final int imageResourceId = R.drawable.img_biodiversidade_zonacao_esquema;

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewSupralitoral;
    private ImageView imageViewSupralitoralIcon;

    private MaterialCardView cardViewMediolitoral;
    private ImageView imageViewMediolitoralIcon;

    private MaterialCardView cardViewInfralitoral;
    private ImageView imageViewInfralitoralIcon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonParaSaberesMais;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        checkVisited();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.avencas_biodiversidade_title));
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        if (tts.isSpeaking()) {
            tts.stop();
        }
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.roteiro_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (tts.isSpeaking()) {
                    tts.stop();
                } else {
                    String text = HtmlCompat.fromHtml(
                            htmlContent,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment, false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);

        cardViewSupralitoral = view.findViewById(R.id.cardview_supralitoral);
        imageViewSupralitoralIcon = view.findViewById(R.id.imageview_supralitoral_ic);

        cardViewMediolitoral = view.findViewById(R.id.cardview_mediolitoral);
        imageViewMediolitoralIcon = view.findViewById(R.id.imageview_mediolitoral_ic);

        cardViewInfralitoral = view.findViewById(R.id.cardview_infralitoral);
        imageViewInfralitoralIcon = view.findViewById(R.id.imageview_infralitoral_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonParaSaberesMais = view.findViewById(R.id.button_parasaberesmais);
    }

    private void setOnClickListeners(View view) {
        cardViewSupralitoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment_to_biodiversidadeZonacaoSupralitoralFragment);
            }
        });

        cardViewMediolitoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment_to_biodiversidadeZonacaoMediolitoralSuperiorFragment);
            }
        });

        cardViewInfralitoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment_to_biodiversidadeZonacaoInfralitoralFragment);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(view).navigate(R.id.action_global_roteiroFragment);
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to questions
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment_to_biodiversidadeZonacaoFragment2PerguntasIntro);
            }
        });

        buttonParaSaberesMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Zonação",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TEXT2SPEECH", "Language not supported");
                        Toast.makeText(getActivity(), "Não tens o linguagem Português disponível no teu dispositivo. Isto acontece normalmente acontece quando a linguagem padrão do dispositivo é outra que não o Português.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }

    private void checkVisited() {
        boolean isBiodiversidadeZonacaoSupralitoralFinished = dashboardViewModel.isBiodiversidadeZonacaoSupralitoralFinished();
        boolean isBiodiversidadeZonacaoMediolitoralFinished = dashboardViewModel.isBiodiversidadeZonacaoMediolitoralFinished();
        boolean isBiodiversidadeZonacaoInfralitoralFinished = dashboardViewModel.isBiodiversidadeZonacaoInfralitoralFinished();

        if (isBiodiversidadeZonacaoSupralitoralFinished) {
            setVisitedIcon(imageViewSupralitoralIcon);
        }

        if (isBiodiversidadeZonacaoMediolitoralFinished) {
            setVisitedIcon(imageViewMediolitoralIcon);
        }


        if (isBiodiversidadeZonacaoInfralitoralFinished) {
            setVisitedIcon(imageViewInfralitoralIcon);
        }

        if (isBiodiversidadeZonacaoSupralitoralFinished && isBiodiversidadeZonacaoMediolitoralFinished && isBiodiversidadeZonacaoInfralitoralFinished) {
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }
    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
    }
}