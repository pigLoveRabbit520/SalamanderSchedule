package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RelativeLayoutWithBottomLine extends RelativeLayout {

	public RelativeLayoutWithBottomLine(Context context) {
		super(context);
		this.setWillNotDraw(false);
	}

	public RelativeLayoutWithBottomLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setWillNotDraw(false);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.title_bar_bottom_linn_color);
		paint.setColor(color);
		int width = this.getWidth();
		int height = this.getHeight();
		canvas.drawLine(0, height - 1, width - 1, height - 1, paint);
		super.onDraw(canvas);
	}
}
