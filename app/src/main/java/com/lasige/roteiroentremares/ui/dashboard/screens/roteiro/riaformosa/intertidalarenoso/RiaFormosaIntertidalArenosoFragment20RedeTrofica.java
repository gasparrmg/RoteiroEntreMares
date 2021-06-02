package com.lasige.roteiroentremares.ui.dashboard.screens.roteiro.riaformosa.intertidalarenoso;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.guiadecampo.GuiaDeCampoViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RiaFormosaIntertidalArenosoFragment20RedeTrofica extends Fragment {

    private static final String QUERY = "SELECT * FROM especie_table_riaformosa " +
            "WHERE nomeCientifico = 'Pachygrapsus marmoratus' OR " + //caranguejo
            "nomeCientifico = 'Anemonia sulcata' OR " + //anemona
            "nomeCientifico = 'Chthamalus spp' OR " + // cracas
            "nomeCientifico = 'Patella spp' OR " + //lapa
            "nomeCientifico = 'Calibris alba' OR " + //passaro
            "nomeCientifico = 'Mytilus galloprovincialis' OR " + //mexilhao
            "nomeCientifico = 'Gobius paganellus' OR " + //caboz
            "nomeComum = 'Algas'";

    private DashboardViewModel dashboardViewModel;
    private GuiaDeCampoViewModel guiaDeCampoViewModel;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;

    private LinearLayout linearLayoutZooplancton;
    private LinearLayout linearLayoutFitoplancton;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private LinearLayout linearLayout6;
    private LinearLayout linearLayout7;
    private LinearLayout linearLayout8;

    private LinearLayout linearLayoutContainer1;
    private LinearLayout linearLayoutContainer2;

    private FloatingActionButton fabNext;

    private List<EspecieRiaFormosa> resultListEspecies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riaformosa_intertidalarenoso20_rede_trofica, container, false);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        guiaDeCampoViewModel = new ViewModelProvider(this).get(GuiaDeCampoViewModel.class);

        setRetainInstance(true);

        initViews(view);
        setOnClickListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.roteiro_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    private void initViews(View view) {
        fabNext = view.findViewById(R.id.btn_fabNext);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        imageView5 = view.findViewById(R.id.imageView5);
        imageView6 = view.findViewById(R.id.imageView6);
        imageView7 = view.findViewById(R.id.imageView7);
        imageView8 = view.findViewById(R.id.imageView8);

        linearLayoutZooplancton = view.findViewById(R.id.linearLayout_zooplancton);
        linearLayoutFitoplancton = view.findViewById(R.id.linearLayout_fitoplancton);

        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
        linearLayout5 = view.findViewById(R.id.linearLayout5);
        linearLayout6 = view.findViewById(R.id.linearLayout6);
        linearLayout7 = view.findViewById(R.id.linearLayout7);
        linearLayout8 = view.findViewById(R.id.linearLayout8);

        linearLayoutContainer1 = view.findViewById(R.id.linearLayout_container1);
        linearLayoutContainer2 = view.findViewById(R.id.linearLayout_container2);

        imageView1.setOnClickListener(especieClickListener);
        imageView2.setOnClickListener(especieClickListener);
        imageView3.setOnClickListener(especieClickListener);
        imageView4.setOnClickListener(especieClickListener);
        imageView5.setOnClickListener(especieClickListener);
        imageView6.setOnClickListener(especieClickListener);
        imageView7.setOnClickListener(especieClickListener);
        imageView8.setOnClickListener(especieClickListener);

        imageView1.setOnLongClickListener(longClickListener);
        imageView2.setOnLongClickListener(longClickListener);
        imageView3.setOnLongClickListener(longClickListener);
        imageView4.setOnLongClickListener(longClickListener);
        imageView5.setOnLongClickListener(longClickListener);
        imageView6.setOnLongClickListener(longClickListener);
        imageView7.setOnLongClickListener(longClickListener);
        imageView8.setOnLongClickListener(longClickListener);

        linearLayout1.setOnDragListener(dragListener);
        linearLayout2.setOnDragListener(dragListener);
        linearLayout3.setOnDragListener(dragListener);
        linearLayout4.setOnDragListener(dragListener);
        linearLayout5.setOnDragListener(dragListener);
        linearLayout6.setOnDragListener(dragListener);
        linearLayout7.setOnDragListener(dragListener);
        linearLayout8.setOnDragListener(dragListener);

        guiaDeCampoViewModel.filterEspeciesRiaFormosa(QUERY);

        guiaDeCampoViewModel.getAllEspecieRiaFormosa().observe(getViewLifecycleOwner(), new Observer<List<EspecieRiaFormosa>>() {
            @Override
            public void onChanged(List<EspecieRiaFormosa> especieRiaFormosas) {
                resultListEspecies = especieRiaFormosas;

                Log.d("REDE_TROFICA", "size: " + especieRiaFormosas.size());

                guiaDeCampoViewModel.getAllEspecieRiaFormosa().removeObserver(this);
            }
        });
    }

    private void setOnClickListeners(View view) {
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_riaFormosaIntertidalArenosoFragment20RedeTrofica_to_riaFormosaIntertidalArenosoFragment21);
            }
        });

        linearLayoutZooplancton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "O Zooplâncton inclui um conjunto de animais microscópicos que vivem suspensos na água.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });

        linearLayoutFitoplancton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setMessage(HtmlCompat.fromHtml(
                        "O Fitoplâncton inclui um conjunto de algas microscópicas que vivem suspensas na água.",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                ));
                materialAlertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });
    }

    private void checkIfCompleted() {
        if (linearLayoutContainer1.getChildCount() == 0 && linearLayoutContainer2.getChildCount() == 0) {
            Toast.makeText(getActivity(), "Parabéns! Completaste a tarefa!", Toast.LENGTH_LONG).show();
            linearLayoutContainer1.setVisibility(View.GONE);
            linearLayoutContainer2.setVisibility(View.GONE);
            fabNext.setVisibility(View.VISIBLE);
            fabNext.setEnabled(true);
        }
    }

    private void initGuiaCampo(String nomeCientifico) {
        boolean found = false;
        EspecieRiaFormosa especieRiaFormosa = null;

        Log.d("REDE_TROFICA", "initGuiaCampo, nomeCientifico: " + nomeCientifico);

        for (int i = 0; i < resultListEspecies.size(); i++) {
            if (resultListEspecies.get(i).getNomeCientifico().equals(nomeCientifico)) {
                Log.d("REDE_TROFICA", "especie found");
                especieRiaFormosa = resultListEspecies.get(i);
                found = true;
                break;
            }
        }

        if (found == true && especieRiaFormosa != null) {
            Intent intent = new Intent(getActivity(), EspecieDetailsActivity.class);
            intent.putExtra("especie", especieRiaFormosa);
            startActivity(intent);
        }
    }

    View.OnClickListener especieClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("REDE_TROFICA", "especieClickListener");

            switch (v.getId()) {
                case R.id.imageView1:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Pachygrapsus marmoratus");
                    break;
                case R.id.imageView2:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView2");
                    initGuiaCampo("Anemonia sulcata");
                    break;
                case R.id.imageView3:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Chthamalus spp");
                    break;
                case R.id.imageView4:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Patella spp");
                    break;
                case R.id.imageView5:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Calibris alba");
                    break;
                case R.id.imageView6:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("");
                    break;
                case R.id.imageView7:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Mytilus galloprovincialis");
                    break;
                case R.id.imageView8:
                    Log.d("REDE_TROFICA", "especieClickListener, imageView1");
                    initGuiaCampo("Gobius paganellus");
                    break;
                default:
                    break;
            }
        }
    };

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData.Item item = new ClipData.Item(v.getTag().toString());

            ClipData dragData = new ClipData(
                    v.getTag().toString(),
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    item
            );

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

            v.startDragAndDrop(dragData, shadow, v, 0);

            v.setVisibility(View.INVISIBLE);

            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            LinearLayout destination = (LinearLayout) view;
            View draggedView = (View) event.getLocalState();
            ViewGroup owner = (ViewGroup) draggedView.getParent();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    view.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedView.setVisibility(View.VISIBLE);
                    view.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DROP:

                    if (destination.getId() == linearLayout1.getId()) {
                        if (draggedView.getId() == imageView8.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout2.getId()) {
                        if (draggedView.getId() == imageView1.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout3.getId()) {
                        if (draggedView.getId() == imageView5.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout4.getId()) {
                        if (draggedView.getId() == imageView2.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout5.getId()) {
                        if (draggedView.getId() == imageView7.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout6.getId()) {
                        if (draggedView.getId() == imageView3.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout7.getId()) {
                        if (draggedView.getId() == imageView4.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else if (destination.getId() == linearLayout8.getId()) {
                        if (draggedView.getId() == imageView6.getId()) {

                            ClipData.Item item = event.getClipData().getItemAt(0);
                            String dragData = item.getText().toString();

                            view.invalidate();
                            owner.removeView(draggedView);

                            destination.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            draggedView.setEnabled(false);
                        } else {
                            Toast.makeText(getActivity(), "Errado! Tenta outra vez.", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    } else {
                        Log.d("DragDrop","Não foi para nenhum dos containers.");
                        draggedView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    Log.d("DragDrop","past events");

                    checkIfCompleted();

                    return true;
                default:
                    Log.e("DragDrop","Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    };
}