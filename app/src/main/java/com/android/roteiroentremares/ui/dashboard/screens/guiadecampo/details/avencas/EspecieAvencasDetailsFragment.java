package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.avencas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;

public class EspecieAvencasDetailsFragment extends Fragment {

    private ViewPager2 viewPager;
    private EspecieAvencas especieAvencas;

    // Views
    private LinearLayout linearLayoutNomeComum;
    private TextView textViewNomeComumContent;
    private LinearLayout linearLayoutNomeCientifico;
    private TextView textViewNomeCientificoContent;
    private LinearLayout linearLayoutGrupo;
    private TextView textViewGrupoContent;
    private LinearLayout linearLayoutCaracteristicas;
    private TextView textViewCaracteristicasContent;
    private LinearLayout linearLayoutAlimentacao;
    private TextView textViewAlimentacaoContent;
    private LinearLayout linearLayoutAdaptacoes;
    private TextView textViewAdaptacoesContent;
    private Button buttonSabiasQue;

    public EspecieAvencasDetailsFragment(EspecieAvencas especieAvencas) {
        this.especieAvencas = especieAvencas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_especie_avencas_details, container, false);

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

        linearLayoutCaracteristicas = view.findViewById(R.id.linearlayout_caracteristicas);
        textViewCaracteristicasContent = view.findViewById(R.id.textView_caracteristicas_content);

        linearLayoutAlimentacao = view.findViewById(R.id.linearlayout_alimentacao);
        textViewAlimentacaoContent = view.findViewById(R.id.textView_alimentacao_content);

        linearLayoutAdaptacoes = view.findViewById(R.id.linearlayout_adaptacoes);
        textViewAdaptacoesContent = view.findViewById(R.id.textView_adaptacoes_content);

        buttonSabiasQue = view.findViewById(R.id.button_sabiasque);

        if (!especieAvencas.getNomeComum().isEmpty()) {
            textViewNomeComumContent.setText(especieAvencas.getNomeComum());
        } else {
            linearLayoutNomeComum.setVisibility(View.GONE);
        }

        if (!especieAvencas.getNomeCientifico().isEmpty()) {
            textViewNomeCientificoContent.setText(especieAvencas.getNomeCientifico());
        } else {
            linearLayoutNomeCientifico.setVisibility(View.GONE);
        }

        if (!especieAvencas.getGrupo().isEmpty()) {
            textViewGrupoContent.setText(especieAvencas.getGrupo());
        } else {
            linearLayoutGrupo.setVisibility(View.GONE);
        }

        if (!especieAvencas.getCaracteristicas().isEmpty()) {
            textViewCaracteristicasContent.setText(especieAvencas.getCaracteristicas());
        } else {
            linearLayoutCaracteristicas.setVisibility(View.GONE);
        }

        if (!especieAvencas.getAlimentacao().isEmpty()) {
            textViewAlimentacaoContent.setText(especieAvencas.getAlimentacao());
        } else {
            linearLayoutAlimentacao.setVisibility(View.GONE);
        }

        if (!especieAvencas.getAdaptacoes().isEmpty()) {
            textViewAdaptacoesContent.setText(especieAvencas.getAdaptacoes());
        } else {
            linearLayoutAdaptacoes.setVisibility(View.GONE);
        }

        if (!especieAvencas.getSabiasQue().isEmpty()) {
            buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (especieAvencas.getPicturesSabiasQue().size() > 0) {
                        ((EspecieDetailsActivity) getActivity()).initImageSlider(especieAvencas.getPicturesSabiasQue());
                    }
                    viewPager.setCurrentItem(1);
                }
            });
        } else {
            buttonSabiasQue.setVisibility(View.GONE);
        }
    }
}