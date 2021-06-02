package com.lasige.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.roteiroentremares.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderDescriptionAdapter extends SliderViewAdapter<SliderDescriptionAdapter.Holder> {

    private Context context;
    private int[] images;
    private String[] descriptions;

    public SliderDescriptionAdapter(Context context, int[] images, String[] descriptions) {
        this.context = context;
        this.images = images;
        this.descriptions = descriptions;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_description_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.textViewDescription.setText(descriptions[position]);

        Glide.with(context)
                .load(images[position])
                .placeholder(android.R.drawable.ic_media_play)
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        private ImageView imageView;
        private TextView textViewDescription;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview_sliderview);
            textViewDescription = itemView.findViewById(R.id.textView_sliderview_desc);
        }
    }
}
