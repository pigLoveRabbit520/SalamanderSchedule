package com.attraction.schedule.view;

import com.attraction.schedule.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

public class ClassView extends TextView {
	// 节数
	private int classNum = -1;

	public ClassView(Context context) {
		super(context, null);
		setStyle();
	}


	public ClassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStyle();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MonthView);
		int classNum = array.getInt(R.styleable.ClassView_classNum, -1);
		if(classNum != -1) this.setClassNum(classNum);
		array.recycle();
	}
	
	private void setStyle() {
		float size = getResources().getDimension(R.dimen.month_week_day_view_text_size);
		int color = getResources().getColor(R.color.deep_blue); 
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		this.setTextColor(color);
		this.setGravity(Gravity.CENTER);
	}
	
	public void setClassNum(int classNum) {
		this.classNum = classNum;
		this.setText(String.valueOf(classNum));
	}
	
	public int getClassNum() {
		return this.classNum;
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.deep_blue); 
        paint.setColor(color);
        int width = this.getWidth();  
        int height = this.getHeight();
        // 画底部线框
        canvas.drawLine(0, height - 1, width - 1, height - 1, paint);
		super.onDraw(canvas);
	}
}
