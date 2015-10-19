package com.attraction.schedule.activity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.attraction.schedule.R;
import com.attraction.schedule.db.Lesson;
import com.attraction.schedule.tool.ParseUtil;
import com.attraction.schedule.view.Timetable;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	@Bind(R.id.tv_main_settings)
	TextView tvSettings;
	@Bind(R.id.timetable_main)
	Timetable timetable;
	private static int FETCH = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}
	
	@OnClick(R.id.tv_main_settings)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_main_settings:
			Intent intent = new Intent(this, ImportActivity.class);
			startActivityForResult(intent, FETCH);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FETCH) {
			if(resultCode == ImportActivity.FETCH_SUCCESS) {
				String html = data.getStringExtra("html");
				ParseUtil util = new ParseUtil();
				List<Lesson> lessons = util.parseLesson(html);
				timetable.addLessons(lessons);
			}
		}
	}

	
}
