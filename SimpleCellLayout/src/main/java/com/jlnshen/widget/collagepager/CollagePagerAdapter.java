package com.jlnshen.widget.collagepager;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import com.jlnshen.widget.celllayout.R;
import com.jlnshen.widget.celllayout.SimpleCellLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class CollagePagerAdapter extends PagerAdapter {
    private static final int[] cellResIds = new int[]{
            R.id.simple_cell1,
            R.id.simple_cell2,
            R.id.simple_cell3,
            R.id.simple_cell4,
            R.id.simple_cell5
    };
    private static final int[][] containerRes = {
            {R.layout.cellcontainer_1_1},
            {R.layout.cellcontainer_2_1},
            {R.layout.cellcontainer_3_1, R.layout.cellcontainer_3_2,
                    R.layout.cellcontainer_3_3, R.layout.cellcontainer_3_4},
            {R.layout.cellcontainer_4_1, R.layout.cellcontainer_4_2,
                    R.layout.cellcontainer_4_3, R.layout.cellcontainer_4_4,
                    R.layout.cellcontainer_4_5, R.layout.cellcontainer_4_6,
                    R.layout.cellcontainer_4_7, R.layout.cellcontainer_4_8,
                    R.layout.cellcontainer_4_9, R.layout.cellcontainer_4_10},
            {R.layout.cellcontainer_5_1, R.layout.cellcontainer_5_2,
                    R.layout.cellcontainer_5_3, R.layout.cellcontainer_5_4,
                    R.layout.cellcontainer_5_5, R.layout.cellcontainer_5_6,
                    R.layout.cellcontainer_5_7, R.layout.cellcontainer_5_8,
                    R.layout.cellcontainer_5_9, R.layout.cellcontainer_5_10,
                    R.layout.cellcontainer_5_11, R.layout.cellcontainer_5_12,
                    R.layout.cellcontainer_5_13, R.layout.cellcontainer_5_14}
    };
    SparseArray<ArrayList<SimpleCellLayout>> mCellContainerRecyler = new SparseArray<ArrayList<SimpleCellLayout>>();
    SparseArray<ArrayList<View>> mItemViewRecycler = new SparseArray<ArrayList<View>>();
    private ListAdapter mListAdapter = null;
    private ArrayList<PageInfo> pages = new ArrayList<PageInfo>();
    private int mGap;
    private ListAdapterObserver mObserver = new ListAdapterObserver();

    CollagePagerAdapter(ListAdapter listAdapter, int gap) {
        super();
        mGap = gap;
        mListAdapter = listAdapter;
        mListAdapter.registerDataSetObserver(mObserver);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        PageInfo page = (PageInfo) o;

        return page.container == view;
    }

    void setListAdapter(ListAdapter listAdapter) {
        mListAdapter = listAdapter;
        mListAdapter.registerDataSetObserver(mObserver);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        preparePages();
        super.notifyDataSetChanged();
    }

    void preparePages() {
        int itemCnt = mListAdapter.getCount();
        if (itemCnt == 0) {
            pages.clear();
            return;
        }

        if (pages.size() == 0) {
            ArrayList<PageInfo> newPages = makePages(itemCnt);

            pages = newPages;
        } else { //Need tests
            PageInfo lastPage = pages.get(pages.size() - 1);
            int oldItemCnt = lastPage.cellCnt + lastPage.firstIndex;

            while (oldItemCnt > itemCnt) {
                pages.remove(lastPage);

                if (pages.size() == 0) {
                    break;
                }

                lastPage = pages.get(pages.size() - 1);
                oldItemCnt = lastPage.cellCnt + lastPage.firstIndex;
            }

            int diff = itemCnt - oldItemCnt;
            ArrayList<PageInfo> tmpPages = makePages(diff + lastPage.cellCnt);

            int lastPageNo = lastPage.page_no;
            int idx = lastPage.firstIndex;
            for (PageInfo page : tmpPages) {
                page.page_no += lastPageNo;
                page.firstIndex += idx;
            }
            pages.remove(lastPage);
            pages.addAll(tmpPages);

        }

    }

    private ArrayList<PageInfo> makePages(int itemCnt) {
        int[] pageCellCnts = new int[5];

        int seed = itemCnt / 12;
        int remaining = itemCnt % 12;

        //Makes pager fill with most type 3 (cell count), 4, 5 cell layouts.
        pageCellCnts[2] = pageCellCnts[3] = pageCellCnts[4] = seed;

        if (remaining > 0 && remaining < 3) {
            if (pageCellCnts[2] == 0) {
                pageCellCnts[remaining - 1]++;
            } else {
                pageCellCnts[2]--;
                pageCellCnts[2 + remaining]++;
            }
        } else {
            switch (remaining) {
                case 3:
                case 4:
                case 5:
                    pageCellCnts[remaining - 1]++;
                    break;
                case 6:
                case 8:
                case 10:
                    pageCellCnts[remaining / 2 - 1] += 2;
                    break;
                case 7:
                case 9:
                    pageCellCnts[remaining / 2 - 1]++;
                    pageCellCnts[remaining / 2]++;
                    break;
                case 11:
                    pageCellCnts[2]++;
                    pageCellCnts[3] += 2;
                    break;
            }
        }

        ArrayList<PageInfo> tmpPages = new ArrayList<PageInfo>();
        for (int i = 0; i < pageCellCnts.length; i++) {
            Random random = new Random(System.currentTimeMillis());
            int numResources = containerRes[i].length;
            int resIndex = Math.abs(random.nextInt()) % numResources;
            for (int j = 0; j < pageCellCnts[i]; j++) {
                PageInfo page = new PageInfo();
                page.cellCnt = i + 1;

                int resId = containerRes[i][resIndex];
                page.res_id = resId;
                resIndex = (resIndex + 1) % numResources;

                tmpPages.add(page);
            }
        }

        Collections.shuffle(tmpPages, new Random(System.currentTimeMillis()));

        int indexCnt = 0;
        for (int i = 0; i < tmpPages.size(); i++) {
            PageInfo page = tmpPages.get(i);
            page.page_no = i;
            page.firstIndex = indexCnt;

            indexCnt += page.cellCnt;
        }
        return tmpPages;
    }

    @Override
    public int getItemPosition(Object object) {
        PageInfo pageInfo = (PageInfo) object;

        if (pageInfo.page_no >= pages.size()) {
            return POSITION_NONE;
        }

        PageInfo newPageInfo = pages.get(pageInfo.page_no);

        if (pageInfo != newPageInfo) {
            return POSITION_NONE;
        }

        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (pages.size() < position) {
            return null;
        }

        PageInfo page = pages.get(position);

        SimpleCellLayout cellContainer = null;
        FrameLayout[] cells = null;
        if (page.container != null) {
            return page;
        }

        ArrayList<SimpleCellLayout> cache = mCellContainerRecyler.get(page.res_id);
        if (cache != null && cache.size() > 0) {
            cellContainer = cache.remove(0);
            cells = (FrameLayout[]) cellContainer.getTag();
            page.container = cellContainer;
            page.cells = cells;
        } else {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());

            cellContainer = (SimpleCellLayout) inflater.inflate(page.res_id, null);
            cells = new FrameLayout[page.cellCnt];

            for (int i = 0; i < cells.length; i++) {
                cells[i] = (FrameLayout) cellContainer.findViewById(cellResIds[i]);
            }

            page.container = cellContainer;
            page.cells = cells;
        }

        cellContainer.setGap(mGap);
        cellContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(cellContainer);

        for (int i = 0; i < page.cellCnt; i++) {
            int cellIdx = page.firstIndex + i;
            int viewType = mListAdapter.getItemViewType(cellIdx);

            ArrayList<View> viewCache = mItemViewRecycler.get(viewType);

            View convertView = null;
            if (viewCache != null && viewCache.size() > 0) {
                convertView = viewCache.remove(0);
            }

            View itemView = mListAdapter.getView(cellIdx, convertView, cells[i]);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cells[i].addView(itemView);
            cells[i].setTag(itemView);
        }

        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        PageInfo page = (PageInfo) object;

        container.removeView(page.container);

        for (int i = 0; i < page.cellCnt; i++) {
            View childView = (View) page.cells[i].getTag();

            if (childView != null) {
                page.cells[i].removeView(childView);

                int cellIdx = page.firstIndex + i;
                int viewType = mListAdapter.getItemViewType(cellIdx);

                ArrayList<View> viewCache = mItemViewRecycler.get(viewType);

                if (viewCache == null) {
                    viewCache = new ArrayList<View>();
                    mItemViewRecycler.put(viewType, viewCache);
                }

                viewCache.add(childView);
            }
        }

        ArrayList<SimpleCellLayout> cache = mCellContainerRecyler.get(page.res_id);
        if (cache == null) {
            cache = new ArrayList<SimpleCellLayout>();
            mCellContainerRecyler.put(page.res_id, cache);
        }

        page.container.setTag(page.cells);
        cache.add(page.container);

        page.container = null;
        page.cells = null;
    }

    static class PageInfo {
        int page_no = 0;
        int res_id = 0;
        int cellCnt = 0;
        int firstIndex = 0;
        SimpleCellLayout container = null;
        FrameLayout[] cells = null;
    }

    private class ListAdapterObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            notifyDataSetChanged();
        }
    }
}
