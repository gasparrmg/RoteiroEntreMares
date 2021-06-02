package com.lasige.roteiroentremares.util;

import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ImageUtils;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import at.lukle.clickableareasimage.PixelPosition;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class ClickableAreasImageRefactored implements View.OnTouchListener {
    private int CLICK_ACTION_THRESHOLD = 200;
    private PhotoViewAttacher attacher;
    private ImageView imageView;
    private OnClickableAreaClickedListener listener;
    private List<ClickableArea> clickableAreas;
    private int imageWidthInPx;
    private int imageHeightInPx;

    private float startX;
    private float startY;

    public ClickableAreasImageRefactored(ImageView imageView, OnClickableAreaClickedListener listener) {
        this.imageView = imageView;

        this.attacher = new PhotoViewAttacher(imageView);

        this.init(listener);
    }

    private void init(OnClickableAreaClickedListener listener) {
        this.listener = listener;
        this.getImageDimensions(imageView);

        imageView.setOnTouchListener(this);
    }

    private void getImageDimensions(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.imageWidthInPx = drawable.getBitmap().getWidth();
            this.imageHeightInPx = drawable.getBitmap().getHeight();
        } else {
            this.imageWidthInPx = (int) ((float) drawable.getBitmap().getWidth() / Resources.getSystem().getDisplayMetrics().density);
            this.imageHeightInPx = (int) ((float) drawable.getBitmap().getHeight() / Resources.getSystem().getDisplayMetrics().density);
        }
    }

    /**
     * @param percentX
     * @param percentY
     * @param absW     -> LARGURA IMAGEM RESOURCE
     * @param absH     -> ALTURA IMAGEM RESOURCE
     * @return
     */
    private PixelPosition getPixelPosition(float percentX, float percentY, int absW, int absH) {
        int absX = Math.round((float) absW * percentX);
        int absY = Math.round((float) absH * percentY);
        return new PixelPosition(absX, absY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.d("ClickableAreasImageRefactored", "ACTION_DOWN -> " + startX + ", " + startY);
                return true;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();
                Log.d("ClickableAreasImageRefactored", "ACTION_UP -> " + endX + ", " + endY);
                if (isAClick(startX, endX, startY, endY)) {
                    // WE HAVE A CLICK!!

                    final RectF displayRect = attacher.getDisplayRect();

                    if (null != displayRect) {
                        // Check to see if the user tapped on the photo
                        if (displayRect.contains(endX, endY)) {
                            float xResult = (endX - displayRect.left)
                                    / displayRect.width();
                            float yResult = (endY - displayRect.top)
                                    / displayRect.height();

                            Log.d("ClickableAreasImageRefactored", "check pixel in -> " + this.imageWidthInPx + ", " + this.imageHeightInPx);
                            PixelPosition pixel = ImageUtils.getPixelPosition(xResult, yResult, this.imageWidthInPx, this.imageHeightInPx);
                            // PixelPosition pixel = getPixelPosition(endX, endY, this.imageWidthInPx, this.imageHeightInPx);
                            Log.d("ClickableAreasImageRefactored", "pixel clicked -> " + pixel.getX() + ", " + pixel.getY());
                            List<ClickableArea> clickableAreas = this.getClickAbleAreas(pixel.getX(), pixel.getY());
                            Iterator i$ = clickableAreas.iterator();

                            while (i$.hasNext()) {
                                ClickableArea ca = (ClickableArea) i$.next();
                                this.listener.onClickableAreaTouched(ca.getItem());
                            }
                        }
                    }
                }
                break;
        }


        return false;
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD);
    }

    /*public void onPhotoTap(View view, float x, float y) {
        PixelPosition pixel = ImageUtils.getPixelPosition(x, y, this.imageWidthInPx, this.imageHeightInPx);
        List<ClickableArea> clickableAreas = this.getClickAbleAreas(pixel.getX(), pixel.getY());
        Iterator i$ = clickableAreas.iterator();

        while(i$.hasNext()) {
            ClickableArea ca = (ClickableArea)i$.next();
            this.listener.onClickableAreaTouched(ca.getItem());
        }
    }*/

    private List<ClickableArea> getClickAbleAreas(int x, int y) {
        List<ClickableArea> clickableAreas = new ArrayList();
        Iterator i$ = this.getClickableAreas().iterator();

        while (i$.hasNext()) {
            ClickableArea ca = (ClickableArea) i$.next();
            Log.d("ClickableAreasImageRefactored", "isBetween -> " + ca.getX() + ", " + ca.getW() + " for " + x);
            if (this.isBetween(ca.getX(), ca.getX() + ca.getW(), x) && this.isBetween(ca.getY(), ca.getY() + ca.getH(), y)) {
                Log.d("ClickableAreasImageRefactored", "isBetween == true");
                clickableAreas.add(ca);
            }
        }

        return clickableAreas;
    }

    private boolean isBetween(int start, int end, int actual) {
        return start <= actual && actual <= end;
    }

    public void setClickableAreas(List<ClickableArea> clickableAreas) {
        this.clickableAreas = clickableAreas;
    }

    public List<ClickableArea> getClickableAreas() {
        return this.clickableAreas;
    }
}