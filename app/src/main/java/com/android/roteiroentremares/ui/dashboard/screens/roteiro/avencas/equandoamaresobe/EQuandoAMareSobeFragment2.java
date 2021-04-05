package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.equandoamaresobe;

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
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

public class EQuandoAMareSobeFragment2 extends Fragment {

    // Views
    private HorizontalScrollView horizontalScrollView1;
    private HorizontalScrollView horizontalScrollView2;

    private MaterialCardView cardViewSargo;
    private ImageView imageViewSargo;

    private MaterialCardView cardViewPeixeRei;
    private ImageView imageViewPeixeRei;

    private MaterialCardView cardViewBodiao;
    private ImageView imageViewBodiao;

    private MaterialCardView cardViewCracas;
    private ImageView imageViewCracas;

    private MaterialCardView cardViewMarachomba;
    private ImageView imageViewMarachomba;

    private MaterialCardView cardViewLapas;
    private ImageView imageViewLapas;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e_quando_a_mare_sobe2, container, false);

        initViews(view);
        insertContent();
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
        horizontalScrollView1 = view.findViewById(R.id.horizontalscrollview1);
        horizontalScrollView2 = view.findViewById(R.id.horizontalscrollview2);

        cardViewSargo = view.findViewById(R.id.cardview_sargo);
        imageViewSargo = view.findViewById(R.id.imageview_sargo);

        cardViewPeixeRei = view.findViewById(R.id.cardview_peixerei);
        imageViewPeixeRei = view.findViewById(R.id.imageview_peixerei);

        cardViewBodiao = view.findViewById(R.id.cardview_bodiao);
        imageViewBodiao = view.findViewById(R.id.imageview_bodiao);

        cardViewCracas = view.findViewById(R.id.cardview_cracas);
        imageViewCracas = view.findViewById(R.id.imageview_cracas);

        cardViewMarachomba = view.findViewById(R.id.cardview_marachomba);
        imageViewMarachomba = view.findViewById(R.id.imageview_marachomba);

        cardViewLapas = view.findViewById(R.id.cardview_lapas);
        imageViewLapas = view.findViewById(R.id.imageview_lapas);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        ViewTreeObserver vto1 = horizontalScrollView1.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int maxScroll = horizontalScrollView1.getChildAt(0).getWidth() - horizontalScrollView1.getWidth();
                horizontalScrollView1.scrollTo(maxScroll/2, 0);
            }
        });

        ViewTreeObserver vto2 = horizontalScrollView2.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int maxScroll = horizontalScrollView2.getChildAt(0).getWidth() - horizontalScrollView2.getWidth();
                horizontalScrollView2.scrollTo(maxScroll/2, 0);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        Glide.with(getActivity())
                .load(R.drawable.img_equandoamaresobe_sargo)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewSargo);

        Glide.with(getActivity())
                .load(R.drawable.img_equandoamaresobe_peixerei)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewPeixeRei);

        Glide.with(getActivity())
                .load(R.drawable.img_equandoamaresobe_bodiao)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewBodiao);

        Glide.with(getActivity())
                .load(R.drawable.img_guiadecampo_cracaspequenas_1)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewCracas);

        Glide.with(getActivity())
                .load(R.drawable.img_guiadecampo_marachomba_1)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewMarachomba);

        Glide.with(getActivity())
                .load(R.drawable.img_guiadecampo_lapa_1)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewLapas);
    }

    private void setOnClickListeners(View view) {
        cardViewSargo.setOnClickListener(new View.OnClickListener() {
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
                Navigation.findNavController(view).navigate(R.id.action_EQuandoAMareSobeFragment2_to_EQuandoAMareSobeFragmentExercicio);
            }
        });
    }
}