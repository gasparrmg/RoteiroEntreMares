package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.mares;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.factory.QuestionFactory;
import com.lasige.roteiroentremares.data.model.Question;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MaresFragment10 extends Fragment implements View.OnClickListener{

    private static final String htmlContent = "As marés mortas devem-se a:";
    private static final int QUESTION_ID = 2; // Question ID from the Sequence at hand
    // Views
    private TextView textViewTitle;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    // Views
    private TextView textViewQuestion;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;

    // Variables
    private Question question;
    private boolean isCorrect;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mares11_pergunta_2, container, false);

        ttsEnabled = false;

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        isCorrect = false;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_mares_title));
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
        super.onCreateOptionsMenu(menu,inflater);
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
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        // Get Question object from the Factory
        question = QuestionFactory.onBoardingQuestions.get(QUESTION_ID-1);

        // Init Views
        textViewTitle = view.findViewById(R.id.text_title);
        textViewQuestion = view.findViewById(R.id.text_question);
        textViewAnswer1 = view.findViewById(R.id.text_answer1);
        textViewAnswer2 = view.findViewById(R.id.text_answer2);
        textViewAnswer3 = view.findViewById(R.id.text_answer3);
        buttonFabNext.setEnabled(true);
    }

    private void setOnClickListeners(View view) {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCorrect) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_question_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_maresFragment10_to_maresFragment11);
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
                    Navigation.findNavController(view).navigate(R.id.action_maresFragment10_to_maresFragment11);
                }
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        textViewAnswer1.setOnClickListener(this);
        textViewAnswer2.setOnClickListener(this);
        textViewAnswer3.setOnClickListener(this);
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        // Title
        textViewTitle.setText(HtmlCompat.fromHtml(
                "O que é uma maré e porque é que ocorre?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        // Question + Answers
        textViewQuestion.setText(question.getQuestion());
        textViewAnswer1.setText(question.getAnswer1());
        textViewAnswer2.setText(question.getAnswer2());
        textViewAnswer3.setText(question.getAnswer3());

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
        if (!isCorrect) {
            switch (v.getId()) {
                case R.id.text_answer1:
                    isCorrect = QuestionFactory.isCorrect(getContext(), (TextView) v, 1, question.getCorrectAnswer());
                    break;
                case R.id.text_answer2:
                    isCorrect = QuestionFactory.isCorrect(getContext(), (TextView) v, 2, question.getCorrectAnswer());
                    break;
                case R.id.text_answer3:
                    isCorrect = QuestionFactory.isCorrect(getContext(), (TextView) v, 3, question.getCorrectAnswer());
                    break;
                default:
                    break;
            }

            if (isCorrect) {
                buttonFabNext.setEnabled(true);
                buttonFabNext.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), getResources().getString(R.string.message_correct_answer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}