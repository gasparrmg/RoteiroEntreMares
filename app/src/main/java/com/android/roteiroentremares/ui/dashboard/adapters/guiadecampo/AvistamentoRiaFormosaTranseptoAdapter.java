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
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasRiaFormosaWithEspecieRiaFormosaPocasInstancias;
import com.android.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvistamentoRiaFormosaTranseptoAdapter extends RecyclerView.Adapter<AvistamentoRiaFormosaTranseptoAdapter.TranseptoHolder> {

    private OnItemClickListener listener;

    private List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentos = new ArrayList<>();

    private Context context;

    public AvistamentoRiaFormosaTranseptoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TranseptoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamento_item, parent, false);
        return new TranseptoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TranseptoHolder holder, int position) {
        AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias currentAvistamento = avistamentos.get(position);
        holder.textViewTitle.setText("Transepto " + currentAvistamento.getAvistamentoTranseptosRiaFormosa().getIdAvistamento());

        holder.image.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return avistamentos.size();
    }

    public void setAvistamentos(List<AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias> avistamentos) {
        this.avistamentos = avistamentos;
        notifyDataSetChanged();
    }

    class TranseptoHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView image;

        public TranseptoHolder(View itemView) {
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
        void onAvistamentoItemClick(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTransepto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
