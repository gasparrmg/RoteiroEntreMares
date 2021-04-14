package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.riaformosa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;

public class EspecieRiaFormosaDetailsFragment extends Fragment {

    private ViewPager2 viewPager;
    private EspecieRiaFormosa especieRiaFormosa;

    // Views
    private LinearLayout linearLayoutNomeComum;
    private TextView textViewNomeComumContent;
    private LinearLayout linearLayoutNomeCientifico;
    private TextView textViewNomeCientificoContent;
    private LinearLayout linearLayoutGrupo;
    private TextView textViewGrupoContent;
    private LinearLayout linearLayoutZona;
    private TextView textViewZonaContent;
    private LinearLayout linearLayoutTipo;
    private TextView textViewTipoContent;
    private LinearLayout linearLayoutCaracteristicas;
    private TextView textViewCaracteristicasContent;
    private LinearLayout linearLayoutAlimentacao;
    private TextView textViewAlimentacaoContent;
    private LinearLayout linearLayoutAdaptacoes;
    private TextView textViewAdaptacoesContent;
    private Button buttonSabiasQue;

    public EspecieRiaFormosaDetailsFragment(EspecieRiaFormosa especieRiaFormosa) {
        this.especieRiaFormosa = especieRiaFormosa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especie_riaformosa_details, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager_especieDetails);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        linearLayoutNomeComum = view.findViewById(R.id.linearlayout_nomecomum);
        textViewNomeComumContent = view.findViewById(R.id.textView_nomecomum_content);

        linearLayoutNomeCientifico = view.findViewById(R.id.linearlayout_nomecientifico);
        textViewNomeCientificoContent = view.findViewById(R.id.textView_nomecientifico_content);

        linearLayoutGrupo = view.findViewById(R.id.linearlayout_grupo);
        textViewGrupoContent = view.findViewById(R.id.textView_grupo_content);

        linearLayoutZona = view.findViewById(R.id.linearlayout_zona);
        textViewZonaContent = view.findViewById(R.id.textView_zona_content);

        linearLayoutTipo = view.findViewById(R.id.linearlayout_tipo);
        textViewTipoContent = view.findViewById(R.id.textView_tipo_content);

        linearLayoutCaracteristicas = view.findViewById(R.id.linearlayout_caracteristicas);
        textViewCaracteristicasContent = view.findViewById(R.id.textView_caracteristicas_content);

        linearLayoutAlimentacao = view.findViewById(R.id.linearlayout_alimentacao);
        textViewAlimentacaoContent = view.findViewById(R.id.textView_alimentacao_content);

        linearLayoutAdaptacoes = view.findViewById(R.id.linearlayout_adaptacoes);
        textViewAdaptacoesContent = view.findViewById(R.id.textView_adaptacoes_content);

        buttonSabiasQue = view.findViewById(R.id.button_sabiasque);

        if (!especieRiaFormosa.getNomeComum().isEmpty()) {
            textViewNomeComumContent.setText(especieRiaFormosa.getNomeComum());
        } else {
            linearLayoutNomeComum.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getNomeCientifico().isEmpty()) {
            textViewNomeCientificoContent.setText(especieRiaFormosa.getNomeCientifico());
        } else {
            linearLayoutNomeCientifico.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getGrupo().isEmpty()) {
            textViewGrupoContent.setText(especieRiaFormosa.getGrupo());
        } else {
            linearLayoutGrupo.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getZona().isEmpty()) {
            textViewZonaContent.setText(especieRiaFormosa.getZona());
        } else {
            linearLayoutZona.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getTipo().isEmpty()) {
            textViewTipoContent.setText(especieRiaFormosa.getTipo());
        } else {
            linearLayoutTipo.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getCaracteristicas().isEmpty()) {
            textViewCaracteristicasContent.setText(especieRiaFormosa.getCaracteristicas());
        } else {
            linearLayoutCaracteristicas.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getAlimentacao().isEmpty()) {
            textViewAlimentacaoContent.setText(especieRiaFormosa.getAlimentacao());
        } else {
            linearLayoutAlimentacao.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getAdaptacoes().isEmpty()) {
            textViewAdaptacoesContent.setText(especieRiaFormosa.getAdaptacoes());
        } else {
            linearLayoutAdaptacoes.setVisibility(View.GONE);
        }

        if (!especieRiaFormosa.getSabiasQue().isEmpty()) {
            buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (especieRiaFormosa.getPicturesSabiasQue().size() > 0) {
                        ((EspecieDetailsActivity) getActivity()).initImageSlider(especieRiaFormosa.getPicturesSabiasQue());
                    }
                    viewPager.setCurrentItem(1);
                }
            });
        } else if (!especieRiaFormosa.getCuriosidades().isEmpty()) {

            buttonSabiasQue.setText("Curiosidades");

            buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (especieRiaFormosa.getPicturesCuriosidades().size() > 0) {
                        ((EspecieDetailsActivity) getActivity()).initImageSlider(especieRiaFormosa.getPicturesCuriosidades());
                    }
                    viewPager.setCurrentItem(1);
                }
            });

        } else {
            buttonSabiasQue.setVisibility(View.GONE);
        }
    }
}