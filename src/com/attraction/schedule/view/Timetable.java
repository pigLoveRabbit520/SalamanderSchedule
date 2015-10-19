package com.attraction.schedule.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.attraction.schedule.R;
import com.attraction.schedule.db.Lesson;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Timetable extends LinearLayout {
	@Bind(R.id.monthView_timetable_month) MonthView monthView;
	@Bind(R.id.sv_timetable) ScrollView svContainer;
	@Bind(R.id.ll_timetable_classLessonContainer) LinearLayout llClassLessonContainer;
	@Bind(R.id.ll_timetable_monthWeekDayViewContainer) LinearLayout llMonthWeekDayViewContainer;
	private LinearLayout llClasses;
	private FrameLayoutWithCross flLessons;
	// 月份的宽度
	private int monthWidth = 0; 
	// 空白块高度(包括节数和课程)
	private int spaceHeight = 0;
	// 课程块的高度
	private int blockHeight = 0;
	// 溢出一天节数
	private static int overClassNum = 10;
	// 默认节数
	private int classNum = 12;
	// 横向显示一周几天
	private static int weekDayNum = 7;
	// 周数对象数组
	private List<MonthWeekDayView> mwdvs = new ArrayList<MonthWeekDayView>();
	
	public Timetable(Context context) {
		super(context, null);
	}

	public Timetable(final Context context, AttributeSet attrs) {
		super(context, attrs);
		LinearLayout view = 
				(LinearLayout)LayoutInflater.from(context).inflate(R.layout.timetable, this, true);
		ButterKnife.bind(this, view);
		
		monthView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				// TODO 自动生成的方法存根
				monthWidth = monthView.getWidth();
				monthView.getViewTreeObserver().removeGlobalOnLayoutListener (this);
			}
		});
		// 除去滚动到顶部的渐变色
		svContainer.setOverScrollMode(View.OVER_SCROLL_NEVER);
		svContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				Timetable.this.spaceHeight = svContainer.getHeight();
				addClasses(context);
				addLessonContainer(context);
				svContainer.getViewTreeObserver().removeGlobalOnLayoutListener (this);
			}
		});
		getMonthWeekDayViews();
		setDateWeek();
	}
	
	/**
	 * 获取周数对象（界面上默认放了7个对象）
	 */
	private void getMonthWeekDayViews() {
		int count = llMonthWeekDayViewContainer.getChildCount();
		for (int i = 0; i < count; i++) {
			MonthWeekDayView mwdv = (MonthWeekDayView)llMonthWeekDayViewContainer.
					getChildAt(i);
			mwdvs.add(mwdv);
		}
	}
	
	/**
	 * 添加一天节数
	 */
	private void addClasses(Context context) {
		int padding = getResources().getDimensionPixelSize(R.dimen.timetable_bottom_class_padding);
		llClasses = new LinearLayoutWithRightLine(context);
		LinearLayout.LayoutParams classParams = 
				new LinearLayout.LayoutParams(monthWidth, LayoutParams.MATCH_PARENT);
		llClasses.setPadding(0, 0, 0, padding);
		llClasses.setOrientation(LinearLayout.VERTICAL);
		llClasses.setLayoutParams(classParams);
		if(classNum >= overClassNum) {
			blockHeight = this.spaceHeight / overClassNum;
		} else {
			blockHeight = this.spaceHeight / classNum;
		}
		for (int i = 0; i < classNum; i++) {
			ClassView cv = new ClassView(context);
			LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, blockHeight);
			cv.setLayoutParams(Params);
			cv.setClassNum(i + 1);
			llClasses.addView(cv);
		}
		this.llClassLessonContainer.addView(llClasses);
	}
	
	/**
	 * 添加课程容器
	 * @param context
	 * @param width
	 * @param height
	 */
	private void addLessonContainer(Context context) {
		// 添加课程容器
		int[] widths = this.getMonthWeekDayViewWidths();
		flLessons = new FrameLayoutWithCross(context, widths, blockHeight, classNum);
		LinearLayout.LayoutParams flParams = 
				new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		flLessons.setLayoutParams(flParams);
		this.llClassLessonContainer.addView(flLessons);
	}
	
	/**
	 * 每个日期周数控件的宽度其实是不一致的
	 * @return
	 */
	private int[] getMonthWeekDayViewWidths() {
		int count = mwdvs.size();
		int[] widths = new int[count];
		for (int i = 0; i < count; i++) {
			widths[i] = mwdvs.get(i).getWidth();
		}
		return widths;
	}
	
	/**
	 * 设置日期和周
	 */
	private void setDateWeek() {
		Calendar calendar = Calendar.getInstance();
        // 0-11
        int month = calendar.get(Calendar.MONTH) + 1;
        // 1-7为一周，星期天为1，转化一下
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        weekDay = weekDay == 1 ? 7 : weekDay - 1;
        int[] arrBase = new int[]{0, 1, 2, 3, 4, 5, 6};
        int minus = 0;
        minus = 1 - weekDay;
        for(int i = 0; i < arrBase.length; i++) {
        	arrBase[i] += minus;
        }
        monthView.setMonth(month);
        setMonthWeekDayEach(arrBase, weekDay);
	}
	
	/**
	 * 依次设置日和周数
	 * @param arr
	 * @param weekDay
	 */
	private void setMonthWeekDayEach(int[] arr, int weekDay) {
		if(arr.length < 7) {
			return;
		}
		for (int i = 0; i < weekDayNum; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, arr[i]);
			MonthWeekDayView mwdv = mwdvs.get(i);
			mwdv.setMonthDay(calendar.get(Calendar.DAY_OF_MONTH));
			mwdv.setWeekDay(arr[i] + weekDay);
		}
	}
	
	/**
	 * 界面上添加课程块
	 * @param lessons
	 */
	public void addLessons(List<Lesson> lessons) {
		flLessons.addLessons(lessons);
	}

}
