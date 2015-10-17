package com.attraction.schedule.view;

import java.util.Random;
import com.attraction.schedule.R;
import com.attraction.schedule.adapter.Lesson;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;


public class LessonView extends TextView {
	// 课程名称
	private String name = null;
	// 课程开始节课
	private int classStart;
	// 课程结束节课
	private int classEnd;
	// 上课教室
	private String classRoom;
	// 课程老师
	private String teacher;
	// 开始周
	private int weekStart;
	// 结束周
	private int weekEnd;
	// 星期几的课
	private int day;
	private int[] bgs = new int[] {
			R.drawable.lesson_view_bg1,
			R.drawable.lesson_view_bg2,
			R.drawable.lesson_view_bg3,
			R.drawable.lesson_view_bg4
	};
	
	public LessonView(Context context) {
		super(context);
	}
	
	public LessonView(Context context, Lesson lesson) {
		super(context);
		init(context, lesson);
	}
	
	/**
	 * 初始化信息
	 */
	private void init(Context context, Lesson lesson) {
		this.name = lesson.getName();
		this.classStart = lesson.getClassStart();
		this.classEnd = lesson.getClassEnd();
		this.classRoom = lesson.getClassRoom();
		this.teacher = lesson.getTeacher();
		this.weekStart = lesson.getWeekStart();
		this.weekEnd = lesson.getWeekEnd();
		this.day = lesson.getDay();
		
		this.setClickable(true);
		this.setTextAppearance(context, R.style.LessonViewTextStyle);
		this.setText(name + "@" + classRoom);
		this.setBackgroundResource(bgs[createRamdomInt()]);
		this.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	/**
	 * 产生随机数
	 * @return
	 */
	private int createRamdomInt() {
		int len = bgs.length;
		Random random = new Random();
		return random.nextInt(len);
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 获取开始节课
	 * @return
	 */
	public int getClassStart() {
		return classStart;
	}
	
	/**
	 * 获取结束节课
	 * @return
	 */
	public int getClassEnd() {
		return classEnd;
	}
	
	/**
	 * 获取开始周
	 * @return
	 */
	public int getWeekStart() {
		return weekStart;
	}
	
	/**
	 * 获取结束周
	 * @return
	 */
	public int getWeekEnd() {
		return weekEnd;
	}
	
	public String getClassRoom() {
		return classRoom;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public int getDay() {
		return day;
	}
}
