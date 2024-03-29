package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.DialogInterface;
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
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeInteracoesFragment2Menu extends Fragment {

    private static final String htmlContent = "Como exemplos de interações entre espécies diferentes, temos:";

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewPredacao;
    private ImageView imageViewPredacaoIcon;

    private MaterialCardView cardViewHerbivora;
    private ImageView imageViewHerbivoraIcon;

    private MaterialCardView cardViewCompeticao;
    private ImageView imageViewCompeticaoIcon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isBiodiversidadeInteracoesPredacaoFinished;
    private boolean isBiodiversidadeInteracoesHerbivoriaFinished;
    private boolean isBiodiversidadeInteracoesCompeticaoFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_interacoes2_menu, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ttsEnabled = false;

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
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                htmlContent,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tts_error_message), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);

        cardViewPredacao = view.findViewById(R.id.cardview_predacao);
        imageViewPredacaoIcon = view.findViewById(R.id.imageview_predacao_ic);

        cardViewHerbivora = view.findViewById(R.id.cardview_herbivora);
        imageViewHerbivoraIcon = view.findViewById(R.id.imageview_herbivora_ic);

        cardViewCompeticao = view.findViewById(R.id.cardview_competicao);
        imageViewCompeticaoIcon = view.findViewById(R.id.imageview_competicao_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        cardViewPredacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesFragment2Menu_to_biodiversidadeInteracoesPredacaoFragment);
            }
        });

        cardViewHerbivora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesFragment2Menu_to_biodiversidadeInteracoesHerbivoriaFragment);
            }
        });

        cardViewCompeticao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesFragment2Menu_to_biodiversidadeInteracoesCompeticaoFragment);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to rede trófica
                if (!(isBiodiversidadeInteracoesPredacaoFinished && isBiodiversidadeInteracoesHerbivoriaFinished && isBiodiversidadeInteracoesCompeticaoFinished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_content_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesFragment2Menu_to_biodiversidadeInteracoesFragment3);
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesFragment2Menu_to_biodiversidadeInteracoesFragment3);
                }
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                getResources().getString(R.string.avencas_biodiversidade_interacoes_title),
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
                        ttsEnabled = false;
                        Log.e("TEXT2SPEECH", "Language not supported");
                    } else {
                        ttsEnabled = true;
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }

    private void checkVisited() {
        isBiodiversidadeInteracoesPredacaoFinished = dashboardViewModel.isBiodiversidadeInteracoesPredacaoFinished();
        isBiodiversidadeInteracoesHerbivoriaFinished = dashboardViewModel.isBiodiversidadeInteracoesHerbivoriaFinished();
        isBiodiversidadeInteracoesCompeticaoFinished = dashboardViewModel.isBiodiversidadeInteracoesCompeticaoFinished();

        if (isBiodiversidadeInteracoesPredacaoFinished) {
            setVisitedIcon(imageViewPredacaoIcon);
        }

        if (isBiodiversidadeInteracoesHerbivoriaFinished) {
            setVisitedIcon(imageViewHerbivoraIcon);
        }

        if (isBiodiversidadeInteracoesCompeticaoFinished) {
            setVisitedIcon(imageViewCompeticaoIcon);
        }

        if (isBiodiversidadeInteracoesPredacaoFinished && isBiodiversidadeInteracoesHerbivoriaFinished && isBiodiversidadeInteracoesCompeticaoFinished) {
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }
    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
    }
}