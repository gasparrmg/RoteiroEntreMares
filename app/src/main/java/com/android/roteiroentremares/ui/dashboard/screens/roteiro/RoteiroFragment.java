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
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoteiroFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private MaterialCardView cardViewHistoriasPassado;
    private ImageView imageViewHistoriasPassado;
    private TextView textViewHistoriasPassadoIsFinished;

    // private MaterialCardView cardViewBiodiversidade;

    private boolean isHistoriasPassadoFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roteiro, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        initViews(view);

        Log.d("NAVIGATION_TEST", "RoteiroFragment created");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("NAVIGATION_TEST", "RoteiroFragment resumed");
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Escolhe um percurso...");
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
    }

    private void initViews(View view) {
        cardViewHistoriasPassado = view.findViewById(R.id.cardview_historiaspassado);
        imageViewHistoriasPassado = view.findViewById(R.id.imageview_historiaspassado);
        textViewHistoriasPassadoIsFinished = view.findViewById(R.id.textView_historiaspassado_is_finished);

        // cardViewBiodiversidade = view.findViewById(R.id.cardview_biodiversidade);

        isHistoriasPassadoFinished = dashboardViewModel.isHistoriasPassadoFinished();

        if (isHistoriasPassadoFinished) {
            textViewHistoriasPassadoIsFinished.setVisibility(View.VISIBLE);
        }

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

        Glide.with(getActivity())
                .load(R.drawable.img_historiaspassado_falha)
                .placeholder(android.R.drawable.ic_media_play)
                .into(imageViewHistoriasPassado);
    }
}