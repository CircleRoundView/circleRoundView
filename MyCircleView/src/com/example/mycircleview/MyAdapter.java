package com.example.mycircleview;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MyAdapter<T> extends BaseAdapter {

	private List<T> t;
	private Context context;
	private OnItemClickListener mOnItemClickListener;
	// private OnRoundCircleClickListener onClickListener = new
	// OnRoundCircleClickListener();

	/*
	 * class OnRoundCircleClickListener implements OnClickListener {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (mOnItemClickListener != null) { if (mMainView == null) { throw
	 * new IllegalStateException("没有绑定到AbCircleView类"); }
	 * 
	 * mOnItemClickListener.onItemClick(mMainView, mMainView.getChildAt(position
	 * + 1), position, position); } } };
	 */

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

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnItemClickListener != null) {
					if (mMainView == null) {
						throw new IllegalStateException("没有绑定到AbCircleView类");
					}

					mOnItemClickListener.onItemClick(mMainView, mMainView.getChildAt(position + 1), position, position);

				}
			}
		});

		button.setImageResource(R.drawable.ic_launcher);

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

	public void setMainView(MyCircleView view) {
		this.mMainView = view;
	}

	public interface OnItemClickListener {
		/**
		 * 
		 * @param view
		 *            MyCircleView类
		 * @param itemVIew
		 *            ImageButton主要
		 * @param position
		 *            点击的第几个控件
		 * @param id
		 */
		void onItemClick(MyCircleView view, View itemVIew, int position, int id);
	}

}
