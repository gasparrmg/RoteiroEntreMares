package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.avencas.biodiversidade;

import android.content.Intent;
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
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo.EspecieHorizontalAdapter;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BiodiversidadeZonacaoInfralitoralFragment2 extends Fragment {

    private static final String htmlContent = "Espécies mais comuns:";
    private static final String QUERY = "SELECT * FROM especie_table_avencas " +
            "WHERE nomeCientifico = 'Melaraphe neritoides' OR " +
            "nomeCientifico = 'Hymeniacidon sanguinea' OR " +
            "nomeCientifico = 'Anemonia sulcata' OR " +
            "nomeCientifico = 'Marthasterias glacialis' OR " +
            "nomeCientifico = 'Paracentrotus lividus' OR " +
            "nomeCientifico = 'Mytilus galloprovincialis' OR " +
            "nomeCientifico = 'Pollicipes pollicipes' OR " +
            "nomeCientifico = 'Octopus vulgaris' OR " +
            "nomeCientifico = 'Necora puber' OR " +
            "nomeComum LIKE 'Alga%'";

    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewTitle2;
    private TextView textViewContent;

    private RecyclerView recyclerView;
    private EspecieHorizontalAdapter adapter;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biodiversidade_zonacao_infralitoral2, container, false);

        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        ttsEnabled = false;

        initViews(view);

        adapter.setOnItemClickListener(new EspecieHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onAvencasItemClick(EspecieAvencas especieAvencas) {
                Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                intent.putExtra("especie", especieAvencas);
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
        SpannableString s = new SpannableString(getResources().getString(R.string.avencas_biodiversidade_title));
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
        textViewTitle2 = view.findViewById(R.id.text_title2);
        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);

        recyclerView = view.findViewById(R.id.recyclerView_especies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new EspecieHorizontalAdapter(getContext(), 0);
        recyclerView.setAdapter(adapter);

        guiaDeCampoViewModel.filterEspecies(QUERY);

        guiaDeCampoViewModel.getAllEspecies().observe(getViewLifecycleOwner(), new Observer<List<EspecieAvencas>>() {
            @Override
            public void onChanged(List<EspecieAvencas> especieAvencas) {
                //set recycler view
                adapter.setEspeciesAvencas(especieAvencas);
            }
        });

        //guiaDeCampoViewModel.filterEspecies(QUERY);
    }

    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(view).navigate(R.id.action_global_roteiroFragment);
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_biodiversidadeZonacaoInfralitoralFragment2_to_biodiversidadeZonacaoInfralitoralFragment3);
            }
        });
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Zonação",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewTitle2.setText(HtmlCompat.fromHtml(
                "Franja do Infralitoral",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                htmlContent,
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

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