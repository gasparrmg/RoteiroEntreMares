package com.android.roteiroentremares.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Question;
import com.google.android.material.card.MaterialCardView;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to generate all the image quizzes
 */
public class ImageQuestionUtils {
    /**
     * Checks answer and modifies TextView's style accordingly
     * @param context
     * @param textView
     * @param cardView
     * @param answered
     * @param correctAnswer
     * @return
     */
    public static boolean isCorrect(Context context, TextView textView, MaterialCardView cardView, int answered, int correctAnswer) {
        if (answered == correctAnswer) {
            // Correct background
            //cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorCorrect));
            cardView.setBackgroundResource(R.drawable.background_answer_correct);
            textView.setTextColor(Color.WHITE);
            return true;
        } else {
            // Wrong background
            //cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
            cardView.setBackgroundResource(R.drawable.background_answer_incorrect);
            textView.setTextColor(Color.WHITE);
            return false;
        }
    }

    public static boolean isCorrectMultiple(Context context, TextView textView, MaterialCardView cardView, int answered, int[] correctAnswers) {
        if (ArrayUtils.contains(correctAnswers, answered)) {
            // Correct background
            //cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorCorrect));
            cardView.setBackgroundResource(R.drawable.background_answer_correct);
            textView.setTextColor(Color.WHITE);
            return true;
        } else {
            // Wrong background
            //cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
            cardView.setBackgroundResource(R.drawable.background_answer_incorrect);
            textView.setTextColor(Color.WHITE);
            return false;
        }
    }
}
