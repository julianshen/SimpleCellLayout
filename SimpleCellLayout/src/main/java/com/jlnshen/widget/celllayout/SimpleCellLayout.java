package com.jlnshen.widget.celllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class SimpleCellLayout extends ViewGroup {
    private int mColCount = 4;
    private int mRowCount = 4;
    private int mGap = 0;


    public SimpleCellLayout(Context context) {
        super(context);
    }

    public SimpleCellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleCellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setColumnCount(int col) {
        if (col <= 0) {
            throw new RuntimeException("You must have one or more column");
        }

        mColCount = col;
    }

    public void setRowCount(int row) {
        if (row <= 0) {
            throw new RuntimeException("You must have one or more row");
        }

        mRowCount = row;
    }

    public void setGap(int gap) {
        if (gap < 0) {
            return;
        }

        mGap = gap;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CellLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMeasureSpecMode == MeasureSpec.UNSPECIFIED || heightMeasureSpecMode == MeasureSpec.UNSPECIFIED) {
            throw new RuntimeException("Cannot have UNSPECIFIED dimension");
        }

        int usableWidth = widthMeasureSpecSize - getPaddingLeft() - getPaddingRight() - mGap * (mColCount - 1);
        int usableHeight = heightMeasureSpecSize - getPaddingTop() - getPaddingBottom() - mGap * (mRowCount - 1);

        int cellWidth = usableWidth / mColCount;
        int cellHeight = usableHeight / mRowCount;

        //measure children
        int childrCnt = getChildCount();
        for (int i = 0; i < childrCnt; i++) {
            View child = getChildAt(i);
            CellLayoutParams params = (CellLayoutParams) child.getLayoutParams();

            if (params.cellColSpan < 1) {
                params.cellColSpan = 1;
            }

            if (params.cellRowSpan < 1) {
                params.cellRowSpan = 1;
            }

            int childWidth = cellWidth * params.cellColSpan;
            int childHeight = cellHeight * params.cellRowSpan;

            if (params.cellColSpan > 1) {
                childWidth += (params.cellColSpan - 1) * mGap;
            }

            if (params.cellRowSpan > 1) {
                childHeight += (params.cellRowSpan - 1) * mGap;
            }

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            int x = params.cellX * cellWidth + params.cellX * mGap + params.leftMargin;
            int y = params.cellY * cellHeight + params.cellY * mGap + params.topMargin;
            params.saveMeasureResult(x, y, childWidth, childHeight);
        }

        setMeasuredDimension(widthMeasureSpecSize, heightMeasureSpecSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrCnt = getChildCount();

        for (int i = 0; i < childrCnt; i++) {
            View child = getChildAt(i);
            CellLayoutParams params = (CellLayoutParams) child.getLayoutParams();

            child.layout(params.x, params.y, params.x + params.width, params.y + params.height);
        }
    }

    public static class CellLayoutParams extends MarginLayoutParams {
        public int cellX = 0;
        public int cellY = 0;
        public int cellRowSpan = 1;
        public int cellColSpan = 1;
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        public CellLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CellLayoutParams(int width, int height) {
            super(width, height);
        }

        public CellLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CellLayoutParams(LayoutParams source) {
            super(source);
        }

        public void saveMeasureResult(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
