package com.android.roteiroentremares.ui.onboarding.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.ui.onboarding.OnBoardingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OnBoardingFragment2 extends Fragment implements Validator.ValidationListener {
    private static final int SEQUENCE_NUMBER = 2;

    // ViewModel
    @Inject
    OnBoardingViewModel onBoardingViewModel;

    // Form Validator
    private Validator formValidator;

    // Form
    @Checked(messageResId = R.string.error_required_field)
    private RadioGroup radioGroupTipoUtilizador;

    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextNome;

    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextEscola;
    private TextInputLayout textInputLayoutEscola;

    @Min(value = 1, messageResId = R.string.error_minimum_school_year)
    @Max(value = 12, messageResId = R.string.error_maximum_school_year)
    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextAnoEscolaridade;
    private TextInputLayout textInputLayoutAnoEscolaridade;

    @Length(min = 9, max = 9, messageResId = R.string.error_invalid_academic_year)
    @NotEmpty(messageResId = R.string.error_required_field)
    private TextInputEditText editTextAnoLectivo;
    private TextInputLayout textInputLayoutAnoLectivo;

    // Views
    private TextView textViewTitle;
    private FloatingActionButton buttonFabNext;
    private ImageButton buttonPrev;
    private ViewPager2 viewPager;

    // Global variables
    private int position = 0;
    private boolean isExplorador = false;

    public OnBoardingFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding2, container, false);

        initViews(view);
        setOnClickListeners();

        // Init Form Validator
        formValidator = new Validator(this);
        formValidator.setValidationListener(this);

        return view;
    }

    private void initViews(View view) {
        textViewTitle = view.findViewById(R.id.text_title);
        editTextNome = view.findViewById(R.id.textinputedittext_nome);
        textInputLayoutEscola = view.findViewById(R.id.textinputlayout_escola);
        editTextEscola = view.findViewById(R.id.textinputedittext_escola);
        textInputLayoutAnoEscolaridade = view.findViewById(R.id.textinputlayout_anoescolaridade);
        editTextAnoEscolaridade = view.findViewById(R.id.textinputedittext_anoescolaridade);
        textInputLayoutAnoLectivo = view.findViewById(R.id.textinputlayout_anolectivo);
        editTextAnoLectivo = view.findViewById(R.id.textinputedittext_anolectivo);
        radioGroupTipoUtilizador = view.findViewById(R.id.radioGroup_userType);
        buttonFabNext = view.findViewById(R.id.btn_fabNext);
        buttonPrev = view.findViewById(R.id.btn_prev);
        viewPager = getActivity().findViewById(R.id.viewPager);

        editTextAnoLectivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                position = editTextAnoLectivo.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextAnoLectivo.getText().length() == 4 && position != 5) {
                    editTextAnoLectivo.setText(editTextAnoLectivo.getText().toString()+"/");
                    editTextAnoLectivo.setSelection(5);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // EMPTY
            }
        });

        radioGroupTipoUtilizador.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobutton_explorador) {
                    isExplorador = true;
                    textInputLayoutEscola.setVisibility(View.INVISIBLE);
                    textInputLayoutAnoEscolaridade.setVisibility(View.INVISIBLE);
                    textInputLayoutAnoLectivo.setVisibility(View.INVISIBLE);
                } else {
                    isExplorador = false;
                    textInputLayoutEscola.setVisibility(View.VISIBLE);
                    textInputLayoutAnoEscolaridade.setVisibility(View.VISIBLE);
                    textInputLayoutAnoLectivo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Declaration of all onClick events
     */
    private void setOnClickListeners() {
        buttonFabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If valid -> onValidationSucceeded()
                formValidator.validate();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(SEQUENCE_NUMBER-2);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        if (radioGroupTipoUtilizador.getCheckedRadioButtonId() == R.id.radiobutton_aluno) {
            onBoardingViewModel.setTipoUtilizador(0);
        } else if (radioGroupTipoUtilizador.getCheckedRadioButtonId() == R.id.radiobutton_professor) {
            onBoardingViewModel.setTipoUtilizador(1);
        } else {
            onBoardingViewModel.setTipoUtilizador(2);
        }

        onBoardingViewModel.setNome(editTextNome.getText().toString());

        if (!isExplorador) {
            onBoardingViewModel.setEscola(editTextEscola.getText().toString());
            onBoardingViewModel.setAnoEscolaridade(editTextAnoEscolaridade.getText().toString());
            onBoardingViewModel.setAnoLectivo(editTextAnoLectivo.getText().toString());
        }

        Toast.makeText(getContext(), "Nome: " + onBoardingViewModel.getNome(), Toast.LENGTH_SHORT).show();

        viewPager.setCurrentItem(SEQUENCE_NUMBER);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
                // ((TextInputLayout) view.getParent().getParent()).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}