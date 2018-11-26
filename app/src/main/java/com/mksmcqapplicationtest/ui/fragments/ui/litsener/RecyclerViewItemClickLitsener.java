package com.mksmcqapplicationtest.ui.fragments.ui.litsener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewItemClickLitsener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    GestureDetector gestureDetector;
    public RecyclerViewItemClickLitsener(Context context,OnItemClickListener listener){
        onItemClickListener = listener;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView =rv.findChildViewUnder(e.getX(),e.getY());
        if(childView !=null && onItemClickListener!= null &&gestureDetector.onTouchEvent(e)){
            onItemClickListener.onItemClick(childView,rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
