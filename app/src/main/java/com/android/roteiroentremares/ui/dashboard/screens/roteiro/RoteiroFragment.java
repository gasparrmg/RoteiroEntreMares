package com.android.roteiroentremares.ui.dashboard.screens.roteiro;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoteiroFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    private int avencasOrRiaFormosa;

    // Avencas

    private MaterialCardView cardViewEQuandoAMareSobe;
    private ImageView imageViewEQuandoAMareSobe;
    private TextView textViewEQuandoAMareSobeIsFinished;

    private MaterialCardView cardViewImpactos;
    private ImageView imageViewImpactos;
    private TextView textViewImpactosIsFinished;

    private MaterialCardView cardViewHistoriasPassado;
    private ImageView imageViewHistoriasPassado;
    private TextView textViewHistoriasPassadoIsFinished;

    private MaterialCardView cardViewNaoFiquesPorAqui;
    private ImageView imageViewNaoFiquesPorAqui;
    private TextView textViewNaoFiquesPorAquiIsFinished;

    private MaterialCardView cardViewBiodiversidade;
    private ImageView imageViewBiodiversidade;
    private TextView textViewBiodiversidadeIsFinished;

    private boolean isHistoriasPassadoFinished;
    private boolean isNaoFiquesPorAquiFinished;
    private boolean isEQuandoAMareSobeFinished;
    private boolean isImpactosFinished;
    private boolean isBiodiversidadeFinished;

    // Ria Formosa

    private MaterialCardView cardViewSapal;
    private ImageView imageViewSapal;
    private TextView textViewSapalIsFinished;

    private MaterialCardView cardViewIntertidalArenoso;
    private ImageView imageViewIntertidalArenoso;
    private TextView textViewIntertidalArenosoIsFinished;

    private MaterialCardView cardViewDunas;
    private ImageView imageViewDunas;
    private TextView textViewDunasIsFinished;

    private MaterialCardView cardViewNaoFiquesPorAqui2;
    private ImageView imageViewNaoFiquesPorAqui2;
    private TextView textViewNaoFiquesPorAqui2IsFinished;

    private MaterialCardView cardViewPradariasMarinhas;
    private ImageView imageViewPradariasMarinhas;
    private TextView textViewPradariasMarinhasIsFinished;

    private boolean isSapalFinished;
    private boolean isNaoFiquesPorAqui2Finished;
    private boolean isIntertidalArenosoFinished;
    private boolean isDunasFinished;
    private boolean isPradariasMarinhasFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        View view;

        avencasOrRiaFormosa = dashboardViewModel.getAvencasOrRiaFormosa();

        if (avencasOrRiaFormosa == 0) {
            // Avencas
            view = inflater.inflate(R.layout.fragment_roteiro, container, false);
        } else {
            // Ria Formosa
            view = inflater.inflate(R.layout.fragment_roteiro_riaformosa, container, false);
        }

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Escolhe um percurso...");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
    }

    private void initViews(View view) {
        if (avencasOrRiaFormosa == 0) {
            // Avencas
            cardViewHistoriasPassado = view.findViewById(R.id.cardview_historiaspassado);
            imageViewHistoriasPassado = view.findViewById(R.id.imageview_historiaspassado);
            textViewHistoriasPassadoIsFinished = view.findViewById(R.id.textView_historiaspassado_is_finished);

            cardViewImpactos = view.findViewById(R.id.cardview_impactos);
            imageViewImpactos = view.findViewById(R.id.imageview_impactos);
            textViewImpactosIsFinished = view.findViewById(R.id.textView_impactos_is_finished);

            cardViewEQuandoAMareSobe = view.findViewById(R.id.cardview_equandoamaresobe);
            imageViewEQuandoAMareSobe = view.findViewById(R.id.imageview_equandoamaresobe);
            textViewEQuandoAMareSobeIsFinished = view.findViewById(R.id.textView_equandoamaresobe_is_finished);

            cardViewNaoFiquesPorAqui = view.findViewById(R.id.cardview_naofiquesporaqui);
            imageViewNaoFiquesPorAqui = view.findViewById(R.id.imageview_naofiquesporaqui);
            textViewNaoFiquesPorAquiIsFinished = view.findViewById(R.id.textView_naofiquesporaqui_is_finished);

            cardViewBiodiversidade = view.findViewById(R.id.cardview_biodiversidade);
            imageViewBiodiversidade = view.findViewById(R.id.imageview_biodiversidade);
            textViewBiodiversidadeIsFinished = view.findViewById(R.id.textView_biodiversidade_is_finished);

            isHistoriasPassadoFinished = dashboardViewModel.isHistoriasPassadoFinished();
            isNaoFiquesPorAquiFinished = dashboardViewModel.isNaoFiquesPorAquiFinished();
            isEQuandoAMareSobeFinished = dashboardViewModel.isEQuandoAMareSobeFinished();
            isImpactosFinished = dashboardViewModel.isImpactosFinished();
            isBiodiversidadeFinished = dashboardViewModel.isBiodiversidadeFinished();

            if (isHistoriasPassadoFinished) {
                textViewHistoriasPassadoIsFinished.setVisibility(View.VISIBLE);
            }

            if (isNaoFiquesPorAquiFinished) {
                textViewNaoFiquesPorAquiIsFinished.setVisibility(View.VISIBLE);
            }

            if (isEQuandoAMareSobeFinished) {
                textViewEQuandoAMareSobeIsFinished.setVisibility(View.VISIBLE);
            }
            if (isImpactosFinished) {
                textViewImpactosIsFinished.setVisibility(View.VISIBLE);
            }

            if (isBiodiversidadeFinished) {
                textViewBiodiversidadeIsFinished.setVisibility(View.VISIBLE);
            }

            cardViewBiodiversidade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBiodiversidadeFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetBiodiversidade();
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_biodiversidadeFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_biodiversidadeFragment);
                    }
                }
            });

            cardViewHistoriasPassado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isHistoriasPassadoFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_historiasPassadoFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_historiasPassadoFragment);
                    }
                }
            });

            cardViewImpactos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isImpactosFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_impactosFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_impactosFragment);
                    }
                }
            });

            cardViewEQuandoAMareSobe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEQuandoAMareSobeFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_EQuandoAMareSobeFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_EQuandoAMareSobeFragment);
                    }
                }
            });

            cardViewNaoFiquesPorAqui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNaoFiquesPorAquiFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_naoFiquesPorAquiFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_naoFiquesPorAquiFragment);
                    }
                }
            });

            Glide.with(getActivity())
                    .load(R.drawable.img_guiadecampo_estreladomar_1)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewBiodiversidade);

            Glide.with(getActivity())
                    .load(R.drawable.img_menu_historiaspassado)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewHistoriasPassado);

            Glide.with(getActivity())
                    .load(R.drawable.img_menu_impactos)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewImpactos);

            Glide.with(getActivity())
                    .load(R.drawable.img_menu_equandoamaresobe)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewEQuandoAMareSobe);

            Glide.with(getActivity())
                    .load(R.drawable.img_menu_naofiquesporaqui)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewNaoFiquesPorAqui);
        } else {
            // Ria Formosa

            cardViewSapal = view.findViewById(R.id.cardview_sapal);
            imageViewSapal = view.findViewById(R.id.imageview_sapal);
            textViewSapalIsFinished = view.findViewById(R.id.textView_sapal_is_finished);

            cardViewIntertidalArenoso = view.findViewById(R.id.cardview_intertidalarenoso);
            imageViewIntertidalArenoso = view.findViewById(R.id.imageview_intertidalarenoso);
            textViewIntertidalArenosoIsFinished = view.findViewById(R.id.textView_intertidalarenoso_is_finished);

            cardViewDunas = view.findViewById(R.id.cardview_dunas);
            imageViewDunas = view.findViewById(R.id.imageview_dunas);
            textViewDunasIsFinished = view.findViewById(R.id.textView_dunas_is_finished);

            cardViewNaoFiquesPorAqui2 = view.findViewById(R.id.cardview_naofiquesporaqui2);
            imageViewNaoFiquesPorAqui2 = view.findViewById(R.id.imageview_naofiquesporaqui2);
            textViewNaoFiquesPorAqui2IsFinished = view.findViewById(R.id.textView_naofiquesporaqui2_is_finished);

            cardViewPradariasMarinhas = view.findViewById(R.id.cardview_pradariasmarinhas);
            imageViewPradariasMarinhas = view.findViewById(R.id.imageview_pradariasmarinhas);
            textViewPradariasMarinhasIsFinished = view.findViewById(R.id.textView_pradariasmarinhas_is_finished);

            isSapalFinished = dashboardViewModel.isRiaFormosaSapalFinished();
            isNaoFiquesPorAqui2Finished = dashboardViewModel.isRiaFormosaNaoFiquesPorAquiFinished();
            // isIntertidalArenosoFinished = dashboardViewModel.isEQuandoAMareSobeFinished();
            isDunasFinished = dashboardViewModel.isRiaFormosaDunasFinished();
            isPradariasMarinhasFinished = dashboardViewModel.isRiaFormosaPradariasMarinhasFinished();

            if (isSapalFinished) {
                textViewSapalIsFinished.setVisibility(View.VISIBLE);
            }

            if (isNaoFiquesPorAqui2Finished) {
                textViewNaoFiquesPorAqui2IsFinished.setVisibility(View.VISIBLE);
            }

            /*if (isIntertidalArenosoFinished) {
                textViewIntertidalArenosoIsFinished.setVisibility(View.VISIBLE);
            }*/

            if (isDunasFinished) {
                textViewDunasIsFinished.setVisibility(View.VISIBLE);
            }

            if (isPradariasMarinhasFinished) {
                textViewPradariasMarinhasIsFinished.setVisibility(View.VISIBLE);
            }

            cardViewSapal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSapalFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaSapalFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaSapalFragment);
                    }
                }
            });

            cardViewIntertidalArenoso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (isHistoriasPassadoFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_historiasPassadoFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_historiasPassadoFragment);
                    }*/

                    Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaIntertidalArenosoFragment);
                }
            });

            cardViewDunas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDunasFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaDunasFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaDunasFragment);
                    }
                }
            });

            cardViewPradariasMarinhas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPradariasMarinhasFinished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaPradariasMarinhasFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaPradariasMarinhasFragment);
                    }
                }
            });

            cardViewNaoFiquesPorAqui2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNaoFiquesPorAqui2Finished) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        materialAlertDialogBuilder.setTitle("Percurso terminado!");
                        materialAlertDialogBuilder.setMessage("Este percurso já foi terminado. Tens a certeza que o queres repetir?");
                        materialAlertDialogBuilder.setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaNaoFiquesPorAquiFragment);
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
                        Navigation.findNavController(view).navigate(R.id.action_roteiroFragment_to_riaFormosaNaoFiquesPorAquiFragment);
                    }
                }
            });

            Glide.with(getActivity())
                    .load(R.drawable.img_riaformosa_sapal)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewSapal);

            Glide.with(getActivity())
                    .load(R.drawable.img_riaformosa_intertidalarenoso)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewIntertidalArenoso);

            Glide.with(getActivity())
                    .load(R.drawable.img_riaformosa_dunas)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewDunas);

            Glide.with(getActivity())
                    .load(R.drawable.img_riaformosa_pradariasmarinhas)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewPradariasMarinhas);

            Glide.with(getActivity())
                    .load(R.drawable.img_riaformosa_onboarding_2)
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(imageViewNaoFiquesPorAqui2);
        }


    }

    private void resetBiodiversidade() {
        // Reset shared preferences variables
        // Delete DB entries regarding tasks
        // guiaDeCampoViewModel.deleteAllAvistamentoPocasAvencas();
        // guiaDeCampoViewModel.deleteAllAvistamentoZonacaoAvencas();
    }
}