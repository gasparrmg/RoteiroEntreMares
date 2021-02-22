package com.android.roteiroentremares.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.MeusArtefactosFragment;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.TurmaArtefactosFragment;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.AvistamentosFragment;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.EspeciesFragment;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GuiaDeCampoActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    // Views
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // Tab Item's Fragments
    private EspeciesFragment especiesFragment;
    private AvistamentosFragment avistamentosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_de_campo);

        initToolbar();

        viewPager = findViewById(R.id.viewPager_guiadecampo);
        tabLayout = findViewById(R.id.tabLayout_guiadecampo);

        especiesFragment = new EspeciesFragment();
        avistamentosFragment = new AvistamentosFragment();

        GuiaDeCampoActivity.TabViewPagerAdapter tabViewPagerAdapter = new GuiaDeCampoActivity.TabViewPagerAdapter(this);
        tabViewPagerAdapter.addFragment(especiesFragment, "Espécies");
        tabViewPagerAdapter.addFragment(avistamentosFragment, "Avistamentos");

        viewPager.setAdapter(tabViewPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Espécies");
                        break;
                    }
                    case 1: {
                        tab.setText("Avistamentos");
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        viewPager.setUserInputEnabled(false);
    }

    /**
     * Inits Toolbar and sets Title
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString(getResources().getString(R.string.title_guiaDeCampo));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class TabViewPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitlesList = new ArrayList<>();

        public TabViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitlesList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}