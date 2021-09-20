package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.bumptech.glide.Glide;
import com.lasige.roteiroentremares.ui.dashboard.screens.guiadecampo.details.EspecieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class EspecieRiaFormosaHorizontalAdapterWithSwitch extends RecyclerView.Adapter<EspecieRiaFormosaHorizontalAdapterWithSwitch.EspecieHolder> {

    private OnItemClickListener listener;

    private List<EspecieRiaFormosa> especiesRiaFormosa = new ArrayList<>();
    private boolean[] instancias;

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public EspecieRiaFormosaHorizontalAdapterWithSwitch(Context context, int spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public EspecieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avistamentos_especie_switch_item, parent, false);
        return new EspecieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspecieHolder holder, int position) {
        EspecieRiaFormosa currentEspecie = especiesRiaFormosa.get(position);
        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());

        holder.checkBoxInstancias.setOnCheckedChangeListener(null);

        holder.checkBoxInstancias.setChecked(instancias[position]);

        holder.checkBoxInstancias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TESTE INSTANCIAS", "current especie: " + especiesRiaFormosa.get(position).getNomeComum());
                if (position != RecyclerView.NO_POSITION) {
                    instancias[position] = isChecked;
                    Log.d("TESTE INSTANCIAS", especiesRiaFormosa.get(position).getNomeComum() + " instancias: " + instancias[position]);
                }
            }
        });

        // ImageView
        Glide.with(context)
                .load(context.getResources().getIdentifier(currentEspecie.getPictures().get(0), "drawable", context.getPackageName()))
                .placeholder(android.R.drawable.ic_media_play)
                .into(holder.imageViewPicture);

        holder.imageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EspecieDetailsActivity.class);
                intent.putExtra("avencasOrRiaFormosa", 1);
                intent.putExtra("especie", currentEspecie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return especiesRiaFormosa.size();
    }

    public void setEspeciesRiaFormosa(List<EspecieRiaFormosa> especiesRiaFormosa) {
        this.especiesRiaFormosa = especiesRiaFormosa;
        this.instancias = new boolean[especiesRiaFormosa.size()];

        notifyDataSetChanged();
    }

    public List<EspecieRiaFormosa> getEspeciesRiaFormosa() {
        return especiesRiaFormosa;
    }

    public boolean[] getInstancias() {
        return instancias;
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewNomeComum;
        private CheckBox checkBoxInstancias;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            checkBoxInstancias = itemView.findViewById(R.id.checkbox_instancias);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        checkBoxInstancias.toggle();
                    }

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
