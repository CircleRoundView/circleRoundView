package com.example.mycircleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MyCircleView extends AbCircleView {
	private Context context;
	private int centerX = 0;
	private int centerY = 0;
	private int RadiusTotal = 0;// 小圆圆心所以在位置的半径大小
	private int rediusCircleRound;// 周围小圆的半径
	private double startDegrees; // 起始位置
	private double endDegrees; // 终止位置
	private double defDegress; // 每个间隔位置
	int distance = 30;
	int mMarginLeft;
	int mMarginTop;
	private OnClickListener mAddCircleClickListener;
	private ImageButton firstAddView;
	private MyAdapter mAdapter;
	private int defCircleRoundCount;

	public MyCircleView(Context context) {
		this(context, null);
	}

	public MyCircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		/* setBackgroundColor(Color.BLUE); */
		/*
		 * centerX = getResources().getDisplayMetrics().widthPixels/2;
		 * 
		 * centerY = getResources().getDisplayMetrics().heightPixels/2;
		 */
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myCircleView);

		int count = a.getIndexCount();
		for (int i = 0; i < count; i++) {
			int index = a.getIndex(i);
			switch (index) {
			case R.styleable.myCircleView_startDegree:
				startDegrees = a.getInteger(index, 270);
				break;
			case R.styleable.myCircleView_endDegree:
				endDegrees = a.getInteger(index, 0);
				break;
			case R.styleable.myCircleView_defDegree:
				defDegress = (int) a.getInteger(index, 30);
				break;
			case R.styleable.myCircleView_radiusTotal:
				RadiusTotal = (int) a.getDimension(index, 280);
				break;
			case R.styleable.myCircleView_radiusRound:
				rediusCircleRound = a.getDimensionPixelOffset(index, 50);
				break;
			case R.styleable.myCircleView_distance:
				distance = a.getDimensionPixelOffset(index, 30);
				break;
			case R.styleable.myCircleView_bgColor:
				setBackgroundColor(a.getColor(index, 0xFFFFFFFF));
				break;

			}
		}
		// 可以用上面的 attr 来设置 。后续优化时更改 临行性的
		defCircleRoundCount = 8;

		a.recycle();

		// RadiusTotal = 280;
		// rediusCircleRound = 60;
		startDegrees = MathUtils.valueOf(startDegrees);
		endDegrees = MathUtils.valueOf(endDegrees);
		defDegress = MathUtils.valueOf(defDegress);

		createAddCircleView(context);

	}

	/**
	 * 设置外圆的个数，不算添加圆，即第一个圆。
	 */
	public void setRoundCircleCount(int count) {
		defCircleRoundCount = count;
	}

	/**
	 * 第一个添加小圆的控件
	 * 
	 * @param context
	 */
	private void createAddCircleView(Context context) {
		firstAddView = new ImageButton(context);
		LayoutParams lp = new LayoutParams(2 * rediusCircleRound, 2 * rediusCircleRound);
		firstAddView.setLayoutParams(lp);
		firstAddView.setImageResource(R.drawable.ic_launcher);
		addView(firstAddView);
	}

	/**
	 * 给添加图标设置点击监听
	 * 
	 * @param addNewCircleClickListener
	 */
	public void setOnAddViewClickListener(OnClickListener addNewCircleClickListener) {
		if (addNewCircleClickListener != null) {
			firstAddView.setOnClickListener(addNewCircleClickListener);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthM = (2 * (RadiusTotal + rediusCircleRound)) + distance;
		int heightM = (2 * (RadiusTotal + rediusCircleRound)) + distance;
		setMeasuredDimension(widthM, heightM);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		// TODO Auto-generated method stub
		centerX = getMeasuredWidth() / 2;
		centerY = getMeasuredHeight() / 2;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			final double degrees = startDegrees - (i * defDegress);
			double rXLoc = RadiusTotal * Math.cos(degrees) + centerX - rediusCircleRound;
			double rYLoc = centerY - RadiusTotal * Math.sin(degrees) - rediusCircleRound;
			child.layout((int) rXLoc, (int) rYLoc, (int) (rXLoc + 2 * rediusCircleRound),
					(int) (rYLoc + 2 * rediusCircleRound));

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

	}

	/**
	 * 外部传来的数据 显示在小圆中的中间件
	 * 
	 * @param adapter
	 */
	@Override
	public void setAdapter(MyAdapter adapter) {
		adapter.setMainView(this);
		notifyDataChanged(adapter);
	}

	/**
	 * 返回默认外围圆个数
	 * @return
	 */
	public int getDefRoundCircleCount(){
		return defCircleRoundCount;
	}
	@Override
	public void notifyDataChanged(MyAdapter adapter) {

		if (mAdapter != null) {
			/**
			 * 超过默认外围圆的个数，就不更新，外部Adapt也要做一下List的限制
			 */
			if (mAdapter.getCount() > defCircleRoundCount) {
				return;
			}
			removeViews(1, getChildCount()-1);
		}
		this.mAdapter = adapter;
		addAdapterView();
		requestLayout();
	}

	/**
	 * 将适配器中的控件添加到当前控件中
	 */
	private void addAdapterView() {
		// TODO Auto-generated method stub
		for (int i = 0; i < mAdapter.getCount(); i++) {

			ImageButton imageView = (ImageButton) mAdapter.getView(i, null, this);
			LayoutParams lp = new LayoutParams(2 * rediusCircleRound, 2 * rediusCircleRound);
			imageView.setLayoutParams(lp);
			addView(imageView);
		}

	}

	/**
	 * 获取子元的个数
	 * 
	 * @return
	 */
	public int getItemCount() {
		return mAdapter.getCount();
	}

}