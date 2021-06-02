package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EspecieRiaFormosaGridAdapter extends RecyclerView.Adapter<EspecieRiaFormosaGridAdapter.EspecieHolder> {

    private OnItemClickListener listener;

    private List<EspecieRiaFormosa> especies = new ArrayList<>();

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public EspecieRiaFormosaGridAdapter(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.especie_grid_item, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieRiaFormosa currentEspecie = especies.get(position);
        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());

        // ImageView
        Glide.with(context)
                .load(context.getResources().getIdentifier(currentEspecie.getPictures().get(0), "drawable", context.getPackageName()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.imageViewPicture);
    }

    @Override
    public int getItemCount() {
        return especies.size();
    }

    public void setEspecies(List<EspecieRiaFormosa> especies) {
        this.especies = especies;
        notifyDataSetChanged();
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewNomeComum;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onRiaFormosaItemClick(especies.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onRiaFormosaItemClick(EspecieRiaFormosa especieRiaFormosa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
