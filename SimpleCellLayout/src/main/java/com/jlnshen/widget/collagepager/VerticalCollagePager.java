package com.jlnshen.widget.collagepager;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.widget.ListAdapter;

import com.jlnshen.widget.celllayout.R;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class VerticalCollagePager extends VerticalViewPager {
    private int mGap = 0;

    public VerticalCollagePager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleCellLayout);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.SimpleCellLayout_gapsize:
                    mGap = a.getDimensionPixelSize(attr, mGap);
                    break;
            }
        }

        a.recycle();
    }

    public VerticalCollagePager(Context context) {
        super(context);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        throw new RuntimeException(
                "Please use ListAdapter instead of setting your own PagerAdapter");
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(new CollagePagerAdapter(adapter, mGap));
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if(getAdapter() == null) {
            return false;
        }

        if(direction < 0) {
            return getCurrentItem() != 0;
        } else {
            return getCurrentItem() < getAdapter().getCount();
        }
    }
}
