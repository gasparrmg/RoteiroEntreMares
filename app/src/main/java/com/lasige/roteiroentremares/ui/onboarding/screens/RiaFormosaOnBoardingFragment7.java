package com.lasige.roteiroentremares.ui.onboarding.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.lasige.roteiroentremares.ui.dashboard.UserDashboardActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.onboarding.viewmodel.OnBoardingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaOnBoardingFragment7 extends Fragment {

    private static final int SEQUENCE_NUMBER = 7;
    private static final String spotCoordinates = "-37.119266,-7.621086";

    private OnBoardingViewModel onBoardingViewModel;
    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ImageButton buttonDirections;
    private ViewPager2 viewPager;

    private ImageView imageView;
    private FloatingActionButton fabFullscreen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_on_boarding7, container, false);

        onBoardingViewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonDirections = view.findViewById(R.id.btn_directions);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager_riaformosa);

        imageView = view.findViewById(R.id.imageView);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);


        setOnClickListeners();
        insertContent();

        return view;
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User finished onBoarding sequence
                onBoardingViewModel.setOnBoarding(true);
                dashboardViewModel.setAvencasOrRiaFormosa(1);

                Intent intent = new Intent(getActivity(), UserDashboardActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER - 2);
            }
        });

        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, R.drawable.img_riaformosa_itenerario);
                startActivity(intent);
            }
        });

        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + spotCoordinates;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        imageView.setImageResource(R.drawable.img_riaformosa_itenerario);

        textViewTitle.setText(HtmlCompat.fromHtml(
                "Ria Formosa",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "Os percursos a realizar decorrem na <u>Praia do Arraial de Tavira</u> (Coordenadas: -37.119266, -7.621086)",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));
    }
}