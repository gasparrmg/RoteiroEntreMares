package com.lasige.roteiroentremares.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;

/**
 * Style a Spannable with a custom {@link Typeface}.
 *
 * @author Tristan Waddington
 */
public class TypefaceSpan extends MetricAffectingSpan {
    /** An <code>LruCache</code> for previously loaded typefaces. */
    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);

    private Typeface mTypeface;

    /**
     * Load the {@link Typeface} and apply to a Spannable.
     */
    public TypefaceSpan(Context context, String typefaceName, int resource) {
        mTypeface = sTypefaceCache.get(typefaceName);

        if (mTypeface == null) {
            /*mTypeface = Typeface.createFromAsset(context.getApplicationContext()
                    .getAssets(), String.format("font/%s", typefaceName));*/

            mTypeface = ResourcesCompat.getFont(context, resource);

            // Cache the loaded Typeface
            sTypefaceCache.put(typefaceName, mTypeface);
        }
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}
