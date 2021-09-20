package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.AvistamentoDunasRiaFormosa;
import com.lasige.roteiroentremares.ui.dashboard.GuiaDeCampoActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaDunasFragmentDunaSecundaria4Tarefa extends Fragment {

    private static final String ZONA = "Duna secundária";

    private static final String htmlContent = "Repete o procedimento em três quadrados diferentes.";

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewQuadrado1;
    private ImageView imageViewQuadrado1Icon;

    private MaterialCardView cardViewQuadrado2;
    private ImageView imageViewQuadrado2Icon;

    private MaterialCardView cardViewQuadrado3;
    private ImageView imageViewQuadrado3Icon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonAvistamentos;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean quadrado1Finished;
    private boolean quadrado2Finished;
    private boolean quadrado3Finished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_dunas_dunaembrionaria3_tarefa, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);
        ttsEnabled = false;
        initViews(view);
        setOnClickListeners(view);
        insertContent();

        checkVisited();

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
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);

        cardViewQuadrado1 = view.findViewById(R.id.cardview_quadrado1);
        imageViewQuadrado1Icon = view.findViewById(R.id.imageview_quadrado1_ic);

        cardViewQuadrado2 = view.findViewById(R.id.cardview_quadrado2);
        imageViewQuadrado2Icon = view.findViewById(R.id.imageview_quadrado2_ic);

        cardViewQuadrado3 = view.findViewById(R.id.cardview_quadrado3);
        imageViewQuadrado3Icon = view.findViewById(R.id.imageview_quadrado3_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonAvistamentos = view.findViewById(R.id.button_avistamentos);
    }

    private void setOnClickListeners(View view) {
        cardViewQuadrado1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quadrado1Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Grelha já submetida");
                    materialAlertDialogBuilder.setMessage("Esta grelha já foi guardada. Tens a certeza que a queres substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            guiaDeCampoViewModel.deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(1, ZONA);
                            quadrado1Finished = false;

                            RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 1);
                            Navigation.findNavController(view).navigate(action);
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                } else {
                    RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 1);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        cardViewQuadrado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quadrado2Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Grelha já submetida");
                    materialAlertDialogBuilder.setMessage("Esta grelha já foi guardada. Tens a certeza que a queres substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            guiaDeCampoViewModel.deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(2, ZONA);
                            quadrado2Finished = false;

                            RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 2);
                            Navigation.findNavController(view).navigate(action);
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                } else {
                    RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 2);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        cardViewQuadrado3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quadrado3Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Grelha já submetida");
                    materialAlertDialogBuilder.setMessage("Esta grelha já foi guardada. Tens a certeza que a queres substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            guiaDeCampoViewModel.deleteAvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias(3, ZONA);
                            quadrado3Finished = false;

                            RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 3);
                            Navigation.findNavController(view).navigate(action);
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    materialAlertDialogBuilder.show();
                } else {
                    RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.ActionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment action = RiaFormosaDunasFragmentDunaSecundaria4TarefaDirections.actionRiaFormosaDunasFragmentDunaSecundaria4TarefaToNewAvistamentoDunasFragment(ZONA, 3);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(quadrado1Finished && quadrado2Finished && quadrado3Finished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setRiaFormosaDunasDunaSecundariaAsFinished();
                            Navigation.findNavController(view).popBackStack(R.id.riaFormosaDunasFragment8Menu ,false);
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
                    dashboardViewModel.setRiaFormosaDunasDunaSecundariaAsFinished();
                    Navigation.findNavController(view).popBackStack(R.id.riaFormosaDunasFragment8Menu ,false);
                }
            }
        });

        buttonAvistamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ir para guia de campo avistamentos
                Intent intent = new Intent(getActivity(), GuiaDeCampoActivity.class);
                intent.putExtra(GuiaDeCampoActivity.GUIA_CAMPO_INTENT_EXTRA_KEY_INITIAL_TAB, 1);
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Duna Secundária",
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

    private void checkVisited() {
        guiaDeCampoViewModel.getAllAvistamentoDunasRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoDunasRiaFormosa>>() {
            @Override
            public void onChanged(List<AvistamentoDunasRiaFormosa> avistamentosDunasRiaFormosa) {
                for (int i = 0; i < avistamentosDunasRiaFormosa.size(); i++) {
                    Log.d("TESTE_VISUAL", "id Avistamento: " + avistamentosDunasRiaFormosa.get(i).getIdAvistamentoDunas());
                    if (avistamentosDunasRiaFormosa.get(i).getZona().equals(ZONA)) {
                        switch (avistamentosDunasRiaFormosa.get(i).getIteracao()) {
                            case 1:
                                setVisitedIcon(imageViewQuadrado1Icon);
                                quadrado1Finished = true;
                                break;
                            case 2:
                                setVisitedIcon(imageViewQuadrado2Icon);
                                quadrado2Finished = true;
                                break;
                            case 3:
                                setVisitedIcon(imageViewQuadrado3Icon);
                                quadrado3Finished = true;
                                break;
                            default:
                                break;
                        }

                        checkIfTaskFinished();
                    }
                }
            }
        });
    }

    private void checkIfTaskFinished() {
        if (quadrado1Finished && quadrado2Finished && quadrado3Finished) {
            if (buttonFabNext.getVisibility() == View.INVISIBLE) {
                buttonFabNext.setVisibility(View.VISIBLE);
                buttonFabNext.setEnabled(true);
            }
        }
    }

    private void setVisitedIcon(ImageView imageView) {
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setImageResource(R.drawable.ic_check);
    }
}