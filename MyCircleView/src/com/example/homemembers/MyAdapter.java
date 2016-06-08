package com.femote.homeui.ui.homemembers;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.femote.homeui.R;

public class MyAdapter<T> extends BaseAdapter {

    private List<T> t;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnRoundCircleClickListener onClickListener = new OnRoundCircleClickListener();
    private OnLongClickListener onLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            boolean cosume = false;

            if (mOnItemClickListener != null) {
                if (mMainView == null) {
                    throw new IllegalStateException("没有绑定到AbCircleView类");
                }
                cosume = mOnItemClickListener.onItemLongClick(mMainView, v, (Integer) v.getTag(), (Integer) v.getTag());
            }

            return cosume;
        }
    };

    class OnRoundCircleClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated methodstub
            if (mOnItemClickListener != null) {
                if (mMainView == null) {
                    throw new IllegalStateException("没有绑定到AbCircleView类");
                }
                mOnItemClickListener.onItemClick(mMainView, v, (Integer) v.getTag(), (Integer) v.getTag());
            }
        }
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, List<T> t) {
        this.context = context;
        this.t = t;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return t.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return t.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ImageButton button = new ImageButton(context);
        button.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams lp = new LayoutParams(2 * mMainView.getRediusCircleRound(), 2 * mMainView.getRediusCircleRound());
        button.setLayoutParams(lp);
        button.setTag(position);
        button.setPadding(0, 0, 0, 0);

        button.setOnClickListener(onClickListener
        /*
		 * 只需要将 View 和相应的 位置position传出去即可， 问题：如何解决动态创建的View的下标绑定问题。 1.
		 * 这个方法需要每次都要给创建的View新建点击监听， 每一个view需要一个新的监听 new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (mOnItemClickListener != null) { if (mMainView == null) {
		 * throw new IllegalStateException( "没有绑定到AbCircleView类"); }
		 * 
		 * mOnItemClickListener.onItemClick( mMainView,
		 * mMainView.getChildAt(position + 1),position, position);
		 * 
		 * } }
		 * 
		 * }、
		 * 
		 * 2. 通过创建View时，给View设置tag的方法，实现position绑定。 点击时，通过点击的View就可以找到View所处的位置
		 */

        );

        button.setOnLongClickListener(onLongClickListener);
        button.setBackgroundResource(R.drawable.center_big_circle);
      //   button.setBackgroundColor(Color.TRANSPARENT);

       // button.setImageResource(R.drawable.center_big_circle);

        return button;
    }

    // List<DataImageButton> 图片
    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        if (mMainView != null) {
            mMainView.notifyDataChanged(this);
        }

    }

    private MyCircleView mMainView;

    /**
     * 设置绑定MyCircleView
     *
     * @param view
     */
    public void setMainView(MyCircleView view) {
        this.mMainView = view;
    }

    /**
     * 移除指定位置的数据/View
     *
     * @param position
     */
    public void removeViewAtPostion(int position) {
        if (position > -1 && position < t.size()) {
            t.remove(position);
        }
        return;

    }


    /**
     * item点击监听接口
     *
     * @author xiangxiang
     */
    public interface OnItemClickListener {
        /**
         * @param view     MyCircleView类
         * @param itemVIew ImageButton主要
         * @param position 点击的第几个控件
         * @param id
         */
        void onItemClick(MyCircleView view, View itemVIew, int position, int id);

        boolean onItemLongClick(MyCircleView view, View itemVIew, int position, int id);
    }

}
