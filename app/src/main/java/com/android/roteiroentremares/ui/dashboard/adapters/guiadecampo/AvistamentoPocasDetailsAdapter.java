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
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.data.model.relations.AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvistamentoPocasDetailsAdapter extends RecyclerView.Adapter<AvistamentoPocasDetailsAdapter.EspecieHolder> {

    private OnItemClickListener listener;

    private AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca;

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public AvistamentoPocasDetailsAdapter(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamentos_especie_item, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieAvencas currentEspecie = avistamentoPoca.getEspeciesAvencasPocasInstancias().get(position).getEspecieAvencas();
        int instancias = avistamentoPoca.getEspeciesAvencasPocasInstancias().get(position).getInstancias();

        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());
        holder.textViewInstancias.setText(String.valueOf(instancias));


        // ImageView
        Glide.with(context)
                .load(context.getResources().getIdentifier(currentEspecie.getPictures().get(0), "drawable", context.getPackageName()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.imageViewPicture);
    }

    @Override
    public int getItemCount() {
        return avistamentoPoca.getEspeciesAvencasPocasInstancias().size();
    }

    public void setAvistamentoPoca(AvistamentoPocasAvencasWithEspecieAvencasPocasInstancias avistamentoPoca) {
        this.avistamentoPoca = avistamentoPoca;
        notifyDataSetChanged();
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewNomeComum;
        private TextView textViewInstancias;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            textViewInstancias = itemView.findViewById(R.id.textView_instancias);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onAvencasItemClick(especiesAvencas.get(position));
                    }
                }
            });*/
        }
    }

    public interface OnItemClickListener {
        void onAvencasItemClick(EspecieAvencas especieAvencas);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
