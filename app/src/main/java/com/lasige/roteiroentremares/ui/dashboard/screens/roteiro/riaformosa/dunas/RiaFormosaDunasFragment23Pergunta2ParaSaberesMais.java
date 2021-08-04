package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.dunas;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.util.ClickableString;
import com.lasige.roteiroentremares.util.TypefaceSpan;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class RiaFormosaDunasFragment23Pergunta2ParaSaberesMais extends Fragment {

    private static final String htmlContent = "Exemplo de ameaças ao sistema dunar:<br>" +
            "<br>" +
            "- Construção de imóveis e de outras infraestruturas (alteram o modo de funcionamento deste sistema ou reduzem ou destroem este habitat)<br>" +
            "- Pisoteio<br>" +
            "- Captações de água<br>" +
            "- Introdução de espécies exóticas (ex. o chorão-da-praia, Carpobrotus edulis)<br>" +
            "- Poluição (lixo marinho)";

    private static final int imageResourceId = R.drawable.img_riaformosa_dunas_23_parasaberesmais_ilustracao;

    // Views
    private TextView textViewTitle;
    private TextView textViewContent;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;

    private ImageView imageView;
    private FloatingActionButton fabFullscreen;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_dunas23_parasaberesmais, container, false);

        Glide.with(getActivity())
                .load(imageResourceId)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        ttsEnabled = false;

        initViews(view);
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
        super.onCreateOptionsMenu(menu, inflater);
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
                Navigation.findNavController(getView()).popBackStack(R.id.roteiroFragment, false);
        }
        return false;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);

        textViewContent = view.findViewById(R.id.text_content);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonFabNext.setVisibility(View.INVISIBLE);
        buttonFabNext.setEnabled(false);

        buttonPrev = view.findViewById(R.id.btn_prev);

        fabFullscreen = view.findViewById(R.id.fab_fullscreen);
        imageView = view.findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        fabFullscreen.setVisibility(View.GONE);

    }


    private void setOnClickListeners(View view) {
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        /*fabFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageFullscreenActivity.class);
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_INFO, "Chorão-das-praias");
                intent.putExtra(ImageFullscreenActivity.INTENT_EXTRA_KEY, imageResourceId);
                startActivity(intent);
            }
        });*/
    }

    /**
     * Inserts all the content text into the proper Views
     */
    private void insertContent() {
        // imageView.setImageResource(imageResourceId);

        textViewTitle.setText("Ameaças ao sistema dunar");

        textViewContent.setText(HtmlCompat.fromHtml(
                "Exemplo de ameaças ao sistema dunar:<br>" +
                        "<br>" +
                        "- Construção de imóveis e de outras infraestruturas (alteram o modo de funcionamento deste sistema ou reduzem ou destroem este habitat)<br>" +
                        "- Pisoteio<br>" +
                        "- Captações de água<br>" +
                        "- Introdução de ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        ));

        SpannableString link = ClickableString.makeLinkSpan("espécies exóticas", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "Os locais aquáticos de todo o mundo têm sido os mais afectados com a invasão de espécies não nativas, ou comumente conhecidas como exóticas. Estas espécies na sua maioria não foram libertadas com o intuito de prejudicar mas sim para uso ornamental ou para serem fixadoras de dunas como por exemplo o tão conhecido chorão. No entanto, como estas espécies não servem de alimento às espécies nativas, a sua adaptação ao novo ambiente pode por em causa a abundância e diversidade das espécies nativas.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });

        link.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)), 0, link.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        textViewContent.append(link);
        textViewContent.append(" (ex. o chorão-da-praia, Carpobrotus edulis)\n" +
                "- Poluição (lixo marinho)");

        ClickableString.makeLinksFocusable(textViewContent);

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