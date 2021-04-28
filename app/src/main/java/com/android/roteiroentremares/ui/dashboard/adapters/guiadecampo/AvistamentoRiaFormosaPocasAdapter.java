package com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvistamentoRiaFormosaPocasAdapter extends RecyclerView.Adapter<AvistamentoRiaFormosaPocasAdapter.PocaHolder> {

    private OnItemClickListener listener;

    private List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentos = new ArrayList<>();

    private Context context;

    public AvistamentoRiaFormosaPocasAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PocaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamento_item, parent, false);
        return new PocaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PocaHolder holder, int position) {
        AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias currentAvistamento = avistamentos.get(position);
        holder.textViewTitle.setText("Poça " + currentAvistamento.getAvistamentoPocasRiaFormosa().getIdAvistamento());

        Log.d("AVISTAMENTO_ADAPTER", "PhotoPath: " + currentAvistamento.getAvistamentoPocasRiaFormosa().getPhotoPath());

        Glide.with(context)
                .load(new File(currentAvistamento.getAvistamentoPocasRiaFormosa().getPhotoPath()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return avistamentos.size();
    }

    public void setAvistamentos(List<AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias> avistamentos) {
        this.avistamentos = avistamentos;
        notifyDataSetChanged();
    }

    class PocaHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView image;

        public PocaHolder(View itemView) {
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
        void onAvistamentoItemClick(AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias avistamentoPoca);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
