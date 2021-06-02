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
public class BiodiversidadeMicrohabitatsFragment extends Fragment {

    private static final String htmlContent = "Nestas plataformas, sobreposto aos padrões temporais de variação das condições físico-químicas, ocorre um gradiente de variação espacial, decorrente do elevado grau de fragmentação do habitat em <b>poças de maré</b>, <b>canais</b> e <b>fendas</b>, cuja topografia e perfil variam acentuadamente com as condições geológicas do substrato e do regime de deposição dos sedimentos.";

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewPocasMare;
    private ImageView imageViewPocasMareIcon;

    private MaterialCardView cardViewCanais;
    private ImageView imageViewCanaisIcon;

    private MaterialCardView cardViewFendas;
    private ImageView imageViewFendasIcon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isBiodiversidadeMicrohabitatsCanaisFinished;
    private boolean isBiodiversidadeMicrohabitatsFendasFinished;
    private boolean isBiodiversidadeMicrohabitatsPocasFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_microhabitats, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ttsEnabled = false;

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();

        checkVisited();
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
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);

        cardViewPocasMare = view.findViewById(R.id.cardview_pocasmare);
        imageViewPocasMareIcon = view.findViewById(R.id.imageview_pocasmare_ic);

        cardViewCanais = view.findViewById(R.id.cardview_canais);
        imageViewCanaisIcon = view.findViewById(R.id.imageview_canais_ic);

        cardViewFendas = view.findViewById(R.id.cardview_fendas);
        imageViewFendasIcon = view.findViewById(R.id.imageview_fendas_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        cardViewCanais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsFragment_to_biodiversidadeMicrohabitatsCanaisFragment);
            }
        });

        cardViewPocasMare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsFragment_to_biodiversidadeMicrohabitatsPocasFragment);
            }
        });

        cardViewFendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsFragment_to_biodiversidadeMicrohabitatsFendasFragment);
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
                if (!(isBiodiversidadeMicrohabitatsCanaisFinished &&
                        isBiodiversidadeMicrohabitatsFendasFinished &&
                        isBiodiversidadeMicrohabitatsPocasFinished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_content_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setBiodiversidadeMicrohabitatsAsFinished();
                            Navigation.findNavController(view).popBackStack(R.id.biodiversidadeMenuFragment ,false);
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
                    dashboardViewModel.setBiodiversidadeMicrohabitatsAsFinished();
                    Navigation.findNavController(view).popBackStack(R.id.biodiversidadeMenuFragment ,false);
                }
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Microhabitats",
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
        isBiodiversidadeMicrohabitatsCanaisFinished = dashboardViewModel.isBiodiversidadeMicrohabitatsCanaisFinished();
        isBiodiversidadeMicrohabitatsFendasFinished = dashboardViewModel.isBiodiversidadeMicrohabitatsFendasFinished();
        isBiodiversidadeMicrohabitatsPocasFinished = dashboardViewModel.isBiodiversidadeMicrohabitatsPocasFinished();

        if (isBiodiversidadeMicrohabitatsCanaisFinished) {
            setVisitedIcon(imageViewCanaisIcon);
        }

        if (isBiodiversidadeMicrohabitatsFendasFinished) {
            setVisitedIcon(imageViewFendasIcon);
        }

        if (isBiodiversidadeMicrohabitatsPocasFinished) {
            setVisitedIcon(imageViewPocasMareIcon);
        }

        if (isBiodiversidadeMicrohabitatsCanaisFinished &&
                isBiodiversidadeMicrohabitatsFendasFinished &&
                isBiodiversidadeMicrohabitatsPocasFinished) {
            buttonFabNext.setEnabled(true);
            buttonFabNext.setVisibility(View.VISIBLE);
        }
    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
    }
}