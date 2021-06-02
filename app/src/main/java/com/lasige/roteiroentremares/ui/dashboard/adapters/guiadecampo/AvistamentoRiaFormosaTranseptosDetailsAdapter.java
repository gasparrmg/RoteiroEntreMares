package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;
import com.lasige.roteiroentremares.data.model.relations.AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias;
import com.lasige.roteiroentremares.ui.common.ImageFullscreenFileActivity;
import com.bumptech.glide.Glide;

import java.io.File;

public class AvistamentoRiaFormosaTranseptosDetailsAdapter extends RecyclerView.Adapter<AvistamentoRiaFormosaTranseptosDetailsAdapter.EspecieHolder> {

    private OnItemClickListener listener;

    private AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTransepto;

    /**
     * 0 -> Avencas
     * 1 -> Ria Formosa
     */
    private int spot;
    private Context context;

    public AvistamentoRiaFormosaTranseptosDetailsAdapter(Context context, int spot) {
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
        EspecieRiaFormosa currentEspecie = avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().get(position).getEspecieRiaFormosa();
        boolean instancias1 = avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().get(position).isInstanciasExpostaPedra();
        boolean instancias2 = avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().get(position).isInstanciasInferiorPedra();
        boolean instancias3 = avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().get(position).isInstanciasSubstrato();
        String photoPath = avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().get(position).getPhotoPathEspecie();

        holder.imageButtonAddPhoto.setVisibility(View.GONE);

        holder.textViewNomeComum.setText(currentEspecie.getNomeComum());

        holder.checkBoxInstancias1.setChecked(instancias1);
        holder.checkBoxInstancias1.setEnabled(false);

        holder.checkBoxInstancias2.setChecked(instancias2);
        holder.checkBoxInstancias2.setEnabled(false);

        holder.checkBoxInstancias3.setChecked(instancias3);
        holder.checkBoxInstancias3.setEnabled(false);

        if (photoPath == null || photoPath.isEmpty()) {
            holder.imageViewPicture.setVisibility(View.GONE);
        } else {
            holder.imageViewPicture.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(new File(photoPath))
                    .placeholder(android.R.drawable.ic_media_play)
                    .into(holder.imageViewPicture);

            holder.imageViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageFullscreenFileActivity.class);
                    intent.putExtra(ImageFullscreenFileActivity.INTENT_EXTRA_KEY, photoPath);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return avistamentoTransepto.getEspeciesRiaFormosaTranseptosInstancias().size();
    }

    public void setAvistamentoTransepto(AvistamentoTranseptosRiaFormosaWithEspecieRiaFormosaTranseptosInstancias avistamentoTransepto) {
        this.avistamentoTransepto = avistamentoTransepto;
        notifyDataSetChanged();
    }

    class EspecieHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewNomeComum;
        private CheckBox checkBoxInstancias1;
        private CheckBox checkBoxInstancias2;
        private CheckBox checkBoxInstancias3;
        private ImageButton imageButtonAddPhoto;

        public EspecieHolder(View itemView) {
            super(itemView);

            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewNomeComum = itemView.findViewById(R.id.textView_nomecomum);
            checkBoxInstancias1 = itemView.findViewById(R.id.checkbox_instancias1);
            checkBoxInstancias2 = itemView.findViewById(R.id.checkbox_instancias2);
            checkBoxInstancias3 = itemView.findViewById(R.id.checkbox_instancias3);
            imageButtonAddPhoto = itemView.findViewById(R.id.button_add_photo);

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
