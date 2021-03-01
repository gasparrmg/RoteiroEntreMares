package com.android.roteiroentremares.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.MeusArtefactosFragment;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.TurmaArtefactosFragment;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.android.roteiroentremares.ui.onboarding.adapters.ViewPagerAdapter;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArtefactosActivity extends AppCompatActivity {

    private ArtefactosViewModel artefactosViewModel;

    private MaterialToolbar toolbar;

    // Views
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // Tab Item's Fragments
    private MeusArtefactosFragment meusArtefactosFragment;
    private TurmaArtefactosFragment turmaArtefactosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artefactos);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        initToolbar();

        viewPager = findViewById(R.id.viewPager_artefactos);
        tabLayout = findViewById(R.id.tabLayout_artefactos);

        meusArtefactosFragment = new MeusArtefactosFragment();
        turmaArtefactosFragment = new TurmaArtefactosFragment();

        TabViewPagerAdapter tabViewPagerAdapter = new TabViewPagerAdapter(this);
        tabViewPagerAdapter.addFragment(meusArtefactosFragment, "Meus Artefactos");
        tabViewPagerAdapter.addFragment(turmaArtefactosFragment, "Artefactos Turma");

        viewPager.setAdapter(tabViewPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Meus");
                        break;
                    }
                    case 1: {
                        tab.setText("Turma");
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

        SpannableString s = new SpannableString(getResources().getString(R.string.title_artefactos
        ));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artefacto_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete_all_artefactos:
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setTitle("Apagar todos os artefactos?");
                materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar todos os teus artefactos?");
                materialAlertDialogBuilder.setPositiveButton("Sim, apagar todos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        artefactosViewModel.deleteAllArtefacto();
                    }
                });
                materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                AlertDialog alertDialog = materialAlertDialogBuilder.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));
        }

        return super.onOptionsItemSelected(item);
    }
}