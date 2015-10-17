package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.LinearLayout;

public class LinearLayoutWithRightLine extends LinearLayout {

	public LinearLayoutWithRightLine(Context context) {
		super(context);
		this.setWillNotDraw(false);
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
