package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutWithBottomLine extends LinearLayout {

	public LinearLayoutWithBottomLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setWillNotDraw(false);
	}

	public LinearLayoutWithBottomLine(Context context) {
		super(context);
		this.setWillNotDraw(false);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.deep_blue); 
        paint.setColor(color);
        int width = this.getWidth();  
        int height = this.getHeight();
        canvas.drawLine(0, height - 1, width - 1, height - 1, paint);
		super.onDraw(canvas);
	}
}
