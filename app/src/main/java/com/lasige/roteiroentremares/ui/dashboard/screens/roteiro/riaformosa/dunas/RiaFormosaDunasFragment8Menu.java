package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas;

import android.content.DialogInterface;
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
import com.lasige.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaDunasFragment8Menu extends Fragment {

    private static final String htmlContent = "Observa bem o sistema dunar onde te encontras. Quantas zonas consegues distinguir?";

    private static final int imageResourceId = R.drawable.img_riaformosa_dunas_esquema_zonas;

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewDunaEmbrionaria;
    private ImageView imageViewDunaEmbrionariaIcon;

    private MaterialCardView cardViewDunaPrimaria;
    private ImageView imageViewDunaPrimariaIcon;

    private MaterialCardView cardViewEspacoInterdunar;
    private ImageView imageViewEspacoInterdunarIcon;

    private MaterialCardView cardViewDunaSecundaria;
    private ImageView imageViewDunaSecundariaIcon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonParaSaberesMais;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isDunaEmbrionariaFinished;
    private boolean isDunaPrimariaFinished;
    private boolean isEspacoInterdunarFinished;
    private boolean isDunaSecundariaFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_dunas8_menu, container, false);

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
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_dunas_title));
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
        textViewTitle.setVisibility(View.GONE);

        textViewContent = view.findViewById(R.id.text_content);

        cardViewDunaEmbrionaria = view.findViewById(R.id.cardview_dunaembrionaria);
        imageViewDunaEmbrionariaIcon = view.findViewById(R.id.imageview_dunaembrionaria_ic);

        cardViewDunaPrimaria = view.findViewById(R.id.cardview_dunaprimaria);
        imageViewDunaPrimariaIcon = view.findViewById(R.id.imageview_dunaprimaria_ic);

        cardViewEspacoInterdunar = view.findViewById(R.id.cardview_espacointerdunar);
        imageViewEspacoInterdunarIcon = view.findViewById(R.id.imageview_espacointerdunar_ic);

        cardViewDunaSecundaria = view.findViewById(R.id.cardview_dunasecundaria);
        imageViewDunaSecundariaIcon = view.findViewById(R.id.imageview_dunasecundaria_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonParaSaberesMais = view.findViewById(R.id.button_parasaberesmais);
    }

    private void setOnClickListeners(View view) {
        cardViewDunaEmbrionaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragmentDunaEmbrionaria);
            }
        });

        cardViewDunaPrimaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragmentDunaPrimaria);
            }
        });

        cardViewEspacoInterdunar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragmentZonaInterdunar);
            }
        });

        cardViewDunaSecundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragmentDunaSecundaria);
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
                // Go to questions
                if (!(isDunaEmbrionariaFinished && isDunaPrimariaFinished && isEspacoInterdunarFinished && isDunaSecundariaFinished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_content_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragment9Pergunta1);
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
                    Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment8Menu_to_riaFormosaDunasFragment9Pergunta1);
                }
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
        isDunaEmbrionariaFinished = dashboardViewModel.isRiaFormosaDunasDunaEmbrionariaFinished();
        isDunaPrimariaFinished = dashboardViewModel.isRiaFormosaDunasDunaPrimariaFinished();
        isDunaSecundariaFinished = dashboardViewModel.isRiaFormosaDunasDunaSecundariaFinished();
        isEspacoInterdunarFinished = dashboardViewModel.isRiaFormosaDunasZonaInterdunarFinished();
        // isBiodiversidadeZonacaoMediolitoralFinished = dashboardViewModel.isBiodiversidadeZonacaoMediolitoralFinished();
        // isBiodiversidadeZonacaoInfralitoralFinished = dashboardViewModel.isBiodiversidadeZonacaoInfralitoralFinished();

        if (isDunaEmbrionariaFinished) {
            setVisitedIcon(imageViewDunaEmbrionariaIcon);
        }

        if (isDunaPrimariaFinished) {
            setVisitedIcon(imageViewDunaPrimariaIcon);
        }

        if (isEspacoInterdunarFinished) {
            setVisitedIcon(imageViewEspacoInterdunarIcon);
        }

        if (isDunaSecundariaFinished) {
            setVisitedIcon(imageViewDunaSecundariaIcon);
        }

        /*
        if (isBiodiversidadeZonacaoMediolitoralFinished) {
            setVisitedIcon(imageViewMediolitoralIcon);
        }


        if (isBiodiversidadeZonacaoInfralitoralFinished) {
            setVisitedIcon(imageViewInfralitoralIcon);
        }

        if (isBiodiversidadeZonacaoSupralitoralFinished && isBiodiversidadeZonacaoMediolitoralFinished && isBiodiversidadeZonacaoInfralitoralFinished) {
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }*/
    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
    }
}