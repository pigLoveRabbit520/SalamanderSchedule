package com.attraction.schedule.view;

import com.attraction.schedule.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FrameLayoutWithTopLine extends FrameLayout {

	public FrameLayoutWithTopLine(Context context) {
		super(context);
		this.setWillNotDraw(false);
	}

	public FrameLayoutWithTopLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setWillNotDraw(false);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.tab_bar_item_text_normal_color); 
        paint.setColor(color);
        int width = this.getWidth();
        canvas.drawLine(0, 0, width - 1, 0, paint);
		super.onDraw(canvas);
	}

}
