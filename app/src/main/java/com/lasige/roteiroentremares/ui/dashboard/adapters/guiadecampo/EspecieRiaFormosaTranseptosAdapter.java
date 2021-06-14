package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EspecieRiaFormosaTranseptosAdapter extends RecyclerView.Adapter<EspecieRiaFormosaTranseptosAdapter.EspecieHolder> {

    private OnItemClickListener listener;
    private OnPhotoClickListener photoListener;

    private List<EspecieRiaFormosa> especiesRiaFormosa = new ArrayList<>();
    private boolean[] instanciasExpostaPedra;
    private boolean[] instanciasInferiorPedra;
    private boolean[] instanciasSubstrato;
    private String[] photoPaths;

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public EspecieRiaFormosaTranseptosAdapter(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamentos_especie_transepto_item, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieRiaFormosa currentEspecie = especiesRiaFormosa.get(position);
        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());

        holder.checkBoxInstanciasExpostaPedra.setOnCheckedChangeListener(null);
        holder.checkBoxInstanciasInferiorPedra.setOnCheckedChangeListener(null);
        holder.checkBoxInstanciasSubstrato.setOnCheckedChangeListener(null);

        holder.checkBoxInstanciasExpostaPedra.setChecked(instanciasExpostaPedra[position]);
        holder.checkBoxInstanciasInferiorPedra.setChecked(instanciasInferiorPedra[position]);
        holder.checkBoxInstanciasSubstrato.setChecked(instanciasSubstrato[position]);

        holder.checkBoxInstanciasExpostaPedra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (position != RecyclerView.NO_POSITION) {
                    instanciasExpostaPedra[position] = isChecked;
                }
            }
        });

        holder.checkBoxInstanciasInferiorPedra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (position != RecyclerView.NO_POSITION) {
                    instanciasInferiorPedra[position] = isChecked;
                }
            }
        });

        holder.checkBoxInstanciasSubstrato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (position != RecyclerView.NO_POSITION) {
                    instanciasSubstrato[position] = isChecked;
                }
            }
        });

        if (photoPaths[position] == null) {
            // No path
            holder.imageButtonAddPhoto.setVisibility(View.VISIBLE);
            holder.imageViewPicture.setVisibility(View.GONE);

            holder.imageViewPicture.setOnClickListener(null);
        } else {
            // Available path
            holder.imageButtonAddPhoto.setVisibility(View.GONE);
            holder.imageViewPicture.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(new File(photoPaths[position]))
                    .into(holder.imageViewPicture);

            holder.imageViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*// open image full screen
                    Log.d("CROP", "opening image with path: " + photoPaths[position]);
                    Intent intent = new Intent(context, ImageFullscreenFileActivity.class);
                    intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, photoPaths[position]);
                    intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_POSITION, position);
                    intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_IS_TRANSEPTO, 1);

                    //context.startActivity(intent);
                    ((Activity) context).startActivityForResult(intent, ImageFullscreenFileActivity.INTENT_EXTRA_RESULT);*/
                    Log.d("TRANSEPTOS", "IMAGEclicked " + photoPaths[position]);
                    photoListener.onPhotoClick(position, photoPaths[position]);
                }
            });
        }

        /*
        // ImageView
        Glide.with(context)
                .load(context.getResources().getIdentifier(currentEspecie.getPictures().get(0), "drawable", context.getPackageName()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.imageViewPicture);*/
    }

    @Override
    public int getItemCount() {
        return especiesRiaFormosa.size();
    }

    public void setEspeciesRiaFormosa(List<EspecieRiaFormosa> especiesRiaFormosa) {
        this.especiesRiaFormosa = especiesRiaFormosa;
        this.instanciasExpostaPedra = new boolean[especiesRiaFormosa.size()];
        this.instanciasInferiorPedra = new boolean[especiesRiaFormosa.size()];
        this.instanciasSubstrato = new boolean[especiesRiaFormosa.size()];
        this.photoPaths = new String[especiesRiaFormosa.size()];

        notifyDataSetChanged();
    }

    public void setPhotoPaths(int index, String value) {
        if (photoPaths != null) {
            photoPaths[index] = value;

            notifyDataSetChanged();
        }
    }

    public boolean deletePhotoInPosition(int positionToDelete) {
        if (photoPaths != null) {
            if (deletePhoto(photoPaths[positionToDelete])) {
                photoPaths[positionToDelete] = null;
                notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean deletePhoto(String path) {
        File fileToDelete = new File(path);

        if (fileToDelete != null) {
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    Log.d("CROP", "last inserted photo deleted");
                    return true;
                } else {
                    Log.d("CROP", "last inserted photo NOT deleted");
                    return false;
                }
            }
        }

        return false;
    }

    public List<EspecieRiaFormosa> getEspeciesRiaFormosa() {
        return especiesRiaFormosa;
    }

    public boolean[] getInstanciasExpostaPedra() {
        return instanciasExpostaPedra;
    }

    public boolean[] getInstanciasInferiorPedra() {
        return instanciasInferiorPedra;
    }

    public boolean[] getInstanciasSubstrato() {
        return instanciasSubstrato;
    }

    public String[] getPhotoPaths() {
        return photoPaths;
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private ImageButton imageButtonAddPhoto;
        private TextView textViewNomeComum;
        private CheckBox checkBoxInstanciasExpostaPedra;
        private CheckBox checkBoxInstanciasInferiorPedra;
        private CheckBox checkBoxInstanciasSubstrato;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            imageButtonAddPhoto = itemView.findViewById(R.id.button_add_photo);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            checkBoxInstanciasExpostaPedra = itemView.findViewById(R.id.checkbox_instancias1);
            checkBoxInstanciasInferiorPedra = itemView.findViewById(R.id.checkbox_instancias2);
            checkBoxInstanciasSubstrato = itemView.findViewById(R.id.checkbox_instancias3);

            imageButtonAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TRANSEPTOS", "image button clicked");
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Log.d("TRANSEPTOS", "listener not null and position valid");
                        listener.onRiaFormosaItemClick(position);
                    }
                }
            });

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onRiaFormosaItemClick(especiesRiaFormosa.get(position));
                    }
                }
            });*/
        }
    }

    public interface OnItemClickListener {
        void onRiaFormosaItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position, String path);
    }

    public void setOnPhotoClickListener(OnPhotoClickListener photoListener) {
        this.photoListener = photoListener;
    }
}
