package com.attraction.schedule.fragment;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.attraction.schedule.R;
import com.attraction.schedule.activity.ImportActivity;
import com.attraction.schedule.db.Lesson;
import com.attraction.schedule.db.LessonDao;
import com.attraction.schedule.helper.ParseHelper;
import com.attraction.schedule.view.OnComponentAddedCompletedListener;
import com.attraction.schedule.view.Timetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
/**
 * 这个类本来是课程表界面，但由于项目不做了（只做了模拟登录部分）
 * 主界面本来有4个tab
 */

public class MainActivity extends Activity implements OnComponentAddedCompletedListener {
	@Bind(R.id.tv_main_settings)
	TextView tvSettings;
    @Bind(R.id.ll_main_root)
    LinearLayout rootView;
	private Timetable timetable;
	private static int FETCH = 3;
	LessonDao lessonDao = null;
	List<Lesson> lessons = null;
	private static final int GET_LESSONS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        ButterKnife.bind(this);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        timetable = new Timetable(this, this);
        timetable.setLayoutParams(params);
        rootView.addView(timetable);
	}

	
	@Override
	public void complete() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lessonDao = new LessonDao(MainActivity.this);
					lessons = lessonDao.queryForAll();
					handler.sendEmptyMessage(GET_LESSONS);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
			Intent intent = new Intent(MainActivity.this, ImportActivity.class);
			startActivityForResult(intent, FETCH);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FETCH) {
			if(resultCode == ImportActivity.FETCH_SUCCESS) {
				String html = data.getStringExtra("html");
				ParseHelper util = new ParseHelper();
				lessons = util.parseLesson(html);
				timetable.addLessons(lessons);
				try {
					lessonDao.addLessons(lessons);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
