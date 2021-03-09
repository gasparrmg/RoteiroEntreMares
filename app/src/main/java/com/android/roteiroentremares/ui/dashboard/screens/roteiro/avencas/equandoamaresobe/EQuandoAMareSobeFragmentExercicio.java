package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.equandoamaresobe;

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
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.factory.QuestionFactory;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.ImageQuestionUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EQuandoAMareSobeFragmentExercicio extends Fragment implements View.OnClickListener {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewAnswer1;
    private ImageView imageViewAnswer1;
    private TextView textViewAnswer1;

    private MaterialCardView cardViewAnswer2;
    private ImageView imageViewAnswer2;
    private TextView textViewAnswer2;

    private MaterialCardView cardViewAnswer3;
    private ImageView imageViewAnswer3;
    private TextView textViewAnswer3;

    private ExtendedFloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    private boolean isCorrect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e_quando_a_mare_sobe_exercicio, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        isCorrect = false;

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
        SpannableString s = new SpannableString("Histórias do Passado");
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
                if (tts.isSpeaking()) {
                    tts.stop();
                } else {
                    String text = "Tendo em conta o que aprendeste sobre o comportamento destes animais durante a maré-cheia, seleciona qual ou quais os organismos que podem ser classificados como sésseis (que não apresentam mobilidade):";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
        }
        return false;
    }

    private void initViews(View view) {
        textViewContent = view.findViewById(R.id.text_question);
        textViewTitle = view.findViewById(R.id.text_title);

        cardViewAnswer1 = view.findViewById(R.id.cardview_answer1);
        imageViewAnswer1 = view.findViewById(R.id.imageview_answer1);
        textViewAnswer1 = view.findViewById(R.id.textview_answer1);

        cardViewAnswer2 = view.findViewById(R.id.cardview_answer2);
        imageViewAnswer2 = view.findViewById(R.id.imageview_answer2);
        textViewAnswer2 = view.findViewById(R.id.textview_answer2);

        cardViewAnswer3 = view.findViewById(R.id.cardview_answer3);
        imageViewAnswer3 = view.findViewById(R.id.imageview_answer3);
        textViewAnswer3 = view.findViewById(R.id.textview_answer3);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        cardViewAnswer1.setOnClickListener(this);
        cardViewAnswer2.setOnClickListener(this);
        cardViewAnswer3.setOnClickListener(this);
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
                dashboardViewModel.setEQuandoAMareSobeAsFinished();
                Navigation.findNavController(view).popBackStack(R.id.roteiroFragment ,false);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText("Pergunta");

        textViewContent.setText(HtmlCompat.fromHtml(
                "Tendo em conta o que aprendeste sobre o comportamento destes animais durante a maré-cheia, seleciona qual ou quais os organismos que podem ser classificados como <b>sésseis (que não apresentam mobilidade)</b>:",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewAnswer1.setText("Peixes residentes");
        textViewAnswer2.setText("Cracas");
        textViewAnswer3.setText("Lapas");

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TEXT2SPEECH", "Language not supported");
                        Toast.makeText(getActivity(), "Não tens o linguagem Português disponível no teu dispositivo. Isto acontece normalmente acontece quando a linguagem padrão do dispositivo é outra que não o Português.", Toast.LENGTH_LONG).show();
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
        if (!isCorrect) {
            Log.d("QUESTION_TEST", "onClick -> !isCorrect");
            switch (v.getId()) {
                case R.id.cardview_answer1:
                    isCorrect = ImageQuestionUtils.isCorrect(getContext(), textViewAnswer1, cardViewAnswer1, 1, 2);
                    break;
                case R.id.cardview_answer2:
                    isCorrect = ImageQuestionUtils.isCorrect(getContext(), textViewAnswer2, cardViewAnswer2, 2, 2);
                    break;
                case R.id.cardview_answer3:
                    isCorrect = ImageQuestionUtils.isCorrect(getContext(), textViewAnswer3, cardViewAnswer3,3, 2);
                    break;
                default:
                    break;
            }

            if (isCorrect) {
                cardViewAnswer1.setEnabled(false);
                cardViewAnswer2.setEnabled(false);
                cardViewAnswer3.setEnabled(false);
                buttonFabNext.setEnabled(true);
                buttonFabNext.setVisibility(View.VISIBLE);
            }
        }
    }
}