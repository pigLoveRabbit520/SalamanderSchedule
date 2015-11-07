package com.attraction.schedule.activity;

import java.sql.SQLException;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.attraction.schedule.R;
import com.attraction.schedule.db.DatabaseHelper;
import com.attraction.schedule.db.Lesson;
import com.attraction.schedule.tool.ParseUtil;
import com.attraction.schedule.view.Timetable;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	@Bind(R.id.tv_main_settings)
	TextView tvSettings;
	@Bind(R.id.timetable_main)
	Timetable timetable;
	private static int FETCH = 0;
	DatabaseHelper dbHelper = null;
	Dao<Lesson, Integer> lessonDao = null;
	List<Lesson> lessons = null;
	private static final int GET_LESSONS = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		dbHelper = DatabaseHelper.getInstance(this);
	}
	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					lessonDao = dbHelper.getLessonDao();
//					lessons = lessonDao.queryForAll();
//					handler.sendEmptyMessage(GET_LESSONS);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
	}

	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		 public void handleMessage(Message msg){
			 switch (msg.what) {
			case GET_LESSONS:
				timetable.addLessons(lessons);
				break;
			default:
				break;
			}
	     } 
	};
	
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
				lessons = util.parseLesson(html);
				timetable.addLessons(lessons);
				try {
					AndroidDatabaseConnection db = new AndroidDatabaseConnection(
							dbHelper.getSqLiteDatabase(), true);
					db.setAutoCommit(false);
					for (Lesson lesson : lessons) {
							lessonDao.create(lesson);
					}
					db.commit(null);
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}

	
}
