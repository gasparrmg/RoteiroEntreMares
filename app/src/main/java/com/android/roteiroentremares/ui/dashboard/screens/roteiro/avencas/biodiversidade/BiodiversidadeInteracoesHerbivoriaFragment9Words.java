package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

public class BiodiversidadeInteracoesHerbivoriaFragment9Words extends Fragment {

    private static final String htmlContent = "Observa agora através do botão abaixo o gráfico que ilustra a percentagem de cobertura de diferentes tipos de algas ao longo do tempo. Que tipo de algas correspondem às duas linhas do gráfico?";

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonCharts;

    private MaterialCardView cardView1;
    private MaterialCardView cardView2;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private LinearLayout linearLayoutContainer1;
    private LinearLayout linearLayoutContainer2;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_interacoes_herbivoria9_words, container, false);

        ttsEnabled = false;

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
        textViewTitle = view.findViewById(R.id.textView_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonCharts = view.findViewById(R.id.button_charts);

        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);

        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);

        linearLayoutContainer1 = view.findViewById(R.id.linearLayout_container1);
        linearLayoutContainer2 = view.findViewById(R.id.linearLayout_container2);

        cardView1.setOnLongClickListener(longClickListener);
        cardView2.setOnLongClickListener(longClickListener);

        linearLayout1.setOnDragListener(dragListener);
        linearLayout2.setOnDragListener(dragListener);
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

                if (!(linearLayoutContainer1.getChildCount() == 0 && linearLayoutContainer2.getChildCount() == 0)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesHerbivoriaFragment9Words_to_biodiversidadeInteracoesHerbivoriaFragment10);
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
                    Navigation.findNavController(view).navigate(R.id.action_biodiversidadeInteracoesHerbivoriaFragment9Words_to_biodiversidadeInteracoesHerbivoriaFragment10);
                }
            }
        });

        buttonCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, R.drawable.img_biodiversidade_interacoes_herbivoria_resultados_3);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Experiências",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

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

    private void checkIfCompleted() {
        if (linearLayoutContainer1.getChildCount() == 0 && linearLayoutContainer2.getChildCount() == 0) {
            Toast.makeText(getActivity(), "Parabéns! Completaste a tarefa!", Toast.LENGTH_LONG).show();
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData.Item item = new ClipData.Item(v.getTag().toString());

            ClipData dragData = new ClipData(
                    v.getTag().toString(),
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    item
            );

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

            v.startDragAndDrop(dragData, shadow, v, 0);

            v.setVisibility(View.INVISIBLE);

            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            LinearLayout destination = (LinearLayout) view;
            View draggedView = (View) event.getLocalState();
            ViewGroup owner = (ViewGroup) draggedView.getParent();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    view.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedView.setVisibility(View.VISIBLE);
                    view.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DROP:

                    if (destination.getId() == linearLayout1.getId()) {
                        if (draggedView.getId() == cardView2.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout2.getId()) {
                        if (draggedView.getId() == cardView1.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else {
                        Log.d("DragDrop","Não foi para nenhum dos containers.");
                        draggedView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    Log.d("DragDrop","past events");

                    checkIfCompleted();

                    return true;
                default:
                    Log.e("DragDrop","Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    };
}