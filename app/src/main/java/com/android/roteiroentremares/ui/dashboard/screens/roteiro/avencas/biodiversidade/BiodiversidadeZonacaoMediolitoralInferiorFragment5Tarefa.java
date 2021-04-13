package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.DialogInterface;
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
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.AvistamentoZonacaoAvencas;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeZonacaoMediolitoralInferiorFragment5Tarefa extends Fragment {

    private static final String ZONA = "Mediolitoral inferior";

    private static final String htmlContent = "Repete o procedimento em três quadrados diferentes.";

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;

    private MaterialCardView cardViewQuadrado1;
    private ImageView imageViewQuadrado1Icon;

    private MaterialCardView cardViewQuadrado2;
    private ImageView imageViewQuadrado2Icon;

    private MaterialCardView cardViewQuadrado3;
    private ImageView imageViewQuadrado3Icon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    private boolean quadrado1Finished;
    private boolean quadrado2Finished;
    private boolean quadrado3Finished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao_mediolitoral_inferior5_tarefa, container, false);

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

        cardViewQuadrado1 = view.findViewById(R.id.cardview_quadrado1);
        imageViewQuadrado1Icon = view.findViewById(R.id.imageview_quadrado1_ic);

        cardViewQuadrado2 = view.findViewById(R.id.cardview_quadrado2);
        imageViewQuadrado2Icon = view.findViewById(R.id.imageview_quadrado2_ic);

        cardViewQuadrado3 = view.findViewById(R.id.cardview_quadrado3);
        imageViewQuadrado3Icon = view.findViewById(R.id.imageview_quadrado3_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
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
                            guiaDeCampoViewModel.deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(1, ZONA);
                            quadrado1Finished = false;

                            BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 1);
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
                    BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 1);
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
                            guiaDeCampoViewModel.deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(2, ZONA);
                            quadrado2Finished = false;

                            BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 2);
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
                    BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 2);
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
                            guiaDeCampoViewModel.deleteAvistamentoZonacaoAvencasWithEspecieAvencasZonacaoInstancias(3, ZONA);
                            quadrado3Finished = false;

                            BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 3);
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
                    BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.ActionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment action = BiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaDirections.actionBiodiversidadeZonacaoMediolitoralInferiorFragment5TarefaToNewEditAvistamentoZonacaoFragment(ZONA, 3);
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
                // Mediolitoral finished
                // Back to zonacao menu

                if (!(quadrado1Finished && quadrado2Finished && quadrado3Finished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setBiodiversidadeZonacaoMediolitoralAsFinished();
                            Navigation.findNavController(view).popBackStack(R.id.biodiversidadeZonacaoFragment ,false);
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
                    dashboardViewModel.setBiodiversidadeZonacaoMediolitoralAsFinished();
                    Navigation.findNavController(view).popBackStack(R.id.biodiversidadeZonacaoFragment ,false);
                }
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Zonação",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                ZONA,
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
                        Log.e("TEXT2SPEECH", "Language not supported");
                        Toast.makeText(getActivity(), "Não tens o linguagem Português disponível no teu dispositivo. Isto acontece normalmente acontece quando a linguagem padrão do dispositivo é outra que não o Português.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }

    private void checkVisited() {
        // TODO

        guiaDeCampoViewModel.getAllAvistamentoZonacaoAvencas().observe(getViewLifecycleOwner(), new Observer<List<AvistamentoZonacaoAvencas>>() {
            @Override
            public void onChanged(List<AvistamentoZonacaoAvencas> avistamentoZonacaoAvencas) {
                for (int i = 0; i < avistamentoZonacaoAvencas.size(); i++) {
                    Log.d("TESTE_VISUAL", "id Avistamento: " + avistamentoZonacaoAvencas.get(i).getIdAvistamentoZonacao());
                    if (avistamentoZonacaoAvencas.get(i).getZona().equals(ZONA)) {
                        switch (avistamentoZonacaoAvencas.get(i).getIteracao()) {
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