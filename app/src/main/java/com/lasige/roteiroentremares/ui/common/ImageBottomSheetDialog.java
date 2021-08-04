package com.lasige.roteiroentremares.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.roteiroentremares.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageBottomSheetDialog extends BottomSheetDialogFragment {

    public static final String ACTION_IMAGE_FULLSCREEN = "action_image_fullscreen";
    public static final String ACTION_NEW_IMAGE = "action_new_image";

    private BottomSheetListener mListener;

    private LinearLayout linearLayoutImageFullscreen;
    private LinearLayout linearLayoutNewImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_bottom_sheet_dialog, container, false);

        linearLayoutImageFullscreen = v.findViewById(R.id.linearLayout_image_fullscreen);
        linearLayoutNewImage = v.findViewById(R.id.linearLayout_new_image);

        linearLayoutImageFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(ACTION_IMAGE_FULLSCREEN);
                dismiss();
            }
        });

        linearLayoutNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(ACTION_NEW_IMAGE);
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String action);
    }

    public void setButtonClickListener(BottomSheetListener bottomSheetListener) {
        mListener = bottomSheetListener;
    }

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }*/
}
