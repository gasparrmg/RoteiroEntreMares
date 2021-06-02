package com.lasige.roteiroentremares.data.factory;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.Question;

import java.util.ArrayList;

/**
 * This class is used to generate all the text quizzes
 */
public class QuestionFactory {
    public static ArrayList<Question> onBoardingQuestions;

    /**
     * Generates the List with the Questions from the onBoarding sequence
     * @return
     */
    public static ArrayList<Question> generateOnBoardingQuestions() {
        onBoardingQuestions = new ArrayList<Question>();

        // Question 1
        onBoardingQuestions.add(new Question(
                1,
                "As marés altas ocorrem:",
                "Quando está Quarto Minguante",
                "Quando está Quarto Crescente",
                "Quando está Lua Nova ou Cheia",
                "Todas as anteriores",
                3
        ));

        // Question 2
        onBoardingQuestions.add(new Question(
                2,
                "As marés mortas devem-se a:",
                "Quando as forças gravitacionais da lua e do sol estão num ângulo de 90 graus, praticamente a anularem-se uma a outra.",
                "Porque a Terra não está coberta na totalidade por oceanos. Existem continentes, e essa terra “atrapalha”.",
                "Quando a Terra, a Lua e o Sol se alinham, o que acontece quando está lua cheia ou lua nova.",
                null,
                1
        ));

        // Question 3
        onBoardingQuestions.add(new Question(
                3,
                "Em alguns lugares ocorre apenas uma maré alta e uma maré baixa por dia, isto deve-se:",
                "À inclinação do eixo de rotação da terra ao redor do sol, em conjunto com a inclinação da órbita da lua em torno da Terra.",
                "À inclinação do eixo de rotação da terra em relação ao plano da sua órbita ao redor do sol.",
                "À inclinação da órbita da lua em torno da Terra.",
                null,
                1
        ));

        return onBoardingQuestions;
    }

    /**
     * Checks answer and modifies TextView's style accordingly
     * @param view
     * @param answered
     * @param correctAnswer
     * @return
     */
    public static boolean isCorrect(Context context, TextView view, int answered, int correctAnswer) {
        if (answered == correctAnswer) {
            // Correct background
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.background_answer_correct));
            view.setTextColor(Color.WHITE);
            return true;
        } else {
            // Wrong background
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.background_answer_incorrect));
            view.setTextColor(Color.WHITE);
            return false;
        }
    }
}
