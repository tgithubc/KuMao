package com.tgithubc.kumao.widget.dragLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by tc:)
 */
public class FirstItemUtils {

    public static boolean isFirstItem(View view) {
        if (view == null) {
            return true;
        }
        if (view instanceof AbsListView) {
            return isFirstItemInAbsListView((AbsListView) view);
        } else if (view instanceof ScrollView) {
            return isFirstItemInScrollView((ScrollView) view);
        } else if (view instanceof RecyclerView) {
            return isFirstItemInRecyclerView((RecyclerView) view);
        } else {
            return true;
        }
    }

    private static boolean isFirstItemInAbsListView(AbsListView listView) {
        if (listView.getFirstVisiblePosition() == 0) {
            View child = listView.getChildAt(0);
            int top = (child == null) ? 0 : child.getTop();
            return top >= 0;
        } else {
            return false;
        }
    }

    private static boolean isFirstItemInScrollView(ScrollView scrollView) {
        int scrollY = scrollView.getScrollY();
        return scrollY <= 0;
    }

    private static boolean isFirstItemInRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return true;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            // 获取第一个完全显示的item position
            final int firstCompletelyVisibleItemPosition =
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstCompletelyVisibleItemPosition == 0
                    || linearLayoutManager.getItemCount() == 0) {
                return true;
            }
        }
        return false;
    }
}
