package com.jlnshen.widget.celllayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jlnshen.widget.celllayout.R;


public class SimpleCellLayout extends ViewGroup {
    private int mColCount = 4;
    private int mRowCount = 4;
    private int mGap = 0;


    public SimpleCellLayout(Context context) {
        super(context);
    }

    public SimpleCellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initFromAttributes(context, attrs);
    }

    public SimpleCellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initFromAttributes(context, attrs);
    }

    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleCellLayout);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.SimpleCellLayout_col:
                    mColCount = a.getInt(attr, mColCount);
                    break;
                case R.styleable.SimpleCellLayout_row:
                    mRowCount = a.getInt(attr, mRowCount);
                    break;
                case R.styleable.SimpleCellLayout_gapsize:
                    mGap = a.getDimensionPixelSize(attr, mGap);
                    break;
            }
        }

        a.recycle();
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
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CellLayoutParams(getContext(), attrs);
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

        int remainingWidth = usableWidth % mColCount;
        int remainingHeight = usableHeight % mRowCount;

        CellInfo[][] cells = new CellInfo[mRowCount][mColCount];

        for (int i = 0; i < mRowCount; i++) {
            for (int j = 0; j < mColCount; j++) {
                CellInfo cell = new CellInfo();
                cell.left = j * (cellWidth + mGap) + (j < remainingWidth ? j : remainingWidth);
                cell.top = i * (cellHeight + mGap) + (i < remainingHeight ? i : remainingHeight);
                cell.right = cell.left + cellWidth + (j < remainingWidth ? 1 : 0);
                cell.bottom = cell.top + cellHeight + (i < remainingHeight ? 1 : 0);
                cells[i][j] = cell;
            }
        }

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

            int left = cells[params.cellY][params.cellX].left;
            int top = cells[params.cellY][params.cellX].top;

            int right = cells[params.cellY + params.cellRowSpan - 1][params.cellX + params.cellColSpan - 1].right;
            int bottom = cells[params.cellY + params.cellRowSpan - 1][params.cellX + params.cellColSpan - 1].bottom;

            int childWidth = right - left + 1;
            int childHeight = bottom - top + 1;

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            params.saveMeasureResult(left, top, childWidth, childHeight);
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

            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.SimpleCellLayout);
            for (int i = 0; i < a.getIndexCount(); i++) {
                int attr = a.getIndex(i);

                switch (attr) {
                    case R.styleable.SimpleCellLayout_colspan:
                        cellColSpan = a.getInt(attr, cellColSpan);
                        if (cellColSpan < 1) {
                            cellColSpan = 1;
                        }
                        break;
                    case R.styleable.SimpleCellLayout_rowspan:
                        cellRowSpan = a.getInt(attr, cellRowSpan);
                        if (cellRowSpan < 1) {
                            cellRowSpan = 1;
                        }
                        break;
                    case R.styleable.SimpleCellLayout_cellX:
                        cellX = a.getInt(attr, cellX);
                        if (cellX < 0) {
                            cellX = 0;
                        }
                        break;
                    case R.styleable.SimpleCellLayout_cellY:
                        cellY = a.getInt(attr, cellY);
                        if (cellY < 0) {
                            cellY = 0;
                        }
                        break;
                }
            }

            a.recycle();
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

    public static class CellInfo {
        int left;
        int top;
        int right;
        int bottom;
    }
}
