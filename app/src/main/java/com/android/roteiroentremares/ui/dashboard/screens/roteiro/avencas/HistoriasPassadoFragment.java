package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.roteiroentremares.R;

public class HistoriasPassadoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historias_passado, container, false);

        view.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_historiasPassadoFragment_to_roteiroFragment);
                // Navigation.findNavController(view).popBackStack(R.id.roteiroFragment, false);
            }
        });

        return view;
    }
}