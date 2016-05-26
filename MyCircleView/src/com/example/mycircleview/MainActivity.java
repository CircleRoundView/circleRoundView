package com.example.mycircleview;

import java.util.ArrayList;
import java.util.List;

import com.example.mycircleview.MyAdapter.OnItemClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MyCircleView mCircleView;
	MyAdapter<String> m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCircleView = (MyCircleView) findViewById(R.id.myCircleView);
		// 在xml文件中就可以声明，不过需要在attr定义 和使用
		mCircleView.setRoundCircleCount(8);
		final List<String> list = new ArrayList<String>();// 数据源

		for (int i = 0; i < 3; i++) {
			list.add("item" + i);
		}

		/**
		 * 添加事件监听
		 */
		m = new MyAdapter<String>(this, list);
		// 添加 添加控件点击监听
		mCircleView.setOnAddViewClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() >= mCircleView.getDefRoundCircleCount()) {
					Toast.makeText(MainActivity.this, "mainActivity list 结合不能超过" + mCircleView.getDefRoundCircleCount(),
							Toast.LENGTH_SHORT).show();
					return;
				}

				list.add("xxxxxx");// 数据的集合 可以是beans 网上下载后的json处理的beans

				m.notifyDataSetChanged();
			}
		});
		/**
		 * item点击事件的监听
		 */
		m.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(MyCircleView view, View itemVIew, int position, int id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, position + "weizhi ", Toast.LENGTH_SHORT).show();

			}

			@Override
			public boolean onItemLongClick(MyCircleView view, View itemVIew, final int position, int id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new Builder(MainActivity.this);
				ab.setTitle("是否删除此成员");
				ab.setMessage("点击确定将会删除此成员，否则取消");
				ab.setNegativeButton("取消", null);
				ab.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						m.removeViewAtPostion(position);
						m.notifyDataSetChanged();
			

					}

				});

				ab.create().show();
				return true;
			}
		});

		mCircleView.setAdapter(m);

	}

}
