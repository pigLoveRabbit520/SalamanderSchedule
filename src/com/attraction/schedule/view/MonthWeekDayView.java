package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MonthWeekDayView extends LinearLayout {

	private TextView tvDate = null;
	private TextView tvWeek = null;
	private int monthDay = 0;
	private int weekDay = -1;

	public MonthWeekDayView(Context context) {
		super(context);
		this.setWillNotDraw(false);
		LayoutInflater.from(context).inflate(R.layout.month_week_day_view, this, true);
		tvDate = (TextView)this.findViewById(R.id.monthWeekDay_month);
		tvWeek = (TextView)this.findViewById(R.id.monthWeekDay_week);
	}
	
	public MonthWeekDayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setWillNotDraw(false);
		LayoutInflater.from(context).inflate(R.layout.month_week_day_view, this, true);
		tvDate = (TextView)this.findViewById(R.id.monthWeekDay_month);
		tvWeek = (TextView)this.findViewById(R.id.monthWeekDay_week);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MonthWeekDayView);
		int monthDay = array.getInt(R.styleable.MonthWeekDayView_monthDay, 0);
		int weekDay = array.getInt(R.styleable.MonthWeekDayView_weekDay, -1);
		if(monthDay != 0) {
			this.setMonthDay(monthDay);
		}
		if(weekDay != -1) {
			this.setWeekDay(weekDay);
		}
		array.recycle();
	}
	
	/**
	 * 设置日期
	 * @param monthDay
	 */
	public void setMonthDay(int monthDay) {
		tvDate.setText(String.valueOf(monthDay));
		this.monthDay = monthDay;
	}
	
	/**
	 * 获取日期
	 * @return
	 */
	public int getMonthDay() {
		return monthDay;
	}

	/**
	 * 设置周数
	 * @param weekDay
	 */
	public void setWeekDay(int weekDay) {
		String str = null;
		switch (weekDay) {
		case 1:
			str = "周一";
			break;
		case 2:
			str = "周二";
			break;
		case 3:
			str = "周三";
			break;
		case 4:
			str = "周四";
			break;
		case 5:
			str = "周五";
			break;
		case 6:
			str = "周六";
			break;
		case 7:
			str = "周日";
			break;
		default:
			break;
		}
		tvWeek.setText(str);
		this.weekDay = weekDay;
	}
	
	/**
	 * 获取周数
	 * @return
	 */
	public int getWeekDay() {
		return weekDay;
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.deep_blue); 
        paint.setColor(color);
        int width = this.getWidth();  
        int height = this.getHeight();
        canvas.drawLine(width - 1, 0, width - 1, height - 1, paint);
		super.onDraw(canvas);
	}
}
