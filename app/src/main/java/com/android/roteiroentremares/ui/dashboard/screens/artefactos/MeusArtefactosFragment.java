package com.android.roteiroentremares.ui.dashboard.screens.artefactos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.ArtefactosActivity;
import com.android.roteiroentremares.ui.dashboard.adapters.artefactos.ArtefactoAdapter;
import com.android.roteiroentremares.ui.dashboard.viewmodel.artefactos.ArtefactosViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

@AndroidEntryPoint
public class MeusArtefactosFragment extends Fragment implements ArtefactoAdapter.OnItemClickListener, ArtefactoAdapter.OnItemLongClickListener {

    // ViewModel
    private ArtefactosViewModel artefactosViewModel;

    // Adapters
    private ArtefactoAdapter adapter;

    // Views
    private RecyclerView recyclerView;
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton fabNewText;
    private FloatingActionButton fabNewImage;
    private FloatingActionButton fabNewAudio;
    private FloatingActionButton fabNewVideo;
    private LinearLayout linearLayoutIsEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meus_artefactos, container, false);

        artefactosViewModel = new ViewModelProvider(this).get(ArtefactosViewModel.class);

        // artefactosViewModel.deleteAllArtefacto();

        initViews(view);
        setOnClickListeners();

        return view;
    }

    private void initViews(View view) {
        // Init RecyclerView

        recyclerView = view.findViewById(R.id.recyclerView_artefactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new ArtefactoAdapter();
        recyclerView.setAdapter(adapter);

        linearLayoutIsEmpty = view.findViewById(R.id.linearlayout_isEmpty);

        artefactosViewModel.getAllArtefactos().observe(getViewLifecycleOwner(), new Observer<List<Artefacto>>() {
            @Override
            public void onChanged(List<Artefacto> artefactos) {
                //update recycler view
                adapter.setArtefactos(artefactos);

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
                materialAlertDialogBuilder.setMessage(R.string.confirm_delete_message);
                materialAlertDialogBuilder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        artefactosViewModel.deleteArtefacto(adapter.getArtefactoAt(viewHolder.getAdapterPosition()));
                        Snackbar.make(recyclerView, "O teu Artefacto foi apagado!", Snackbar.LENGTH_SHORT).show();
                    }
                });
                materialAlertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
                materialAlertDialogBuilder.show();
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

        // Init FABs
        fabMenu = view.findViewById(R.id.fab_expand_menu_button);
        fabNewText = view.findViewById(R.id.fab_newText);
        fabNewImage = view.findViewById(R.id.fab_newImage);
        fabNewAudio = view.findViewById(R.id.fab_newAudio);
        fabNewVideo = view.findViewById(R.id.fab_newVideo);
    }

    private void setOnClickListeners() {
        fabNewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                Intent intent = new Intent(getActivity(), NewArtefactoActivity.class);
                intent.putExtra("NEW_ARTEFACTO_TYPE", 0);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onItemClick(Artefacto artefacto) {
        Log.d("MEUSARTEFACTOS_FRAGMENT", "Item Cliked.");
        Intent intent = new Intent(getActivity(), EditArtefactoActivity.class);
        intent.putExtra("EDIT_ARTEFACTO_ID", artefacto.getId());
        intent.putExtra("EDIT_ARTEFACTO_TITLE", artefacto.getTitle());
        intent.putExtra("EDIT_ARTEFACTO_CONTENT", artefacto.getContent());
        intent.putExtra("EDIT_ARTEFACTO_TYPE", artefacto.getType());
        intent.putExtra("EDIT_ARTEFACTO_DESCRIPTION", artefacto.getDescription());
        intent.putExtra("EDIT_ARTEFACTO_DATE", artefacto.getDate());
        intent.putExtra("EDIT_ARTEFACTO_LATITUDE", artefacto.getLatitude());
        intent.putExtra("EDIT_ARTEFACTO_LONGITUDE", artefacto.getLongitude());
        intent.putExtra("EDIT_ARTEFACTO_CODIGOTURMA", artefacto.getCodigoTurma());
        intent.putExtra("EDIT_ARTEFACTO_SHARED", artefacto.isShared());
        startActivityForResult(intent, 2);
    }

    @Override
    public void onLongItemClick(Artefacto artefacto, MaterialCardView view) {
        // Highlight card
        view.setStrokeColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        view.setStrokeWidth(4);

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        materialAlertDialogBuilder.setMessage(R.string.confirm_delete_message);
        materialAlertDialogBuilder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                artefactosViewModel.deleteArtefacto(artefacto);
                Snackbar.make(recyclerView, "O teu Artefacto foi apagado!", Snackbar.LENGTH_SHORT).show();
            }
        });
        materialAlertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
        materialAlertDialogBuilder.show();
    }
}