package com.jlnshen.widget.collagepager;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomFlyPageTransformer implements ViewPager.PageTransformer {

    private static final float ZOOM_FACTOR = 0.9f;

    @Override
    public void transformPage(View view, float pos) {
        if (pos <= 1 && pos > -1) {
            float scale = Math.max(1 - Math.abs(pos), ZOOM_FACTOR);
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setAlpha(1 - Math.abs(pos));
        }
    }
}
