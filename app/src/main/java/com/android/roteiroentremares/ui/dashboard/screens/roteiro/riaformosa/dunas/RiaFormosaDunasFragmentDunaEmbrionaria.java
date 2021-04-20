package com.android.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieRiaFormosaHorizontalWithTinyDescAdapter;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaDunasFragmentDunaEmbrionaria extends Fragment {

    private static final String htmlContent = "<b>Dirige-te para a zona mais próxima do mar</b><br><br>" +
            "As espécies vegetais mais características nas dunas embrionárias são:";

    private static final String QUERY = "SELECT * FROM especie_table_riaformosa " +
            "WHERE nomeCientifico = 'Elymus farctus' OR " +
            "nomeCientifico = 'Cakile maritima' OR " +
            "nomeCientifico = 'Salsola kali' OR " +
            "nomeCientifico = 'Euphorbia paralias'";

    private GuiaDeCampoViewModel guiaDeCampoViewModel;
    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextView textViewContent2;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonSabiasQue;

    private RecyclerView recyclerView;
    private EspecieRiaFormosaHorizontalWithTinyDescAdapter adapter;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_sapal_especies, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ttsEnabled = false;

        initViews(view);

        adapter.setOnItemClickListener(new EspecieRiaFormosaHorizontalWithTinyDescAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EspecieRiaFormosa especieRiaFormosa) {
                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("avencasOrRiaFormosa", dashboardViewModel.getAvencasOrRiaFormosa());
                intent.putExtra("especie", especieRiaFormosa);
                startActivity(intent);
            }
        });

        setOnClickListeners(view);
        insertContent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.riaformosa_dunas_title));
        s.setSpan(new TypefaceSpan(getActivity(), "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        if (tts.isSpeaking()) {
            tts.stop();
        }
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.roteiro_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (ttsEnabled) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    } else {
                        String text = HtmlCompat.fromHtml(
                                htmlContent,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tts_error_message), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.item_back_to_main_menu:
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment ,false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        textViewContent2 = view.findViewById(R.id.text_content2);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonSabiasQue = view.findViewById(R.id.button_sabiasque);

        recyclerView = view.findViewById(R.id.recyclerView_especies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new EspecieRiaFormosaHorizontalWithTinyDescAdapter(getContext(), 0);
        recyclerView.setAdapter(adapter);

        guiaDeCampoViewModel.filterEspeciesRiaFormosa(QUERY);

        guiaDeCampoViewModel.getAllEspecieRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<EspecieRiaFormosa>>() {
            @Override
            public void onChanged(List<EspecieRiaFormosa> especieRiaFormosas) {
                adapter.setEspecies(especieRiaFormosas);
            }
        });
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
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragmentDunaEmbrionaria_to_riaFormosaDunasFragmentDunaEmbrionaria2);
            }
        });

        buttonSabiasQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaDunasFragmentDunaEmbrionaria_to_riaFormosaDunasFragmentDunaEmbrionariaSabiasQue);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Duna embrionária",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString link = ClickableString.makeLinkSpan("link", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open website
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://flora-on.pt/index.php"));
                startActivity(browserIntent);
            }
        });

        textViewContent2.setText("Para saberes mais sobre estas espécies, visita este ");
        textViewContent2.append(link);

        ClickableString.makeLinksFocusable(textViewContent2);

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ttsEnabled = false;
                        Log.e("TEXT2SPEECH", "Language not supported");
                    } else {
                        ttsEnabled = true;
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }
}