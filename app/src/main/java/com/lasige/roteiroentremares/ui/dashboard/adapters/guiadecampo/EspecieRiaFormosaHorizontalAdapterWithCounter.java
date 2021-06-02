package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

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
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class EspecieRiaFormosaHorizontalAdapterWithCounter extends RecyclerView.Adapter<EspecieRiaFormosaHorizontalAdapterWithCounter.EspecieHolder> {

    private OnItemClickListener listener;

    private List<EspecieRiaFormosa> especiesRiaFormosa = new ArrayList<>();
    private int[] instancias;

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public EspecieRiaFormosaHorizontalAdapterWithCounter(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamentos_especie_counter_item, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieRiaFormosa currentEspecie = especiesRiaFormosa.get(position);
        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());

        holder.elegantNumberButton.setNumber(String.valueOf(instancias[position]));

        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d("TESTE INSTANCIAS", "current especie: " + especiesRiaFormosa.get(position).getNomeComum());
                if (position != RecyclerView.NO_POSITION) {
                    instancias[position] = newValue;
                    Log.d("TESTE INSTANCIAS", especiesRiaFormosa.get(position).getNomeComum() + " instancias: " + instancias[position]);
                }
            }
        });

        // ImageView
        Glide.with(context)
                .load(context.getResources().getIdentifier(currentEspecie.getPictures().get(0), "drawable", context.getPackageName()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.imageViewPicture);
    }

    @Override
    public int getItemCount() {
        return especiesRiaFormosa.size();
    }

    public void setEspeciesRiaFormosa(List<EspecieRiaFormosa> especiesRiaFormosa) {
        this.especiesRiaFormosa = especiesRiaFormosa;
        this.instancias = new int[especiesRiaFormosa.size()];
        notifyDataSetChanged();
    }

    public List<EspecieRiaFormosa> getEspeciesRiaFormosa() {
        return especiesRiaFormosa;
    }

    public int[] getInstancias() {
        return instancias;
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewNomeComum;
        private ElegantNumberButton elegantNumberButton;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            elegantNumberButton = itemView.findViewById(R.id.elegant_number_picker_instancias);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onRiaFormosaItemClick(especiesRiaFormosa.get(position));
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
