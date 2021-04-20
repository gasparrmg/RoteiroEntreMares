package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas;

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
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.AvistamentosRiaFormosaChartsActivity;
import com.android.roteiroentremares.util.ImageQuestionUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class RiaFormosaDunasFragment9Pergunta1 extends Fragment implements View.OnClickListener {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewAnswer1;
    private TextView textViewAnswer1;

    private MaterialCardView cardViewAnswer2;
    private TextView textViewAnswer2;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonCharts;

    private TextToSpeech tts;

    private boolean isCorrect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_dunas9_pergunta_1, container, false);

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
                    String text = "Analisa o gráfico obtido com base nos registos que realizaste." +
                            "Onde se observa maior diversidade de plantas?";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewContent = view.findViewById(R.id.text_question);
        textViewTitle = view.findViewById(R.id.text_title);
        textViewTitle.setVisibility(View.GONE);

        cardViewAnswer1 = view.findViewById(R.id.cardview_answer1);
        textViewAnswer1 = view.findViewById(R.id.textview_answer1);

        cardViewAnswer2 = view.findViewById(R.id.cardview_answer2);
        textViewAnswer2 = view.findViewById(R.id.textview_answer2);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonCharts = view.findViewById(R.id.button_charts);

        cardViewAnswer1.setOnClickListener(this);
        cardViewAnswer2.setOnClickListener(this);
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
                if (!isCorrect) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_question_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment9Pergunta1_to_riaFormosaDunasFragment10Pergunta2);
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
                    Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragment9Pergunta1_to_riaFormosaDunasFragment10Pergunta2);
                }
            }
        });

        buttonCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvistamentosRiaFormosaChartsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {

        textViewContent.setText(HtmlCompat.fromHtml(
                "<b>1.</b> Analisa o gráfico obtido com base nos registos que realizaste. Onde se observa maior diversidade de plantas?",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewAnswer1.setText("Na direção Mar-terra");
        textViewAnswer2.setText("Na direção Terra-Mar");

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
                    isCorrect = ImageQuestionUtils.isCorrect(getContext(), textViewAnswer1, cardViewAnswer1, 1, 1);
                    break;
                case R.id.cardview_answer2:
                    isCorrect = ImageQuestionUtils.isCorrect(getContext(), textViewAnswer2, cardViewAnswer2, 2, 1);
                    break;
                default:
                    break;
            }

            if (isCorrect) {
                Toast.makeText(getActivity(), getResources().getString(R.string.message_correct_answer), Toast.LENGTH_SHORT).show();

                cardViewAnswer1.setEnabled(false);
                cardViewAnswer2.setEnabled(false);
                buttonFabNext.setEnabled(true);
                buttonFabNext.setVisibility(View.VISIBLE);
            }
        }
    }
}