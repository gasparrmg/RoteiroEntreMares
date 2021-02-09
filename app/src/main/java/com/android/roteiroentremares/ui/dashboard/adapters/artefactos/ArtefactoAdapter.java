package com.android.roteiroentremares.ui.dashboard.adapters.artefactos;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.EditArtefactoActivity;
import com.android.roteiroentremares.ui.dashboard.screens.artefactos.NewArtefactoActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArtefactoAdapter extends ListAdapter<Artefacto, RecyclerView.ViewHolder> {

    private Context context;

    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLong;

    public ArtefactoAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Artefacto> DIFF_CALLBACK = new DiffUtil.ItemCallback<Artefacto>() {
        @Override
        public boolean areItemsTheSame(@NonNull Artefacto oldItem, @NonNull Artefacto newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Artefacto oldItem, @NonNull Artefacto newItem) {
            if (oldItem != null || oldItem.getDescription() != null) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getContent().equals(newItem.getContent()) &&
                        oldItem.getType() == newItem.getType() &&
                        oldItem.getDescription().equals(newItem.getDescription()) &&
                        oldItem.getDate().equals(newItem.getDate()) &&
                        oldItem.getLatitude().equals(newItem.getLatitude()) &&
                        oldItem.getLongitude().equals(newItem.getLongitude()) &&
                        oldItem.getCodigoTurma().equals(newItem.getCodigoTurma()) &&
                        oldItem.isShared() == newItem.isShared();
            }

            return false;
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View imageItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_image_item, parent, false);
                return new ViewHolderImage(imageItemView);
            default:
                View textItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_text_item, parent, false);
                return new ViewHolderText(textItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Where we get the data to the Views
        Artefacto currentArtefacto = getItem(position);

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
                ViewHolderImage viewHolderImage = (ViewHolderImage) holder;
                viewHolderImage.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderImage.textViewDescription.setText(currentArtefacto.getDescription());
                viewHolderImage.textViewDate.setText(currentArtefacto.getDate());

                File imageFile = new File(currentArtefacto.getContent());

                if (imageFile.exists()) {
                    //viewHolderImage.imageViewPhoto.setImageURI(Uri.fromFile(new File(currentArtefacto.getContent())));

                    Glide.with(context)
                            .load(new File(currentArtefacto.getContent()))
                            .placeholder(android.R.drawable.ic_media_play)
                            .into(viewHolderImage.imageViewPhoto);
                } else {
                    Log.e("ARTEFACTO_ADAPTER", "Não foi possível encontrar a imagem.");
                }
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
        return getItem(position).getType();
    }

    /*@Override
    public int getItemCount() {
        return artefactos.size();
    }*/

    public Artefacto getArtefactoAt(int position) {
        return getItem(position);
    }

    /*public void setArtefactos(List<Artefacto> artefactos) {
        this.artefactos = artefactos;
        notifyDataSetChanged(); // SHOULD NOT USE, THERE'S MORE EFFICIENT WAYS
    }*/

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
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
                    }
                    return true;
                }
            });
        }
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        private ImageView imageViewPhoto;
        private MaterialCardView cardView;


        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewDate = itemView.findViewById(R.id.textView_date);
            imageViewPhoto = itemView.findViewById(R.id.imageview_picture);
            cardView = itemView.findViewById(R.id.cardview_artefacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
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
