package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

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
import com.lasige.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeMicrohabitatsPocasFragment4Tarefa extends Fragment {

    private static final String htmlContent = "<b>TAREFA:</b> Observa com atenção uma poça perto de ti e regista todos os organismos presentes, tentando identifica-los com a ajuda do <b>Guia de Campo</b>.<br><br>Repete este procedimento em três poças diferentes. " +
            "Regista as principais características de cada uma das poças e tira uma fotografia a cada uma delas.";

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;

    private MaterialCardView cardViewPoca1;
    private ImageView imageViewPoca1Icon;

    private MaterialCardView cardViewPoca2;
    private ImageView imageViewPoca2Icon;

    private MaterialCardView cardViewPoca3;
    private ImageView imageViewPoca3Icon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    private boolean poca1Finished;
    private boolean poca2Finished;
    private boolean poca3Finished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_microhabitats_pocas4_tarefa, container, false);

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
        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewContent = view.findViewById(R.id.text_content);

        cardViewPoca1 = view.findViewById(R.id.cardview_poca1);
        imageViewPoca1Icon = view.findViewById(R.id.imageview_poca1_ic);

        cardViewPoca2 = view.findViewById(R.id.cardview_poca2);
        imageViewPoca2Icon = view.findViewById(R.id.imageview_poca2_ic);

        cardViewPoca3 = view.findViewById(R.id.cardview_poca3);
        imageViewPoca3Icon = view.findViewById(R.id.imageview_poca3_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        cardViewPoca1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poca1Finished) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Poça já submetida");
                    materialAlertDialogBuilder.setMessage("Esta poça já foi guardada. Tens a certeza que a queres substituir com um novo registo?");
                    materialAlertDialogBuilder.setPositiveButton("Substituir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.ActionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment action = BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.actionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment(1);
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
                    BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.ActionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment action = BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.actionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment(1);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        cardViewPoca2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.ActionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment action = BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.actionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment(2);
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewPoca3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.ActionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment action = BiodiversidadeMicrohabitatsPocasFragment4TarefaDirections.actionBiodiversidadeMicrohabitatsPocasFragment4TarefaToNewEditAvistamentoPocasFragment(3);
                Navigation.findNavController(view).navigate(action);
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
                if (!(poca1Finished && poca2Finished && poca3Finished)) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsPocasFragment4Tarefa_to_biodiversidadeMicrohabitatsPocasFragment5SopaLetras);
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
                    Navigation.findNavController(view).navigate(R.id.action_biodiversidadeMicrohabitatsPocasFragment4Tarefa_to_biodiversidadeMicrohabitatsPocasFragment5SopaLetras);
                }
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
                "Poças de maré",
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
        guiaDeCampoViewModel.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(1).observe(getViewLifecycleOwner(), new Observer<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>() {
            @Override
            public void onChanged(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias != null) {
                    setVisitedIcon(imageViewPoca1Icon);
                    poca1Finished = true;
                    checkIfTaskFinished();
                }
            }
        });

        guiaDeCampoViewModel.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(2).observe(getViewLifecycleOwner(), new Observer<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>() {
            @Override
            public void onChanged(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias != null) {
                    setVisitedIcon(imageViewPoca2Icon);
                    poca2Finished = true;
                    checkIfTaskFinished();
                }
            }
        });

        guiaDeCampoViewModel.getAvistamentoPocasAvencasWithEspecieAvencasPocasInstancias(3).observe(getViewLifecycleOwner(), new Observer<AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias>() {
            @Override
            public void onChanged(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPocasAvencasWithEspecieAvencasPocasInstancias) {
                if (avistamentoPocasAvencasWithEspecieAvencasPocasInstancias != null) {
                    setVisitedIcon(imageViewPoca3Icon);
                    poca3Finished = true;
                    checkIfTaskFinished();
                }
            }
        });
    }

    private void checkIfTaskFinished() {
        if (poca1Finished && poca2Finished && poca3Finished) {
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