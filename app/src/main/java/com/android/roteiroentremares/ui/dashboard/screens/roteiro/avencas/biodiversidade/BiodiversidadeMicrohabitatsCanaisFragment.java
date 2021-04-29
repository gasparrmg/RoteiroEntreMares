package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.util.TimeUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class BiodiversidadeMicrohabitatsCanaisFragment extends Fragment {

    private static final String htmlContent = "Os canais são zonas permanentemente ligados ao mar, permanecendo com água durante todo o período de baixa-mar. Estes locais são utilizados como berçário de espécies de peixes comercialmente exploradas (para saberes um pouco mais, ouve a seguinte gravação áudio)";

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;
    private TextView textViewContent2;

    private ImageButton buttonPlay;
    private ImageButton buttonPause;
    private TextView textViewAudioDuration;
    private SeekBar seekBar;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private MediaPlayer mediaPlayer;
    private TextToSpeech tts;

    private Handler handlerSeekBar;
    private Runnable updateSeekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_microhabitats_canais, container, false);

        initViews(view);
        setOnClickListeners(view);
        insertContent();

        setupAudio();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
        setupAudio();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.avencas_biodiversidade_title));
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

        stopPlayer();

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
                    if (mediaPlayer.isPlaying()) {
                        pause();
                    }
                    String text = HtmlCompat.fromHtml(
                            htmlContent,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewContent = view.findViewById(R.id.text_content);
        textViewContent2 = view.findViewById(R.id.text_content2);

        buttonPlay = view.findViewById(R.id.imagebutton_play_audio);
        buttonPause = view.findViewById(R.id.imagebutton_pause_audio);
        textViewAudioDuration = view.findViewById(R.id.textView_audio_duration);
        seekBar = view.findViewById(R.id.seekbar_audio);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();

                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    play();
                }
            }
        });
    }

    private void setOnClickListeners(View view) {
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

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
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsCanaisFragment_to_biodiversidadeMicrohabitatsCanaisFragment2);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Microhabitats",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                "Canais",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));


        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent2.setText("Esta gravação é da autoria de Frederico Almada, Biólogo Marinho.");

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

    private void setupAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.audio_biodiversidade1);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();

                    seekBar.setProgress(0);

                    handlerSeekBar.removeCallbacks(updateSeekBar);

                    buttonPause.setVisibility(View.GONE);
                    buttonPlay.setVisibility(View.VISIBLE);
                }
            });

            seekBar.setMax(mediaPlayer.getDuration());
            textViewAudioDuration.setText(TimeUtils.getTimeString(mediaPlayer.getDuration()));
            textViewAudioDuration.setVisibility(View.VISIBLE);
        }
    }

    private void play() {
        if (mediaPlayer == null) {
            setupAudio();
        }

        if (mediaPlayer != null) {
            if (tts.isSpeaking()) {
                tts.stop();
            }

            buttonPlay.setVisibility(View.GONE);
            buttonPause.setVisibility(View.VISIBLE);

            mediaPlayer.start();

            handlerSeekBar = new Handler();
            updateRunnable();
            handlerSeekBar.postDelayed(updateSeekBar, 0);
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            buttonPause.setVisibility(View.GONE);
            buttonPlay.setVisibility(View.VISIBLE);

            mediaPlayer.pause();

            handlerSeekBar.removeCallbacks(updateSeekBar);
        }
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            if (handlerSeekBar != null) {
                handlerSeekBar.removeCallbacks(updateSeekBar);
            }

            mediaPlayer.release();
            mediaPlayer = null;

            Log.d("BIODIVERSIDADE_MICROHABITATS_CANAIS_FRAGMENT", "MediaPlayer released.");
        }
    }

    private void updateRunnable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handlerSeekBar.postDelayed(this, 500);
            }
        };
    }
}