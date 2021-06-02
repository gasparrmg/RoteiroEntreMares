package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.historiaspassado;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.GestureDetectGridView;
import com.lasige.roteiroentremares.util.PuzzleFactory;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HistoriasPassadoFragment10Puzzle extends Fragment implements PuzzleFactory.OnSolvedListener {

    // Views
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private GestureDetectGridView gestureDetectGridView;

    private PuzzleFactory puzzleFactory;

    private static final int[] puzzleImages = {
            R.drawable.img_historiaspassado_puzzle_1,
            R.drawable.img_historiaspassado_puzzle_2,
            R.drawable.img_historiaspassado_puzzle_3,
            R.drawable.img_historiaspassado_puzzle_4,
            R.drawable.img_historiaspassado_puzzle_5,
            R.drawable.img_historiaspassado_puzzle_6,
            R.drawable.img_historiaspassado_puzzle_7,
            R.drawable.img_historiaspassado_puzzle_8,
            R.drawable.img_historiaspassado_puzzle_9
    };

    private boolean isSolved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historias_passado10_puzzle, container, false);

        initViews(view);
        setOnClickListeners(view);

        isSolved = false;

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

        puzzleFactory = new PuzzleFactory(getActivity(), gestureDetectGridView, this, puzzleImages);
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
                if (!isSolved) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    materialAlertDialogBuilder.setTitle("Atenção!");
                    materialAlertDialogBuilder.setMessage(getResources().getString(R.string.warning_task_not_finished));
                    materialAlertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Navigation.findNavController(view).navigate(R.id.action_historiasPassadoFragment10Puzzle_to_historiasPassadoFragment11);
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
                    Navigation.findNavController(view).navigate(R.id.action_historiasPassadoFragment10Puzzle_to_historiasPassadoFragment11);
                }
            }
        });
    }

    @Override
    public void onSolvedCallback() {
        Toast.makeText(getActivity(), "Parabéns! Resolveste o Puzzle!", Toast.LENGTH_SHORT).show();

        isSolved = true;

        buttonFabNext.setVisibility(View.VISIBLE);
        buttonFabNext.setEnabled(true);
    }
}