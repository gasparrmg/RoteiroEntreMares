package com.android.roteiroentremares.ui.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.WindowManager;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Question;
import com.android.roteiroentremares.data.factory.QuestionFactory;
import com.android.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    // ViewModel
    /*@Inject
    OnBoardingViewModel onBoardingViewModel;*/
    private OnBoardingViewModel onBoardingViewModel;

    public ArrayList<Question> onBoardingQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        // Prevents buttons constrained on the bottom of the parent to pop-up on the top of the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Generates questions
        onBoardingQuestions = QuestionFactory.generateOnBoardingQuestions();
    }
}