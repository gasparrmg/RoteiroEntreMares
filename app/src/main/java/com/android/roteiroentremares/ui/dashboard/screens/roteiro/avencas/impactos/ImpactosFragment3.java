package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

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
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImpactosFragment3 extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    // Cards corretas

    private MaterialCardView cardViewCapturaExcessiva;
    private TextView textViewCapturaExcessiva;
    private ImageView imageViewCapturaExcessivaIcon;

    private MaterialCardView cardViewPisoteio;
    private TextView textViewPisoteio;
    private ImageView imageViewPisoteioIcon;

    private MaterialCardView cardViewPoluicao;
    private TextView textViewPoluicao;
    private ImageView imageViewPoluicaoIcon;

    private MaterialCardView cardViewTempAgua;
    private TextView textViewCapturaTempAgua;
    private ImageView imageViewTempAguaIcon;

    // Cards incorretas
    private MaterialCardView cardView2;
    private TextView textView2;
    private ImageView imageView2;
    private MaterialCardView cardView3;
    private TextView textView3;
    private ImageView imageView3;
    private MaterialCardView cardView6;
    private TextView textView6;
    private ImageView imageView6;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isCapturaExcessivaFinished;
    private boolean isPisoteioFinished;
    private boolean isPoluicaoFinished;
    private boolean isTempAguaFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos3_exercicio, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        ttsEnabled = false;
        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();

        checkIfLinksVisited();
    }

    private void checkIfLinksVisited() {
        isCapturaExcessivaFinished = dashboardViewModel.isImpactosCapturaExcessivaFinished();
        isPisoteioFinished = dashboardViewModel.isImpactosPisoteioFinished();
        isPoluicaoFinished = dashboardViewModel.isImpactosPoluicaoFinished();
        isTempAguaFinished = dashboardViewModel.isImpactosTempAguaFinished();

        boolean was2Answered = dashboardViewModel.isImpactos2Answered();
        boolean was3Answered = dashboardViewModel.isImpactos3Answered();
        boolean was6Answered = dashboardViewModel.isImpactos6Answered();


        if (isCapturaExcessivaFinished) {
            setCorrect(imageViewCapturaExcessivaIcon);
        }

        if (isPisoteioFinished) {
            setCorrect(imageViewPisoteioIcon);
        }

        if (isPoluicaoFinished) {
            setCorrect(imageViewPoluicaoIcon);
        }

        if (isTempAguaFinished) {
            setCorrect(imageViewTempAguaIcon);
        }

        if (was2Answered) {
            setIncorrect(imageView2);
        }

        if (was3Answered) {
            setIncorrect(imageView3);
        }

        if (was6Answered) {
            setIncorrect(imageView6);
        }

        if (isCapturaExcessivaFinished &&
                isPisoteioFinished &&
                isPoluicaoFinished &&
                isTempAguaFinished) {

            setIncorrect(imageView2);
            setIncorrect(imageView3);
            setIncorrect(imageView6);

            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);

            // Toast.makeText(getActivity(), getResources().getString(R.string.message_correct_answer), Toast.LENGTH_LONG).show();
        }

    }

    private void setCorrect(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
        imageView.setVisibility(View.VISIBLE);
    }

    private void setIncorrect(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorError), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_close);
        imageView.setVisibility(View.VISIBLE);
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Impacto da ação humana");
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
                                "Na tua opinião, quais serão as pressões a que estes locais estão sujeitos? Seleciona as opções que consideras corretas.",
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
        textViewContent = view.findViewById(R.id.text_question);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        // Cards corretas

        cardViewCapturaExcessiva = view.findViewById(R.id.cardview_answer1);
        textViewCapturaExcessiva = view.findViewById(R.id.textview_answer1);
        imageViewCapturaExcessivaIcon = view.findViewById(R.id.imageview_ic1);

        cardViewPisoteio = view.findViewById(R.id.cardview_answer4);
        textViewPisoteio = view.findViewById(R.id.textview_answer4);
        imageViewPisoteioIcon = view.findViewById(R.id.imageview_ic4);

        cardViewPoluicao = view.findViewById(R.id.cardview_answer5);
        textViewPoluicao = view.findViewById(R.id.textview_answer5);
        imageViewPoluicaoIcon = view.findViewById(R.id.imageview_ic5);

        cardViewTempAgua = view.findViewById(R.id.cardview_answer7);
        textViewCapturaTempAgua = view.findViewById(R.id.textview_answer7);
        imageViewTempAguaIcon = view.findViewById(R.id.imageview_ic7);

        // Cards incorretas

        cardView2 = view.findViewById(R.id.cardview_answer2);
        textView2 = view.findViewById(R.id.textview_answer2);
        imageView2 = view.findViewById(R.id.imageview_ic2);

        cardView3 = view.findViewById(R.id.cardview_answer3);
        textView3 = view.findViewById(R.id.textview_answer3);
        imageView3 = view.findViewById(R.id.imageview_ic3);

        cardView6 = view.findViewById(R.id.cardview_answer6);
        textView6 = view.findViewById(R.id.textview_answer6);
        imageView6 = view.findViewById(R.id.imageview_ic6);

        cardView2.setOnClickListener(incorrectListener);
        cardView3.setOnClickListener(incorrectListener);
        cardView6.setOnClickListener(incorrectListener);
    }

    private void setOnClickListeners(View view) {
        cardViewCapturaExcessiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosCapturaExcessivaFragment);
            }
        });

        cardViewPisoteio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosPisoteioFragment);
            }
        });

        cardViewPoluicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosPoluicaoFragment);
            }
        });

        cardViewTempAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosTempAguaFragment);
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
                if (!(isCapturaExcessivaFinished &&
                        isPisoteioFinished &&
                        isPoluicaoFinished &&
                        isTempAguaFinished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_content_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosOcupacaoHumanaFragmentText);
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
                    Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosOcupacaoHumanaFragmentText);
                }
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

    private View.OnClickListener incorrectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardview_answer2:
                    dashboardViewModel.setImpactos2AsAnswered();
                    setIncorrect(imageView2);
                    break;
                case R.id.cardview_answer3:
                    dashboardViewModel.setImpactos3AsAnswered();
                    setIncorrect(imageView3);
                    break;
                case R.id.cardview_answer6:
                    dashboardViewModel.setImpactos6AsAnswered();
                    setIncorrect(imageView6);
                    break;
                default:
                    break;
            }
        }
    };
}