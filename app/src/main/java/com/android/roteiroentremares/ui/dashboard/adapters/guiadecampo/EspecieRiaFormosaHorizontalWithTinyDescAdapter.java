package com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.EspecieRiaFormosa;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EspecieRiaFormosaHorizontalWithTinyDescAdapter extends RecyclerView.Adapter<EspecieRiaFormosaHorizontalWithTinyDescAdapter.EspecieHolder> {

    private OnItemClickListener listener;

    private List<EspecieRiaFormosa> especies = new ArrayList<>();
    // private List<EspecieRiaFormosa> especiesAvencas = new ArrayList<>();

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public EspecieRiaFormosaHorizontalWithTinyDescAdapter(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.especie_item_with_tiny_desc, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieRiaFormosa currentEspecie = especies.get(position);
        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());
        holder.textViewNomeCientifico.setText(currentEspecie.getNomeCientifico());

        if (currentEspecie.getTinyDesc().isEmpty()) {
            holder.textViewTinyDesc.setVisibility(View.GONE);
        } else {
            holder.textViewTinyDesc.setText(currentEspecie.getTinyDesc());
        }

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
        private TextView textViewNomeCientifico;
        private TextView textViewTinyDesc;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            textViewNomeCientifico = itemView.findViewById(R.id.textView_nomecientifico);
            textViewTinyDesc = itemView.findViewById(R.id.textView_tinydesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(especies.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EspecieRiaFormosa especieRiaFormosa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
