package com.lasige.roteiroentremares.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.roteiroentremares.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageUtils {
    public static void createImageDialog(Context context, int icon) {
        Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        settingsDialog.setContentView(R.layout.image_layout);
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setCanceledOnTouchOutside(true);

        Button btnDismiss = settingsDialog.findViewById(R.id.btn_dismiss);

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

        PhotoView photoView = (PhotoView) settingsDialog.findViewById(R.id.photo_view);
        photoView.setImageResource(icon);
        settingsDialog.show();
    }

    public static void createPhotoDialog(Context context, Uri uri) {
        Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        settingsDialog.setContentView(R.layout.image_layout);
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setCanceledOnTouchOutside(true);

        Button btnDismiss = settingsDialog.findViewById(R.id.btn_dismiss);

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

        PhotoView photoView = (PhotoView) settingsDialog.findViewById(R.id.photo_view);
        photoView.setImageURI(uri);
        settingsDialog.show();
    }
}
