package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.equandoamaresobe;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.ClickableAreasImageRefactored;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;

public class EQuandoAMareSobeFragment2Refactored extends Fragment implements OnClickableAreaClickedListener {

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ImageView imageView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_e_quando_a_mare_sobe2_refactored, container, false);

        initViews(view);
        // setupClickableAreas();
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("E quando a maré sobe?");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
    }

    private void initViews(View view) {
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        imageView = view.findViewById(R.id.imageView);

        //imageView.setImageResource(R.drawable.img_equandoamaresobe_bg);
        setupClickableAreas();
    }

    private void setupClickableAreas() {
        ClickableAreasImageRefactored clickableAreasImage = new ClickableAreasImageRefactored(imageView, this);
        // ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(imageView), this);
        List<ClickableArea> clickableAreas = new ArrayList<>();

        clickableAreas.add(new ClickableArea(150, 320, 170, 100, "peixe-rei"));
        clickableAreas.add(new ClickableArea(20, 490, 280, 140, "sargo"));
        clickableAreas.add(new ClickableArea(350, 400, 200, 110, "bodião"));
        clickableAreas.add(new ClickableArea(0, 765, 370, 255, "cracas"));
        clickableAreas.add(new ClickableArea(400, 970, 221, 134, "lapas"));
        clickableAreas.add(new ClickableArea(380, 770, 170, 90, "marachomba"));

        clickableAreasImage.setClickableAreas(clickableAreas);
    }

    @Override
    public void onClickableAreaTouched(Object o) {
        Log.d("clickable_area", "CLICKED!");
        if (o instanceof String) {
            if (((String) o).equals("peixe-rei")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Peixe-Rei (Atherina presbyter)",
                        "Espécie de peixe explorada comercialmente. Ocorre em cardumes. Vive em água salina ou salobra. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentar. Alimenta-se de pequenos crustáceos, moluscos, larvas de insetos (visitante de maré)",
                        R.drawable.img_equandoamaresobe_peixerei
                );

                Navigation.findNavController(view).navigate(action);
            } else if (((String) o).equals("sargo")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Sargos/Safias (Diplodus sargus)",
                        "Espécie de peixe explorada comercialmente. Ocorre em pequenos cardumes ou solitário. Vive em fundos rochosos ou arenosos, até 160m de profundidade. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentar . Alimenta-se de crustáceos, vermes e moluscos (espécie “visitante de maré”)",
                        R.drawable.img_equandoamaresobe_sargo
                );

                Navigation.findNavController(view).navigate(action);
            } else if (((String) o).equals("bodião")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Bodião (Symphodus sp)",
                        "Espécie de peixe explorada comercialmente. Vive em costas rochosas, cobertas de algas, até profundidades de 30-50m. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentarem. Alimenta-se de moluscos, vermes e crustáceos (espécie “visitante de maré”)",
                        R.drawable.img_equandoamaresobe_bodiao
                );

                Navigation.findNavController(view).navigate(action);
            } else if (((String) o).equals("cracas")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Cracas (Chthamalus spp)",
                        "Estes crustáceos marinhos são filtradores. Enquanto na maré-baixa, eles recolhem os cirros, fechando-se dentro da concha, durante o período da maré-cheia, colocam os cirros no exterior para se alimentarem dos partículas em suspensão na água.",
                        R.drawable.img_guiadecampo_cracaspequenas_1
                );

                Navigation.findNavController(view).navigate(action);
            } else if (((String) o).equals("lapas")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Lapas (Patella spp.)",
                        "Estes gastrópodes marinhos são herbívoros. Enquanto na maré-baixa, estes organismos permanecem imóveis, fixando-se ao substrato rochoso, através do seu pé musculoso, durante o período da maré-cheia, deslocam-se por cima do substrato, alimentando-se das algas, através de uma estrutura denominada rádula (estrutura com dentes).",
                        R.drawable.img_guiadecampo_lapa_1
                );

                Navigation.findNavController(view).navigate(action);
            } else if (((String) o).equals("marachomba")) {
                EQuandoAMareSobeFragment2RefactoredDirections.ActionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2RefactoredDirections.actionEQuandoAMareSobeFragment2RefactoredToEQuandoAMareSobeDetailsFragment(
                        "Marachomba (Lipophrys pholis)",
                        "Espécie de peixe residente nas plataformas rochosas da zona entre-marés. Enquanto que na maré-baixa este animal se refugia nas poças de maré ou em fendas, no período de maré-cheia, desloca-se nadando, por toda a plataforma para se alimentar.  Alimenta-se de pequenos moluscos, cracas, algas, anfípodes (omnívoro).",
                        R.drawable.img_guiadecampo_marachomba_1
                );

                Navigation.findNavController(view).navigate(action);
            }
        }
    }

    private void setOnClickListeners(View view) {
        /*cardViewSargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Sargos/Safias (Diplodus sargus)",
                        "Espécie de peixe explorada comercialmente. Ocorre em pequenos cardumes ou solitário. Vive em fundos rochosos ou arenosos, até 160m de profundidade. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentar . Alimenta-se de crustáceos, vermes e moluscos (espécie “visitante de maré”)",
                        R.drawable.img_equandoamaresobe_sargo
                );
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewBodiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Bodião (Symphodus sp)",
                        "Espécie de peixe explorada comercialmente. Vive em costas rochosas, cobertas de algas, até profundidades de 30-50m. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentarem. Alimenta-se de moluscos, vermes e crustáceos (espécie “visitante de maré”)",
                        R.drawable.img_equandoamaresobe_bodiao
                );
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewPeixeRei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Peixe-Rei (Atherina presbyter)",
                        "Espécie de peixe explorada comercialmente. Ocorre em cardumes. Vive em água salina ou salobra. Durante a maré-cheia, estes animais utilizam a plataforma rochosa para se alimentar. Alimenta-se de pequenos crustáceos, moluscos, larvas de insetos (visitante de maré)",
                        R.drawable.img_equandoamaresobe_peixerei
                );
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewMarachomba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Marachomba (Lipophrys pholis)",
                        "Espécie de peixe residente nas plataformas rochosas da zona entre-marés. Enquanto que na maré-baixa este animal se refugia nas poças de maré ou em fendas, no período de maré-cheia, desloca-se nadando, por toda a plataforma para se alimentar.  Alimenta-se de pequenos moluscos, cracas, algas, anfípodes (omnívoro).",
                        R.drawable.img_guiadecampo_marachomba_1
                );
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewCracas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Cracas (Chthamalus spp)",
                        "Estes crustáceos marinhos são filtradores. Enquanto na maré-baixa, eles recolhem os cirros, fechando-se dentro da concha, durante o período da maré-cheia, colocam os cirros no exterior para se alimentarem dos partículas em suspensão na água.",
                        R.drawable.img_guiadecampo_cracaspequenas_1
                );
                Navigation.findNavController(view).navigate(action);
            }
        });

        cardViewLapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EQuandoAMareSobeFragment2Directions.ActionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment action = EQuandoAMareSobeFragment2Directions.actionEQuandoAMareSobeFragment2ToEQuandoAMareSobeDetailsFragment(
                        "Lapas (Patella spp.)",
                        "Estes gastrópodes marinhos são herbívoros. Enquanto na maré-baixa, estes organismos permanecem imóveis, fixando-se ao substrato rochoso, através do seu pé musculoso, durante o período da maré-cheia, deslocam-se por cima do substrato, alimentando-se das algas, através de uma estrutura denominada rádula (estrutura com dentes).",
                        R.drawable.img_guiadecampo_lapa_1
                );
                Navigation.findNavController(view).navigate(action);
            }
        });*/

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_EQuandoAMareSobeFragment2Refactored_to_EQuandoAMareSobeFragmentExercicio);
            }
        });
    }
}