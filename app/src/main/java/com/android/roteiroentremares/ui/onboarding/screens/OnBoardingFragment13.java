package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Question;
import com.android.roteiroentremares.data.factory.QuestionFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class OnBoardingFragment13 extends Fragment implements View.OnClickListener {

    private static final int QUESTION_ID = 2; // Question ID from the Sequence at hand
    private static final int SEQUENCE_NUMBER = 12;

    // Views
    private TextView textViewTitle;
    private TextView textViewQuestion;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private CircularProgressIndicator progressBar;
    private ViewPager2 viewPager;

    // Variables
    private Question question;
    private boolean isCorrect;

    public OnBoardingFragment13() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding13, container, false);

        // Get Question object from the Factory
        question = QuestionFactory.onBoardingQuestions.get(QUESTION_ID-1);

        // Init Views
        textViewTitle = view.findViewById(R.id.text_title);
        textViewQuestion = view.findViewById(R.id.text_question);
        textViewAnswer1 = view.findViewById(R.id.text_answer1);
        textViewAnswer2 = view.findViewById(R.id.text_answer2);
        textViewAnswer3 = view.findViewById(R.id.text_answer3);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        progressBar = view.findViewById(R.id.progressBar);
        viewPager = getActivity().findViewById(R.id.viewPager);

        progressBar.setMax(viewPager.getAdapter().getItemCount());
        progressBar.setProgress(SEQUENCE_NUMBER);
        buttonFabNext.setEnabled(false);

        isCorrect = false;

        // Init methods
        setOnClickListeners();
        insertContent();

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
    }

    /**
     * This onClickListener is responsible for handling the answers from the User
     * @param v
     */
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
            }
        }
    }
}