package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.equandoamaresobe;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.android.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class EQuandoAMareSobeDetailsFragment extends Fragment {

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private ImageView imageView;
    private FloatingActionButton fabFullscreen;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private Button buttonParaSaberMais;

    private String title;
    private String content;
    private int imageResourceId;
    private boolean isFromGuiaCampo;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e_quando_a_mare_sobe_details, container, false);

        getSafeArgs();

        initViews(view);
        insertContent();
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void getSafeArgs() {
        if (getArguments() != null) {
            EQuandoAMareSobeDetailsFragmentArgs args = EQuandoAMareSobeDetailsFragmentArgs.fromBundle(getArguments());
            title = args.getTitle();
            content = args.getContent();
            imageResourceId = args.getImageResourceId();
            isFromGuiaCampo = args.getIsFromGuiaCampo();
        }
    }

    private void initToolbar() {
        SpannableString s = new SpannableString("E quando a maré sobe? ");
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_text_to_speech:
                if (tts.isSpeaking()) {
                    tts.stop();
                    item.setIcon(R.drawable.ic_volume);
                } else {
                    tts.speak(content, TextToSpeech.QUEUE_FLUSH, null, null);
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
        imageView = view.findViewById(R.id.imageView);
        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonParaSaberMais = view.findViewById(R.id.button_parasaberesmais);
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        imageView.setImageResource(imageResourceId);

        textViewTitle.setText(title);
        textViewContent.setText(content);
    }

    private void setOnClickListeners(View view) {
        buttonParaSaberMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("Sargos/Safias (Diplodus sargus)")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fishbase.se/summary/Diplodus-sargus.html")));
                } else if (title.equals("Bodião (Symphodus sp)")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fishbase.se/summary/Symphodus-melops.html")));
                } else if (title.equals("Peixe-Rei (Atherina presbyter)")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fishbase.se/summary/Atherina-presbyter.html")));
                } else if (title.equals("Marachomba (Lipophrys pholis)")) {
                    // create especieAvencas
                    EspecieAvencas especieAvencasMarachomba = new EspecieAvencas(
                            "Marachomba",
                            "Lipophrys pholis",
                            "Peixes ósseos (Chordata)",
                            "Peixes da família dos Blenídeos, de pequenas dimensões, residentes nas plataformas rochosas. Corpo alongado, achatado ventralmente, sem escamas.",
                            "Omnívoros (lapas, mexilhões, invertebrados e algas).",
                            "Ausência de escamas; corpo comprimido ventralmente; barbatanas com raios fortalecidos; olhos em posição elevada.",
                            "Estas espécies de peixes residentes na zona intertidal apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                                    "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                                    "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito imbricadas\n" +
                                    "- Ausência bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                                    "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                                    "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                                    "- Capacidade de alterar a côr (mimetismo)\n" +
                                    "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)\n",
                            true,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            2,
                            new ArrayList<String>(Arrays.asList(
                                    "img_guiadecampo_marachomba_1"
                            )),
                            new ArrayList<String>(Arrays.asList()),
                            ""
                    );

                    Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                    intent.putExtra("especie", especieAvencasMarachomba);
                    startActivity(intent);
                    // start guia de campo
                } else if (title.equals("Cracas (Chthamalus spp)")) {
                    // create especieAvencas

                    EspecieAvencas especieAvencasCracas = new EspecieAvencas(
                            "Cracas pequenas",
                            "Chthamalus spp",
                            "Crustáceos (Arthropoda)",
                            "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                                    "Presentes no mediolitoral.",
                            "Filtradores (alimentam-se de partículas em suspensão na água)",
                            "Fecham-se no interior das placas",
                            "",
                            false,
                            false,
                            false,
                            false,
                            false,
                            true,
                            false,
                            5,
                            new ArrayList<String>(Arrays.asList(
                                    "img_guiadecampo_cracaspequenas_1"
                            )),
                            new ArrayList<String>(Arrays.asList()),
                            ""
                    );

                    Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                    intent.putExtra("especie", especieAvencasCracas);
                    startActivity(intent);
                    // start guia de campo
                } else {
                    // create especieAvencas
                    EspecieAvencas especieAvencasLapas = new EspecieAvencas(
                            "Lapa",
                            "Patella spp",
                            "Moluscos (Mollusca)",
                            "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, com a forma de disco.\n" +
                                    "Presente no mediolitoral.",
                            "Herbívoro (raspa as algas)",
                            "Fixam-se firmemente às rochas, e escavam uma pequena cavidade, à qual se ajusta perfeitamente, impedindo a perda de água.",
                            "Apesar de parecerem imóveis as lapas são moluscos herbívoros que fazem deslocações sobre o substrato para rasparem pequenas algas das quais se alimentam. Para isso têm o auxílio de uma rádula na boca, que é uma espécie de fita com muitos dentes. Quando acabam estas excursões alimentares voltam exatamente ao mesmo local na rocha de onde saíram. Este comportamento é mais facilmente observável em marés-baixas noturnas, dias húmidos ou na maré cheia.",
                            true,
                            false,
                            false,
                            false,
                            false,
                            false,
                            true,
                            4,
                            new ArrayList<String>(Arrays.asList(
                                    "img_guiadecampo_lapa_1"
                            )),
                            new ArrayList<String>(Arrays.asList(
                                    "img_guiadecampo_sabiasque_lapa_1"
                            )),
                            ""
                    );

                    // start guia de campo
                    Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
                    intent.putExtra("especie", especieAvencasLapas);
                    startActivity(intent);
                }
            }
        });


        fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Image Activity
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "PT"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TEXT2SPEECH", "Language not supported");
                        Toast.makeText(getActivity(), "Não tens o linguagem Português disponível no teu dispositivo. Isto acontece normalmente acontece quando a linguagem padrão do dispositivo é outra que não o Português.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("TEXT2SPEECH", "Initialization failed");
                }
            }
        });
    }
}