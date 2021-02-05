package com.android.roteiroentremares.ui.dashboard.adapters.artefactos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ArtefactoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Artefacto> artefactos = new ArrayList<>();

    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLong;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artefacto_text_item, parent, false);
        return new ViewHolderText(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Where we get the data to the Views
        Artefacto currentArtefacto = artefactos.get(position);

        switch (holder.getItemViewType()) {
            case 0:
                // Text Artefact
                ViewHolderText viewHolderText = (ViewHolderText) holder;
                viewHolderText.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderText.textViewContent.setText(currentArtefacto.getContent());
                viewHolderText.textViewDate.setText(currentArtefacto.getDate());
                break;
            case 1:
                // Image Artefact
                ViewHolderText viewHolderImage = (ViewHolderText) holder;
                viewHolderImage.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderImage.textViewContent.setText(currentArtefacto.getContent());
                break;
            case 2:
                // Audio Artefact
                ViewHolderText viewHolderAudio = (ViewHolderText) holder;
                viewHolderAudio.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderAudio.textViewContent.setText(currentArtefacto.getContent());
                break;
            case 3:
                // Video Artefact
                ViewHolderText viewHolderVideo = (ViewHolderText) holder;
                viewHolderVideo.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderVideo.textViewContent.setText(currentArtefacto.getContent());
                break;

            default:
                // Text Artefact
                ViewHolderText viewHolderTextAlt = (ViewHolderText) holder;
                viewHolderTextAlt.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderTextAlt.textViewContent.setText(currentArtefacto.getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return artefactos.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return artefactos.size();
    }

    public Artefacto getArtefactoAt(int position) {
        return artefactos.get(position);
    }

    public void setArtefactos(List<Artefacto> artefactos) {
        this.artefactos = artefactos;
        notifyDataSetChanged(); // SHOULD NOT USE, THERE'S MORE EFFICIENT WAYS
    }

    private class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;
        private TextView textViewDate;
        private MaterialCardView cardView;


        public ViewHolderText(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewContent = itemView.findViewById(R.id.textView_content);
            textViewDate = itemView.findViewById(R.id.textView_date);
            cardView = itemView.findViewById(R.id.cardview_artefacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(artefactos.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(artefactos.get(position), cardView);
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Artefacto artefacto);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(Artefacto artefacto, MaterialCardView cardView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listenerLong = listener;
    }
}
