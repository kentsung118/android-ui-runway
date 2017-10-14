package com.android.ui.kent.demo.recyclerview.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.ui.kent.common.NoDoubleClickUtils;

/**
 * Created by Kent on 2017/10/14.
 */

public class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private LoadMoreData loadMoreData;

    public RecyclerOnScrollListener(LinearLayoutManager layoutManager, LoadMoreData loadMoreData) {
        this.layoutManager = layoutManager;
        this.loadMoreData = loadMoreData;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();
        //dy>0表示向上滑动
        //lastVisibleItem >= totalItemCount - 2表示剩下两个
        if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {

            //加上NoDoubleClick防止多重觸發
            if(!NoDoubleClickUtils.isDoubleClick(500)){
                loadMoreData.loadMore();
            }
        }
    }

    public interface LoadMoreData {
        void loadMore();
    }
}