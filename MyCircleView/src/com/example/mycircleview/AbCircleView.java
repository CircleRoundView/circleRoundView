package com.example.mycircleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 周围分布圆形控件抽象父类
 * 
 * @author xiangxiang
 *
 */
public abstract class AbCircleView extends ViewGroup {

	public AbCircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AbCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public AbCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public abstract void notifyDataChanged(MyAdapter adapter);

	public abstract void setAdapter(MyAdapter adapter);

}
