package com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.widget.ImageView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.SliderAdapter;
import com.android.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.android.roteiroentremares.ui.onboarding.screens.AvencasOnBoardingFragment1;
import com.android.roteiroentremares.ui.onboarding.screens.AvencasOnBoardingFragment2;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class EspecieDetailsActivity extends AppCompatActivity {
    private EspecieAvencas especie;

    private MaterialToolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especie_details);

        especie = (EspecieAvencas) getIntent().getSerializableExtra("especie");

        initToolbar();
        initImageSlider(especie.getPictures());
        initViewPager();
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_especie);

        SpannableString s = new SpannableString(especie.getNomeComum());
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColorExpanded);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setTitle(s);

        setSupportActionBar(toolbar);
    }

    public void initImageSlider(ArrayList<String> picturesText) {
        sliderView = findViewById(R.id.imageSlider);

        //ArrayList<String> picturesText = especie.getPictures(); // String ArrayList
        int[] images = new int[picturesText.size()];

        for (int i = 0; i < images.length; i++) {
            images[i] = getResources().getIdentifier(picturesText.get(i), "drawable", getPackageName());
        }

        sliderAdapter = new SliderAdapter(this, images);
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager_especieDetails);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

        if (especie.getSabiasQue().isEmpty()) {
            fragmentList.add(new EspecieDetailsFragment(especie));
        } else {
            fragmentList.add(new EspecieDetailsFragment(especie));
            fragmentList.add(new EspecieSabiasQueFragment(especie));
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragmentList,
                getSupportFragmentManager(),
                getLifecycle()
        );

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }
}