package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.historiaspassado;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.util.GestureDetectGridView;
import com.android.roteiroentremares.util.PuzzleFactory;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HistoriasPassadoFragment10Puzzle extends Fragment implements PuzzleFactory.OnSolvedListener{

    // Views
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private GestureDetectGridView gestureDetectGridView;

    private PuzzleFactory puzzleFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historias_passado10_puzzle, container, false);

        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Histórias do Passado");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
    }

    private void initViews(View view) {
        gestureDetectGridView = view.findViewById(R.id.grid_view);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        puzzleFactory = new PuzzleFactory(getActivity(), gestureDetectGridView, this);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_historiasPassadoFragment10Puzzle_to_historiasPassadoFragment11);
            }
        });
    }

    @Override
    public void onSolvedCallback() {
        Toast.makeText(getActivity(), "Parabéns! Resolveste o Puzzle!", Toast.LENGTH_SHORT).show();

        buttonFabNext.setVisibility(View.VISIBLE);
        buttonFabNext.setEnabled(true);
    }
}