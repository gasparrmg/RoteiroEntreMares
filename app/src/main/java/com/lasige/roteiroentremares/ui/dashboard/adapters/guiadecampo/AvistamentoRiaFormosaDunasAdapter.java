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
import com.lasige.roteiroentremares.data.model.relations.AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvistamentoRiaFormosaDunasAdapter extends RecyclerView.Adapter<AvistamentoRiaFormosaDunasAdapter.DunasHolder> {

    private OnItemClickListener listener;

    private List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentos = new ArrayList<>();

    private Context context;

    public AvistamentoRiaFormosaDunasAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DunasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamento_item, parent, false);
        return new DunasHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DunasHolder holder, int position) {
        AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias currentAvistamento = avistamentos.get(position);
        holder.textViewTitle.setText(currentAvistamento.getAvistamentoDunasRiaFormosa().getZona() + " - Quadrado " + currentAvistamento.getAvistamentoDunasRiaFormosa().getIteracao());

        Glide.with(context)
                .load(new File(currentAvistamento.getAvistamentoDunasRiaFormosa().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return avistamentos.size();
    }

    public void setAvistamentos(List<AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias> avistamentos) {
        this.avistamentos = avistamentos;
        notifyDataSetChanged();
    }

    class DunasHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView image;

        public DunasHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_title);
            image = itemView.findViewById(R.id.imageview_picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onAvistamentoItemClick(avistamentos.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onAvistamentoItemClick(AvistamentoDunasRiaFormosaWithEspecieRiaFormosaDunasInstancias avistamento);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
