package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.AvistamentosChartsActivity;
import com.android.roteiroentremares.util.ImageQuestionUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class BiodiversidadeZonacaoFragment5Pergunta3 extends Fragment implements View.OnClickListener {

    private static final int[] correctAnswers = {1, 3, 6, 7};

    // Views
    private TextView textViewTitle;
    private MaterialCardView cardViewAnswer1;
    private MaterialCardView cardViewAnswer2;
    private MaterialCardView cardViewAnswer3;
    private MaterialCardView cardViewAnswer4;
    private MaterialCardView cardViewAnswer5;
    private MaterialCardView cardViewAnswer6;
    private MaterialCardView cardViewAnswer7;
    private MaterialCardView cardViewAnswer8;

    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private TextView textViewAnswer4;
    private TextView textViewAnswer5;
    private TextView textViewAnswer6;
    private TextView textViewAnswer7;
    private TextView textViewAnswer8;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonCharts;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean isCorrect1;
    private boolean isCorrect2;
    private boolean isCorrect3;
    private boolean isCorrect4;
    private boolean isCorrect5;
    private boolean isCorrect6;
    private boolean isCorrect7;
    private boolean isCorrect8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao5_pergunta_3, container, false);

        ttsEnabled = false;
        isCorrect1 = false;
        isCorrect2 = false;
        isCorrect3 = false;
        isCorrect4 = false;
        isCorrect5 = false;
        isCorrect6 = false;
        isCorrect7 = false;
        isCorrect8 = false;

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Biodiversidade");
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
                                "A abundância de seres vivos aumenta/diminui significativamente quanto maior/menor for a proximidade da zona infralitoral. É expectável que a diversidade de organismos seja abundante/reduzida na zona supralitoral do intertidal, aumentando/diminuindo progressivamente na zona mediolitoral.",
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

        cardViewAnswer1 = view.findViewById(R.id.cardview_answer1);
        cardViewAnswer2 = view.findViewById(R.id.cardview_answer2);
        cardViewAnswer3 = view.findViewById(R.id.cardview_answer3);
        cardViewAnswer4 = view.findViewById(R.id.cardview_answer4);
        cardViewAnswer5 = view.findViewById(R.id.cardview_answer5);
        cardViewAnswer6 = view.findViewById(R.id.cardview_answer6);
        cardViewAnswer7 = view.findViewById(R.id.cardview_answer7);
        cardViewAnswer8 = view.findViewById(R.id.cardview_answer8);

        textViewAnswer1 = view.findViewById(R.id.textview_answer1);
        textViewAnswer2 = view.findViewById(R.id.textview_answer2);
        textViewAnswer3 = view.findViewById(R.id.textview_answer3);
        textViewAnswer4 = view.findViewById(R.id.textview_answer4);
        textViewAnswer5 = view.findViewById(R.id.textview_answer5);
        textViewAnswer6 = view.findViewById(R.id.textview_answer6);
        textViewAnswer7 = view.findViewById(R.id.textview_answer7);
        textViewAnswer8 = view.findViewById(R.id.textview_answer8);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonCharts = view.findViewById(R.id.button_charts);

        cardViewAnswer1.setOnClickListener(this);
        cardViewAnswer2.setOnClickListener(this);
        cardViewAnswer3.setOnClickListener(this);
        cardViewAnswer4.setOnClickListener(this);
        cardViewAnswer5.setOnClickListener(this);
        cardViewAnswer6.setOnClickListener(this);
        cardViewAnswer7.setOnClickListener(this);
        cardViewAnswer8.setOnClickListener(this);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isCorrect1 && isCorrect3 && isCorrect6 && isCorrect7)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_question_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment5Pergunta3_to_biodiversidadeZonacaoFragment6Pergunta4);
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
                    Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoFragment5Pergunta3_to_biodiversidadeZonacaoFragment6Pergunta4);
                }
            }
        });

        buttonCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosChartsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText("Zonação");

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

    @Override
    public void onClick(View v) {
        Log.d("QUESTION_TEST", "onClick");
        if (!(isCorrect1 && isCorrect3 && isCorrect6 && isCorrect7)) {
            Log.d("QUESTION_TEST", "onClick -> !isCorrect");
            switch (v.getId()) {
                case R.id.cardview_answer1:
                    isCorrect1 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer1, cardViewAnswer1, 1, correctAnswers);
                    cardViewAnswer2.setEnabled(false);
                    break;
                case R.id.cardview_answer2:
                    isCorrect2 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer2, cardViewAnswer2, 2, correctAnswers);
                    break;
                case R.id.cardview_answer3:
                    isCorrect3 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer3, cardViewAnswer3, 3, correctAnswers);
                    cardViewAnswer4.setEnabled(false);
                    break;
                case R.id.cardview_answer4:
                    isCorrect4 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer4, cardViewAnswer4, 4, correctAnswers);
                    break;
                case R.id.cardview_answer5:
                    isCorrect5 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer5, cardViewAnswer5, 5, correctAnswers);
                    break;
                case R.id.cardview_answer6:
                    isCorrect6 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer6, cardViewAnswer6, 6, correctAnswers);
                    cardViewAnswer5.setEnabled(false);
                    break;
                case R.id.cardview_answer7:
                    isCorrect7 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer7, cardViewAnswer7, 7, correctAnswers);
                    cardViewAnswer8.setEnabled(false);
                    break;
                case R.id.cardview_answer8:
                    isCorrect7 = ImageQuestionUtils.isCorrectMultiple(getContext(), textViewAnswer8, cardViewAnswer8, 8, correctAnswers);
                    break;
                default:
                    break;
            }

            if (isCorrect1 && isCorrect3 && isCorrect6 && isCorrect7) {
                cardViewAnswer1.setEnabled(false);
                cardViewAnswer2.setEnabled(false);
                cardViewAnswer3.setEnabled(false);
                cardViewAnswer4.setEnabled(false);
                cardViewAnswer5.setEnabled(false);
                cardViewAnswer6.setEnabled(false);
                cardViewAnswer7.setEnabled(false);
                buttonFabNext.setEnabled(true);
                buttonFabNext.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), getResources().getString(R.string.message_correct_answer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}