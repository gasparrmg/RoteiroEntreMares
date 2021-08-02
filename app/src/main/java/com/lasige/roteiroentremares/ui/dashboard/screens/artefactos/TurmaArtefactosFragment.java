package com.lasige.roteiroentremares.ui.dashboard.screens.artefactos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenActivity;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.lasige.roteiroentremares.ui.common.MediaPlayerActivity;
import com.lasige.roteiroentremares.ui.dashboard.adapters.artefactos.ArtefactoAdapter;
import com.lasige.roteiroentremares.ui.dashboard.adapters.artefactos.ArtefactoTurmaAdapter;
import com.lasige.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

@AndroidEntryPoint
public class TurmaArtefactosFragment extends Fragment implements ArtefactoTurmaAdapter.OnItemClickListener, ArtefactoTurmaAdapter.OnItemLongClickListener {

    // ViewModel
    private ArtefactosViewModel artefactosViewModel;

    // Adapters
    private ArtefactoTurmaAdapter adapter;

    // Views
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutIsEmpty;
    private ProgressBar progressBar;
    private TextView textViewIsEmptyTitle;
    private TextView textViewIsEmptyMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turma_artefactos, container, false);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        // artefactosViewModel.deleteAllArtefacto();

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        // Init RecyclerView

        recyclerView = view.findViewById(R.id.recyclerView_artefactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new ArtefactoTurmaAdapter(getContext());
        recyclerView.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                recyclerView.smoothScrollToPosition(0);
            }
        });

        linearLayoutIsEmpty = view.findViewById(R.id.linearlayout_isEmpty);
        progressBar = view.findViewById(R.id.progress_bar);

        textViewIsEmptyTitle = view.findViewById(R.id.textView_isEmpty_title);
        textViewIsEmptyMessage = view.findViewById(R.id.textView_isEmpty_message);

        if (artefactosViewModel.getTipoUtilizador() == 1) {
            textViewIsEmptyTitle.setText(getResources().getString(R.string.artefactos_turma_isempty_title_professor));
            textViewIsEmptyMessage.setText(getResources().getString(R.string.artefactos_turma_isempty_message_professor));
        } else if (artefactosViewModel.getTipoUtilizador() == 2) {
            textViewIsEmptyTitle.setText(getResources().getString(R.string.artefactos_turma_isempty_title_explorador));
            textViewIsEmptyMessage.setText(getResources().getString(R.string.artefactos_turma_isempty_message_explorador));
        }

        artefactosViewModel.getAllArtefactosTurma().observe(getViewLifecycleOwner(), new Observer<List<ArtefactoTurma>>() {
            @Override
            public void onChanged(List<ArtefactoTurma> artefactos) {
                //update recycler view
                adapter.submitList(artefactos);
                progressBar.setVisibility(View.GONE);

                if (artefactos.size() == 0) {
                    linearLayoutIsEmpty.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutIsEmpty.setVisibility(View.GONE);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                materialAlertDialogBuilder.setTitle("Apagar artefacto?");
                materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar este artefacto?");
                materialAlertDialogBuilder.setPositiveButton("Sim, apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        artefactosViewModel.deleteArtefactoTurma(adapter.getArtefactoAt(viewHolder.getAdapterPosition()));
                        Snackbar.make(recyclerView, "O teu Artefacto foi apagado!", Snackbar.LENGTH_SHORT).show();
                    }
                });
                materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DISMISS DIALOG
                    }
                });
                materialAlertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = materialAlertDialogBuilder.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorError))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(ArtefactoTurma artefactoTurma) {
        // Open Image Activity
        if (artefactoTurma.getType() == 1) {
            Intent intent = new Intent(getActivity(), ImageFullscreenFileActivity.class);
            intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, artefactoTurma.getContent());
            startActivity(intent);
        } else if (artefactoTurma.getType() == 2) {
            // Audio
        } else if (artefactoTurma.getType() == 3) {
            // Video
            Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
            intent.putExtra("path", artefactoTurma.getContent());
            startActivity(intent);
        }
    }

    @Override
    public void onLongItemClick(ArtefactoTurma artefactoTurma, MaterialCardView view) {
        // Highlight card
        view.setStrokeColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        view.setStrokeWidth(4);

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        materialAlertDialogBuilder.setTitle("Apagar artefacto?");
        materialAlertDialogBuilder.setMessage("Tens a certeza que queres apagar este artefacto?");
        materialAlertDialogBuilder.setPositiveButton("Sim, apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                artefactosViewModel.deleteArtefactoTurma(artefactoTurma);
                Snackbar.make(recyclerView, "O teu Artefacto foi apagado!", Snackbar.LENGTH_SHORT).show();
            }
        });
        materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DISMISS DIALOG
            }
        });
        materialAlertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                view.setStrokeWidth(0);
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = materialAlertDialogBuilder.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));
    }
}