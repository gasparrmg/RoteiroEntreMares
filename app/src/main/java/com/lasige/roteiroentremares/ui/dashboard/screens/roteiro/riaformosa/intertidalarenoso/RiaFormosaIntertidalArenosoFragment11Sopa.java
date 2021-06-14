package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rjbasitali.wordsearch.Word;
import com.rjbasitali.wordsearch.WordSearchView;

import java.util.Locale;

public class RiaFormosaIntertidalArenosoFragment11Sopa extends Fragment {

    private static final String htmlContent = "Tenta descobrir alguns dos organismos presentes nas poças de maré:<br><br>Anémona, Caranguejo, Camarão, Mexilhão, Marachomba";

    // Views
    // private TextView textViewTitle;
    // private TextView textViewTitle2;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private WordSearchView wordsGrid;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private int wordsFound;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_microhabitats_pocas5_sopa_letras, container, false);
        ttsEnabled = false;
        initViews(view);
        setupWordSearch(view);
        setOnClickListeners(view);
        insertContent();

        wordsFound = 0;

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_intertidalarenoso_title));
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
        /*textViewTitle = view.findViewById(R.id.text_title);
        textViewTitle2 = view.findViewById(R.id.text_title2);*/
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setupWordSearch(View view) {
        wordsGrid = view.findViewById(R.id.wordsGrid);

        wordsGrid.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.open_sans));


        // create a 10x10 grid of characters which includes words to be found
        wordsGrid.setLetters(new char[][]{
                "CMFUIWQRYW".toCharArray(),
                "MARACHOMBA".toCharArray(),
                "GPRVTSIOCA".toCharArray(),
                "OKKAUXÃRNS".toCharArray(),
                "VOCPNRVÉJZ".toCharArray(),
                "MRHVAGMMYG".toCharArray(),
                "IJIMXOURAC".toCharArray(),
                "JRAHNMDEKI".toCharArray(),
                "JCZAMQKOJX".toCharArray(),
                "DMEXILHÃOO".toCharArray()
        });

        // words with their respective starting and ending X and Y values in the grid
        wordsGrid.setWords(
                new Word("MARACHOMBA", false, 1, 0, 1, 9),
                new Word("CARANGUEJO", false, 0, 0, 9, 9),
                new Word("MEXILHÃO", false, 9, 1, 9, 8),
                new Word("OÃRAMAC", false, 8, 1, 2, 7),
                new Word("ANÉMONA", false, 8, 3, 2, 9));

        // callback when a word is found
        wordsGrid.setOnWordSearchedListener(new WordSearchView.OnWordSearchedListener() {
            @Override
            public void wordFound(String word) {
                if (word.equals("OÃRAMAC")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(word);
                    stringBuilder.reverse();
                    Toast.makeText(getActivity(), "Encontraste a palavra " + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Encontraste a palavra " + word, Toast.LENGTH_SHORT).show();
                }


                wordsFound++;

                if (wordsFound >= 5) {
                    buttonFabNext.setVisibility(View.VISIBLE);
                    buttonFabNext.setEnabled(true);

                    vibrator.vibrate(500);
                }
            }
        });
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(view).navigate(R.id.action_global_roteiroFragment);
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordsFound < 5) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment11Sopa_to_riaFormosaIntertidalArenosoFragment12);
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
                    Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment11Sopa_to_riaFormosaIntertidalArenosoFragment12);
                }
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
}