package com.devanshi.tambola.coinpicker.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpanningLinearLayoutManager extends LinearLayoutManager {

    private boolean setScrollHorizontally = false;
    private int itemCount;
    public SpanningLinearLayoutManager(Context context, int minAdapterItem) {
        super(context);
        this.itemCount = minAdapterItem;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return spanLayoutSize(super.generateDefaultLayoutParams());
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return spanLayoutSize(super.generateLayoutParams(c, attrs));
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return spanLayoutSize(super.generateLayoutParams(lp));
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    private RecyclerView.LayoutParams spanLayoutSize(RecyclerView.LayoutParams layoutParams){
        if(getOrientation() == HORIZONTAL){
            layoutParams.width = (int) Math.round(getHorizontalSpace() / (double) getMaxItemCount());
        }
        else if(getOrientation() == VERTICAL){
            layoutParams.height = (int) Math.round(getVerticalSpace() /  (double) getMaxItemCount());
        }
        return layoutParams;
    }

    private int getMaxItemCount() {
        return Math.min(this.getItemCount(), this.itemCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
    @Override
    public boolean canScrollHorizontally() {
        return setScrollHorizontally;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    public void setScrollHorizontally(boolean setScrollHorizontally) {
        this.setScrollHorizontally = setScrollHorizontally;
    }

    public void setMaxItemsToShowInScreen(int itemCount) {
        this.itemCount = itemCount;
    }
}
