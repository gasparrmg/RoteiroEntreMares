package com.android.roteiroentremares.ui.dashboard.adapters.guiadecampo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.roteiroentremares.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    private Context context;
    private int[] images;

    public SliderAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
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

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview_sliderview);
        }
    }
}
