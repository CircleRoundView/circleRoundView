package com.example.mycircleview;

import java.util.ArrayList;
import java.util.List;

import com.example.mycircleview.MyAdapter.OnItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MyCircleView mCircleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCircleView = (MyCircleView) findViewById(R.id.myCircleView);
		final List<String> list = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			list.add("item" + i);
		}

		/**
		 * 添加事件监听
		 */
		final MyAdapter<String> m = new MyAdapter<String>(this, list);
		// 添加 添加控件点击监听
		mCircleView.setOnAddViewClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.add("xxxxxx");
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
		});
		mCircleView.setAdapter(m);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
