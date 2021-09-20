package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

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
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.ui.dashboard.GuiaDeCampoActivity;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa.AvistamentosRiaFormosaPocasTranseptosChartsActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaIntertidalArenosoFragment13TarefaTranseptos extends Fragment {

    private static final String htmlContent = "Repete este procedimento em três transeptos diferentes";

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;

    private MaterialCardView cardViewTransepto1;
    private ImageView imageViewTransepto1Icon;

    private MaterialCardView cardViewTransepto2;
    private ImageView imageViewTransepto2Icon;

    private MaterialCardView cardViewTransepto3;
    private ImageView imageViewTransepto3Icon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonAvistamentos;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean transepto1Finished;
    private boolean transepto2Finished;
    private boolean transepto3Finished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_intertidalarenoso13_tarefa_transeptos, container, false);
        ttsEnabled = false;
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

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

        cardViewTransepto1 = view.findViewById(R.id.cardview_transepto1);
        imageViewTransepto1Icon = view.findViewById(R.id.imageview_transepto1_ic);

        cardViewTransepto2 = view.findViewById(R.id.cardview_transepto2);
        imageViewTransepto2Icon = view.findViewById(R.id.imageview_transepto2_ic);

        cardViewTransepto3 = view.findViewById(R.id.cardview_transepto3);
        imageViewTransepto3Icon = view.findViewById(R.id.imageview_transepto3_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonAvistamentos = view.findViewById(R.id.button_avistamentos);
    }

    private void setOnClickListeners(View view) {
        buttonAvistamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ir para guia de campo avistamentos
                Intent intent = new Intent(getActivity(), GuiaDeCampoActivity.class);
                intent.putExtra(GuiaDeCampoActivity.GUIA_CAMPO_INTENT_EXTRA_KEY_INITIAL_TAB, 1);
                startActivity(intent);
            }
        });

        cardViewTransepto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transepto1Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Transepto já submetido!");
                    materialAlertDialogBuilder.setMessage("Este transepto já foi guardado. Tens a certeza que o queres apagar e substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Apagar e substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(1);
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
                    RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(1);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        cardViewTransepto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transepto2Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Transepto já submetido!");
                    materialAlertDialogBuilder.setMessage("Este transepto já foi guardado. Tens a certeza que o queres apagar e substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Apagar e substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(2);
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
                    RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(2);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        cardViewTransepto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transepto3Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Transepto já submetido!");
                    materialAlertDialogBuilder.setMessage("Este transepto já foi guardado. Tens a certeza que o queres apagar e substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Apagar e substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(3);
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
                    RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.ActionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment action = RiaFormosaIntertidalArenosoFragment13TarefaTranseptosDirections.actionRiaFormosaIntertidalArenosoFragment13TarefaTranseptosToRiaFormosaNewAvistamentoTranseptoFragment(3);
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
                if (!(transepto1Finished && transepto2Finished && transepto3Finished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment13TarefaTranseptos_to_riaFormosaIntertidalArenosoFragment14);
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
                    Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment13TarefaTranseptos_to_riaFormosaIntertidalArenosoFragment14);
                }
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Biodiversidade",
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
        guiaDeCampoViewModel.getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(1).observe(getViewLifecycleOwner(), new Observer<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>() {
            @Override
            public void onChanged(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                if (avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias != null) {
                    setVisitedIcon(imageViewTransepto1Icon);
                    transepto1Finished = true;
                    checkIfTaskFinished();
                }
            }
        });

        guiaDeCampoViewModel.getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(2).observe(getViewLifecycleOwner(), new Observer<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>() {
            @Override
            public void onChanged(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                if (avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias != null) {
                    setVisitedIcon(imageViewTransepto2Icon);
                    transepto2Finished = true;
                    checkIfTaskFinished();
                }
            }
        });

        guiaDeCampoViewModel.getAvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias(3).observe(getViewLifecycleOwner(), new Observer<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias>() {
            @Override
            public void onChanged(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias) {
                if (avistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias != null) {
                    setVisitedIcon(imageViewTransepto3Icon);
                    transepto3Finished = true;
                    checkIfTaskFinished();
                }
            }
        });
    }

    private void checkIfTaskFinished() {
        /*if (transepto1Finished || transepto2Finished || transepto3Finished) {
            buttonGraficos.setVisibility(View.VISIBLE);
        } else {
            buttonGraficos.setVisibility(View.GONE);
        }*/

        if (transepto1Finished && transepto2Finished && transepto3Finished) {
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