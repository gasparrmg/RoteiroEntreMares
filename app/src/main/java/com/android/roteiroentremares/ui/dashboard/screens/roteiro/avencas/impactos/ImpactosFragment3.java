package com.android.roteiroentremares.ui.dashboard.screens.roteiro.avencas.impactos;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.util.ClickableString;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImpactosFragment3 extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextView textViewContent2;

    private MaterialCardView cardViewCapturaExcessiva;
    private ImageView imageViewCapturaExcessivaIcon;
    private MaterialCardView cardViewPisoteio;
    private ImageView imageViewPisoteioIcon;
    private MaterialCardView cardViewPoluicao;
    private ImageView imageViewPoluicaoIcon;
    private MaterialCardView cardViewTempAgua;
    private ImageView imageViewTempAguaIcon;
    private MaterialCardView cardViewOcupacaoHumana;
    private ImageView imageViewOcupacaoHumanaIcon;

    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impactos3, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        initViews(view);
        setOnClickListeners(view);
        insertContent(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();

        checkIfLinksVisited();
    }

    private void checkIfLinksVisited() {
        boolean isCapturaExcessivaFinished = dashboardViewModel.isImpactosCapturaExcessivaFinished();
        boolean isPisoteioFinished = dashboardViewModel.isImpactosPisoteioFinished();
        boolean isPoluicaoFinished = dashboardViewModel.isImpactosPoluicaoFinished();
        boolean isTempAguaFinished = dashboardViewModel.isImpactosTempAguaFinished();
        boolean isOcupacaoHumanaFinished = dashboardViewModel.isImpactosOcupacaoHumanaFinished();

        if (isCapturaExcessivaFinished) {
            imageViewCapturaExcessivaIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
            imageViewCapturaExcessivaIcon.setImageResource(R.drawable.ic_check);
        }

        if (isPisoteioFinished) {
            imageViewPisoteioIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
            imageViewPisoteioIcon.setImageResource(R.drawable.ic_check);
        }

        if (isPoluicaoFinished) {
            imageViewPoluicaoIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
            imageViewPoluicaoIcon.setImageResource(R.drawable.ic_check);
        }

        if (isTempAguaFinished) {
            imageViewTempAguaIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
            imageViewTempAguaIcon.setImageResource(R.drawable.ic_check);
        }

        if (isOcupacaoHumanaFinished) {
            imageViewOcupacaoHumanaIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorCorrect), android.graphics.PorterDuff.Mode.SRC_IN);
            imageViewOcupacaoHumanaIcon.setImageResource(R.drawable.ic_check);
        }

        if (isCapturaExcessivaFinished &&
                isPisoteioFinished &&
                isPoluicaoFinished &&
                isTempAguaFinished &&
                isOcupacaoHumanaFinished) {
            buttonFabNext.setVisibility(View.VISIBLE);
            buttonFabNext.setEnabled(true);
        }

    }

    private void initToolbar() {
        SpannableString s = new SpannableString("Impacto da ação humana");
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
                    String text = HtmlCompat.fromHtml(
                            "As zonas costeiras, em particular a zona entre marés, são zonas de interface entre o ambiente terrestre e o ambiente marinho, constituindo-se como um ecossistema frágil e delicado." +
                                    "No entanto, o facto de serem zonas de fácil acesso, leva a que estes locais estejam sujeitos a diferentes e fortes pressões causadas pela ação humana. De entre as principais pressões, salienta-se a: " +
                                    "- Captura excessiva de invertebrados;" +
                                    "- Pisoteio;" +
                                    "- Poluição (plástico e microplástico);" +
                                    "- Aumento da temperatura da água (espécies exóticas)" +
                                    "- Ocupação humana das arribas.",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                return true;
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        textViewContent = view.findViewById(R.id.text_content);
        textViewContent2 = view.findViewById(R.id.text_content2);

        cardViewCapturaExcessiva = view.findViewById(R.id.cardview_capturaexcessiva);
        imageViewCapturaExcessivaIcon = view.findViewById(R.id.imageview_capturaexcessiva_ic);

        cardViewPisoteio = view.findViewById(R.id.cardview_pisoteio);
        imageViewPisoteioIcon = view.findViewById(R.id.imageview_pisoteio_ic);

        cardViewPoluicao = view.findViewById(R.id.cardview_poluicao);
        imageViewPoluicaoIcon = view.findViewById(R.id.imageview_poluicao_ic);

        cardViewTempAgua = view.findViewById(R.id.cardview_tempagua);
        imageViewTempAguaIcon = view.findViewById(R.id.imageview_tempagua_ic);

        cardViewOcupacaoHumana = view.findViewById(R.id.cardview_ocupacaohumana);
        imageViewOcupacaoHumanaIcon = view.findViewById(R.id.imageview_ocupacaohumana_ic);

        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
    }

    private void setOnClickListeners(View view) {
        cardViewCapturaExcessiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosCapturaExcessivaFragment);
            }
        });

        cardViewPisoteio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosPisoteioFragment);
            }
        });

        cardViewPoluicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosPoluicaoFragment);
            }
        });

        cardViewTempAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosTempAguaFragment);
            }
        });

        cardViewOcupacaoHumana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosOcupacaoHumanaFragment);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_impactosFragment3_to_impactosFragment4);
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

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent(View view) {
        textViewTitle.setText(HtmlCompat.fromHtml(
                "Impacto da ação humana",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent.setText(HtmlCompat.fromHtml(
                "As zonas costeiras, em particular a zona entre marés, são zonas de interface entre o ambiente terrestre e o ambiente marinho, constituindo-se como um ecossistema frágil e delicado.<br>" +
                        "<br>" +
                        "No entanto, o facto de serem zonas de fácil acesso, leva a que estes locais estejam sujeitos a diferentes e fortes pressões causadas pela ação humana.<br><br>De entre as principais pressões, salientam-se:",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        textViewContent2.setText("Poderás aceder aos próximos ecrãs assim que assim que ficares a saber mais sobre estes problemas.");
    }
}