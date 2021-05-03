package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeMenuFragment extends Fragment {

    private static final String htmlContent = "A distribuição dos organismos nestas plataformas rochosas depende em grande medida das características morfológicas, fisiológicas e comportamentais, que lhes permitem a sobrevivência nestes locais.";

    private static final int imageResourceId = R.drawable.img_biodiversidade_pontos_assinalados;

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewMicrohabitats;
    private TextView textViewMicrohabitats;
    private ImageView imageViewMicrohabitatsIcon;

    private MaterialCardView cardViewZonacao;
    private TextView textViewZonacao;
    private ImageView imageViewZonacaoIcon;

    private MaterialCardView cardViewInteracoes;
    private TextView textViewInteracoes;
    private ImageView imageViewInteracoesIcon;

    private ExtendedFloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonPontosAVisitar;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isBiodiversidadeMicrohabitatsFinished;
    private boolean isBiodiversidadeZonacaoFinished;
    private boolean isBiodiversidadeInteracoesFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_menu, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ttsEnabled = false;

        initViews(view);
        insertContent();
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        checkIfLinksVisited();
    }

    private void checkIfLinksVisited() {
        isBiodiversidadeMicrohabitatsFinished = dashboardViewModel.isBiodiversidadeMicrohabitatsFinished();
        isBiodiversidadeZonacaoFinished = dashboardViewModel.isBiodiversidadeZonacaoFinished();
        isBiodiversidadeInteracoesFinished = dashboardViewModel.isBiodiversidadeInteracoesFinished();

        if (isBiodiversidadeMicrohabitatsFinished) {
            setVisitedIcon(imageViewMicrohabitatsIcon);
        }

        if (isBiodiversidadeZonacaoFinished) {
            setVisitedIcon(imageViewZonacaoIcon);
        }

        if (isBiodiversidadeInteracoesFinished) {
            setVisitedIcon(imageViewInteracoesIcon);
        }

        if (isBiodiversidadeInteracoesFinished && isBiodiversidadeMicrohabitatsFinished && isBiodiversidadeZonacaoFinished) {
            // Show finished button
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }

    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
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
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonPontosAVisitar = view.findViewById(R.id.button_pontos_a_visitar);

        cardViewMicrohabitats = view.findViewById(R.id.cardview_microhabitats);
        imageViewMicrohabitatsIcon = view.findViewById(R.id.imageview_microhabitats_ic);

        cardViewZonacao = view.findViewById(R.id.cardview_zonacao);
        imageViewZonacaoIcon = view.findViewById(R.id.imageview_zonacao_ic);

        cardViewInteracoes = view.findViewById(R.id.cardview_interacoes);
        imageViewInteracoesIcon = view.findViewById(R.id.imageview_interacoes_ic);
    }

    private void setOnClickListeners(View view) {
        cardViewMicrohabitats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMenuFragment_to_biodiversidadeMicrohabitatsFragment);
            }
        });

        cardViewZonacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMenuFragment_to_biodiversidadeZonacaoFragment);
            }
        });

        cardViewInteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMenuFragment_to_biodiversidadeInteracoesFragment);
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
                // Finished Biodiversidade
                // Back to MAIN menu

                if (!(isBiodiversidadeInteracoesFinished && isBiodiversidadeMicrohabitatsFinished && isBiodiversidadeZonacaoFinished)) {
                    // not finished -> alert
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_content_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setBiodiversidadeAsFinished();
                            Navigation.findNavController(view).popBackStack(R.id.roteiroFragment ,false);
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
                    // no alert
                    dashboardViewModel.setBiodiversidadeAsFinished();
                    Navigation.findNavController(view).popBackStack(R.id.roteiroFragment ,false);
                }
            }
        });

        buttonPontosAVisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });

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

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}