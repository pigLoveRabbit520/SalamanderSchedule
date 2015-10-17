package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class MonthView extends TextView {
	private int month = 0;
	private int sizeMinus = 7;
	
	public MonthView(Context context) {
		super(context);
	}
	
	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MonthView);
		int month = array.getInt(R.styleable.MonthView_month, 0);
		if(month != 0) this.setMonth(month);
		array.recycle();
	}

	public void setMonth(int month) {
		this.month = month;
		int size = getResources().getDimensionPixelSize(R.dimen.monthWeekDayView_text_size);
		SpannableString msp = new SpannableString(month + "月");
		int length = String.valueOf(month).length();
		msp.setSpan(new AbsoluteSizeSpan(size, false), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(size- sizeMinus, false), length, length + 1, 
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		this.setText(msp);
	}
	
	public int getMonth() {
		return this.month;
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
